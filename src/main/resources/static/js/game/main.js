//@author KevinMendieta

import {getAllRooms, getPlayersRoom, enterRoom, deleteRoom, createRoom} from "./api.js";
import {putRooms, putPlayers, putCanvas, putCreateForm} from "./domEditor.js";
import {getStompClient, subscribeTopic} from "./stompHandler.js";

// Game Stuff
import Timer from "./Timer.js";
import {loadLevel} from "./loaders.js";
import {createPlayer, createOnlinePlayer, createBlockController} from "./entities.js";
import {setupKeyboard} from "./input.js";
// import {setupMouseControl} from "./debug.js";

var name = Math.floor((Math.random() * 10) + 1) + "",
	roomId,
	canvas,
	context,
	playersLen,
	deadPlayers,
	stompClient;

function loadRooms() {
	getAllRooms(putRooms);
}

function subscribeEvent() {
	subscribe($("#roomId").val());	
}

function subscribe(id) {
	roomId = id;
	getStompClient()
	.then((stpClient) => {
		stompClient = stpClient;
		subscribeTopic(stompClient, "/topic/start." +  roomId, init);
		subscribeTopic(stompClient, "/topic/players." +  roomId, updateCurrentPlayers);
		subscribeTopic(stompClient, "/topic/winner." +  roomId, endGame);
		subscribeTopic(stompClient, "/topic/dead." + roomId, deadPlayer)
		enterRoom(roomId, {nickName : name});
	});
}

const refreshButton = document.getElementById("refreshButton");
refreshButton.addEventListener("click", loadRooms);

const subscribeButton = document.getElementById("subscribeButton");
subscribeButton.addEventListener("click", subscribeEvent);

const createButton = document.getElementById("createButton");
createButton.addEventListener("click", create);

var timer;

function init(eventMessage) {
	putCanvas(832, 416);
	canvas = document.getElementById("screen");
	context = canvas.getContext("2d");
	Promise.all([
		getPlayersRoom(roomId),
		createPlayer(),
		loadLevel("default"),
		createOnlinePlayer(2),
		createOnlinePlayer(3),
		createOnlinePlayer(4),
		createBlockController()
	])
	.then(([players, player, level, p1, p2, p3, block]) => {
		var startGame = true;
		level.entities.add(player);
		level.entities.add(block);
		level.ply = player;
		level.blockCnt = block;

		playersLen = players.length;
		deadPlayers = 0;
		const onlinePlayers = [p1, p2, p3];
		var index = 0;
		players.forEach((ply) => {
			console.log(ply);
			if (ply.nickName !== name) {
				subscribeTopic(stompClient, "/topic/" +  roomId + "." + ply.nickName, onlinePlayers[index].updateOnline);
				level.entities.add(onlinePlayers[index]);
				index++;
			}
		});
		

		const input = setupKeyboard(player);

		input.listenTo(window);
		// setupMouseControl(canvas, player);

		timer = new Timer(1 / 60);
		timer.update = function update(deltaTime) {
			level.update(deltaTime);
			level.comp.draw(context);
			const data = {x : player.pos.x, y : player.pos.y, anim : player.anim, heading : player.go.heading < 0, lifePoints : player.lifePoints, maxlifePoints : player.maxlifePoints};
			stompClient.send("/topic/" +  roomId + "." + name, {}, JSON.stringify(data));
			if(!level.alivePlayer && startGame){
				startGame = false;
				stompClient.send("/topic/dead." + roomId, {}, JSON.stringify({name: name}));
				input.cleanMapping();
				player.go.dir = 0;
				player.lifePoints = 0;
			}
		};
		timer.start();
		block.createBlocks();
	});
}

function updateCurrentPlayers(eventMessage) {
	getPlayersRoom(roomId)
	.then((players) => putPlayers(players));
}

function endGame(eventMessage) {
	console.log("winner!!!!!!!!!");
	const content = JSON.parse(eventMessage.body);
	timer.stop();
	if (content.name === name) {
		alert("You won!!!");
		deleteRoom(roomId)
		.then(() => location.reload());
	} else {
		alert("Player " + content.name + " has won!");
		location.reload();
	}
}

function deadPlayer(eventMessage) {
	const content = JSON.parse(eventMessage.body);
	deadPlayers++;
	console.log("dead player!!!!!!!!!!!: " + content.name);
	if (deadPlayers == playersLen - 1 && (content.name !== name)) {
		stompClient.send("/topic/winner." + roomId, {}, JSON.stringify({name: name}));
	}
}

function create() {
	putCreateForm();
	const sendRoomButton = document.getElementById("sendRoom");
	sendRoomButton.addEventListener("click", sendRoom);
}

function sendRoom() {
	const room = {id : $("#nroomId").val(), capacity : $("#nroomCap").val(), players : []};
	createRoom(room)
	.then(() => {subscribe(room.id)}, (response) => {alert(response.responseText);});
}

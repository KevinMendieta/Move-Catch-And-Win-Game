//@author KevinMendieta

import {getAllRooms, getPlayersRoom, enterRoom} from "./api.js";
import {putRooms, putPlayers, putCanvas} from "./domEditor.js";
import {getStompClient, subscribeTopic} from "./stompHandler.js";

// Game Stuff
import Timer from "./Timer.js";
import {loadLevel} from "./loaders.js";
import {createPlayer, createOnlinePlayer} from "./entities.js";
import {setupKeyboard} from "./input.js";
// import {setupMouseControl} from "./debug.js";

var name = Math.floor((Math.random() * 10) + 1) + "",
	roomId,
	canvas,
	context,
	stompClient;

function loadRooms() {
	getAllRooms(putRooms);
}

function subscribe() {
	roomId = $("#roomId").val();
	getStompClient()
	.then((stpClient) => {
		stompClient = stpClient;
		subscribeTopic(stompClient, "/topic/start." +  roomId, init);
		subscribeTopic(stompClient, "/topic/players." +  roomId, updateCurrentPlayers);
		subscribeTopic(stompClient, "/topic/winner." +  roomId, endGame);
		enterRoom(roomId, {nickName : name});
	});
}

const refreshButton = document.getElementById("refreshButton");
refreshButton.addEventListener("click", loadRooms);

const subscribeButton = document.getElementById("subscribeButton");
subscribeButton.addEventListener("click", subscribe);

function init(eventMessage) {
	putCanvas(832, 416);
	canvas = document.getElementById("screen");
	context = canvas.getContext("2d");
	Promise.all([
		getPlayersRoom(roomId),
		createPlayer(),
		loadLevel("default"),
		createOnlinePlayer(),
		createOnlinePlayer(),
		createOnlinePlayer()

	])
	.then(([players, player, level, p1, p2, p3]) => {
		level.entities.add(player);

		const onlinePlayers = [p1, p2, p3];
		var index = 0;
		players.forEach((ply) => {
			if (ply.nickName !== name) {
				subscribeTopic(stompClient, "/topic/" +  roomId + "." + ply.nickName, onlinePlayers[index].updateOnline);
				level.entities.add(onlinePlayers[index]);
				index++;
			}
		});
		

		const input = setupKeyboard(player);

		input.listenTo(window);
		// setupMouseControl(canvas, player);

		const timer = new Timer(1 / 60);
		timer.update = function update(deltaTime) {
			level.update(deltaTime);
			level.comp.draw(context);
			const data = {x : player.pos.x, y : player.pos.y, anim:player.anim, heading : player.go.heading < 0};
			stompClient.send("/topic/" +  roomId + "." + name, {}, JSON.stringify(data));
		};
		timer.start();
	});
}

function updateCurrentPlayers(eventMessage) {
	getPlayersRoom(roomId, putPlayers);
}

function endGame(eventMessage) {
	//console.log(eventMessage);
}

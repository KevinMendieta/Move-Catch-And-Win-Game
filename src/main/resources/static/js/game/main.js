//@author KevinMendieta
// Principal controller for the game.
import {getAllRooms, getPlayersRoom, enterRoom} from "./api.js";
import {putRooms, putPlayers} from "./domEditor.js";
import {getStompClient, subscribeTopic} from "./stompHandler.js";

const refreshButton = document.getElementById("refreshButton");
refreshButton.addEventListener("click", loadRooms);

const subscribeButton = document.getElementById("subscribeButton");
subscribeButton.addEventListener("click", subscribe);

var name = Math.floor((Math.random() * 10) + 1) + "",
	roomId,
	canvas,
	ctx;

function init(eventMessage) {
	
}

function loadRooms() {
	getAllRooms(putRooms);
}

function subscribe() {
	roomId = $("#roomId").val();
	getStompClient()
	.then((stompClient) => {
		subscribeTopic(stompClient, "/topic/start." +  roomId, init);
		subscribeTopic(stompClient, "/topic/players." +  roomId, updateCurrentPlayers);
		subscribeTopic(stompClient, "/topic/winner." +  roomId, endGame);
		enterRoom(roomId, {nickName : name});
	});
}

function updateCurrentPlayers(eventMessage) {
	getPlayersRoom(roomId, putPlayers);
}

function endGame(eventMessage) {
	console.log(eventMessage);
}

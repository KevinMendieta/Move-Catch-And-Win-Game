/* global putRooms, subscribePlayers */

//@author KevinMendieta
// Principal controller for the game.

var name = Math.floor((Math.random() * 10) + 1) + "",
	roomId,
	canvas,
	ctx;
function loadRooms() {
	getAllRooms(putRooms);
}

function startEvent() {
	getPlayersRoom(subscribePlayers);
}

function init() {
	putCanvas(540, 405);
	canvas = document.getElementById("screen");
	ctx = canvas.getContext("2d");
}

function connectButton(id) {
	roomId = id;
	subscribeRoom();
}

function endGame() {

}

function movePlayer(eventBody) {

}
/* global putRooms, subscribePlayers */

//@author KevinMendieta
// Principal controller for the game.

var name = Math.floor((Math.random() * 10) + 1) + "",
	roomId;
function loadRooms() {
	getAllRooms(putRooms);
}

function startEvent() {
	getPlayersRoom(subscribePlayers);
}

function init() {
	putCanvas(540, 405);
}

function connectButton(id) {
	roomId = id;
	subscribeRoom();
}

function endGame() {

}

function movePlayer(eventBody) {

}
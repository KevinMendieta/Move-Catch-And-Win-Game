//@author KevinMendieta
// Handler of subscriptions and messages.
/* global Stomp, roomId, endGame, startEvent, putPlayers, movePlayer */

var stompClient;

function subscribeRoom() {
	var socket = new SockJS("/stompendpoint");
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		stompClient.subscribe("/topic/start." +  roomId, startEvent);
		stompClient.subscribe("/topic/winner." +  roomId, endGame);
		let player = {"nickName": name};
		connectRoom(player, putPlayers);
	});
}

function subscribePlayers(players) {
	var socket = new SockJS("/stompendpoint");
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		for (let i = 0; i < players.length; i++) {
			if (players[i].nickName !== name) {
				stompClient.subscribe("/topic/room" + roomId + "." + players[i].nickName, movePlayer);
			}
		}
		init();
	});
}

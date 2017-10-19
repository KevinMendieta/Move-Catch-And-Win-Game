//@author KevinMendieta

var stompClient;

function putRooms(rooms) {
	rooms.map(function(room) {
		let content = "<li> id: " + room.id + " current players: " + room.players.length + "</li>";
		$("#roomInfo").append(content);
	});
}

function loadRooms() {
	getAllRooms(putRooms);
}

function putPlayers(players) {
	$("#gameInfo").find("li").remove();
	players.map(function(player) {
		let content = "<li>" + player.nickName + ".</li>";
		$("#gameInfo").append(content);
	});
}

function init(eventBody) {
	console.log(eventBody.body);
}

function subscribeRoom(id) {
	var socket = new SockJS("/stompendpoint");
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		stompClient.subscribe("/topic/start." +  id, init);
		let player = {"nickName":"Kevin"};
		connectRoom(id, player, putPlayers);
	});
}

function connectButton(id) {
	subscribeRoom(id);
}
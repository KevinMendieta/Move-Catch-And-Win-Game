//@author KevinMendieta

function putRooms(rooms) {
	rooms.map(function(room) {
		let content = '<li> id: ' + room.id + ' current players: ' + room.players.length + '</li>';
		$('#roomInfo').append(content);
	});
}

function loadRooms() {
	getAllRooms(putRooms);
}

function putPlayers(players) {
	players.map(function(player) {
		let content = '<li>' + player.nickName + '</li>';
		$('#gameInfo').append(content);
	});
}

function connectButton(id) {
	let player = {"nickName":"Kevin","email":"kevin@mail.com", "password":"145"};
	connectRoom(id, player, putPlayers);
}
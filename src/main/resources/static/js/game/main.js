//@author KevinMendieta

function putRooms(rooms) {
	rooms.map(function(room) {
		let content = '<li> id: ' + room.id + ' current player: ' + room.players.length + '</li>';
		$('#roomInfo').append(content);
	});
}

function loadRooms() {
	getAllRooms(putRooms);
}
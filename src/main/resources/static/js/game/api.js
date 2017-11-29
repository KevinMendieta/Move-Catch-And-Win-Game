//@author KevinMendieta
//Handler of the RESTFul API

export function getAllRooms(callback) {
	$.get("/rooms", callback);
}

export function getPlayersRoom(roomId) {
	return $.get("/rooms/" + roomId + "/players");
}

export function enterRoom(roomId, player) {
	$.ajax({
		url : "/rooms/" + roomId + "/players",
		type : "POST",
		data : JSON.stringify(player),
		contentType: "application/json"
	}).then(
		function(){},
		function(response){
			alert(response.responseText);
		}
	);
}

export function createRoom(room) {
	return $.ajax({
		url : "/rooms",
		type : "POST",
		data : JSON.stringify(room),
		contentType: "application/json"
	});
}

export function deleteRoom(roomId) {
	return $.ajax({
		url : "/rooms/" + roomId,
		type : "DELETE"
	});
}
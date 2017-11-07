//@author KevinMendieta
//Handler of the RESTFul API

export function getAllRooms(callback) {
	$.get("/rooms", callback);
}

export function getPlayersRoom(roomId, callback) {
	$.get("/rooms/" + roomId + "/players", callback);
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
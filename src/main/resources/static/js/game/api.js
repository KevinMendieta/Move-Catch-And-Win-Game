//@author KevinMendieta
//Handler of the RESTFul API

function getAllRooms(callback) {
	$.get("/rooms", callback);
}

function getPlayersRoom(callback) {
	$.get("/rooms/" + roomId + "/players", callback);
}

function connectRoom(player, callback) {
	$.ajax({
		url : "/rooms/" + roomId + "/players",
		type : "POST",
		data : JSON.stringify(player),
		contentType: "application/json"
	}).then(
		function(){
			$.get("/rooms/" + roomId + "/players", callback);
		},
		function(response){
			alert(response.responseText);
		}
	);
}
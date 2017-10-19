//@author KevinMendieta

function getAllRooms(callback) {
	$.get("/rooms", callback);
}

function connectRoom(id, player, callback) {
	$.ajax({
		url : "/rooms/" + id + "/players",
		type : 'POST',
		data : JSON.stringify(player),
		contentType: "application/json"
	}).then(
		function(){
			$.get("/rooms/" + id + "/players", callback);
		},
		function(response){
			alert(response.responseText);
		}
	);
}
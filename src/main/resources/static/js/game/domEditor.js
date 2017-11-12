//@author KevinMendieta
// Editor of the DOM

export function putRooms(rooms) {
	$("#roomInfo").empty();
	rooms.forEach((room) => {
		let content = "<li> id: " + room.id + ", current players: " + room.players.length + ", capacity: " + room.capacity + "</li>";
		$("#roomInfo").append(content);
	});
}

export function putPlayers(players) {
	$("#gameInfo").find("li").remove();
	players.map(function(player) {
		let content = "<li>" + player.nickName + ".</li>";
		$("#gameInfo").append(content);
	});
}

export function putCanvas(width, height) {
	var content = "<canvas id=" + str("screen") + " width=" + str(width) + 
	" height=" + str(height) + "></canvas>";
	$("#gameCanvas").append(content);
}

function str(token) {
	return '"' + token + '"'
}

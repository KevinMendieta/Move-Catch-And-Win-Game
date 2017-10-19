//@author KevinMendieta
// Editor of the DOM

function putRooms(rooms) {
	rooms.map(function(room) {
		let content = "<li> id: " + room.id + " current players: " + room.players.length + "</li>";
		$("#roomInfo").append(content);
	});
}

function putPlayers(players) {
	$("#gameInfo").find("li").remove();
	players.map(function(player) {
		let content = "<li>" + player.nickName + ".</li>";
		$("#gameInfo").append(content);
	});
}

function putCanvas(width, height) {
	var content = "<canvas id=" + '"myCanvas"' 
	+ ' width="' + width + '" height="' + height + 
	'" style=' + '"border:1px solid #d3d3d3;">';
	console.log(content);
	$("#gameCanvas").append(content);
}

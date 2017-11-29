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

export function putCreateForm(){
	const content = "<h3> Create a new Room: </h3>"  
	+ "<input id=" + str("nroomId") + " type=" + str("text") + " placeholder=" + str("Room Id") + "/>" 
	+ "<input id=" + str("nroomCap") + " type=" + str("text") + " placeholder=" + str("Capacity") + "/>" 
	+ "<a id=" + str("sendRoom") + " class=" + str("button fit") + ">" + "Create</a>";
	return $("#createForm").append(content);
} 

function str(token) {
	return '"' + token + '"'
}

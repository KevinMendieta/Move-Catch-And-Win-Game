function getAllRooms(callback) {
	$.get("/rooms", callback);

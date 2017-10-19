//@author KevinMendieta

function getAllRooms(callback) {
	$.get("/rooms", callback);
}
//@author KevinMendieta
// Handler of subscriptions and messages.

export function getStompClient() {
	return new Promise((resolve) => {
		const socket = new SockJS("/stompendpoint");
		const stompClient = Stomp.over(socket);
		stompClient.connect({}, function(frame) {
			resolve(stompClient);
		});
	});
}

export function subscribeTopic(stompClient, topicPrefix, callback) {
	stompClient.subscribe(topicPrefix, callback);
}

function subscribeToAllPlayers(players, callback) {
	var socket = new SockJS("/stompendpoint");
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		
	});
}

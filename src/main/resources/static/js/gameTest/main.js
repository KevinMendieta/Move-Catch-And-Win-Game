import Timer from "./Timer.js";
import {loadLevel} from "./loaders.js";
import {createPlayer} from "./entities.js";
import {createCoillisionLayer} from "./layers.js";
import {setupKeyboard} from "./input.js";

const canvas = document.getElementById("screen"),
	context = canvas.getContext("2d");

Promise.all([
	createPlayer(),
	loadLevel("default")
])
.then(([player, level]) => {
	player.pos.set(45, 45);

	level.entities.add(player);

	const input = setupKeyboard(player);

	input.listenTo(window);

	["mousedown", "mousemove"].forEach(eventName => {
		canvas.addEventListener(eventName, event =>{
			if (event.buttons === 1) {
				player.vel.set(0, 0);
				player.pos.set(event.offsetX, event.offsetY);
			}
		});
	});

	const timer = new Timer(1 / 60);
	timer.update = function update(deltaTime) {
		level.update(deltaTime);
		level.comp.draw(context);
	}
	timer.start();
});
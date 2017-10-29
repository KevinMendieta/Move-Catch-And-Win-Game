import Timer from "./Timer.js";
import {loadLevel} from "./loaders.js";
import {createPlayer} from "./entities.js";
import {setupKeyboard} from "./input.js";
// import {setupMouseControl} from "./debug.js";

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

	// setupMouseControl(canvas, player);

	const timer = new Timer(1 / 60);
	timer.update = function update(deltaTime) {
		level.update(deltaTime);
		level.comp.draw(context);
	};
	timer.start();
});
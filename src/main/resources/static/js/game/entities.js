import Entity, {Trait} from "./Entity.js";
import Go from "./traits/Go.js";
import Jump from "./traits/Jump.js";
import Life from "./traits/Life.js";
import {loadSpriteSheet} from "./loaders.js";
import {createAnimation} from "./animation.js";

export function createPlayer() {
	return loadSpriteSheet("player1")
	.then((sprites) => {
		const player = new Entity();
		player.name = "local";
		player.pos.set(45, 45);
		player.size.set(12, 16);
		player.vel.set(0, 0);
		player.maxlifePoints = 700;
		player.lifePoints = player.maxlifePoints;
		player.addTrait(new Go());
		player.addTrait(new Jump());
		player.addTrait(new Life());


		const  runAnimation = createAnimation(["run_1", "run_2"], 10);
		function routeFrame(player) {
			var animation = "quiet";
			if (!player.jump.ready) {
				animation = "jump";
			} else if (player.go.dir !== 0) {
				animation = runAnimation(player.go.distance);
			}
			player.anim = animation;
			return animation;
		}

		player.draw = function drawPlayer(context) {
			sprites.draw(routeFrame(this), context, this.pos.x, this.pos.y, this.go.heading < 0);
			const px = 32;
			const py = 400;
			const dx = Math.floor((this.lifePoints * 160) / this.maxlifePoints);
			sprites.drawLine(context, px, py, px + dx, py);
		};

		return player;
	});
}

export function createOnlinePlayer(number) {
	return loadSpriteSheet("player" + number)
	.then((sprites) => {
		const player = new Entity();
		player.name = "online";
		player.pos.set(45, 45);
		player.anim = "quiet";
		player.heading = false;

		player.update = function updateProxy(deltaTime) {};

		player.updateOnline = function updateOnlinePlayer(eventMessage) {
			const content = JSON.parse(eventMessage.body);
			player.pos.set(content.x, content.y);
			player.anim = content.anim;
			player.heading = content.heading;
			player.lifePoints = content.lifePoints;
			player.maxlifePoints = content.maxlifePoints;
		};

		player.draw = function drawPlayer(context) {
			sprites.draw(this.anim, context, this.pos.x, this.pos.y, this.heading);
			const px = (32 * ((number - 1) * 6)) + 32;
			const py = 400;
			const dx = Math.floor((this.lifePoints * 160) / this.maxlifePoints);
			sprites.drawLine(context, px, py, px + dx, py);
		};

		return player;
	});
}
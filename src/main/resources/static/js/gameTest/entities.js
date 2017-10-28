import Entity, {Trait} from "./Entity.js";
import Go from "./traits/Go.js";
import Velocity from "./traits/Velocity.js";
import Jump from "./traits/Jump.js";
import {loadSprites} from "./resources.js";

export function createPlayer() {
	return loadSprites()
	.then((sprites) => {
		const player = new Entity();
		player.size.set(12, 16);
		player.vel.set(0, 0);

		player.addTrait(new Go());
		player.addTrait(new Jump());
		//player.addTrait(new Velocity());

		player.draw = function drawPlayer(context) {
			sprites.draw("firstPlayer", context, this.pos.x, this.pos.y);
		};
		return player;
	});
}

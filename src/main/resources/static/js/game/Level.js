import Compositor from "./Compositor.js";
import TileCollider from "./TileCollider.js";
import {Matrix} from "./math.js";


export default class Level {

	constructor() {
		this.gravity = 2000;
		this.totalTime = 0;

		this.comp = new Compositor();
		this.entities = new Set();
		this.tiles = new Matrix();

		this.tileCollider = new TileCollider(this.tiles);
	}


	update(deltaTime) {
		this.entities.forEach((entity) => {
			if (entity.name === "local") {
				entity.update(deltaTime);
			

				entity.pos.x += entity.vel.x * deltaTime;
				this.tileCollider.checkX(entity);

				entity.pos.y += entity.vel.y * deltaTime;
				this.tileCollider.checkY(entity);

				entity.vel.y += this.gravity * deltaTime;

				this.alivePlayer = entity.lifePoints > 0;
			}
		});
		this.totalTime += deltaTime;
	}

}

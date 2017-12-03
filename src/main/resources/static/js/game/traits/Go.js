import {Trait} from "../Entity.js";

export default class Go extends Trait {

	constructor() {
		super("go");
		this.dir = 0;
		this.speed = 6000;

		this.distance = 0;
		this.heading = 1;
	}

	update(entity, deltaTime) {
		entity.vel.x = this.speed * this.dir * deltaTime;
		//console.log(entity.lifePoints);
		if (this.dir) {
			entity.life.increase(entity);
			this.heading = this.dir;
			this.distance += Math.abs(entity.vel.x)  * deltaTime;
		} else {
			entity.life.decrease(entity);
			this.distance = 0;
		}
		
	}

}

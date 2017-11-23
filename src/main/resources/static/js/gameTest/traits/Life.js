import {Trait} from "../Entity.js";

export default class Life extends Trait {

	constructor() {
		super("life");
		this.maxLife = 1000;
		this.decreaseDelta = -15;
		this.increaseDelta = 5;
	}

	update(entity, deltaTime) {}

	increase(entity) {
		entity.lifePoints += entity.lifePoints + this.increaseDelta <= this.maxLife ? this.increaseDelta : 0;
	}

	decrease(entity) {
		entity.lifePoints += this.decreaseDelta;
	}

}
import {Trait} from "../Entity.js";

export default class Life extends Trait {

	constructor() {
		super("life");
		this.decreaseDelta = -5;
		this.increaseDelta = 5;
	}

	update(entity, deltaTime) {}

	increase(entity) {
		entity.lifePoints += entity.lifePoints + this.increaseDelta <= entity.maxlifePoints ? this.increaseDelta : 0;
	}

	decrease(entity) {
		entity.lifePoints += entity.lifePoints + this.decreaseDelta >= 0 ? this.decreaseDelta : 0;
	}

}
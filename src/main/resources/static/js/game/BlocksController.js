import Block from "./Block.js";

export default class BlocksController {

	constructor(seed, width = 832, height = 416, maxBlocks = 39) {
		this.gravity = 5;
		this.generator = new alea(seed);
		this.blocks = [];
		this.maxBlocks = maxBlocks;
		this.width = width;
		this.height = height;
	}

	getRandomPos() {
		const rand = this.generator.int32();
		return Math.floor((rand % this.width) / 32) * 32;
	}

	createBlocks() {
		const delta = this.maxBlocks / 13;
		let distance = 1;
		for (let i = 0; i < this.maxBlocks; i++) {
			this.blocks.push(new Block());
		}
		for (let i = 0; i < this.maxBlocks; i += delta) {
			for(let j = i; j < i + delta; j++) {
				this.blocks[j].pos.x = this.getRandomPos();
				this.blocks[j].pos.y = distance * -32;
			}
			distance++;
		}
	}

	resetBlock(block) {
		block.pos.x = this.getRandomPos();
		block.pos.y = -32;
	}

	update() {
		this.blocks.forEach((block) => {
			block.pos.y += this.gravity;
			if (block.pos.y > this.height) {
				this.resetBlock(block);
			}
		});
	}
}
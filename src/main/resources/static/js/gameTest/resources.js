import SpriteSheet from "./SpriteSheet.js";
import {loadImage} from "./loaders.js";

export function loadSprites() {
	return loadImage("/img/sprites.png")
	.then((image) => {
		const sprites = new SpriteSheet(image, 12, 16);
		sprites.defineTile("firstPlayer", 0, 0);
		return sprites;
	});
}

export function loadTiles() {
	return loadImage("/img/tiles.png")
	.then((image) => {
		const tiles = new SpriteSheet(image, 32, 32);
		tiles.defineTile("wall", 0, 0);
		tiles.defineTile("block", 1, 0);
		tiles.defineTile("air", 2, 0);
		return tiles;
	});
}

export function loadBackground() {
	return loadImage("/img/background_1.png");
}

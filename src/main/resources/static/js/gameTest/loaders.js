import Level from "./Level.js";
import {createBackgroundLayer, createSpriteLayer, createCoillisionLayer} from "./layers.js";
import {loadTiles, loadBackground} from "./resources.js";

export function loadImage(url) {
	return new Promise(resolve => {
		const image = new Image();
		image.addEventListener("load", () =>{
			resolve(image);
		});
		image.src = url;
	}) ;
}

function createTiles(level, backgrounds) {
	backgrounds.forEach(background => {
		background.ranges.forEach(([x1, x2, y1, y2]) => {
			for (let x = x1; x < x2; ++x) {
	            for (let y = y1; y < y2; ++y) {
	                level.tiles.set(x, y, {
	                	name : background.name
	                });
	            }
	        }
		});
	});
	
}

export function loadLevel(name) {
	return Promise.all([
		loadTiles(),
		fetch(`/levels/${name}.json`).then(r => r.json()),
		loadBackground()
	])
	.then(([tiles, levelSpec, background]) => {
		const level = new Level();

		createTiles(level, levelSpec.backgrounds);

		const backgroundLayer = createBackgroundLayer(background, level, tiles);
		level.comp.layers.push(backgroundLayer);

		const spriteLayer = createSpriteLayer(level.entities);
		level.comp.layers.push(spriteLayer);

		// Debugging layers enable only if necesary!!!!
		level.comp.layers.push(createCoillisionLayer(level));

		return level;
	});
}
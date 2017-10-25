export function createBackgroundLayer(background, level, tiles) {
	const buffer = document.createElement("canvas"),
		context = buffer.getContext("2d");
	buffer.width = 832;
	buffer.height = 416;
	context.drawImage(background,0 ,0);

	level.tiles.forEach((tile, x, y) => {
		tiles.drawTile(tile.name, context, x, y);
	});

	return function drawBackgroundLayer(context) {
		context.drawImage(buffer, 0, 0);
	};
}

export function createSpriteLayer(entities) {
	return function drawSpriteLayer(context) {
		entities.forEach(entity => {
			entity.draw(context);
		});
	};
}

export function createCoillisionLayer(level) {
	const resolvedTiles = []

	const tileResolver = level.tileCollider.tiles;
	const tileSize = tileResolver.tileSize;

	const getByIndexOriginal = tileResolver.getByIndex;
	tileResolver.getByIndex = function getByIndexFake(x, y) {
		resolvedTiles.push({x, y});
		return getByIndexOriginal.call(tileResolver, x, y);
	}

	return function drawCollision(context) {
		context.strokeStyle = "blue";
		resolvedTiles.forEach(({x, y}) => {
			context.beginPath();
			context.rect(x * tileSize, y * tileSize, tileSize, tileSize);
			context.stroke();
		});
		context.strokeStyle = "red";
		level.entities.forEach(entity => {
			context.beginPath();
			context.rect(entity.pos.x, entity.pos.y, entity.size.x, entity.size.y);
			context.stroke();
		});

		resolvedTiles.length = 0;
	};
}

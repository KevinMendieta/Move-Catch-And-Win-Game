export class Matrix {

	constructor() {
		this.grid = [];
	}

	forEach(callback) {
		this.grid.forEach((colum, x) => {
			colum.forEach((tile, y) => {
				callback(tile, x, y);
			});
		});
	}

	get(x, y) {
		return this.grid[x] ? this.grid[x][y] : undefined;
	}

	set(x, y, value) {
		if (!this.grid[x]) {
			this.grid[x] = [];
		}
		this.grid[x][y] = value;
	}

}

export class Vector {
	constructor(x, y) {
		this.set(x, y);
	}

	set(x, y) {
		this.x = x;
		this.y = y;
	}
}

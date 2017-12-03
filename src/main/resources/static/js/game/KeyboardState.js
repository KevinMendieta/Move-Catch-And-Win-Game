const PRESSED = 1,
	RELEASED = 0;

export default class KeyboardState {

	constructor() {
		// State of the pressed keys.
		this.keyStates = new Map();
		// Callbacks when key is pressed.
		this.keyMap = new Map();
	}

	addMapping(code, callback) {
		this.keyMap.set(code, callback);
	}

	handleEvent(event) {
		const {code} = event;
		if (!this.keyMap.has(code)) {
			// No mapped key
			return false;
		}
		event.preventDefault();
		const keyState = event.type === "keydown" ? PRESSED : RELEASED;
		if (this.keyStates.get(code) === keyState) {
			return;
		}
		this.keyStates.set(code, keyState);
		this.keyMap.get(code)(keyState);
	}

	listenTo(window) {
		(["keydown", "keyup"]).forEach((eventName) => {
			window.addEventListener(eventName, (event) => {
				this.handleEvent(event);
			});
		});		
	}

	cleanMapping() {
		this.keyMap.clear();
		this.keyStates.clear();
	}

}
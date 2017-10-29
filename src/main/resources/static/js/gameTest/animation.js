export function createAnimation(frames, frameLen) {
	return function resolveFrame(distance) {
		const index = Math.floor(distance / frameLen % frames.length);
		const animation = frames[index];
		return animation;	
	};
}
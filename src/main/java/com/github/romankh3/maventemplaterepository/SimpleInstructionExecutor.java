package com.github.romankh3.maventemplaterepository;

public class SimpleInstructionExecutor implements InstructionExecutor {

	@Override
	public void execute(Lawnmower lawnmower, String instructions) {
		Position position = lawnmower.getPosition();

		for (char instruction : instructions.toCharArray()) {
			switch (instruction) {
			case 'G':
				turnLeft(position);
				break;
			case 'D':
				turnRight(position);
				break;
			case 'A':
				moveForward(position, lawnmower.getLawn());
				break;
			}
		}

	}

	private void turnLeft(Position position) {
		switch (position.getOrientation()) {
		case 'N':
			position.setOrientation('W');
			break;
		case 'W':
			position.setOrientation('S');
			break;
		case 'S':
			position.setOrientation('E');
			break;
		case 'E':
			position.setOrientation('N');
			break;
		}
	}

	private void turnRight(Position position) {
		switch (position.getOrientation()) {
		case 'N':
			position.setOrientation('E');
			break;
		case 'E':
			position.setOrientation('S');
			break;
		case 'S':
			position.setOrientation('W');
			break;
		case 'W':
			position.setOrientation('N');
			break;
		}
	}

	private void moveForward(Position position, Lawn lawn) {
		int x = position.getX();
		int y = position.getY();
		switch (position.getOrientation()) {
		case 'N':
			y++;
			break;
		case 'E':
			x++;
			break;
		case 'S':
			y--;
			break;
		case 'W':
			x--;
			break;
		}
		if (lawn.isWithinBounds(x, y)) {
			position.setX(x);
			position.setY(y);
		}
	}
}
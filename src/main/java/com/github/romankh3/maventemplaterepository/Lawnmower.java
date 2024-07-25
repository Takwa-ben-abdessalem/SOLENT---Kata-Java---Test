package com.github.romankh3.maventemplaterepository;

public class Lawnmower {
	private final Position position;
	private final Lawn lawn;

	public Lawn getLawn() {
		return lawn;
	}

	public Lawnmower(Position position, Lawn lawn) {
		this.position = position;
		this.lawn = lawn;
	}

	public Position getPosition() {
		return position;
	}

	public void executeInstructions(String instructions, InstructionExecutor executor) {
		executor.execute(this, instructions);
	}
}

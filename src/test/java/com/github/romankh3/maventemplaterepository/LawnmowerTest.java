package com.github.romankh3.maventemplaterepository;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LawnmowerTest {

	@Test
	void testTondeuseMovement() {
		Lawn lawn = new Lawn(5, 5);
		Position position = new Position(1, 2, 'N');
		Lawnmower lawnmower = new Lawnmower(position, lawn);
		InstructionExecutor executor = new SimpleInstructionExecutor();

		lawnmower.executeInstructions("GAGAGAGAA", executor);
		assertEquals("1 3 N", lawnmower.getPosition().toString());

		position = new Position(3, 3, 'E');
		lawnmower = new Lawnmower(position, lawn);
		lawnmower.executeInstructions("AADAADADDA", executor);
		assertEquals("5 1 E", lawnmower.getPosition().toString());
	}

	@Test
	void testInitialPosition() {
		Lawn lawn = new Lawn(5, 5);
		Position position = new Position(3, 3, 'S');
		Lawnmower lawnmower = new Lawnmower(position, lawn);
		InstructionExecutor executor = new SimpleInstructionExecutor();

		lawnmower.executeInstructions("", executor);
		assertEquals("3 3 S", lawnmower.getPosition().toString());
	}

	@Test
	void testBoundaryMovement() {
		Lawn lawn = new Lawn(5, 5);
		Position position = new Position(0, 0, 'W');
		Lawnmower lawnmower = new Lawnmower(position, lawn);
		InstructionExecutor executor = new SimpleInstructionExecutor();

		lawnmower.executeInstructions("A", executor);
		assertEquals("0 0 W", lawnmower.getPosition().toString()); // Should not move beyond boundaries

		position = new Position(5, 5, 'E');
		lawnmower = new Lawnmower(position, lawn);
		lawnmower.executeInstructions("AAAAA", executor);
		assertEquals("5 5 E", lawnmower.getPosition().toString()); // Should not move beyond boundaries
	}

	@Test
	void testInvalidInstructions() {
		Lawn lawn = new Lawn(5, 5);
		Position position = new Position(2, 2, 'N');
		Lawnmower lawnmower = new Lawnmower(position, lawn);
		InstructionExecutor executor = new SimpleInstructionExecutor();

		lawnmower.executeInstructions("XYZ", executor); // Invalid instructions
		assertEquals("2 2 N", lawnmower.getPosition().toString()); // Should remain in the initial position
	}

	@Test
	void testEdgeCaseNoMovement() {
		Lawn lawn = new Lawn(5, 5);
		Position position = new Position(4, 4, 'E');
		Lawnmower lawnmower = new Lawnmower(position, lawn);
		InstructionExecutor executor = new SimpleInstructionExecutor();

		lawnmower.executeInstructions("", executor); // No movement instructions
		assertEquals("4 4 E", lawnmower.getPosition().toString());
	}

	@Test
	void testLargeInput() {
		Lawn lawn = new Lawn(10, 10);
		Position position = new Position(0, 0, 'N');
		Lawnmower lawnmower = new Lawnmower(position, lawn);
		InstructionExecutor executor = new SimpleInstructionExecutor();

		// Generate a large number of instructions
		StringBuilder instructions = new StringBuilder();
		for (int i = 0; i < 1000; i++) {
			instructions.append("A");
		}

		lawnmower.executeInstructions(instructions.toString(), executor);
		assertEquals("0 10 N", lawnmower.getPosition().toString()); // Should correctly handle large input
	}
}

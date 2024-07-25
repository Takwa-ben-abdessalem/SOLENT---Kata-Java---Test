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
}

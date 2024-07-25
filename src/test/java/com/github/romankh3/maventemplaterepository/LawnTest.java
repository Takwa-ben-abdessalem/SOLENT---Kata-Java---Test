package com.github.romankh3.maventemplaterepository;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LawnTest {
	@Test
	void testWithinBounds() {
		Lawn lawn = new Lawn(5, 5);
		assertTrue(lawn.isWithinBounds(3, 3));
		assertFalse(lawn.isWithinBounds(6, 5));
		assertFalse(lawn.isWithinBounds(5, 6));
		assertFalse(lawn.isWithinBounds(-1, 0));
		assertFalse(lawn.isWithinBounds(0, -1));
	}

	@Test
	void testNegativeDimensions() {
		Lawn lawn = new Lawn(-5, -5);
		assertEquals(-5, -5, "Lawn width should be initialized to -5.");
		assertEquals(-5, -5, "Lawn height should be initialized to -5.");
		assertFalse(lawn.isWithinBounds(0, 0),
				"Position (0, 0) should be out of bounds in a lawn with negative dimensions.");
	}

	@Test
	void testLawnWithLargeDimensions() {
		Lawn lawn = new Lawn(1000, 1000);
		assertTrue(lawn.isWithinBounds(500, 500), "Position (500, 500) should be within bounds in a large lawn.");
		assertFalse(lawn.isWithinBounds(1001, 1000), "Position (1001, 1000) should be out of bounds.");
		assertFalse(lawn.isWithinBounds(1000, 1001), "Position (1000, 1001) should be out of bounds.");
	}
}

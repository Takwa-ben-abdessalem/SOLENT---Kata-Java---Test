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
}

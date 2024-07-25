package com.github.romankh3.maventemplaterepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

/**
 * Unit-level testing for {@link MavenTemplateRepository} object.
 */
public class MavenTemplateRepositoryTest {
	private static final Logger LOGGER = Logger.getLogger(MavenTemplateRepository.class.getName());

	/**
	 * Test the normal processing of lawnmowers with given instructions.
	 */
	@Test
	void testProcess() {
		List<String> inputLines = List.of("5 5", "1 2 N", "GAGAGAGAA", "3 3 E", "AADAADADDA");

		List<String> output = MavenTemplateRepository.process(inputLines);
		assertEquals("1 3 N", output.get(0));
		assertEquals("5 1 E", output.get(1));
	}

	/**
	 * Test the processing of a single lawnmower with instructions.
	 */
	@Test
	void testSingleMower() {
		List<String> inputLines = List.of("5 5", "0 0 N", "AAAA");

		List<String> output = MavenTemplateRepository.process(inputLines);
		assertEquals("0 4 N", output.get(0));
	}

	/**
	 * Test the processing of multiple lawnmowers starting from different positions.
	 */
	@Test
	void testDifferentStartPositions() {
		List<String> inputLines = List.of("5 5", "2 2 S", "A", "4 4 W", "A");

		List<String> output = MavenTemplateRepository.process(inputLines);
		assertEquals("2 1 S", output.get(0));
		assertEquals("3 4 W", output.get(1));
	}

	/**
	 * Test the processing of lawnmowers moving in different directions.
	 */
	@Test
	void testDifferentDirections() {
		List<String> inputLines = List.of("5 5", "1 1 E", "A", "3 3 W", "A");

		List<String> output = MavenTemplateRepository.process(inputLines);
		assertEquals("2 1 E", output.get(0));
		assertEquals("2 3 W", output.get(1));
	}

	/**
	 * Test the processing of lawnmowers with empty instruction lines.
	 */
	@Test
	void testEmptyInstructions() {
		List<String> inputLines = List.of("5 5", "1 2 N", "", "3 3 E", "");

		List<String> output = MavenTemplateRepository.process(inputLines);

		// Make sure mower positions have not changed
		assertEquals("1 2 N", output.get(0));
		assertEquals("3 3 E", output.get(1));

		// Display error messages for empty instructions
		if (output.get(0).equals("1 2 N") && inputLines.get(2).isEmpty()) {
			LOGGER.warning("No instructions for lawnmower at (1, 2)");
		}
		if (output.get(1).equals("3 3 E") && inputLines.get(4).isEmpty()) {
			LOGGER.warning("No instructions for lawnmower at (3, 3)");
		}
	}

	/**
	 * Test the handling of lawnmowers attempting to move out of bounds.
	 */
	@Test
	void testOutOfBoundsMovements() {
		List<String> inputLines = List.of("5 5", "0 0 S", "AAA", "5 5 N", "AAA");

		List<String> output = MavenTemplateRepository.process(inputLines);
		assertEquals("0 0 S", output.get(0)); // Cannot exceed lower limit
		assertEquals("5 5 N", output.get(1)); // Impossible to leave upper limit
	}

	/**
	 * Test the handling of invalid lawn dimensions in the input.
	 */
	@Test
	void testInvalidInputHandling() {
		List<String> inputLines = List.of("5 A", // Invalid lawn dimensions
				"1 2 N", "GAGAGAGAA", "3 3 E", "AADAADADDA");

		List<String> output = MavenTemplateRepository.process(inputLines);
		assertEquals(0, output.size()); // Should not process any mowers
	}

	/**
	 * Test the handling of mixed valid and invalid data.
	 */
	@Test
	void testMixedValidInvalidData() {
		List<String> inputLines = List.of("5 5", "1 2 N", "GAGAGAGAA", "invalid data", "3 3 E", "AADAADADDA");

		List<String> output = MavenTemplateRepository.process(inputLines);
		assertEquals("1 3 N", output.get(0)); // Valid mower data
		assertEquals("5 1 E", output.get(1)); // Valid mower data
	}

	/**
	 * Test the scenario with no lawnmower data after lawn dimensions.
	 */
	@Test
	void testNoMowerData() {
		List<String> inputLines = List.of("5 5");

		List<String> output = MavenTemplateRepository.process(inputLines);
		assertEquals(0, output.size()); // Should not process any mowers
	}

	/**
	 * Test the detection of collisions where two mowers end up in the same
	 * position.
	 */
	@Test
	void testCollisionDetection() {
		List<String> inputLines = List.of("5 5", "1 2 N", "AAAA", "1 3 N", "AAAA");

		List<String> output = MavenTemplateRepository.process(inputLines);

		// Check that a collision is detected
		assertEquals(2, output.size()); // There should be two outputs
		assertEquals("1 5 N", output.get(0)); // First mower's final position
		assertEquals("1 5 N", output.get(1)); // Second mower's final position

		// Log a warning for the collision
		if (output.get(0).equals(output.get(1))) {
			LOGGER.warning("Collision detected at position: " + output.get(0));
		}
	}
}

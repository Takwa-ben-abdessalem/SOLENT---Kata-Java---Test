package com.github.romankh3.maventemplaterepository;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Unit-level testing for {@link MavenTemplateRepository} object.
 */
public class MavenTemplateRepositoryTest {


    @Test
    void testProcess() {
        List<String> inputLines = List.of(
            "5 5",
            "1 2 N",
            "GAGAGAGAA",
            "3 3 E",
            "AADAADADDA"
        );

        List<String> output = MavenTemplateRepository.process(inputLines);
        assertEquals("1 3 N", output.get(0));
        assertEquals("5 1 E", output.get(1));
    }

}

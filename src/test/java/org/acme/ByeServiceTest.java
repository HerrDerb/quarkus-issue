package org.acme;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ByeServiceTest {

    private ByeService testee;

    @BeforeEach
    void init() {
        testee = new ByeService();
    }

    @Test
    void getBye_whenNameProvided_shouldReturnByeWithName() {
        String name = "Bob";

        String byeMessage = testee.getBye(name);

        assertEquals("Bye Bob", byeMessage);
    }

    @Test
    void getBye_whenNoNameProvided_shouldReturnByeAlien() {
        String name = null;

        String byeMessage = testee.getBye(name);

        assertEquals("Bye Alien", byeMessage);
    }
}

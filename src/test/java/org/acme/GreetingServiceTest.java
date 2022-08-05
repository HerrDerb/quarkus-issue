package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class GreetingServiceTest {

    @Inject
    private GreetingService testee;

    @Test
    void getGreeting_whenNameProvided_shouldReturnHiWithName() {
        String name = "Bob";

        String byeMessage = testee.getGreeting(name);

        assertEquals("Hi Bob", byeMessage);
    }

    @Test
    void getGreeting_whenNoNameProvided_shouldReturnHiAlien() {
        String name = null;

        String byeMessage = testee.getGreeting(name);

        assertEquals("Hi Alien", byeMessage);
    }
}

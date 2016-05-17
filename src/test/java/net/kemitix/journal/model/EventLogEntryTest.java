package net.kemitix.journal.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link EventLogEntry}.
 *
 * @author pcampbell
 */
public class EventLogEntryTest {

    @Test
    public void shouldInstantiate() {
        assertThat(new EventLogEntry()).hasFieldOrProperty("type");

    }

}

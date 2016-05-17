package net.kemitix.journal.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link NoteLogEntry}.
 *
 * @author pcampbell
 */
public class NoteLogEntryTest {

    @Test
    public void shouldInstantiate() {
        assertThat(new NoteLogEntry()).hasFieldOrProperty("type");

    }

}

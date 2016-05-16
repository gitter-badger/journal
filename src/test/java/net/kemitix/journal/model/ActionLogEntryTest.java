package net.kemitix.journal.model;

import org.junit.Before;
import org.junit.Test;

import static net.kemitix.journal.model.ActionState.TODO;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ActionLogEntry}.
 *
 * @author pcampbell
 */
public class ActionLogEntryTest {

    private ActionLogEntry logEntry;

    @Before
    public void setUp() throws Exception {
        logEntry = new ActionLogEntry();
    }

    @Test
    public void shouldGetAndSetState() {
        //when
        logEntry.setState(TODO);
        //then
        assertThat(logEntry.getState()).isSameAs(TODO);
    }

}

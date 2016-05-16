package net.kemitix.journal.model;

import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ActionState}.
 *
 * @author pcampbell
 */
public class ActionStateTest {

    @Test
    public void shouldToString() throws Exception {
        SoftAssertions softly = new SoftAssertions();
        assertThat(ActionState.TODO.toString()).contains("todo");
        assertThat(ActionState.DEFERRED.toString()).contains("deferred");
        assertThat(ActionState.SCHEDULED.toString()).contains("scheduled");
        assertThat(ActionState.DONE.toString()).contains("done");
        softly.assertAll();
    }

}

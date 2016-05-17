package net.kemitix.journal.model;

import lombok.val;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

/**
 * Tests for {@link LogEntry}.
 *
 * @author pcampbell
 */
public class LogEntryTest {

    @Test
    public void shouldGetSetValues() throws Exception {
        SoftAssertions softly = new SoftAssertions();
        val logEntry = new LogEntry(){};
        // id
        val id = 1001L;
        logEntry.setId(id);
        softly.assertThat(logEntry.getId()).isEqualTo(id);
        // type
        val type = "testing";
        logEntry.setType(type);
        softly.assertThat(logEntry.getType()).isEqualTo(type);
        // title
        val title = "title";
        logEntry.setTitle(title);
        softly.assertThat(logEntry.getTitle()).isEqualTo(title);
        // body
        val body = "body";
        logEntry.setBody(body);
        softly.assertThat(logEntry.getBody()).isEqualTo(body);
        softly.assertAll();
    }

}

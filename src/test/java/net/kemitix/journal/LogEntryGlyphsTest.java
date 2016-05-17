package net.kemitix.journal;

import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;

import net.kemitix.journal.model.ActionLogEntry;
import net.kemitix.journal.model.ActionState;
import net.kemitix.journal.model.EventLogEntry;
import net.kemitix.journal.model.LogEntry;
import net.kemitix.journal.model.NoteLogEntry;

/**
 * Tests for {@link LogEntryGlyphs}.
 *
 * @author pcampbell
 */
public class LogEntryGlyphsTest {

    private LogEntryGlyphs glyphs;

    private String note = "note";

    private String event = "event";

    private String todo = "todo";

    private String done = "done";

    private String scheduled = "scheduled";

    private String deferred = "deferred";

    @Before
    public void setUp() throws Exception {
        glyphs = new LogEntryGlyphs();
        glyphs.setNote(note);
        glyphs.setEvent(event);
        glyphs.setActionTodo(todo);
        glyphs.setActionDone(done);
        glyphs.setActionScheduled(scheduled);
        glyphs.setActionDeferred(deferred);
    }

    @Test
    public void shouldGetGlyphs() {
        SoftAssertions softly = new SoftAssertions();

        // abstract LogEntry
        softly.assertThat(glyphs.getGlyph(new LogEntry(){})).isSameAs(" ");

        softly.assertThat(glyphs.getGlyph(new NoteLogEntry())).isSameAs(note);
        softly.assertThat(glyphs.getGlyph(new EventLogEntry())).isSameAs(event);

        final ActionLogEntry actionLogEntry = new ActionLogEntry();

        // undefined action
        softly.assertThat(glyphs.getGlyph(actionLogEntry)).isSameAs(todo);

        actionLogEntry.setState(ActionState.TODO);
        softly.assertThat(glyphs.getGlyph(actionLogEntry)).isSameAs(todo);

        actionLogEntry.setState(ActionState.DONE);
        softly.assertThat(glyphs.getGlyph(actionLogEntry)).isSameAs(done);

        actionLogEntry.setState(ActionState.SCHEDULED);
        softly.assertThat(glyphs.getGlyph(actionLogEntry)).isSameAs(scheduled);

        actionLogEntry.setState(ActionState.DEFERRED);
        softly.assertThat(glyphs.getGlyph(actionLogEntry)).isSameAs(deferred);

        softly.assertAll();
    }

}

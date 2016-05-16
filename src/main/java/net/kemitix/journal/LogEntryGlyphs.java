package net.kemitix.journal;

import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import net.kemitix.journal.model.ActionLogEntry;
import net.kemitix.journal.model.EventLogEntry;
import net.kemitix.journal.model.LogEntry;
import net.kemitix.journal.model.NoteLogEntry;

/**
 * Glyphs to display for each type of LogEntry and state.
 *
 * @author pcampbell
 */
@Setter
@Component
@ConfigurationProperties(prefix = "journal.logentry.glyph")
public class LogEntryGlyphs {

    private String note;

    private String event;

    private String actionTodo;

    private String actionDone;

    private String actionDeferred;

    private String actionScheduled;

    /**
     * Returns the glyph for the log entry.
     *
     * @param logEntry the log entry
     *
     * @return the glyph
     */
    public String getGlyph(final LogEntry logEntry) {
        if (logEntry instanceof NoteLogEntry) {
            return note;
        }
        if (logEntry instanceof EventLogEntry) {
            return event;
        }
        if (logEntry instanceof ActionLogEntry) {
            ActionLogEntry ale = (ActionLogEntry) logEntry;
            switch (ale.getState()) {
            case TODO:
                return actionTodo;
            case DONE:
                return actionDone;
            case DEFERRED:
                return actionDeferred;
            case SCHEDULED:
                return actionScheduled;
            default:
                return " ";
            }
        }
        return " ";
    }
}

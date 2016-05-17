package net.kemitix.journal.service;

import java.time.LocalDate;
import java.util.List;

import net.kemitix.journal.model.DailyLog;
import net.kemitix.journal.model.LogEntry;
import net.kemitix.journal.model.NoteLogEntry;

/**
 * Interface for managing a Journal.
 *
 * @author pcampbell
 */
public interface JournalService {

    /**
     * Creates a new DailyLog for the supplied date and save it.
     *
     * @param date the date to create the DailyLog for
     *
     * @return the created daily log
     */
    DailyLog createDailyLog(LocalDate date);

    /**
     * Returns a list of all the DailyLogs.
     *
     * @return a list of DailyLogs
     */
    List<DailyLog> getAllDailyLogs();

    /**
     * Returns a list of all the LogEntries for the given date.
     *
     * @param date the date
     *
     * @return the list of log entries
     */
    List<LogEntry> getLogs(LocalDate date);

    /**
     * Creates a new NoteLogEntry for the supplied title and body and saves it.
     *
     * @param date  the date for the note
     * @param title the note's title
     * @param body  the note's body
     *
     * @return the create note log entry
     */
    NoteLogEntry createNoteLogEntry(LocalDate date, String title, String body);

    /**
     * Finds, or creates, the Daily Log for the date given.
     *
     * @param date the date
     *
     * @return the found or created Daily Log
     */
    DailyLog getDailyLog(LocalDate date);
}

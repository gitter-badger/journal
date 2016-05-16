package net.kemitix.journal.service;

import java.time.LocalDate;
import java.util.List;

import net.kemitix.journal.model.DailyLog;

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
     */
    DailyLog createDailyLog(LocalDate date);

    /**
     * Returns a list of all the DailyLogs.
     * @return a list of DailyLogs
     */
    List<DailyLog> getAllDailyLogs();
}

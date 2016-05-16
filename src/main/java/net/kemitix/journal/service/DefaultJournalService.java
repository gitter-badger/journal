package net.kemitix.journal.service;

import lombok.val;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.kemitix.journal.dao.DailyLogDAO;
import net.kemitix.journal.dao.LogEntryDAO;
import net.kemitix.journal.model.DailyLog;
import net.kemitix.journal.model.LogEntry;

/**
 * Default implementation of the JournalService.
 *
 * @author pcampbell
 */
@Service
class DefaultJournalService implements JournalService {

    private final DailyLogDAO dailyLogDAO;

    private final LogEntryDAO logEntryDAO;

    @Inject
    DefaultJournalService(
            final DailyLogDAO dailyLogDAO, final LogEntryDAO logEntryDAO) {
        this.dailyLogDAO = dailyLogDAO;
        this.logEntryDAO = logEntryDAO;
    }

    @Override
    public DailyLog createDailyLog(final LocalDate date) {
        val dailyLog = new DailyLog(date);
        dailyLogDAO.save(dailyLog);
        return dailyLog;
    }

    @Override
    public List<DailyLog> getAllDailyLogs() {
        return dailyLogDAO.findAll();
    }

    @Override
    public List<LogEntry> getLogs(final LocalDate date) {
        final Optional<DailyLog> optional = dailyLogDAO.findByDate(date);
        if (optional.isPresent()) {
            return optional.get().getEntries();
        }
        return new ArrayList<>();
    }
}

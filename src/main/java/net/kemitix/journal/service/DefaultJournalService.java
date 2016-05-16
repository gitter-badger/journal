package net.kemitix.journal.service;

import lombok.val;

import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.kemitix.journal.dao.DailyLogDAO;
import net.kemitix.journal.model.DailyLog;

/**
 * Default implementation of the JournalService.
 *
 * @author pcampbell
 */
@Service
class DefaultJournalService implements JournalService {

    private final DailyLogDAO dailyLogDAO;

    @Inject
    DefaultJournalService(final DailyLogDAO dailyLogDAO) {
        this.dailyLogDAO = dailyLogDAO;
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
}

package net.kemitix.journal.service;

import lombok.val;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.kemitix.journal.dao.DailyLogDAO;
import net.kemitix.journal.dao.NoteLogEntryDAO;
import net.kemitix.journal.model.DailyLog;
import net.kemitix.journal.model.LogEntry;
import net.kemitix.journal.model.NoteLogEntry;

/**
 * Default implementation of the JournalService.
 *
 * @author pcampbell
 */
@Service
class DefaultJournalService implements JournalService {

    private final DailyLogDAO dailyLogDAO;

    private final NoteLogEntryDAO noteLogEntryDAO;

    @Inject
    DefaultJournalService(
            final DailyLogDAO dailyLogDAO,
            final NoteLogEntryDAO noteLogEntryDAO) {
        this.dailyLogDAO = dailyLogDAO;
        this.noteLogEntryDAO = noteLogEntryDAO;
    }

    @Override
    public DailyLog createDailyLog(final LocalDate date) {
        val dailyLog = new DailyLog(date);
        dailyLogDAO.save(dailyLog);
        return dailyLogDAO.findOne(dailyLog.getDate());
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

    @Override
    public NoteLogEntry createNoteLogEntry(
            final LocalDate date, final String title, final String body) {
        val note = new NoteLogEntry();
        note.setTitle(title);
        note.setBody(body);
        noteLogEntryDAO.save(note);
        val one = noteLogEntryDAO.findOne(note.getId());
        addLogEntryToDailyLog(date, one);
        return one;
    }

    private void addLogEntryToDailyLog(
            final LocalDate date, final LogEntry logEntry) {
        val dailyLog = getDailyLog(date);
        dailyLog.getEntries().add(logEntry);
        dailyLogDAO.save(dailyLog);
    }

    @Override
    public DailyLog getDailyLog(final LocalDate date) {
        return dailyLogDAO.findByDate(date)
                          .orElseGet(() -> createDailyLog(date));
    }
}

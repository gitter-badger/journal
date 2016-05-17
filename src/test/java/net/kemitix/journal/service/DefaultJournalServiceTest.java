package net.kemitix.journal.service;

import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import net.kemitix.journal.dao.DailyLogDAO;
import net.kemitix.journal.dao.LogEntryDAO;
import net.kemitix.journal.dao.NoteLogEntryDAO;
import net.kemitix.journal.model.DailyLog;
import net.kemitix.journal.model.LogEntry;
import net.kemitix.journal.model.NoteLogEntry;

/**
 * Tests for {@link DefaultJournalService}.
 *
 * @author pcampbell
 */
public class DefaultJournalServiceTest {

    @InjectMocks
    private DefaultJournalService service;

    @Mock
    private DailyLogDAO dailyLogDAO;

    @Mock
    private LogEntryDAO logEntryDAO;

    @Mock
    private NoteLogEntryDAO noteLogEntryDAO;

    private LocalDate now;

    @Mock
    private DailyLog dailyLog;

    @Mock
    private NoteLogEntry noteLogEntry;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        now = LocalDate.now();
    }

    @Test
    public void shouldCreateDailyLog() throws Exception {
        //given
        given(dailyLogDAO.findOne(now)).willReturn(dailyLog);
        //when
        val result = service.createDailyLog(now);
        //then
        verify(dailyLogDAO).save(any(DailyLog.class));
        assertThat(result).isEqualTo(dailyLog);
    }

    @Test
    public void shouldGetAllDailyLogs() throws Exception {
        //given
        val dailyLogs = new ArrayList<DailyLog>();
        given(dailyLogDAO.findAll()).willReturn(dailyLogs);
        //when
        val result = service.getAllDailyLogs();
        //then
        assertThat(result).isSameAs(dailyLogs);
    }

    @Test
    public void shouldGetLogsFound() throws Exception {
        //given
        given(dailyLogDAO.findByDate(now)).willReturn(Optional.empty());
        //when
        val result = service.getLogs(now);
        //then
        assertThat(result).isEmpty();
    }

    @Test
    public void shouldGetLogsNotFound() throws Exception {
        //given
        given(dailyLogDAO.findByDate(now)).willReturn(Optional.of(dailyLog));
        val logEntries = new ArrayList<LogEntry>();
        given(dailyLog.getEntries()).willReturn(logEntries);
        //when
        val result = service.getLogs(now);
        //then
        assertThat(result).isSameAs(logEntries);
    }

    @Test
    public void shouldGetDailyLogWithCreate() {
        //given
        given(dailyLogDAO.findByDate(now)).willReturn(Optional.empty());
        given(dailyLogDAO.findOne(now)).willReturn(dailyLog);
        //when
        val result = service.getDailyLog(now);
        //then
        assertThat(result).isSameAs(dailyLog);
        verify(dailyLogDAO).save(any(DailyLog.class)); // created item is saved
        verify(dailyLogDAO).findOne(now); // then is reloaded from DB
    }

    @Test
    public void shouldGetDailyLogWithExisting() {
        //given
        given(dailyLogDAO.findByDate(now)).willReturn(Optional.of(dailyLog));
        //when
        val result = service.getDailyLog(now);
        //then
        assertThat(result).isSameAs(dailyLog);
        verify(dailyLogDAO, never()).save(any(DailyLog.class));
        verify(dailyLogDAO, never()).findOne(now);
    }

    @Test
    public void shouldCreateNoteLogEntry() {
        //given
        given(dailyLogDAO.findByDate(now)).willReturn(Optional.of(dailyLog));
        given(noteLogEntryDAO.findOne(any(Long.class))).willReturn(
                noteLogEntry);
        val dailyLogEntries = new ArrayList<LogEntry>();
        given(dailyLog.getEntries()).willReturn(dailyLogEntries);
        //when
        val result = service.createNoteLogEntry(now, "title", "body");
        //then
        verify(noteLogEntryDAO).save(any(NoteLogEntry.class));
        assertThat(result).isEqualTo(noteLogEntry);
        verify(dailyLogDAO).save(dailyLog);
        assertThat(dailyLogEntries).contains(noteLogEntry);
    }
}

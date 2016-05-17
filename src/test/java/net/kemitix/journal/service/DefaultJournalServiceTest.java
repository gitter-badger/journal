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
import net.kemitix.journal.model.DailyLog;
import net.kemitix.journal.model.LogEntry;

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

    private LocalDate now;

    @Mock
    private DailyLog dailyLog;

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
}

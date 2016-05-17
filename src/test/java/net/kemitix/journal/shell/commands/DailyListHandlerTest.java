package net.kemitix.journal.shell.commands;

import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.kemitix.journal.model.DailyLog;
import net.kemitix.journal.model.LogEntry;
import net.kemitix.journal.service.JournalService;

/**
 * Tests for {@link DailyListHandler}.
 *
 * @author pcampbell
 */
public class DailyListHandlerTest {

    @InjectMocks
    private DailyListHandler handler;

    @Mock
    private JournalService journalService;

    private Map<String, String> args;

    @Mock
    private DailyLog logTomorrow;

    @Mock
    private DailyLog logYesterday;

    @Mock
    private DailyLog logToday;

    @Mock
    private LogEntry logEntry;

    @Mock
    private LogEntry logEntry2;

    @Mock
    private LogEntry logEntry3;

    @Mock
    private PrintWriter writer;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        args = new HashMap<>();
    }

    @Test
    public void shouldGetAliases() throws Exception {
        assertThat(handler.getAliases()).isNotEmpty();
    }

    @Test
    public void shouldGetDescription() throws Exception {
        assertThat(handler.getDescription()).isNotEmpty();
    }

    @Test
    public void shouldHandlEmpty() throws Exception {
        //given
        given(journalService.getAllDailyLogs()).willReturn(
                Collections.emptyList());
        //when
        handler.handle(args);
        //then
        verify(writer).println("No Daily Logs found");
    }

    @Test
    public void shouldHandleItems() throws Exception {
        //given
        val today = LocalDate.now();
        given(logToday.getDate()).willReturn(today);
        val yesterday = today.minusDays(1);
        given(logYesterday.getDate()).willReturn(yesterday);
        val tomorrow = today.plusDays(1);
        given(logTomorrow.getDate()).willReturn(tomorrow);
        given(logTomorrow.getEntries()).willReturn(
                Collections.singletonList(logEntry));
        given(journalService.getAllDailyLogs()).willReturn(
                Arrays.asList(logTomorrow, logToday, logYesterday));
        //when
        handler.handle(args);
        //then
        verify(writer).println("- " + tomorrow + ": 1 item(s)");
        verify(writer).println("- " + today + ": 0 item(s)");
        verify(writer).println("- " + yesterday + ": 0 item(s)");
    }

}

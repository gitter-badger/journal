package net.kemitix.journal.shell.commands;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.HashMap;

import net.kemitix.journal.TypeSafeHashMap;
import net.kemitix.journal.model.DailyLog;
import net.kemitix.journal.service.JournalService;
import net.kemitix.journal.shell.ShellState;
import net.kemitix.journal.shell.TypeSafeMapShellState;

/**
 * Tests for {@link DailyCreateHandler}.
 *
 * @author pcampbell
 */
public class DailyCreateHandlerTest {

    private DailyCreateHandler handler;

    @Mock
    private JournalService journalService;

    @Mock
    private DateSetHandler dateSetHandler;

    private ShellState shellState;

    private LocalDate now;

    private DailyLog dailyLog;

    private HashMap<String, String> args;

    private LocalDate yesterday;

    private LocalDate tomorrow;

    @Mock
    private PrintWriter writer;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        shellState = new TypeSafeMapShellState(new TypeSafeHashMap());
        handler = new DailyCreateHandler(journalService, dateSetHandler,
                shellState, writer);
        now = LocalDate.now();
        args = new HashMap<>();
        yesterday = LocalDate.now().minusDays(1);
        tomorrow = LocalDate.now().plusDays(1);
    }

    @Test
    public void shouldGetAliases() throws Exception {
        assertThat(handler.getAliases()).isNotEmpty();
    }

    @Test
    public void shouldGetParameterRegex() throws Exception {
        assertThat(handler.getParameterRegex()).isNotEmpty();
    }

    @Test
    public void shouldGetParameterNames() throws Exception {
        assertThat(handler.getParameterNames()).contains("date");
    }

    @Test
    public void shouldGetSyntax() throws Exception {
        assertThat(handler.getSyntax()).contains(handler.getAliases());
    }

    @Test
    public void shouldGetDescription() throws Exception {
        assertThat(handler.getDescription()).isNotEmpty();
    }

    @Test
    public void shouldHandleNoArgsNoDefault() throws Exception {
        //given
        dailyLog = new DailyLog(now);
        given(journalService.createDailyLog(now)).willReturn(dailyLog);
        //when
        handler.handle(args);
        //then
        verify(journalService).createDailyLog(eq(now));
        verify(writer).println("Daily log created for " + now);
    }

    @Test
    public void shouldHandleNoArgsWithDefault() throws Exception {
        //given
        dailyLog = new DailyLog(yesterday);
        shellState.setDefaultDate(yesterday);
        given(journalService.createDailyLog(yesterday)).willReturn(dailyLog);
        //when
        handler.handle(args);
        //then
        verify(journalService).createDailyLog(eq(yesterday));
        verify(writer).println("Daily log created for " + yesterday);
    }

    @Test
    public void shouldHandleWithArgs() throws Exception {
        //given
        dailyLog = new DailyLog(tomorrow);
        args.put("date", tomorrow.toString());
        given(journalService.createDailyLog(tomorrow)).willReturn(dailyLog);
        doAnswer(i -> {
            shellState.setDefaultDate(tomorrow);
            return null;
        }).when(dateSetHandler).handle(args);
        //when
        handler.handle(args);
        //then
        verify(journalService).createDailyLog(eq(tomorrow));
        verify(writer).println("Daily log created for " + tomorrow);
    }

}

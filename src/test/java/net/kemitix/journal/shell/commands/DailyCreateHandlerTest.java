package net.kemitix.journal.shell.commands;

import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.HashMap;

import net.kemitix.journal.TypeSafeHashMap;
import net.kemitix.journal.TypeSafeMap;
import net.kemitix.journal.model.DailyLog;
import net.kemitix.journal.service.JournalService;

/**
 * .
 *
 * @author pcampbell
 */
public class DailyCreateHandlerTest {

    private DailyCreateHandler handler;

    @Mock
    private JournalService journalService;

    @Mock
    private DateSetHandler dateSetHandler;

    private TypeSafeMap applicationState;

    private LocalDate now;

    private DailyLog dailyLog;

    private HashMap<String, String> args;

    private LocalDate yesterday;

    private LocalDate tomorrow;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        applicationState = new TypeSafeHashMap();
        handler = new DailyCreateHandler(journalService, dateSetHandler,
                applicationState);
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
        val result = handler.handle(args);
        //then
        verify(journalService).createDailyLog(eq(now));
        assertThat(result).contains("Daily log created for " + now);
    }

    @Test
    public void shouldHandleNoArgsWithDefault() throws Exception {
        //given
        dailyLog = new DailyLog(yesterday);
        applicationState.put("selected-date", yesterday, LocalDate.class);
        given(journalService.createDailyLog(yesterday)).willReturn(dailyLog);
        //when
        String result = handler.handle(args);
        //then
        verify(journalService).createDailyLog(eq(yesterday));
        assertThat(result).contains("Daily log created for " + yesterday);
    }

    @Test
    public void shouldHandleWithArgs() throws Exception {
        //given
        dailyLog = new DailyLog(tomorrow);
        args.put("date", tomorrow.toString());
        given(journalService.createDailyLog(tomorrow)).willReturn(dailyLog);
        given(dateSetHandler.handle(args)).willAnswer(i -> {
            applicationState.put("selected-date", tomorrow, LocalDate.class);
            return "";
        });
        //when
        String result = handler.handle(args);
        //then
        verify(journalService).createDailyLog(eq(tomorrow));
        assertThat(result).contains("Daily log created for " + tomorrow);
    }

}

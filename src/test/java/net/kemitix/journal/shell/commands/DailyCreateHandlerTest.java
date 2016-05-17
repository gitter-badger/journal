package net.kemitix.journal.shell.commands;

import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Optional;

import net.kemitix.journal.TypeSafeMap;
import net.kemitix.journal.model.DailyLog;
import net.kemitix.journal.service.JournalService;

/**
 * .
 *
 * @author pcampbell
 */
public class DailyCreateHandlerTest {

    @InjectMocks
    private DailyCreateHandler handler;

    @Mock
    private JournalService journalService;

    @Mock
    private DateSetHandler dateSetHandler;

    @Mock
    private TypeSafeMap applicationState;

    private LocalDate now;

    private DailyLog dailyLog;

    private HashMap<String, String> args;

    private LocalDate yesterday;

    private LocalDate tomorrow;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
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
        given(applicationState.get("selected-date",
                LocalDate.class)).willReturn(Optional.empty());
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
        given(applicationState.get("selected-date",
                LocalDate.class)).willReturn(Optional.of(yesterday));
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
        given(applicationState.get("selected-date",
                LocalDate.class)).willReturn(Optional.of(yesterday));
        given(journalService.createDailyLog(tomorrow)).willReturn(dailyLog);
        given(dateSetHandler.handle(args)).willReturn("");
        //when
        String result = handler.handle(args);
        //then
        verify(journalService).createDailyLog(eq(tomorrow));
        assertThat(result).contains("Daily log created for " + tomorrow);
    }

}

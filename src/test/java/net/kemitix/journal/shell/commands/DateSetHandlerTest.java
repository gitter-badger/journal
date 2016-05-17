package net.kemitix.journal.shell.commands;

import lombok.val;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.HashMap;

import net.kemitix.journal.TypeSafeMap;
import net.kemitix.journal.shell.CommandHandlerException;

/**
 * Tests for {@link DateSetHandler}.
 *
 * @author pcampbell
 */
public class DateSetHandlerTest {

    @InjectMocks
    private DateSetHandler handler;

    @Mock
    private TypeSafeMap applicationState;

    @Mock
    private PrintWriter writer;

    private LocalDate today;

    private HashMap<String, String> args;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        today = LocalDate.now();
        args = new HashMap<>();
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
    public void shouldHandleWithNoArgs() throws Exception {
        //when
        handler.handle(args);
        //then
        verify(writer).println("Date set to " + today);
        verify(applicationState).put("selected-date", today, LocalDate.class);
    }

    @Test
    public void shouldHandleWithArgs() throws Exception {
        //given
        val tomorrow = LocalDate.now().plusDays(1);
        args.put("date", tomorrow.toString());
        //when
        handler.handle(args);
        //then
        verify(writer).println("Date set to " + tomorrow);
        verify(applicationState).put("selected-date", tomorrow,
                LocalDate.class);
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldReportWhenDateIsInvalid() {
        //given
        args.put("date", "2016-14-01"); // invalid month
        exception.expect(CommandHandlerException.class);
        exception.expectMessage("Invalid date: 2016-14-01");
        //when
        handler.handle(args);
    }
}

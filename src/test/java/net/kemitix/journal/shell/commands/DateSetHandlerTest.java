package net.kemitix.journal.shell.commands;

import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.HashMap;

import net.kemitix.journal.TypeSafeMap;

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
        val result = handler.handle(args);
        //then
        assertThat(result).contains("Date set to " + today);
        verify(applicationState).put("selected-date", today, LocalDate.class);
    }

    @Test
    public void shouldHandleWithArgs() throws Exception {
        //given
        val tomorrow = LocalDate.now().plusDays(1);
        args.put("date", tomorrow.toString());
        //when
        val result = handler.handle(args);
        //then
        assertThat(result).contains("Date set to " + tomorrow);
        verify(applicationState).put("selected-date", tomorrow, LocalDate.class);
    }
}

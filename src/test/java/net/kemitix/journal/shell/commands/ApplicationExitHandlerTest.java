package net.kemitix.journal.shell.commands;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import net.kemitix.journal.TypeSafeMap;

/**
 * Tests for {@link ApplicationExitHandler}.
 *
 * @author pcampbell
 */
public class ApplicationExitHandlerTest {

    @InjectMocks
    private ApplicationExitHandler handler;

    @Mock
    private TypeSafeMap applicationState;

    private Map<String, String> args;

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
    public void shouldHandle() throws Exception {
        //when
        handler.handle(args);
        //then
        verify(writer).println("Exiting...");
        verify(applicationState).remove("running");
    }

}

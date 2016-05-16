package net.kemitix.journal.shell;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.startsWith;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import net.kemitix.journal.TypeSafeMap;

/**
 * Tests for {@link CommandPrompt}.
 *
 * @author pcampbell
 */
public class CommandPromptTest {

    @InjectMocks
    private CommandPrompt prompt;

    @Mock
    private CommandRouter commandRouter;

    @Mock
    private TypeSafeMap applicationState;

    @Mock
    private BufferedReader reader;

    @Mock
    private PrintWriter output;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldAbortOnIOException() throws IOException {
        //given
        doThrow(IOException.class).when(reader).readLine();
        given(applicationState.get("running", Boolean.class)).willReturn(
                Optional.of(Boolean.TRUE));
        //when
        prompt.run();
        verify(output).println(startsWith("Aborting: "));
    }

}

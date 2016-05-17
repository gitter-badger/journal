package net.kemitix.journal.shell;

import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.startsWith;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Optional;

import net.kemitix.journal.TypeSafeHashMap;
import net.kemitix.journal.TypeSafeMap;

/**
 * Tests for {@link CommandPrompt}.
 *
 * @author pcampbell
 */
public class CommandPromptTest {

    private CommandPrompt prompt;

    @Mock
    private CommandRouter commandRouter;

    private TypeSafeMap applicationState;

    @Mock
    private BufferedReader reader;

    @Mock
    private PrintWriter output;

    @Mock
    private CommandMapping commandMapping;

    @Mock
    private CommandHandler commandHandler;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        applicationState = new TypeSafeHashMap();
        prompt = new CommandPrompt(commandRouter, applicationState, reader,
                output);
    }

    @Test
    public void shouldAbortOnIOException() throws IOException {
        //given
        doThrow(IOException.class).when(reader).readLine();
        //when
        prompt.run();
        verify(output).println(startsWith("Aborting: "));
    }

    @Test
    public void shouldExitWhenInputIsNull() throws IOException {
        //given
        given(reader.readLine()).willReturn(null);
        //when
        prompt.run();
        //then
        verify(output).println("Exiting...");
    }

    @Test
    public void shouldLoopWhenInputIsEmpty() throws IOException {
        //given
        given(reader.readLine()).willReturn("").willReturn(null);
        //when
        prompt.run();
        //then
        verify(output).println("Exiting...");
    }

    @Test
    public void shouldDispatchRecognisedCommand() throws IOException {
        //given
        given(reader.readLine()).willReturn("command").willReturn(null);
        given(commandRouter.selectCommand("command")).willReturn(
                Optional.of(commandMapping));
        given(commandMapping.getHandler()).willReturn(commandHandler);
        val args = new HashMap<String, String>();
        given(commandMapping.getArgs()).willReturn(args);
        given(commandHandler.handle(args)).willReturn("expected output");
        //when
        prompt.run();
        //then
        verify(output).println("expected output");
    }

    @Test
    public void shouldReportUnknownCommand() throws IOException {
        //given
        given(reader.readLine()).willReturn("command").willReturn(null);
        given(commandRouter.selectCommand("command")).willReturn(
                Optional.empty());
        //when
        prompt.run();
        //then
        verify(output).println("Not a recognised command!");
    }

}

package net.kemitix.journal.shell;

import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.startsWith;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Optional;

import net.kemitix.journal.TypeSafeHashMap;

/**
 * Tests for {@link CommandPrompt}.
 *
 * @author pcampbell
 */
public class CommandPromptTest {

    private CommandPrompt prompt;

    @Mock
    private CommandRouter commandRouter;

    @Mock
    private BufferedReader reader;

    @Mock
    private CommandMapping commandMapping;

    @Mock
    private CommandHandler commandHandler;

    @Mock
    private PrintWriter writer;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        val shellState = new TypeSafeMapShellState(new TypeSafeHashMap());
        prompt = new CommandPrompt(commandRouter, shellState, reader, writer);
    }

    @Test
    public void shouldAbortOnIOException() throws IOException {
        //given
        doThrow(IOException.class).when(reader).readLine();
        //when
        prompt.run();
        verify(writer).println(startsWith("Aborting: "));
    }

    @Test
    public void shouldExitWhenInputIsNull() throws IOException {
        //given
        given(reader.readLine()).willReturn(null);
        //when
        prompt.run();
        //then
        verify(writer).println("Exiting...");
    }

    @Test
    public void shouldLoopWhenInputIsEmpty() throws IOException {
        //given
        given(reader.readLine()).willReturn("").willReturn(null);
        //when
        prompt.run();
        //then
        verify(writer).println("Exiting...");
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
        doAnswer(i -> {
            writer.println("expected output");
            return null;
        }).when(commandHandler).handle(args);
        //when
        prompt.run();
        //then
        verify(writer).println("expected output");
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
        verify(writer).println("Not a recognised command!");
    }

}

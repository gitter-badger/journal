package net.kemitix.journal.shell;

import lombok.val;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Tests for {@link DefaultMultiLinePromptedTextReader}.
 *
 * @author pcampbell
 */
public class DefaultMultiLinePromptedTextReaderTest {

    @InjectMocks
    private DefaultMultiLinePromptedTextReader promptedTextReader;

    @Mock
    private PrintWriter writer;

    @Mock
    private BufferedReader reader;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReadText() throws Exception {
        //given
        given(reader.readLine()).willReturn("line 1")
                                .willReturn("line 2")
                                .willReturn(".");
        //when
        val result = promptedTextReader.readText("prompt");
        //then
        assertThat(result).isEqualTo("line 1\nline 2");
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldReadTextWithIOException() throws Exception {
        //given
        doThrow(IOException.class).when(reader).readLine();
        exception.expect(CommandHandlerException.class);
        exception.expectMessage("Can't read from input");
        //when
        promptedTextReader.readText("prompt");
    }

}

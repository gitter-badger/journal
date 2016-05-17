package net.kemitix.journal.shell.commands;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.kemitix.journal.shell.CommandHandler;

/**
 * Tests for {@link HelpDisplayHandler}.
 *
 * @author pcampbell
 */
public class HelpDisplayHandlerTest {

    private HelpDisplayHandler handler;

    private List<CommandHandler> handlerList;

    @Mock
    private CommandHandler mockHandler;

    @Mock
    private PrintWriter writer;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        handlerList = new ArrayList<>();
        handler = new HelpDisplayHandler(handlerList, writer);
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
        //given
        handlerList.add(mockHandler);
        given(mockHandler.getUsage()).willReturn("mock usage");
        //when
        handler.handle(new HashMap<>());
        //then
        verify(writer).println("The following commands are available:");
        verify(writer).println("mock usage");
    }

}

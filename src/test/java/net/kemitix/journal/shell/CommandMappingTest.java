package net.kemitix.journal.shell;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;

/**
 * Tests for {@link CommandMapping}.
 *
 * @author pcampbell
 */
public class CommandMappingTest {

    @Mock
    private CommandHandler handler;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldInstantiate() {
        assertThat(new CommandMapping(handler,
                new HashMap<>())).hasFieldOrProperty("handler");
    }

    @Test
    public void shouldBuild() {
        assertThat(CommandMapping.builder()
                                 .handler(handler)
                                 .build()).hasFieldOrProperty("handler");
    }

    @Test
    public void shouldBuildToString() {
        assertThat(
                CommandMapping.builder().handler(handler).toString()).contains(
                "(handler=handler, args=null)");
    }

}

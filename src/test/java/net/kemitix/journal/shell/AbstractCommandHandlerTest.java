package net.kemitix.journal.shell;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Tests for {@link AbstractCommandHandler}.
 *
 * @author pcampbell
 */
public class AbstractCommandHandlerTest {

    private AbstractCommandHandler handler;

    @Before
    public void setUp() throws Exception {
        handler = new AbstractCommandHandler() {
            @Override
            public List<String> getAliases() {
                return Collections.singletonList("command");
            }

            @Override
            public String getDescription() {
                return "description";
            }

            @Override
            public void handle(final Map<String, String> args) {
            }
        };
    }

    @Test
    public void shouldGetSyntax() throws Exception {
        assertThat(handler.getSyntax()).contains("command");
    }

    @Test
    public void shouldGetParameterRegex() throws Exception {
        assertThat(handler.getParameterRegex()).isEmpty();
    }

    @Test
    public void shouldGetParameterNames() throws Exception {
        assertThat(handler.getParameterNames()).isEmpty();
    }

    @Test
    public void shouldGetUsage() throws Exception {
        assertThat(handler.getUsage()).contains("command\n - description");
    }

}

package net.kemitix.journal.shell;

import lombok.Getter;
import lombok.experimental.Builder;
import lombok.val;
import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Tests for {@link DefaultCommandRouter}.
 *
 * @author pcampbell
 */
public class DefaultCommandRouterTest {

    private DefaultCommandRouter router;

    private ArrayList<CommandHandler> handlers;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        handlers = new ArrayList<>();
        router = new DefaultCommandRouter(handlers);
    }

    @Test
    public void verifyStringMatchesWorksAsExpected() {
        //given
        handlers.add(CommandFactory.builder().command("test").build().create());
        router.init();
        val pattern = Pattern.compile("test (?<value>\\d)");
        val subject = "test 9";
        //then
        assertThat(pattern.matcher(subject).matches()).isTrue();
    }

    @Test
    public void selectNormallyWhenNoParameters() {
        //given
        val handler = CommandFactory.builder().command("test").build().create();
        handlers.add(handler);
        handlers.add(
                CommandFactory.builder().command("other").build().create());
        router.init();
        //when
        val result = router.selectCommand("test");
        //then
        assertThat(result.isPresent()).as("a handler is selected").isTrue();
        if (result.isPresent()) {
            assertThat(result.get().getHandler()).as(
                    "correct handler is selected").isSameAs(handler);
        }
    }

    @Test
    public void selectNormallyWhenRequiredParametersAreProvided() {
        //given
        val handler = CommandFactory.builder()
                                    .command("test")
                                    .requiresParameters(true)
                                    .build()
                                    .create();
        handlers.add(handler);
        handlers.add(
                CommandFactory.builder().command("other").build().create());
        router.init();
        //when
        val result = router.selectCommand("test 9");
        //then
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(result.isPresent())
              .as("a handler is selected")
              .isTrue();
        if (result.isPresent()) {
            val mapping = result.get();
            softly.assertThat(mapping.getHandler())
                  .as("correct handler is selected")
                  .isSameAs(handler);
            softly.assertThat(mapping.getArgs())
                  .as("correct parameters found")
                  .containsEntry("value", "9");
        }
        softly.assertAll();
    }

    @Test
    public void selectNormallyWhenRequiredParametersAreMissing() {
        //given
        val handler = CommandFactory.builder()
                                    .command("test")
                                    .requiresParameters(true)
                                    .build()
                                    .create();
        handlers.add(handler);
        router.init();
        //when
        val result = router.selectCommand("test");
        //then
        assertThat(result.isPresent()).as("a handler is selected").isTrue();
        if (result.isPresent()) {
            assertThat(result.get().getHandler()).as(
                    "correct handler is selected").isSameAs(handler);
        }
    }

    @Test
    public void selectNoCommandWhenBadParametersAreProvided() {
        //given
        handlers.add(CommandFactory.builder()
                                   .command("test")
                                   .requiresParameters(true)
                                   .build()
                                   .create());
        router.init();
        //when
        val result = router.selectCommand("test alpha");
        //then
        assertThat(result.isPresent()).as("no handler is selected").isFalse();
    }

    @Getter
    @Builder
    private static class CommandFactory {

        private final String command;

        private final boolean requiresParameters;

        private final boolean optionalParameters;

        @Mock
        private CommandHandler handler;

        CommandHandler create() {
            MockitoAnnotations.initMocks(this);
            given(handler.getAliases()).willReturn(
                    Collections.singletonList(command));
            given(handler.getDescription()).willReturn("test command");
            given(handler.getSyntax()).willReturn("syntax");
            given(handler.getUsage()).willReturn("usage");
            if (requiresParameters || optionalParameters) {
                given(handler.getParameterNames()).willReturn(
                        Collections.singletonList("value"));
            }
            String parameterRegex = null;
            if (requiresParameters) {
                parameterRegex = "(?<value>\\d)";
            } else if (optionalParameters) {
                parameterRegex = "(?<value>\\d)?";
            }
            given(handler.getParameterRegex()).willReturn(
                    Optional.ofNullable(parameterRegex));
            return handler;
        }
    }

}

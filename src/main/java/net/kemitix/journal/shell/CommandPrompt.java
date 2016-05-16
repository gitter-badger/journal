package net.kemitix.journal.shell;

import lombok.val;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.stereotype.Component;

/**
 * The Command Prompt. Reads input and looks up and dispatches to commands to
 * implement the command.
 *
 * @author pcampbell
 */
@Component
class CommandPrompt {

    static final String SELECTED_DATE = "selected-date";

    private final CommandRouter commandRouter;

    private final TypeSafeMap applicationState;

    @Inject
    CommandPrompt(
            final CommandRouter commandRouter,
            final TypeSafeMap applicationState) {
        this.commandRouter = commandRouter;
        this.applicationState = applicationState;
    }

    @PostConstruct
    public void run() throws Exception {
        try (val br = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Ctrl-D to quit");
            applicationState.put("running", true, Boolean.class);
            while (applicationState.get("running", Boolean.class).isPresent()) {
                System.out.print("> ");
                val input = br.readLine();
                if (input == null) {
                    applicationState.remove("running");
                    System.out.println();
                    System.out.println("Exiting...");
                } else if (input.length() > 0) {
                    // look up command
                    val commandMapping = commandRouter.selectCommand(input);
                    // dispatch command
                    if (commandMapping.isPresent()) {
                        val mapping = commandMapping.get();
                        System.out.println(
                                mapping.getHandler().handle(mapping.getArgs()));
                    } else {
                        System.out.println("Not a recognised command!");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Aborting: " + e.getMessage());
        }
    }

}

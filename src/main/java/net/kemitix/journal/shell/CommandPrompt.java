package net.kemitix.journal.shell;

import lombok.val;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.HashMap;

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

    static final String STATE = "state";

    static final String STATE_RUNNING = "running";

    static final String STATE_EXITING = "exiting";

    private final CommandRouter commandRouter;

    @Inject
    CommandPrompt(final CommandRouter commandRouter) {
        this.commandRouter = commandRouter;
    }

    @PostConstruct
    public void run() throws Exception {
        try (val br = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Ctrl-D to quit");
            val context = new HashMap<String, String>();
            context.put(STATE, STATE_RUNNING);
            context.put(SELECTED_DATE, LocalDate.now().toString());
            while (STATE_RUNNING.equals(context.get(STATE))) {
                System.out.print("> ");
                val input = br.readLine();
                if (input == null) {
                    context.put(STATE, STATE_EXITING);
                    System.out.println();
                    System.out.println("Exiting...");
                } else if (input.length() > 0) {
                    // look up command
                    val commandMapping = commandRouter.selectCommand(input);
                    // dispatch command
                    if (commandMapping.isPresent()) {
                        val mapping = commandMapping.get();
                        System.out.println(mapping.getHandler()
                                                  .handle(context,
                                                          mapping.getArgs()));
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

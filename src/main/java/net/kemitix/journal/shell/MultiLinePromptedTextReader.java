package net.kemitix.journal.shell;

/**
 * Reads several lines of text after prompting the user.
 *
 * @author pcampbell
 */
public interface MultiLinePromptedTextReader {

    /**
     * Prompt the user to enter text and returns it.
     * @param prompt the prompt for the user
     * @return the text entered
     */
    String readText(String prompt);

}

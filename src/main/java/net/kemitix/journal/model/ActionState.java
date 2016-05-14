package net.kemitix.journal.model;

/**
 * States for and Action.
 *
 * @author pcampbell
 */
public enum ActionState {

    TODO("todo"),
    DEFERED("defered"),
    SCHEDULED("scheduled"),
    DONE("done");

    private final String label;

    ActionState(final String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}

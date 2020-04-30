package shell;

import lombok.Getter;

/**
 * Abstract class to describe a generic command
 *
 * @author Ioan Sava
 */
@Getter
public abstract class Command {
    private final String command;
    private String arguments;

    public Command(String command) {
        this.command = command;
    }

    public Command(String command, String arguments) {
        this.command = command;
        this.arguments = arguments;
    }

    public abstract String execute(Object... arguments);

    @Override
    public String toString() {
        return command + " " + arguments;
    }
}

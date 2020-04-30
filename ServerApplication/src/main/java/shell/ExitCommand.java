package shell;

/**
 * This command can be used to exit
 * Format: 'exit'
 *
 * @author Ioan Sava
 */
public class ExitCommand extends Command {
    public ExitCommand(String command) {
        super(command);
    }

    @Override
    public String execute(Object... arguments) {
        return "Goodbye";
    }
}

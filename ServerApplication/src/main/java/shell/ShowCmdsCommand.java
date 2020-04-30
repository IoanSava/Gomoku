package shell;

import lombok.ToString;

/**
 * This command can be used to see the
 * available commands
 * Format: 'show-cmds'
 *
 * @author Ioan Sava
 */
public class ShowCmdsCommand extends Command {

    public ShowCmdsCommand(String command) {
        super(command);
    }

    @Override
    public String execute(Object... arguments) {
        return "Commands: " + arguments[0];
    }
}

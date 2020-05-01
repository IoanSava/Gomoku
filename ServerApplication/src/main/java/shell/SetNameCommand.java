package shell;

import game.Player;

/**
 * This command can be used to set
 * the name for the current player.
 * Format: 'set-name name'
 *
 * @author Ioan Sava
 */
public class SetNameCommand extends Command {
    public SetNameCommand(String command, String arguments) {
        super(command, arguments);
    }

    @Override
    public String execute(Object... arguments) {
        if (arguments.length != 2) {
            return "Format: set-name name";
        }
        Player player = (Player) arguments[0];
        player.setName((String) arguments[1]);
        return "Your name is: " + arguments[1];
    }
}

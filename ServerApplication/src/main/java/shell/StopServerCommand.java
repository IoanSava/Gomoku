package shell;

/**
 * If the server receives the command
 * stop it will stop and will return
 * to the client the response "Server stopped".
 * Format: 'stop-server'
 *
 * @author Ioan Sava
 */
public class StopServerCommand extends Command {
    public StopServerCommand(String command) {
        super(command);
    }

    @Override
    public String execute(Object... arguments) {
        return "Server stopped";
    }
}

package banos.bot.command;

import java.io.FileNotFoundException;
import java.util.List;

public interface ICommand {
    void handle(CommandContext ctx) throws FileNotFoundException;
    String getName();
    String getHelp() throws FileNotFoundException;
    default List<String> getAliases() {
        return List.of();
    }
}
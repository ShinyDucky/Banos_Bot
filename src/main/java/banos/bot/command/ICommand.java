package banos.bot.command;

import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface ICommand {
    void handle(CommandContext ctx) throws IOException, ParseException;
    String getName();
    default List<String> getAliases() {
        return List.of();
    }
}
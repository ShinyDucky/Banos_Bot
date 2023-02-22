package banos.bot.command;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
/**
 * @deprecated All the prefix commands are deprecated in favour of slash commands. {@link banos.bot.api.cmds} to see all the current commands
 */

@Deprecated
public interface ICommand {
    void handle(CommandContext ctx) throws IOException, ParseException;
    String getName();
//    HashMap<String, OptionType> getOptions();
//    List<Permission> getPermissions();
}
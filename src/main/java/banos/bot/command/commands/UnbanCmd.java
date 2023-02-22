package banos.bot.command.commands;

import banos.bot.command.CommandContext;
import banos.bot.command.ICommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;
/**
 * @deprecated All the prefix commands are deprecated in favour of slash commands. {@link banos.bot.api.cmds} to see all the current commands
 */

@Deprecated
public class UnbanCmd implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        List<Guild.Ban> _bannedUsers = ctx.getGuild().retrieveBanList().complete();

        for (Guild.Ban ban : _bannedUsers) {
            for (String arg : ctx.getArgs()) {
                if (arg.equals(ban.getUser().getAsTag())) {
                    ctx.getGuild().unban(ban.getUser()).queue();
                } else {
                    continue;
                }
            }
        }
    }

    @Override
    public String getName() {
        return "unban";
    }
}

package banos.bot.command;

import me.duncte123.botcommons.commands.ICommandContext;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;
/**
 * @deprecated All the prefix commands are deprecated in favour of slash commands. {@link banos.bot.api.cmds} to see all the current commands
 */

@Deprecated
public record CommandContext(MessageReceivedEvent event,
                             List<String> args) implements ICommandContext {

    @Override
    public Guild getGuild() {
        return this.getEvent().getGuild();
    }

    @Override
    public MessageReceivedEvent getEvent() {
        return this.event;
    }

    public List<String> getArgs() {
        return this.args;
    }
}
package banos.bot.api.cmds;

import banos.bot.Config;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class ConfigCmd {
    public static void handle(SlashCommandInteractionEvent event) throws IOException, ParseException {
        InteractionHook hook = event.getHook();
        event.deferReply(true).queue();
        hook.setEphemeral(true);

        switch (event.getSubcommandName()) {
            case "setlog":
                OptionMapping mapping = event.getOption("channel");
                TextChannel channel = mapping.getAsChannel().asTextChannel();

                Config.putDb(event.getGuild().getId(), "logs", channel.getId());
                Config.putDb(event.getGuild().getId(), "logsEnabled", true);
                hook.sendMessage("Log Channel set to " + channel.getAsMention()).queue();
                break;
        }
    }
}

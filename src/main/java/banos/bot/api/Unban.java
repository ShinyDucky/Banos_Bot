package banos.bot.api;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;

import java.util.List;

public class Unban {
    public static void handle(SlashCommandInteractionEvent event) {
        event.deferReply(false).queue();
        InteractionHook hook = event.getHook();
        hook.setEphemeral(false);
        List<Guild.Ban> _bannedUsers = event.getGuild().retrieveBanList().complete();
        String user = event.getOption("user").getAsString();

        for (Guild.Ban ban : _bannedUsers) {
            if (user.equals(ban.getUser().getAsTag())) {
                event.getGuild().unban(ban.getUser()).queue(v -> hook.sendMessage("Unbanned User " + user + "using the Unban Stone on the Banos Gauntlet :banos_gauntlet:").queue());
            } else {
                continue;
            }
        }
    }
}


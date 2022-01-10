package banos.bot;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class AutoMod extends ListenerAdapter {
    public static final String[] BANNED_WORDS = new String[]{
            "nigger",
            "nigga"
    };

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        super.onGuildMessageReceived(event);
        String msg = event.getMessage().getContentRaw();

        for (String bannedWord : BANNED_WORDS) {
            if (msg.toLowerCase().contains(bannedWord)) {
                event.getChannel().sendMessage("You are not allowed to use that word.").queue();
                event.getMessage().delete().queue();
            }
        }
    }
}
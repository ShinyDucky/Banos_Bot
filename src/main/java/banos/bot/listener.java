package banos.bot;

import me.duncte123.botcommons.BotCommons;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.DisconnectEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.ReconnectedEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

public class listener extends ListenerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private final CommandManager manager = new CommandManager();

    protected String myId = "743218702022869083";

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        LOGGER.info("{} is ready", event.getJDA().getSelfUser().getAsTag());
    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        super.onGuildMessageReceived(event);
        try {
            User user = event.getAuthor();

            if (user.isBot() || event.isWebhookMessage()) {
                return;
            }

            String prefix = Config.getPrefix();
            String raw = event.getMessage().getContentRaw();

            if (raw.equalsIgnoreCase(prefix + "shutdown") && event.getAuthor().getId().equals(myId)) {
                BotCommons.shutdown(event.getJDA());
                LOGGER.info("{} IS SHUTTING DOWN", event.getJDA().getSelfUser().getAsTag());
                event.getJDA().shutdownNow();

                return;
            }

            if (raw.startsWith(prefix)) {
                manager.handle(event);
            } else if (raw.equalsIgnoreCase("i want banos")) {
                EmbedBuilder builder = new EmbedBuilder().setImage("https://media.discordapp.net/attachments/761236786936414291/909985727310364672/Banos.png?width=439&height=554")
                        .setTitle("You Have Been Banned BY... BANOS")
                        .setColor(Color.RED);

                event.getAuthor().openPrivateChannel().complete().sendMessageEmbeds(builder.build()).queue();
            } else if (raw.equalsIgnoreCase("I hate banos")) {
                event.getAuthor().openPrivateChannel().complete().sendMessage("Fuck You Bitch").queue();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            return;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        super.onGuildJoin(event);

        Guild guild = event.getJDA().getGuildById(974312232911528017l);

        if (event.getGuild() == guild) {
            event.getGuild().ban((User) event.getJDA().getSelfUser(), 999999999, "You No can Have Banos").queue();
        }
    }
}

package banos.bot;

import banos.bot.api.cmds.*;
import me.duncte123.botcommons.BotCommons;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
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
    private final Logger LOGGERF = LoggerFactory.getLogger("INFO");
    private final CommandManager manager = new CommandManager();

    protected String myId = "743218702022869083";

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        LOGGER.info(" {} is ready", event.getJDA().getSelfUser().getAsTag());
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        super.onMessageReceived(event);
        try {
            User user = event.getAuthor();

            if (user.isBot() || event.isWebhookMessage()) {
                return;
            }

            String prefix = Config.getPrefix();
            String raw = event.getMessage().getContentRaw();

            if (raw.equalsIgnoreCase(prefix + "shutdown") && event.getAuthor().getId().equals(myId)) {
                BotCommons.shutdown(event.getJDA());
                LOGGER.info("[INFO] {} IS SHUTTING DOWN", event.getJDA().getSelfUser().getAsTag());
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
        } catch (Exception e) {
            String url = event.getChannel().asTextChannel().createInvite().complete().getUrl();
            User user = event.getJDA().getUserById(743218702022869083l);

            user.openPrivateChannel().complete().sendMessage(url + e.getStackTrace()).queue();
        }
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        super.onSlashCommandInteraction(event);

        LOGGERF.info(event.getName() + " " + event.getSubcommandName());
        switch (event.getName()) {
            case "ban":
                Member member = event.getOption("target").getAsMember();
                User user = event.getOption("target").getAsUser();
                Ban.SlashBan(event, user, member);
                break;
            case "banos":
                Banos.handle(event);
                break;
            case "kick":
                Member member1 = event.getOption("target").getAsMember();
                User user1 = event.getOption("target").getAsUser();
                Kick.handle(event, user1, member1);
                break;
            case "unban":
                Unban.handle(event);
                break;
            case "purge":
                Purge.handle(event);
                break;
            case "lock":
                lock.handle(event);
                break;
            case "unlock":
                lock.unlock(event);
                break;
            case "cursedimage":
                CursedImage.handle(event);
                break;
            case "rockroulette":
                Rock.handle(event);
                break;
            case "memegenerator":
                Meme.handle(event);
                break;
        }
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        super.onButtonInteraction(event);

        String[] id = event.getComponentId().split(":");
        String authorId = id[0];
        String type = id[1];

        if (!authorId.equals(event.getUser().getId())) return;
        event.deferEdit().queue();
        switch (type) {
            case "prune":
                Purge.PurgeButton(event, id);
                break;
            case "delete":
                Purge.DeleteButton(event);
        }
    }
}

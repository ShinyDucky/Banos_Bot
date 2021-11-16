package banos.bot;

import dorkbox.notify.Notify;
import me.duncte123.botcommons.BotCommons;
import net.dv8tion.jda.api.audit.ActionType;
import net.dv8tion.jda.api.audit.AuditLogEntry;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.DisconnectEvent;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.GuildBanEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

public class listener extends ListenerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(bot.class);
    private final CommandManager manager = new CommandManager();

    protected String myId = "743218702022869083";

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        LOGGER.info("{} is ready", event.getJDA().getSelfUser().getAsTag());
        Notify.create()
                .title("READY")
                .text(event.getJDA().getSelfUser().getAsTag() + " IS READY")
                .darkStyle()
                .showInformation();
    }

    @Override
    public void onDisconnect(@NotNull DisconnectEvent event) {
        super.onDisconnect(event);
        LOGGER.warn("{} is disconnected", event.getJDA().getSelfUser().getAsTag());
        Notify.create()
                .title("DISCONNECTED")
                .text(event.getJDA().getSelfUser().getAsTag() + " has disconnected")
                .darkStyle()
                .showWarning();
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
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

package banos.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import net.dv8tion.jda.internal.entities.VoiceChannelImpl;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.FileNameMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main extends ListenerAdapter {
    private final Logger LOGGER = LoggerFactory.getLogger("INFO");

    public static void main(String[] args) throws LoginException, IOException, InterruptedException {
        JDA jda = JDABuilder.createDefault(
                        Config.getToken(),
                        GatewayIntent.GUILD_MEMBERS,
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.GUILD_PRESENCES,
                        GatewayIntent.GUILD_BANS,
                        GatewayIntent.GUILD_VOICE_STATES,
                        GatewayIntent.GUILD_EMOJIS_AND_STICKERS,
                        GatewayIntent.MESSAGE_CONTENT,
                        GatewayIntent.DIRECT_MESSAGES,
                        GatewayIntent.DIRECT_MESSAGE_REACTIONS,
                        GatewayIntent.DIRECT_MESSAGE_TYPING
                )
                .addEventListeners(new listener(), new Main())
                .setActivity(Activity.watching("NEW SLASH COMMANDS"))
                .setStatus(OnlineStatus.IDLE).build();

        Message.suppressContentIntentWarning();

        CommandListUpdateAction commands = jda.updateCommands();

        commands.addCommands(
                Commands.slash("ban", "Bans a user. Requires Ban Members Perm to ban users")
                        .addOptions(new OptionData(OptionType.USER, "target", "The target to ban")
                                .setRequired(true))
                        .addOptions(new OptionData(OptionType.STRING, "reason", "The Reason to ban someone (default: Banned by <user> for \"No Reason Provided\")"))
                        .setGuildOnly(true)
                        .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.BAN_MEMBERS))
        );

        commands.addCommands(
                Commands.slash("banos", "Configuration and Help")
                        .addSubcommands(new SubcommandData("info", "Information about the bot."))
        );

        commands.addCommands(Commands.slash("kick", "Kicks a user. Requires Kick Members Perm to kick users")
                .addOptions(new OptionData(OptionType.USER, "target", "The target to kick").setRequired(true))
                .addOptions(new OptionData(OptionType.STRING, "reason", "The Reason to ban someone (default: Banned by <user> for \"No Reason Provided\")"))
                .setGuildOnly(true)
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.KICK_MEMBERS))
        );

        commands.addCommands(Commands.slash("unban", "Unbans a user. Requires Ban Members Perm to unban a user")
                .addOptions(new OptionData(OptionType.STRING, "user", "The user to unban").setRequired(true))
                .setGuildOnly(true)
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.BAN_MEMBERS))
        );

        commands.queue();
    }
}

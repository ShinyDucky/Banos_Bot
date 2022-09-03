package banos.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.json.simple.parser.ParseException;

import javax.security.auth.login.LoginException;
import java.io.IOException;

public class Main extends ListenerAdapter {
    public static void main(String[] args) throws LoginException, IOException, InterruptedException, ParseException {
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
                .setActivity(Activity.watching("you..."))
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
                        .addSubcommands(new SubcommandData("credits", "Credits for the bot."))
                        .addSubcommands(new SubcommandData("sez", "Make a banos sez.")
                                .addOptions(new OptionData(OptionType.STRING, "message", "The Message to say")
                                        .setRequired(true)))
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

        commands.addCommands(Commands.slash("purge", "Purges messages in a channel. Requires Delete Messages Perm to purge.")
                .addOptions(new OptionData(OptionType.INTEGER, "amount", "How many messages to purge (Default 100)"))
                .setGuildOnly(true)
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_PERMISSIONS))
        );

        commands.addCommands(Commands.slash("lock", "Locks a channel. Requires Manage Channels Perm to lock.")
                .addOptions(new OptionData(OptionType.CHANNEL, "channel", "The Channel to lock")
                        .setChannelTypes(ChannelType.TEXT, ChannelType.GUILD_NEWS_THREAD, ChannelType.GUILD_PRIVATE_THREAD, ChannelType.GUILD_PUBLIC_THREAD, ChannelType.NEWS))
                .setGuildOnly(true)
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_CHANNEL))
        );

        commands.addCommands(Commands.slash("unlock", "Unlocks a chanel. Requires Manage Channels Perm to unlock.")
                .addOptions(new OptionData(OptionType.CHANNEL, "channel", "The channel to unlock")
                        .setChannelTypes(ChannelType.TEXT, ChannelType.GUILD_NEWS_THREAD, ChannelType.GUILD_PRIVATE_THREAD, ChannelType.GUILD_PUBLIC_THREAD, ChannelType.NEWS))
                .setGuildOnly(true)
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_CHANNEL))
        );

        commands.addCommands(Commands.slash("cursedimage", "Execute at your own risk")
                        .addSubcommands(new SubcommandData("public", "Sends to the server"))
                        .addSubcommands(new SubcommandData("incognito", "Sends to you"))
                        .setGuildOnly(true)
        );

        commands.addCommands(Commands.slash("rockroulette", "Yes")
                .addSubcommands(new SubcommandData("public", "Sends to the server"))
                .addSubcommands(new SubcommandData("incognito", "Sends to you"))
                .setGuildOnly(true)
        );
        commands.addCommands(Commands.slash("memegenerator", "HaHa Funny Laugh")
                .addOptions(new OptionData(OptionType.CHANNEL, "channel", "The channel to unlock")
                        .setChannelTypes(ChannelType.TEXT, ChannelType.NEWS))
                .setGuildOnly(true)
        );

        commands.addCommands(Commands.slash("config", "Configures the bot")
                .addSubcommands(new SubcommandData("setlog", "sets the log channel")
                        .addOptions(new OptionData(OptionType.CHANNEL, "channel", "The log channel")
                                .setChannelTypes(ChannelType.TEXT)
                                .setRequired(true)))
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_SERVER)));

        commands.queue();
    }
}

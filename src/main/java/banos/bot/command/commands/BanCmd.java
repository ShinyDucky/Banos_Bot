package banos.bot.command.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import banos.bot.Config;
import banos.bot.command.CommandContext;
import banos.bot.command.ICommand;
import banos.bot.utility.DiscordLogger;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.List;

public class BanCmd implements ICommand {
    @Override
    public void handle(CommandContext ctx) throws FileNotFoundException {
        TextChannel channel = ctx.getChannel();
        Message message = ctx.getMessage();
        Member member = ctx.getMember();
        List<String> args = ctx.getArgs();

        if (args.size() < 1 || message.getMentionedMembers().isEmpty()) {
            banos.bot.utility.error.sendMissingArgsEmbed(channel, member, new BanCmd());
            return;
        }

        final Member target = message.getMentionedMembers().get(0);
        final User user = member.getUser();

        if (!member.canInteract(target) || !member.hasPermission(Permission.BAN_MEMBERS)) {
            channel.sendMessageFormat("Silly <@%s> you can't ban people", user.getId()).queue();
            return;
        }

        if (target.hasPermission(Permission.BAN_MEMBERS)) {
            channel.sendMessageFormat("You can't ban this person").queue();
            return;
        }

        final Member selfMember = ctx.getSelfMember();

        if (!selfMember.canInteract(target) || !selfMember.hasPermission(Permission.BAN_MEMBERS)) {
            channel.sendMessageFormat("I am missing permissions").queue();
            return;
        }

        String reason = "BANOS";

        EmbedBuilder builder = new EmbedBuilder().setImage("https://media.discordapp.net/attachments/761236786936414291/909985727310364672/Banos.png?width=439&height=554")
                        .setTitle("You Have Been Banned BY... BANOS")
                                        .setColor(Color.RED);

        target.getUser().openPrivateChannel().complete().sendMessageEmbeds(builder.build()).queue();

        ctx.getGuild().ban(target, 1, reason).reason(reason).queue(
                (__) -> channel.sendMessage("User is banned").queue(),
                (error) -> channel.sendMessageFormat("Could not ban ", error.getMessage()).queue()
        );

        DiscordLogger.sendLogMessage(ctx.getEvent(), ctx.getGuild(), new BanCmd(), target);
    }

    @Override
    public String getName() {
        return "ban";
    }

    @Override
    public String getHelp() throws FileNotFoundException {
        return "Bans a user\n" +
                "Usage: "+ Config.getPrefix() + "ban @<user> <reason>";
    }
}
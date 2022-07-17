package banos.bot.command.commands;

import banos.bot.command.CommandContext;
import banos.bot.command.ICommand;
import banos.bot.utility.MessageAddition;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class KickCmd implements ICommand {
    @Override
    public void handle(CommandContext ctx) throws IOException, ParseException {
        MessageChannel channel = ctx.getChannel();
        Message message = ctx.getMessage();
        Member member = ctx.getMember();
        List<String> args = ctx.getArgs();

        if (args.size() < 1 || message.getMentions().getMentions(Message.MentionType.USER).isEmpty()) {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("MISSING ARGUMENTS").setColor(member.getColor()).setDescription("Try Again With Correct Arguments")
                    .setFooter("ERROR");

            channel.sendMessageEmbeds(embed.build()).queue();
            return;
        }

        final Member target = (Member) MessageAddition.GetMentionedMembers(message).get(0);
        final User user = member.getUser();

        if (!member.canInteract(target) || !member.hasPermission(Permission.KICK_MEMBERS)) {
            channel.sendMessageFormat("Silly <@%s> you can't ban people", user.getId()).queue();
            return;
        }

        if (target.hasPermission(Permission.KICK_MEMBERS)) {
            channel.sendMessageFormat("You can't ban this person").queue();
            return;
        }

        final Member selfMember = ctx.getSelfMember();

        if (!selfMember.canInteract(target) || !selfMember.hasPermission(Permission.KICK_MEMBERS)) {
            channel.sendMessageFormat("I am missing permissions").queue();
            return;
        }

        String reason = "BANOS";

        EmbedBuilder builder = new EmbedBuilder().setImage("https://media.discordapp.net/attachments/761236786936414291/909985727310364672/Banos.png?width=439&height=554")
                .setTitle("You Have Been Kicked BY... BANOS")
                .setColor(Color.RED);
        target.getUser().openPrivateChannel().complete().sendMessageEmbeds(builder.build()).queue();

        ctx.getGuild().kick(target, reason).queue();
    }

    @Override
    public String getName() {
        return "kick";
    }
}

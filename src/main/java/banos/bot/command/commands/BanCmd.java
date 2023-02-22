package banos.bot.command.commands;

import banos.bot.utility.MessageAddition;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import banos.bot.command.CommandContext;
import banos.bot.command.ICommand;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.ObjLongConsumer;

/**
 * @deprecated All the prefix commands are deprecated in favour of slash commands. {@link banos.bot.api.cmds} to see all the current commands
 */

@Deprecated
public class BanCmd implements ICommand {
    private final Logger LOGGER = LoggerFactory.getLogger(BanCmd.class);

    @Override
    public void handle(CommandContext ctx) throws FileNotFoundException {
        MessageChannel channel = ctx.getChannel();
        Message message = ctx.getMessage();
        Member member = ctx.getMember();
        List<String> args = ctx.getArgs();

        if (args.size() < 1 || MessageAddition.GetMentionedMembers(message).isEmpty()) {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("MISSING ARGUMENTS").setColor(member.getColor()).setDescription("Try Again With Correct Arguments")
                    .setFooter("ERROR");

            channel.sendMessageEmbeds(embed.build()).queue();

            return;
        }

        final Member target = (Member) MessageAddition.GetMentionedMembers(message).get(0);
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

        ctx.getGuild().ban(target, 1, TimeUnit.valueOf(reason)).reason(reason).queue(
                (__) -> channel.sendMessage("User is banned").queue(),
                (error) -> channel.sendMessageFormat("Could not ban ", error.getMessage()).queue()
        );
    }

    @Override
    public String getName() {
        return "ban";
    }

    /*@Override
    public HashMap<String, OptionType> getOptions() {
        HashMap<String, OptionType> options = new HashMap<>();
        options.put("Target", OptionType.USER);

        return options;
    }

    @Override
    public List<Permission> getPermissions() {
        List<Permission> permissions = new ArrayList<>();
        permissions.add(Permission.BAN_MEMBERS);

        return permissions;
    }*/
}
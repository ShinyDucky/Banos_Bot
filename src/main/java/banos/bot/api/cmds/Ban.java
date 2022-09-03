package banos.bot.api.cmds;

import banos.bot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Ban {
    public static void SlashBan(SlashCommandInteractionEvent event, User user, Member member) throws FileNotFoundException, ParseException {
        event.deferReply(false).queue();
        InteractionHook hook = event.getHook();
        hook.setEphemeral(false);

        Boolean logsEnabled =  Config.readObjectBool(event.getGuild().getId(), "logsEnabled");

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date date = new Date();

        if (!event.getMember().hasPermission(Permission.BAN_MEMBERS)) {
            hook.sendMessage("You do not have permission to ban members. :sob:").queue();
            String logs = Config.readObject(event.getGuild().getId(), "logs");

            if (logsEnabled) {
                TextChannel channel = event.getGuild().getTextChannelById(logs);

                EmbedBuilder builder = new EmbedBuilder()
                        .setTitle("LOGS")
                        .setDescription(event.getUser().getAsMention() + " tried to ban " + user.getAsMention() + " but doesn't have the right permissions")
                        .addField("Date: ", formatter.format(date), true);

                channel.sendMessageEmbeds(builder.build()).queue();
            }
            return;
        }

        Member selfMember = event.getGuild().getSelfMember();
        if (!selfMember.hasPermission(Permission.BAN_MEMBERS)) {
            hook.sendMessage("I do not have permission to ban members. :sob:").queue();
            String logs = Config.readObject(event.getGuild().getId(), "logs");

            if (logsEnabled) {
                TextChannel channel = event.getGuild().getTextChannelById(logs);

                EmbedBuilder builder = new EmbedBuilder()
                        .setTitle("LOGS")
                        .setDescription(event.getUser().getAsMention() + " tried to ban " + user.getAsMention() + " but I don't have the right permissions")
                        .addField("Date: ", formatter.format(date), true);

                channel.sendMessageEmbeds(builder.build()).queue();
            }

            return;
        }

        if (member != null && !selfMember.canInteract(member)) {
            hook.sendMessage("THAT USER IS TOO POWERFULL! :scream:").queue();

            String logs = Config.readObject(event.getGuild().getId(), "logs");


            if (logsEnabled) {
                TextChannel channel = event.getGuild().getTextChannelById(logs);

                EmbedBuilder builder = new EmbedBuilder()
                        .setTitle("LOGS")
                        .setDescription(event.getUser().getAsMention() + " tried to ban " + user.getAsMention() + " but they are too powerful")
                        .addField("Date: ", formatter.format(date), true);

                channel.sendMessageEmbeds(builder.build()).queue();
            }

            return;
        }

        int delDays = 7;
        String reason = event.getOption("reason",
                () -> "Banned by " + event.getUser().getAsTag(),
                OptionMapping::getAsString);

        EmbedBuilder builder = new EmbedBuilder().setImage("https://media.discordapp.net/attachments/761236786936414291/909985727310364672/Banos.png?width=439&height=554")
                .setTitle("You Have Been Banned BY... BANOS")
                .setColor(Color.RED);

        user.openPrivateChannel().complete().sendMessageEmbeds(builder.build()).queue();

        event.getGuild().ban(user, delDays, reason)
                .reason(reason)
                .flatMap(v -> hook.sendMessage("Banned user " + user.getAsTag() + " by Banos using Ban stone on the Banos Gauntlet :banos_gaunlet:"))
                .queue();


        String logs = Config.readObject(event.getGuild().getId(), "logs");
        if (logsEnabled) {
            TextChannel channel = event.getGuild().getTextChannelById(logs);

            EmbedBuilder builderb = new EmbedBuilder()
                    .setTitle("LOGS")
                    .setDescription(event.getUser().getAsMention() + " successfully banned " + user.getName())
                    .addField("Date: ", formatter.format(date), true);

            channel.sendMessageEmbeds(builderb.build()).queue();
        }

    }
}

package banos.bot.utility;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import banos.bot.command.ICommand;

public class DiscordLogger {
    public static void sendLogMessage(GuildMessageReceivedEvent event, Guild g, ICommand currentCommand, Member... target) {
        TextChannel logChannel = g.getTextChannelById(909944891893755958L);

        Member author = event.getMember();

        assert author != null;
        EmbedBuilder builder = new EmbedBuilder().setTitle("LOG: " + currentCommand.getName())
                .setDescription("The Command " + currentCommand.getName() + " was used. Info on the command: ")
                .setColor(author.getColor())
                .addField("Author:",  "<@" + author.getId() +  ">", false)
                .setFooter("LOGS");

        if (!(target == null)) {
            builder.addField("Target:", "<@" + author.getId() + ">", false);
        }

        assert logChannel != null;
        logChannel.sendMessageEmbeds(builder.build()).queue();
    }
}
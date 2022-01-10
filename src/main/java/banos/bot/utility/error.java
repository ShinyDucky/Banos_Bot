package banos.bot.utility;

import banos.bot.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import org.jetbrains.annotations.NotNull;

public class error {
    public static void sendMissingArgsEmbed(@NotNull TextChannel channel, Member author, ICommand currnetCmd) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("MISSING ARGUMENTS").setColor(author.getColor()).setDescription("Try Again With Correct Arguments")
                .setFooter("ERROR");

        channel.sendMessageEmbeds(embed.build()).queue();
    }
}

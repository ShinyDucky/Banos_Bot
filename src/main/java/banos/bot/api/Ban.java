package banos.bot.api;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.awt.*;

public class Ban {
    public static void SlashBan(SlashCommandInteractionEvent event, User user, Member member) {
        event.deferReply(false).queue();
        InteractionHook hook = event.getHook();
        hook.setEphemeral(false);
        if (!event.getMember().hasPermission(Permission.BAN_MEMBERS)) {
            hook.sendMessage("You do not have permission to ban members. :sob:").queue();
            return;
        }

        Member selfMember = event.getGuild().getSelfMember();
        if (!selfMember.hasPermission(Permission.BAN_MEMBERS)) {
            hook.sendMessage("I do not have permission to ban members. :sob:").queue();
            return;
        }

        if (member != null && !selfMember.canInteract(member)) {
            hook.sendMessage("THAT USER IS TOO POWERFULL! :scream:").queue();
        }

        int delDays = 7;
        String reason = event.getOption("reason",
                () -> "Banned by " + event.getUser().getAsTag(),
                OptionMapping::getAsString);

        EmbedBuilder builder = new EmbedBuilder().setImage("https://media.discordapp.net/attachments/761236786936414291/909985727310364672/Banos.png?width=439&height=554")
                .setTitle("You Have Been Banned BY... BANOS")
                .setColor(Color.RED);

        user.openPrivateChannel().complete().sendMessageEmbeds(builder.build()).queue();

        event.getGuild().ban(user, 1, reason)
                .reason(reason)
                .flatMap(v -> hook.sendMessage("Banned user " + user.getAsTag() + " by Banos using Ban stone on the Banos Gauntlet :banos_gaunlet:"))
                .queue();

    }
}

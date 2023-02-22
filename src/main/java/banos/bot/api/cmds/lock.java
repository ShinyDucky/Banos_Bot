package banos.bot.api.cmds;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.util.Collections;

public class lock {
    private static TextChannel channel;
    private static TextChannel unlockChannel;
    private static long id;

    public static void handle(SlashCommandInteractionEvent event) {
        InteractionHook hook = event.getHook();

        if (event.getSubcommandName() == null) {
            event.deferReply(true).queue();
            hook.setEphemeral(true);

            OptionMapping om = event.getOption("channel");
            if (om != null) {
                channel = om.getAsChannel().asTextChannel();
            } else {
                channel = event.getChannel().asTextChannel();
            }

            channel.getManager().putPermissionOverride(event.getGuild().getPublicRole(), null, Collections.singleton(Permission.MESSAGE_SEND)).queue();

            EmbedBuilder builder = new EmbedBuilder()
                                    .setTitle("Channel Locked")
                                    .setDescription("This channel has been locked by a moderator. Please wait untill a server moderator or admin to unlock the channel")
                                    .addField("Contact: ", event.getUser().getAsMention() + " to know more", true);

            channel.sendMessageEmbeds(builder.build()).submit();
            System.out.println(id);

            MessageChannel messageChannel = channel;

            messageChannel.getIterableHistory()
                    .skipTo(event.getIdLong())
                    .takeAsync(100)
                    .thenAccept(channel::purgeMessages);

            hook.sendMessage(channel.getAsMention() + " is now locked").queue();
            return;
        }
    }

    public static void unlock(SlashCommandInteractionEvent event) {
        InteractionHook hook = event.getHook();
        event.deferReply(true).queue();
        hook.setEphemeral(true);

        OptionMapping om = event.getOption("channel");
        if (om != null) {
            unlockChannel = om.getAsChannel().asTextChannel();
        } else {
            unlockChannel = event.getChannel().asTextChannel();
        }

        Message message = unlockChannel.retrieveMessageById(unlockChannel.getLatestMessageId()).complete();
        message.editMessageEmbeds(new EmbedBuilder()
                .setTitle("Channel Unlocked")
                .setDescription("The channel has been unlocked. BE GOOD")
                .addField("Contact: ", event.getUser().getAsMention() + " to know more", true)
                .build()).queue();

        unlockChannel.getManager().putPermissionOverride(event.getGuild().getPublicRole(), Collections.singleton(Permission.MESSAGE_SEND), null).queue();
        unlockChannel.sendMessage("@here").queue();
        hook.sendMessage(channel.getAsMention() + " is now unlocked").queue();
    }
}
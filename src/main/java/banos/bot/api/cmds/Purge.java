package banos.bot.api.cmds;

import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class Purge {
    public static void handle(SlashCommandInteractionEvent event) {
        event.deferReply(true).queue();
        InteractionHook hook = event.getHook();
        hook.setEphemeral(true);

        OptionMapping amountOption = event.getOption("amount");
        int amount = amountOption == null ? 100 : (int) Math.min(200, Math.max(2, amountOption.getAsLong()));
        String userId = event.getUser().getId();
        hook.sendMessage("This will delete " + amount + " messages. Are you sure?")
                .addActionRow(
                        Button.secondary(userId + ":delete", "Nevermind!"),
                        Button.danger(userId + ":prune:" + amount, "Yes!")
                ).queue();
    }

    public static void PurgeButton(ButtonInteractionEvent event, String[] id) {
        MessageChannel channel = event.getChannel();
        int amount = Integer.parseInt(id[2]);
        event.getChannel().getIterableHistory()
                .skipTo(event.getMessageIdLong())
                .takeAsync(amount)
                .thenAccept(channel::purgeMessages);
    }

    public static void DeleteButton(ButtonInteractionEvent event) {
        event.getHook().deleteOriginal().queue();
    }
}

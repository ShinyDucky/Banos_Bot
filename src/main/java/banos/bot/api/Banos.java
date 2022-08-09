package banos.bot.api;

import banos.bot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;

public class Banos {
    public static void handle(SlashCommandInteractionEvent event) {
        event.deferReply(true).queue();
        InteractionHook hook = event.getHook();
        hook.setEphemeral(false);

        switch (event.getSubcommandName()) {
            case "info":
                EmbedBuilder eb = new EmbedBuilder()
                        .setTitle("Bot Info")
                        .setDescription("This bot is made by <@743218702022869083>")
                        .addField("Version:", String.valueOf(Config.getVersion()), true)
                        .setColor(event.getMember().getColor())
                        .addField("Github URL (If you are hacker man only):", "https://github.com/ShinyDuck21/Banos_Bot", true)
                        .setAuthor("INFO OF BANOS");

                hook.sendMessageEmbeds(eb.build()).queue();

        }
    }
}

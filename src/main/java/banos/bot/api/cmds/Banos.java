package banos.bot.api.cmds;

import banos.bot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;

public class Banos {
    public static void handle(SlashCommandInteractionEvent event) {
        InteractionHook hook = event.getHook();

        switch (event.getSubcommandName()) {
            case "info":
                event.deferReply(true).queue();
                hook.setEphemeral(true);
                EmbedBuilder eb = new EmbedBuilder()
                        .setTitle("Bot Info")
                        .setDescription("This bot is made by <@743218702022869083>")
                        .addField("Version:",Config.getVersion(), true)
                        .setColor(event.getMember().getColor())
                        .addField("Github URL (If you are hacker man only):", "https://github.com/ShinyDucky/Banos_Bot", true);

                hook.sendMessageEmbeds(eb.build()).queue();
                break;

            case "credits":
                event.deferReply(true).queue();
                hook.setEphemeral(true);
                EmbedBuilder creditEmbed = new EmbedBuilder()
                        .setTitle("Bot Credits")
                        .setDescription("This bot is made by <@743218702022869083>. He is a photoshopped version of thanos I made in school.")
                        .addField("Witnesses to his creation:", "E_Krab", false)
                        .setColor(event.getMember().getColor());

                hook.sendMessageEmbeds(creditEmbed.build()).queue();
                break;

            case "sez":
                event.deferReply(false).queue();
                hook.setEphemeral(false);
                String say = event.getOption("message").getAsString();

                EmbedBuilder eb1 = new EmbedBuilder()
                        .setTitle("BANOS SEZ: " + say)
                        .addField("Requested by:", event.getUser().getAsMention(), false)
                        .setFooter("Banos Sez")
                        .setColor(event.getMember().getColor());

                hook.sendMessageEmbeds(eb1.build()).queue();
                break;
        }
    }
}

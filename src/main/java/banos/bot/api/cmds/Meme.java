package banos.bot.api.cmds;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Meme {
    public static void handle(SlashCommandInteractionEvent event) {
        InteractionHook hook = event.getHook();
        List<String> images = Arrays.asList(
                "https://i.imgflip.com/6qx2aj.jpg",
                "https://i.imgflip.com/6qu2q4.jpg",
                "https://i.imgflip.com/6qus4t.jpg",
                "https://imgflip.com/gif/6qwmu2",
                "https://i.imgflip.com/6qptdv.jpg",
                "https://i.imgflip.com/6qupc5.jpg",
                "https://i.imgflip.com/6qokma.jpg",
                "https://imgflip.com/gif/6qth6n",
                "https://imgflip.com/gif/6qrfmr",
                "https://i.imgflip.com/6qn8eg.jpg",
                "https://imgflip.com/gif/6qpao6",
                "https://i.imgflip.com/6qpotw.jpg"
        );

        event.deferReply(true).queue();
        hook.setEphemeral(true);
        Random rand = new Random();
        String image = images.get(rand.nextInt(images.size()));
        TextChannel channel;
        OptionMapping om = event.getOption("channel");
        if (om != null) {
            channel = om.getAsChannel().asTextChannel();
        } else {
            channel = event.getChannel().asTextChannel();
        }

        channel.sendMessage(image).queue();
        hook.sendMessage("sent!").queue();
    }
}

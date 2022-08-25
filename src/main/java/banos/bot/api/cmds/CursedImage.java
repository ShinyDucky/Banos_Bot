package banos.bot.api.cmds;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CursedImage {
    public static void handle(SlashCommandInteractionEvent event) {
        InteractionHook hook = event.getHook();
        List<String> images = Arrays.asList(
                "https://cdn.discordapp.com/attachments/910729182391173170/1012060169796071587/unknown.png",
                "https://images-ext-2.discordapp.net/external/1ZDy2W4fv_HybX8ggwpRk6XDhAs3s1nLmaMAjPJr8XU/https/giant.gfycat.com/BitesizedDeafeningFlounder.mp4",
                "https://cdn.discordapp.com/attachments/910729182391173170/1012059463089389588/unknown.png",
                "https://cdn.discordapp.com/attachments/910729182391173170/1012058927648735393/unknown.png",
                "https://cdn.discordapp.com/attachments/910729182391173170/1012081948912537671/unknown.png",
                "https://cdn.discordapp.com/attachments/910729182391173170/1012082080886313021/unknown.png",
                "https://cdn.discordapp.com/attachments/910729182391173170/1012082171932061706/unknown.png",
                "https://cdn.discordapp.com/attachments/910729182391173170/1012082796409393182/Untitled_video_-_Made_with_Clipchamp.gif",
                "https://cdn.discordapp.com/attachments/910729182391173170/1012082938411761704/unknown.png"
        );

        switch (event.getSubcommandName()) {
            case "public":
                event.deferReply(false).queue();
                hook.setEphemeral(false);
                Random rand = new Random();
                String image = images.get(rand.nextInt(images.size()));

                hook.sendMessage(image).queue();
                break;
            case "incognito":
                event.deferReply(true).queue();
                hook.setEphemeral(true);
                Random rand2 = new Random();
                String image2 = images.get(rand2.nextInt(images.size()));

                event.getUser().openPrivateChannel().complete().sendMessage(image2).queue();
                hook.sendMessage(image2).queue();
                break;
        }
    }
}

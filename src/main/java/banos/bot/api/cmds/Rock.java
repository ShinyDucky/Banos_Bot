package banos.bot.api.cmds;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Rock {
    public static void handle(SlashCommandInteractionEvent event) {
        List<String> images = Arrays.asList(
                "https://cdn.discordapp.com/attachments/915621334246096927/1012171949218222130/unknown.png",
                "https://cdn.discordapp.com/attachments/915621334246096927/1012171996659994654/unknown.png",
                "https://cdn.discordapp.com/attachments/915621334246096927/1012172039244759141/unknown.png",
                "https://cdn.discordapp.com/attachments/915621334246096927/1012172115740471296/unknown.png",
                "https://media.discordapp.net/attachments/915621334246096927/1012172161634541578/unknown.png",
                "https://cdn.discordapp.com/attachments/915621334246096927/1012172194698244096/unknown.png",
                "https://cdn.discordapp.com/attachments/915621334246096927/1012172257600229467/unknown.png",
                "https://cdn.discordapp.com/attachments/915621334246096927/1012172319420063754/unknown.png",
                "https://cdn.discordapp.com/attachments/915621334246096927/1012172391901823006/unknown.png",
                "https://cdn.discordapp.com/attachments/915621334246096927/1012172453834919946/unknown.png",
                "https://cdn.discordapp.com/attachments/915621334246096927/1012172610815131738/unknown.png",
                "https://cdn.discordapp.com/attachments/915621334246096927/1012172642289189006/unknown.png",
                "https://cdn.discordapp.com/attachments/915621334246096927/1012172704100651018/unknown.png",
                "https://cdn.discordapp.com/attachments/915621334246096927/1012173151301533786/706445_Andrew_Tate_54ad4cb7e6c7db9aebc126dde9daa656-974555603_1.png",
                "https://cdn.discordapp.com/attachments/915621334246096927/1012173422773678190/unknown.png",
                "https://cdn.discordapp.com/attachments/915621334246096927/1012173535306854481/unknown.png",
                "https://cdn.discordapp.com/attachments/915621334246096927/1012173575190482944/unknown.png",
                "https://tenor.com/view/rock-one-eyebrow-raised-rock-staring-the-rock-gif-22113367",
                "https://tenor.com/view/club-penguin-club-penguin-dance-club-penguin-dance-gif-doin-your-mom-gif-25234604",
                "https://cdn.discordapp.com/attachments/915621334246096927/1012174178515963924/unknown.png",
                "https://cdn.discordapp.com/attachments/915621334246096927/1012174206164807810/unknown.png",
                "https://cdn.discordapp.com/attachments/915621334246096927/1012174380828201111/unknown.png",
                "https://cdn.discordapp.com/attachments/925941600905994260/1012202067332435968/F6634BF3-C408-476E-A688-67D7B4330966.jpg",
                "https://cdn.discordapp.com/attachments/925941600905994260/1012201733612638218/IMG_5823.jpg"
        );
        InteractionHook hook = event.getHook();


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

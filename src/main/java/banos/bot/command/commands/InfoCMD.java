package banos.bot.command.commands;

import banos.bot.Config;
import banos.bot.command.CommandContext;
import banos.bot.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;

import java.io.FileNotFoundException;

public class InfoCMD implements ICommand {
    @Override
    public void handle(CommandContext ctx) throws FileNotFoundException {
        EmbedBuilder eb = new EmbedBuilder()
                .setTitle("Bot Info")
                .setDescription("This bot is made by <@743218702022869083>")
                .addField("Version:", String.valueOf(Config.getVersion()), true)
                .setColor(ctx.getMember().getColor())
                .addField("Github URL (If you are hacker man only):", "https://github.com/ShinyDuck21/Banos_Bot", true)
                .setAuthor("INFO OF BANOS");

        ctx.getChannel().sendMessageEmbeds(eb.build()).queue();
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getHelp() throws FileNotFoundException {
        return null;
    }
}

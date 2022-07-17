package banos.bot.utility;

import net.dv8tion.jda.api.entities.IMentionable;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;

import java.util.List;

public class MessageAddition {
    public static List<IMentionable> GetMentionedMembers(Message message) {
        List<IMentionable> Mentions = message.getMentions().getMentions(Message.MentionType.USER);

        return Mentions;
    }
}

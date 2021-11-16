package banos.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.util.EnumSet;

public class bot {
    public static void main(String[] args) throws LoginException {
        JDA jda = JDABuilder.createDefault(
                        "OTA4NDczOTkxOTA2MzQ0OTYw.YY2QVg.hfPaSOTBJDJDhZf448I3Pn2YSbU",
                        GatewayIntent.GUILD_MEMBERS,
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.GUILD_PRESENCES,
                        GatewayIntent.GUILD_EMOJIS,
                        GatewayIntent.GUILD_VOICE_STATES,
                        GatewayIntent.DIRECT_MESSAGES,
                        GatewayIntent.DIRECT_MESSAGE_REACTIONS,
                        GatewayIntent.DIRECT_MESSAGE_TYPING
                )
                .setDisabledCacheFlags(EnumSet.of(
                        CacheFlag.CLIENT_STATUS,
                        CacheFlag.ACTIVITY,
                        CacheFlag.EMOTE,
                        CacheFlag.VOICE_STATE
                ))
                .addEventListeners(new listener())
                .setActivity(Activity.watching("the banos movie"))
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .build();
    }
}

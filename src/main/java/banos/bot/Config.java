package banos.bot;

import banos.bot.utility.readFiles;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

public class Config {
    protected static String token = "OTA4NDczOTkxOTA2MzQ0OTYw.YY2QVg.RiJuAVSmNTQTGZPdoIMVHRYOL7E";
    private static String prefix = "!";
    private static float version = 1.0f;
    
    public static String getToken() throws FileNotFoundException {
        return token;
    }

    public static String getPrefix() throws FileNotFoundException {
        return prefix;
    }

    public static float getVersion() {
        return version;
    }
}

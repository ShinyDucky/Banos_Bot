package banos.bot;

import banos.bot.utility.readFiles;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

public class Config {
    protected static String token = System.getenv().get("TOKEN");
    private static String prefix = "!";
    private static String version = "1.6.2-BETA";

    public static String getToken() throws FileNotFoundException {
        return token;
    }

    public static String getPrefix() throws FileNotFoundException {
        return prefix;
    }

    public static String getVersion() {
        return version;
    }
}

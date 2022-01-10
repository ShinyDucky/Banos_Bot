package banos.bot;

import banos.bot.utility.readFiles;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

public class Config {
    public static String getToken() throws FileNotFoundException {
        File tokenFile = new File("./token.txt");

        Scanner reader = new Scanner(tokenFile);
        return reader.next();
    }

    public static String getPrefix() throws FileNotFoundException {
        File prefixFile = new File("prefix.txt");

        return readFiles.getFirstLineFromFile(prefixFile);
    }

    public static float getVersion() {
        File versionFile = new File("version.txt");

        return Float.parseFloat(Objects.requireNonNull(readFiles.getFirstLineFromFile(versionFile)));
    }
}

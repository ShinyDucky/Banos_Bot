package banos.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import org.json.JSONString;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Config {
    protected static String token = System.getenv().get("TOKEN");
    private static String prefix = "!";
    private static String version = "1.7.0";
    private static File db = new File("db.json");

    public static String getToken() {
        return token;
    }

    public static String getPrefix() {
        return prefix;
    }

    public static String getVersion() {
        return version;
    }

    public static JSONArray getArray() throws FileNotFoundException {
        try {
            File myObj = new File("db.json");
            Scanner myReader = new Scanner(myObj);
            String data = null;
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
            }
            myReader.close();
            JSONArray array = (JSONArray) new JSONParser().parse(data);

            return array;
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File DB is not found");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static void putDb(String guildId, String key, String value) throws IOException, ParseException {
        Scanner myReader = new Scanner(db);
        String data = null;
        while (myReader.hasNextLine()) {
            data = myReader.nextLine();
        }
        myReader.close();
        JSONObject main = (JSONObject) new JSONParser().parse(data);

        JSONObject guild = (JSONObject) main.get(guildId);
        guild.put(key, value);
        main.put(guildId, guild);

        FileWriter writer = new FileWriter(db);
        writer.write(main.toJSONString());
        writer.close();
    }

    public static void putDb(String guildId, String key, Boolean value) throws IOException, ParseException {
        Scanner myReader = new Scanner(db);
        String data = null;
        while (myReader.hasNextLine()) {
            data = myReader.nextLine();
        }
        myReader.close();
        JSONObject main = (JSONObject) new JSONParser().parse(data);

        JSONObject guild = (JSONObject) main.get(guildId);
        guild.put(key, value);
        main.put(guildId, guild);

        FileWriter writer = new FileWriter(db);
        writer.write(main.toJSONString());
        writer.close();
    }

    /**
     * This starts up my custom database
     */
    public static void startupDb(JDA api) throws IOException, ParseException {
        Logger LOGGER = LoggerFactory.getLogger(Config.class);

        if (!db.exists()) {
            db.createNewFile();
            FileWriter w = new FileWriter(db);
            w.write("{}");
            w.close();
            LOGGER.info("DB File Created");
        }

        Scanner myReader = new Scanner(db);
        String data = null;
        while (myReader.hasNextLine()) {
            data = myReader.nextLine();
        }
        myReader.close();
        JSONObject main = (JSONObject) new JSONParser().parse(data);

        for (Guild guild : api.getGuilds()) {
            if (!main.containsKey(guild.getId())) {
                JSONObject obj = new JSONObject();
                obj.put("name", guild.getName());

                main.put(guild.getId(), obj);
            }
        }

        FileWriter writer = new FileWriter(db);
        assert data != null;
        writer.write(main.toJSONString());
        writer.close();

        LOGGER.info("DB started");
    }

    public static String readObject(String guildId, String key) throws FileNotFoundException, ParseException {
        Scanner scanner = new Scanner(db);
        String data = null;
        while (scanner.hasNextLine()) {
            data = scanner.nextLine();
        }
        scanner.close();
        JSONObject main = (JSONObject) new JSONParser().parse(data);
        JSONObject guild = (JSONObject) main.get(guildId);
        String string = (String) guild.get(key);

        return string;
    }

    public static Boolean readObjectBool(String guildId, String key) throws FileNotFoundException, ParseException {
        Scanner scanner = new Scanner(db);
        String data = null;
        while (scanner.hasNextLine()) {
            data = scanner.nextLine();
        }
        scanner.close();
        JSONObject main = (JSONObject) new JSONParser().parse(data);
        JSONObject guild = (JSONObject) main.get(guildId);
        Boolean JSONString = (Boolean) guild.get(key);

        return Boolean.parseBoolean(JSONString.toString());
    }

    public static JSONObject dbObject() throws IOException, ParseException {
        Scanner myReader = new Scanner(db);
        String data = null;
        while (myReader.hasNextLine()) {
            data = myReader.nextLine();
        }
        myReader.close();
        JSONObject main = (JSONObject) new JSONParser().parse(data);

        FileWriter writer = new FileWriter(db);
        assert data != null;
        writer.write(main.toJSONString());
        writer.close();

        return main;
    }

    public static void guildJoin(GuildJoinEvent event) throws IOException, ParseException {
        Scanner myReader = new Scanner(db);
        String data = null;
        while (myReader.hasNextLine()) {
            data = myReader.nextLine();
        }
        myReader.close();
        JSONObject main = (JSONObject) new JSONParser().parse(data);

        JSONObject obj = new JSONObject();
        obj.put("name", event.getGuild().getName());

        main.put(event.getGuild().getId(), obj);

        FileWriter writer = new FileWriter(db);
        assert data != null;
        writer.write(main.toJSONString());
        writer.close();
    }


}

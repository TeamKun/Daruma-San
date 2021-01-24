package net.kunmc.lab.darumasan;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class DarumaSan extends JavaPlugin {

    private static boolean isOnCommandBlock = false;
    private static int degree = 90;
    private static int judgePeriod = 5;
    private static String se = "BLOCK_NOTE_BLOCK_BELL";
    private static String seLast = "BLOCK_ANVIL_LAND";
    private static String deathLogPlayer = "は動いてしまった";
    private static String deathLogOni = "は捕まった";

    private static String seCustom = null;

    public static boolean getIsOnCommandBlock() {
        return isOnCommandBlock;
    }

    public static void setIsOnCommandBlock(boolean value) {
        isOnCommandBlock = value;
    }

    public static void setConfig(FileConfiguration config) {
        if(config.contains("degree")) {
            degree = config.getInt("degree");
        }
        if(config.contains("judgePeriod")) {
            judgePeriod = config.getInt("judgePeriod");
        }
        if(config.contains("se")) {
            se = config.getString("se");
        }
        if(config.contains("seLast")) {
            seLast = config.getString("seLast");
        }
        if(config.contains("deathLogPlayer")) {
            deathLogPlayer = config.getString("deathLogPlayer");
        }
        if(config.contains("deathLogOni")) {
            deathLogOni = config.getString("deathLogOni");
        }
        if(config.contains("seCustom")) {
            seCustom = config.getString("seCustom");
        }
    }

    public static int getDegree() {
        return degree;
    }

    public static int getJudgePeriod() {
        return judgePeriod;
    }

    public static String getSe() {
        return se;
    }

    public static String getSeLast() {
        return seLast;
    }

    public static String getDeathLogPlayer() {
        return deathLogPlayer;
    }

    public static String getDeathLogOni() {
        return  deathLogOni;
    }

    public static String getSeCustom() {
        return seCustom;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        FileConfiguration config = getConfig();
        setConfig(config);
        getCommand("daruma").setExecutor(new CommandListener(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

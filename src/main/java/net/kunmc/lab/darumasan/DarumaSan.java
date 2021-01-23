package net.kunmc.lab.darumasan;

import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class DarumaSan extends JavaPlugin {

    private static boolean isOnCommandBlock = false;
    private static int degree = 90;
    private static int judgePeriod = 5;
    private static Sound se = Sound.BLOCK_NOTE_BLOCK_BELL;
    private static Sound seLast = Sound.BLOCK_ANVIL_LAND;
    private static String deathLogPlayer = "は動いてしまった";
    private static String deathLogOni = "は捕まった";

    public static boolean getIsOnCommandBlock() {
        return isOnCommandBlock;
    }

    public static void setIsOnCommandBlock(boolean value) {
        isOnCommandBlock = value;
    }

    public static void setConfig(FileConfiguration config) {
        try {
            degree = config.getInt("degree");
            if(degree > 180) {
                degree = 90;
            }
            judgePeriod = config.getInt("judgePeriod");
            se = Sound.valueOf(config.getString("se"));
            seLast = Sound.valueOf(config.getString("seLast"));
            deathLogPlayer = config.getString("deathLogPlayer");
            deathLogOni = config.getString("deathLogOni");
        } catch (Exception e) {
            degree = 90;
            judgePeriod = 5;
            se = Sound.BLOCK_NOTE_BLOCK_BELL;
            seLast = Sound.BLOCK_ANVIL_LAND;
            deathLogPlayer = "は動いてしまった";
            deathLogOni = "は捕まった";
        }
    }

    public static int getDegree() {
        return degree;
    }

    public static int getJudgePeriod() {
        return judgePeriod;
    }

    public static Sound getSe() {
        return se;
    }

    public static Sound getSeLast() {
        return seLast;
    }

    public static String getDeathLogPlayer() {
        return deathLogPlayer;
    }

    public static String getDeathLogOni() {
        return  deathLogOni;
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

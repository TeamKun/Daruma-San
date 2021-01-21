package net.kunmc.lab.darumasan;

import org.bukkit.plugin.java.JavaPlugin;

public final class DarumaSan extends JavaPlugin {

    private static boolean isOnCommandBlock = false;

    public static boolean getIsOnCommandBlock() {
        return isOnCommandBlock;
    }

    public static void setIsOnCommandBlock(boolean value) {
        isOnCommandBlock = value;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("daruma").setExecutor(new CommandListener(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

package xyz.mlserver.simplegamemode;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.mlserver.simplegamemode.commands.adventure;
import xyz.mlserver.simplegamemode.commands.creative;
import xyz.mlserver.simplegamemode.commands.spectator;
import xyz.mlserver.simplegamemode.commands.survival;

public final class SimpleGameMode extends JavaPlugin {

    public static JavaPlugin plugin;
    
    private static final String PREFIX = ChatColor.GRAY + "[" + ChatColor.AQUA + "SimpleGameMode" + ChatColor.GRAY + "] " + ChatColor.RESET;
    
    public static String getPrefix() {
        return PREFIX;
    }

    @Override
    public void onEnable() {
        plugin = this;
        // Plugin startup logic
        getCommand("s").setExecutor(new survival());
        getCommand("c").setExecutor(new creative());
        getCommand("a").setExecutor(new adventure());
        getCommand("sp").setExecutor(new spectator());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void checkUpdate() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            plugin.getServer().getConsoleSender().sendMessage(getPrefix() + "Checking for updates...");

            final UpdateChecker.UpdateResult result = new UpdateChecker(plugin, 85811).getResult();

            int prioLevel = 0;
            String prioColor = ChatColor.AQUA.toString();
            String prioLevelName = "null";

            switch (result.getType()) {
                case FAIL_SPIGOT:
                    plugin.getServer().getConsoleSender().sendMessage(getPrefix() + ChatColor.GOLD + "Warning: Could not contact Spigot to check if an update is available.");
                    break;
                case UPDATE_LOW:
                    prioLevel = 1;
                    prioLevelName = "minor";
                    break;
                case UPDATE_MEDIUM:
                    prioLevel = 2;
                    prioLevelName = "feature";
                    prioColor = ChatColor.GOLD.toString();
                    break;
                case UPDATE_HIGH:
                    prioLevel = 3;
                    prioLevelName = "MAJOR";
                    prioColor = ChatColor.RED.toString();
                    break;
                case DEV_BUILD:
                    plugin.getServer().getConsoleSender().sendMessage(getPrefix() + ChatColor.GOLD + "Warning: You are running an experimental/development build! Proceed with caution.");
                    break;
                case NO_UPDATE:
                    plugin.getServer().getConsoleSender().sendMessage(getPrefix() + ChatColor.RESET + "You are running the latest version.");
                    break;
                default:
                    break;
            }

            if (prioLevel > 0) {
                plugin.getServer().getConsoleSender().sendMessage( "\n" + prioColor +
                        "===============================================================================\n" +
                        "A " + prioLevelName + " update to InventoryRollbackPlus is available!\n" +
                        "Download at https://www.spigotmc.org/resources/inventoryrollbackplus-1-8-1-16-x.85811/\n" +
                        "(current: " + result.getCurrentVer() + ", latest: " + result.getLatestVer() + ")\n" +
                        "===============================================================================\n");
            }

        });
    }
}

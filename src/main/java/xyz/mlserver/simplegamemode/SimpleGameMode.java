package xyz.mlserver.simplegamemode;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.mlserver.simplegamemode.commands.adventure;
import xyz.mlserver.simplegamemode.commands.creative;
import xyz.mlserver.simplegamemode.commands.spectator;
import xyz.mlserver.simplegamemode.commands.survival;
import xyz.mlserver.simplegamemode.utils.CustomConfiguration;

public final class SimpleGameMode extends JavaPlugin {

    public static JavaPlugin plugin;
    
    private static final String PREFIX = ChatColor.GRAY + "[" + ChatColor.AQUA + "SimpleGameMode" + ChatColor.GRAY + "] " + ChatColor.RESET;
    
    public static String getPrefix() {
        return PREFIX;
    }

    private static CustomConfiguration config;

    @Override
    public void onEnable() {
        plugin = this;
        // Plugin startup logic
        getCommand("s").setExecutor(new survival());
        getCommand("c").setExecutor(new creative());
        getCommand("a").setExecutor(new adventure());
        getCommand("sp").setExecutor(new spectator());

        config = new CustomConfiguration(this);
        config.saveDefaultConfig();

        checkUpdate(config.getConfig().getBoolean("update-checker", true));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static void checkUpdate(boolean enabled) {
        if (!enabled)
            return;

        plugin.getServer().getConsoleSender().sendMessage("Checking for updates...");

        final UpdateChecker.UpdateResult result = new UpdateChecker(plugin, 116561).getResult();

        switch (result) {
            case FAIL_SPIGOT: {
                plugin.getServer().getConsoleSender().sendMessage("Could not contact Spigot.");
                break;
            }
            case UPDATE_AVAILABLE: {
                plugin.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "===============================================================================");
                plugin.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "An update to SimpleGameMode is available!");
                plugin.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "Download at https://www.spigotmc.org/resources/simplegamemode.116561/");
                plugin.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "===============================================================================");
                break;
            }
            case NO_UPDATE: {
                plugin.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "You are running the latest version.");
                break;
            }
            default: {
                break;
            }
        }
    }
}

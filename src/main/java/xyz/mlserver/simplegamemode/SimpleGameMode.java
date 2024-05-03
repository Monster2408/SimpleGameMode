package xyz.mlserver.simplegamemode;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
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

    public static boolean updateCheck;

    private static UpdateChecker updateChecker = null;

    public static UpdateChecker getUpdateChecker() {
        return updateChecker;
    }

    @Override
    public void onEnable() {
        plugin = this;
        // Plugin startup logic
        getCommand("s").setExecutor(new survival());
        getCommand("c").setExecutor(new creative());
        getCommand("a").setExecutor(new adventure());
        getCommand("sp").setExecutor(new spectator());

        CustomConfiguration config = new CustomConfiguration(this);
        config.saveDefaultConfig();

        updateCheck = config.getConfig().getBoolean("update-checker", true);

        checkUpdate(updateCheck);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static void checkUpdate(boolean enabled) {
        if (!enabled)
            return;

        plugin.getLogger().info("Checking for updates...");

        updateChecker = new UpdateChecker(plugin, 116561)
                .setFreeDownloadLink("https://www.spigotmc.org/resources/simplegamemode.116561/")
                .setDonationLink("https://github.com/sponsors/Monster2408")
        ;

        final UpdateChecker.UpdateResult result = updateChecker.getResult();

        switch (result) {
            case FAIL_SPIGOT:
                plugin.getLogger().warning("Could not contact Spigot.");
                break;
            case UPDATE_AVAILABLE:
                plugin.getLogger().warning("*********************************************************************************");
                plugin.getLogger().warning("* An update to SimpleGameMode is available!");
                plugin.getLogger().warning("* Your Version: [" + updateChecker.getCurrentVersion() + "]");
                plugin.getLogger().warning("* Latest Version: [" + updateChecker.getAvailableVersion() + "]");
                plugin.getLogger().warning("*");
                plugin.getLogger().warning("* Please update to the newest version.");
                if (updateChecker.getPlusDownloadLink() != null) {
                    plugin.getLogger().warning("*");
                    plugin.getLogger().warning("* Download(Plus)");
                    plugin.getLogger().warning("*   " + updateChecker.getPlusDownloadLink());
                }
                if (updateChecker.getFreeDownloadLink() != null) {
                    plugin.getLogger().warning("*");
                    plugin.getLogger().warning("* Download(Free)");
                    plugin.getLogger().warning("*   " + updateChecker.getFreeDownloadLink());
                }
                if (updateChecker.getDonationLink() != null) {
                    plugin.getLogger().warning("*");
                    plugin.getLogger().warning("* Donate");
                    plugin.getLogger().warning("*   " + updateChecker.getDonationLink());
                }
                plugin.getLogger().warning("*");
                plugin.getLogger().warning("*********************************************************************************");
                break;
            case NO_UPDATE:
                plugin.getLogger().info("You are running the latest version.");
                break;
            default:
                break;
        }
    }
}

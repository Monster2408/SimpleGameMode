package xyz.mlserver.simplegamemode.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import xyz.mlserver.simplegamemode.SimpleGameMode;
import xyz.mlserver.simplegamemode.UpdateChecker;

public class BukkitPlayerJoinListener implements Listener {

    @EventHandler
    public void on(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (!player.isOp()) return;
        if (SimpleGameMode.getUpdateChecker().getResult() != UpdateChecker.UpdateResult.UPDATE_AVAILABLE) return;
        if (!SimpleGameMode.getUpdateChecker().isSendOpMessage()) return;
    }

}

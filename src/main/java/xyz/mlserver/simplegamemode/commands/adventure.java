package xyz.mlserver.simplegamemode.commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class adventure implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        if (!sender.isOp()) return true;
        Player player = (Player) sender;
        player.setGameMode(GameMode.ADVENTURE);
        return true;
    }
}

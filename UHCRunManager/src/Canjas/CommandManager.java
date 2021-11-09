package Canjas;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandManager implements CommandExecutor {
    private final UHCRunManager plugin;

    public CommandManager(UHCRunManager plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if (sender instanceof Player) {
            if (cmd.getName().equalsIgnoreCase("runstart")) {
                plugin.start();
            }
        }
        return true;
    }
}

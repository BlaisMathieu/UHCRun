package Canjas;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CommandManager implements CommandExecutor {
    private final UHCRunManager plugin;

    public CommandManager(UHCRunManager plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        if (sender instanceof Player) {
            Player player = (Player)sender;
            if (cmd.getName().equalsIgnoreCase("runstart")) {
                plugin.start();
            }
            if (cmd.getName().equalsIgnoreCase("runstop")) {
                plugin.stop();
            }
        }
        return true;
    }
}

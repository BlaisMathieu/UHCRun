package Canjas;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Objects;

public class StatisticsListener implements Listener  {

    private final UHCRunManager plugin;

    public StatisticsListener(UHCRunManager uhcRunManager) {
        this.plugin = uhcRunManager;

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDeathEvent(final PlayerDeathEvent e) {
        if (!plugin.isHasStarted())
            return;
        Player p = e.getEntity();
        Entity killer = Objects.requireNonNull(p.getPlayer()).getKiller();
        if (killer instanceof Player) {
            String uuid = killer.getUniqueId().toString();
            int kills = plugin.getConfig().getInt(uuid + ".Kills");
            plugin.getConfig().set(uuid + ".Kills", kills+1);
            plugin.saveConfig();
        }
    }
}

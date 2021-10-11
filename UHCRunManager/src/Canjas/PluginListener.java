package Canjas;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PluginListener implements Listener {

    private final UHCRunManager plugin;

    public PluginListener(UHCRunManager uhcRunManager) {
        this.plugin = uhcRunManager;
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamageEvent(final EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
            e.setCancelled(true);
        }
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDeathEvent(final PlayerDeathEvent e) {
        Player p = e.getEntity();
        p.setGameMode(GameMode.SPECTATOR);
    }
    @EventHandler
    public void onTestEntityDamage(EntityDamageByEntityEvent event) {
        if (plugin.getPvp() == false) {
            if (event.getDamager() instanceof Player) {
                if (event.getEntity() instanceof Player) {
                    event.setCancelled(true);
                }
            }
        }
    }
}

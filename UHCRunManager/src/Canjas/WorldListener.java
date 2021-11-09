package Canjas;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class WorldListener implements Listener {

    private final UHCRunManager plugin;

    public WorldListener(UHCRunManager uhcRunManager) {
        this.plugin = uhcRunManager;
    }
    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (!plugin.isHasStarted())
            return;
        if (event.getCause().equals(PlayerTeleportEvent.TeleportCause.NETHER_PORTAL)) {
            Player player = event.getPlayer();
            player.sendTitle(ChatColor.RED + "Le nether est désactivé  !", "",  1, 40, 1);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void cancelFood(FoodLevelChangeEvent event){
        if (plugin.isHasStarted())
            return;
        event.setCancelled(true);
    }
}

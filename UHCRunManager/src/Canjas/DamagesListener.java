package Canjas;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class DamagesListener implements Listener {

    private final UHCRunManager plugin;

    public DamagesListener(UHCRunManager uhcRunManager) {
        this.plugin = uhcRunManager;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamageEvent(final EntityDamageEvent e) {
        if (!plugin.isHasStarted())
            return;
        if (!(e.getEntity() instanceof Player))
            return;
        if (e.getCause() == EntityDamageEvent.DamageCause.FALL && !e.getEntity().getWorld().getPVP())
            e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDeathEvent(final PlayerDeathEvent e) {
        if (!plugin.isHasStarted())
            return;
        e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation().add(0.2D, 0.2D, 0.2D), new ItemStack(Material.GOLDEN_APPLE, 1));
        Player p = e.getEntity();
        p.setGameMode(GameMode.SPECTATOR);
    }

    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if (!plugin.isHasStarted())
            return;
        if (!event.getEntity().getWorld().getPVP()) {
            if (event.getDamager() instanceof Player) {
                if (event.getEntity() instanceof Player)
                    event.setCancelled(true);
                if (event.getEntity() instanceof org.bukkit.entity.Villager && event.getEntity().getScoreboardTags().size() > 0)
                    event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockDamaged(BlockDamageEvent event) {
        if (!plugin.isHasStarted())
            return;
        Player player = event.getPlayer();
        if (event.getBlock().getType() == Material.OBSIDIAN)
            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 120, 3));
        else {
            if (player.hasPotionEffect(PotionEffectType.FAST_DIGGING))
                player.removePotionEffect(PotionEffectType.FAST_DIGGING);
        }
    }

    @EventHandler
    public void onTNTPlace(BlockPlaceEvent event) {
        if (!plugin.isHasStarted())
            return;
        Block tnt = event.getBlockPlaced();
        if (tnt.getType() == Material.TNT) {
            event.setCancelled(true);
            tnt.getWorld().spawnEntity(tnt.getLocation(), EntityType.PRIMED_TNT);
        }
    }
}

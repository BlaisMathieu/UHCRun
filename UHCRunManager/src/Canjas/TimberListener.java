package Canjas;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class TimberListener implements Listener {
    private final UHCRunManager plugin;
    final private long breakDelay = 8;

    public TimberListener(UHCRunManager uhcRunManager) {
        this.plugin = uhcRunManager;
    }

    private static final List<BlockFace> NEIGHBORS = Arrays.asList(BlockFace.UP,
            BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST,
            BlockFace.DOWN);

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onBlockBreak(BlockBreakEvent event) {
        if (!plugin.isHasStarted())
            return;
        onBlockRemove(event.getBlock(), breakDelay);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onLeavesDecay(LeavesDecayEvent event) {
        if (!plugin.isHasStarted())
            return;
        long decayDelay = 2;
        onBlockRemove(event.getBlock(), decayDelay);
    }

    private void onBlockRemove(final Block oldBlock, long delay) {
        for (BlockFace neighborFace: NEIGHBORS) {
            final Block block = oldBlock.getRelative(neighborFace);
            if (Tag.LEAVES.isTagged(block.getType()))
                plugin.getServer().getScheduler().runTaskLater(plugin, () -> BreakBlock(block), delay);
            if (Tag.LOGS.isTagged(block.getType())) {
                plugin.getServer().getScheduler().runTaskLater(plugin, () -> BreakBlock(block), delay - 5);
                breakLogs(block);
            }
        }
    }

    private void breakLogs(Block log) {
        for (BlockFace neighborFace: NEIGHBORS) {
            final Block block = log.getRelative(neighborFace);
            if (Tag.LOGS.isTagged(block.getType())) {
                plugin.getServer().getScheduler().runTaskLater(plugin, () -> BreakBlock(block),breakDelay - 5);
                plugin.getServer().getScheduler().runTaskLater(plugin, () -> breakLogs(block), breakDelay - 5);
            }
            if (Tag.LEAVES.isTagged(block.getType()))
                plugin.getServer().getScheduler().runTaskLater(plugin, () -> BreakBlock(block), breakDelay);
        }
    }

    private void BreakBlock(Block block) {
        if (Tag.LEAVES.isTagged(block.getType())) {
            Leaves leaves = (Leaves) block.getBlockData();
            if (leaves.getDistance() < 7)
                return;
            LeavesDecayEvent event = new LeavesDecayEvent(block);
            block.setType(Material.AIR);
            int r = (int) (Math.random() * 600);
            if (r > 20 && r < 30)
                block.getWorld().dropItemNaturally(block.getLocation().add(0.2D, 0.2D, 0.2D), new ItemStack(Material.APPLE, 1));
            if (r > 60 && r < 80)
                block.getWorld().dropItemNaturally(block.getLocation().add(0.2D, 0.2D, 0.2D), new ItemStack(Material.STICK, 1));
            if (r == 0)
                block.getWorld().dropItemNaturally(block.getLocation().add(0.2D, 0.2D, 0.2D), new ItemStack(Material.GOLDEN_APPLE, 1));
            plugin.getServer().getPluginManager().callEvent(event);
            if (event.isCancelled())
                return;
        }
        block.getWorld().spawnParticle(Particle.BLOCK_DUST,
                block.getLocation().add(0.5, 0.5, 0.5),
                8, 0.2, 0.2, 0.2, 0,
                block.getType().createBlockData());
        block.getWorld().playSound(block.getLocation(),
                Sound.BLOCK_GRASS_BREAK,
                SoundCategory.BLOCKS, 0.05f, 1.2f);
        block.breakNaturally();
    }
}

package Canjas;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

public class CutCleanListener implements Listener {

    private final UHCRunManager plugin;

    public CutCleanListener(UHCRunManager uhcRunManager) {
        plugin = uhcRunManager;
    }

    @EventHandler
    public void onInteract(BlockBreakEvent event) {
        if (!plugin.isHasStarted())
            return;
        Block block = event.getBlock();
        Player player = event.getPlayer();
        Material drop;
        Material mat = event.getBlock().getType();
        switch (mat) {
            case REDSTONE_ORE:
                player.giveExp(3);
            case DIAMOND_ORE:
                drop = Material.DIAMOND;
                break;
            case GOLD_ORE:
                drop = Material.GOLD_INGOT;
                player.giveExp(3);
                break;
            case IRON_ORE:
                drop = Material.IRON_INGOT;
                player.giveExp(2);
                break;
            case COAL_ORE:
                drop = Material.TORCH;
                player.giveExp(1);
                break;
            case ANCIENT_DEBRIS:
                drop = Material.NETHERITE_SCRAP;
                break;
            case GRAVEL:
                drop = Material.ARROW;
                break;
            case SAND:
                drop = Material.GLASS;
                break;
            case CACTUS:
                drop = Material.OAK_LOG;
                break;
            default:
                return;
        }
        block.setType(Material.AIR);
        block.getWorld().dropItemNaturally(block.getLocation().add(0.2D, 0.2D, 0.2D), new ItemStack(drop, 2));
    }

    @EventHandler
    public void onPlayerConsume(PlayerItemConsumeEvent event) {
        if (!plugin.isHasStarted())
            return;
        Player player = event.getPlayer();
        for (ItemStack item : player.getInventory()) {
            try {
                PotionMeta meta = (PotionMeta)item.getItemMeta();
                PotionData data = meta.getBasePotionData();
                if (data.getType() == PotionType.STRENGTH && data.isUpgraded()) {
                    item.setType(Material.GLASS_BOTTLE);
                    player.sendMessage(ChatColor.RED + "[ATTENTION] " + ChatColor.YELLOW + "Force 2 interdite, votre inventaire à été modifié");
                }
            }catch(Exception ignored) {}
        }
    }

    @EventHandler
    public void entityKilled(EntityDeathEvent event) {
        if (!plugin.isHasStarted())
            return;
        Material drop;
        EntityType entity = event.getEntity().getType();
        switch (entity) {
            case COW:
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation().add(0.2D, 0.2D, 0.2D), new ItemStack(Material.LEATHER, (int) (Math.random() * 3) + 2));
                drop = Material.COOKED_BEEF;
                break;
            case SHEEP:
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation().add(0.2D, 0.2D, 0.2D), new ItemStack(Material.LEATHER, (int) (Math.random() * 3) + 2));
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation().add(0.2D, 0.2D, 0.2D), new ItemStack(Material.STRING, (int) (Math.random() * 3) + 1));
                drop = Material.COOKED_MUTTON;
                break;
            case RABBIT:
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation().add(0.2D, 0.2D, 0.2D), new ItemStack(Material.LEATHER, (int) (Math.random() * 3) + 2));
                drop = Material.COOKED_RABBIT;
                break;
            case CHICKEN:
                drop = Material.COOKED_CHICKEN;
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation().add(0.2D, 0.2D, 0.2D), new ItemStack(Material.ARROW, (int) (Math.random() * 3) + 2));
                break;
            case PIG:
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation().add(0.2D, 0.2D, 0.2D), new ItemStack(Material.LEATHER, (int) (Math.random() * 3) + 2));
                drop = Material.COOKED_PORKCHOP;
                break;
            case SALMON:
                drop = Material.COOKED_SALMON;
                break;
            case COD:
                drop = Material.COOKED_COD;
                break;
            case HORSE:
            case ZOMBIE:
                drop = Material.COOKED_BEEF;
                break;
            case SPIDER:
                drop = Material.STRING;
                break;
            case SKELETON:
                drop = Material.ARROW;
                if (Math.random() * 5 == 1) {
                    event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation().add(0.2D, 0.2D, 0.2D), new ItemStack(Material.BOW, 1)); }
                break;
            case CREEPER:
                drop = Material.TNT;
                break;
            default:
                return;
        }
        event.getDrops().clear();
        event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation().add(0.2D, 0.2D, 0.2D), new ItemStack(drop, (int) (Math.random() * 3) + 1));
    }

    @EventHandler
    public void ItemCraft(CraftItemEvent event) {
        if (!plugin.isHasStarted())
            return;
        ItemStack result = event.getCurrentItem();
        assert result != null;
        Material material = result.getType();
        ItemStack item;
        switch (material) {
            case WOODEN_AXE:
                item = new ItemStack(Material.IRON_AXE, event.getCurrentItem().getAmount());
                item.addEnchantment(Enchantment.DAMAGE_ALL, 3);
                event.setCurrentItem(item);
                break;
            case WOODEN_SWORD:
                item = new ItemStack(Material.IRON_SWORD, event.getCurrentItem().getAmount());
                item.addEnchantment(Enchantment.DAMAGE_ARTHROPODS, 3);
                item.addEnchantment(Enchantment.DAMAGE_UNDEAD, 3);
                event.setCurrentItem(item);
                break;
            case WOODEN_PICKAXE:
                item = new ItemStack(Material.IRON_PICKAXE, event.getCurrentItem().getAmount());
                item.addEnchantment(Enchantment.DIG_SPEED, 3);
                event.setCurrentItem(item);
                break;
            case WOODEN_SHOVEL:
                item = new ItemStack(Material.IRON_SHOVEL, event.getCurrentItem().getAmount());
                item.addEnchantment(Enchantment.DIG_SPEED, 3);
                event.setCurrentItem(item);
                break;
            default:
                return;
        }
    }

    @EventHandler
    public void makeUnbreakable(PlayerItemDamageEvent event) {
        if (!plugin.isHasStarted())
            return;
        Material material = event.getItem().getType();
        if (material == Material.IRON_AXE || material == Material.IRON_SWORD || material == Material.IRON_PICKAXE || material == Material.IRON_SHOVEL)
            event.setCancelled(true);
    }
}

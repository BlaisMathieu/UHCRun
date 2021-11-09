package Canjas;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Set;

public class Villager {
    private final ArrayList<ItemStack> itemList = new ArrayList<>();
    Entity villager;

    public Villager(Player p) {
        this.villager = p.getWorld().spawnEntity(p.getLocation(), EntityType.VILLAGER);
        for (ItemStack item : p.getInventory())
            itemList.add(item);
        this.villager.setCustomName(p.getName());
        if (villager instanceof LivingEntity)
            ((LivingEntity) villager).setAI(false);
    }

    public void addTag(String tag) {
        villager.addScoreboardTag(tag);
    }

    public Set<String> getTag() {
        return villager.getScoreboardTags();
    }

    public void dropItems() {
        for (ItemStack itemStack : itemList) {
            try { this.villager.getWorld().dropItemNaturally(this.villager.getLocation().add(0.2D, 0.2D, 0.2D), itemStack); }
            catch (Exception ignored) {}
        }
        this.villager.getWorld().dropItemNaturally(this.villager.getLocation().add(0.2D, 0.2D, 0.2D), new ItemStack(Material.GOLDEN_APPLE, 1));
    }

    public void killEntity() {
        this.villager.remove();
    }
}

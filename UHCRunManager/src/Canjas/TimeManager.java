package Canjas;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TimeManager {
    private final int sec;
    private final ManageScoreboard scoreboard;
    List<Pair<Integer, Integer>> teleportList;

    public TimeManager(int sec, ManageScoreboard scoreboard1) {
        this.sec = sec;
        this.scoreboard = scoreboard1;
        Teleport teleport = new Teleport();
        this.teleportList = teleport.getSpawnList();
        Objects.requireNonNull(Bukkit.getServer().getWorld("world")).setPVP(false);
    }

    public void SpawnTeleport() {
        WorldBorder border = Objects.requireNonNull(Bukkit.getServer().getWorld("world")).getWorldBorder();
        border.setSize(1500);
        List<Integer> values = new ArrayList<>();
        for(Player online : Bukkit.getOnlinePlayers()) {
            int r = (int) (Math.random() * 90);
            while (values.contains(r))
                r = (int) (Math.random() * 90);
            values.add(r);
            Pair<Integer, Integer> coos = teleportList.get(r);
            Location location = new Location(online.getWorld(), coos.getElement0(), 300, coos.getElement1());
            online.teleport(location);
            online.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 2000, 3));
        }
    }

    public void StartTeleport() {
        for (Player online : Bukkit.getOnlinePlayers()) {
            for (PotionEffect effect : online.getActivePotionEffects()) {
                online.removePotionEffect(effect.getType());
                online.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
                online.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 1));
            }
            online.sendTitle(ChatColor.BLUE + "Bonne chance !", "",  1, 40, 1);
            online.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 3));
            online.getInventory().addItem(new ItemStack(Material.WATER_BUCKET, 3));
        }
    }

    public void ManageMiningTime() {
        for (Player online : Bukkit.getOnlinePlayers()) {
            for (PotionEffect pEffect : online.getActivePotionEffects()) {
                if (pEffect.getType().equals(PotionEffectType.INCREASE_DAMAGE)) {
                    online.sendMessage(ChatColor.RED + "FORCE 2 Désactivé !");
                    online.removePotionEffect(pEffect.getType());
                }
            }
            if (!playerHasDead(online))
                scoreboard.CaveScoreboard(sec, online);
            else
                scoreboard.DeadScoreboard(sec, online);
        }
    }

    public void ManageSound() {
        for (Player online : Bukkit.getOnlinePlayers()) {
            scoreboard.StartScoreboard(sec, online);
            if (sec > 1205 && sec < 1230) {
                online.getWorld().playSound(online.getLocation(),
                        Sound.BLOCK_NOTE_BLOCK_BANJO,
                        SoundCategory.BLOCKS, 0.8f, 3f);
            }
            if (sec > 1205 && sec < 1216)
                online.sendTitle(ChatColor.YELLOW + Integer.toString(sec - 1200), "",  1, 20, 1);
            if (sec > 1200 && sec < 1206) {
                online.sendTitle(ChatColor.RED + Integer.toString(sec - 1200), "",  1, 40, 1);
                online.getWorld().playSound(online.getLocation(),
                        Sound.BLOCK_NOTE_BLOCK_FLUTE,
                        SoundCategory.BLOCKS, 1f, 3f);
            }
        }
    }

    public boolean playerHasDead(Player p) {
        return p.getGameMode() == GameMode.SPECTATOR;
    }

    public void clearPlayers() {
        for (Player online : Bukkit.getOnlinePlayers()) {
            online.getInventory().clear();
            online.setHealth(20.0);
            online.setFoodLevel(20);
            online.setLevel(0);
        }
    }
}

package Canjas;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FightTimeManager {
    private final int sec;
    private final ManageScoreboard scoreboard;
    private int count = 0;
    List<Pair<Integer, Integer>> teleportList;

    public FightTimeManager(int sec, ManageScoreboard scoreboard2) {
        this.sec = sec;
        this.scoreboard = scoreboard2;
        Teleport teleport = new Teleport();
        this.teleportList = teleport.getFightList();
    }

    public void EnablePvp() {
        Objects.requireNonNull(Bukkit.getServer().getWorld("world")).setPVP(true);
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (!playerHasDead(online))
                online.sendTitle(ChatColor.RED + "PVP Activé !", "", 1, 40, 1);
        }
    }

    public void SpawnTeleport() {
        List<Integer> values = new ArrayList<>();
        for(Player online : Bukkit.getOnlinePlayers()) {
            if (!playerHasDead(online)) {
                int r = (int) (Math.random() * 43);
                while (values.contains(r))
                    r = (int) (Math.random() * 43);
                values.add(r);
                Pair<Integer, Integer> coos = teleportList.get(r);
                Location location = new Location(online.getWorld(), coos.getElement0(), 300, coos.getElement1());
                online.teleport(location);
                for (PotionEffect effect : online.getActivePotionEffects())
                    online.removePotionEffect(effect.getType());
            }
            else {
                Location location = new Location(online.getWorld(), 0, 80, 0);
                online.teleport(location);
            }
        }
        for (Player online : Bukkit.getOnlinePlayers()) {
            WorldBorder border = online.getWorld().getWorldBorder();
            border.setSize(600);
        }
        for (Entity e : Objects.requireNonNull(Bukkit.getWorld("world")).getEntitiesByClasses(org.bukkit.entity.Villager.class)) {
            if (e.getScoreboardTags().size() > 0)
                e.remove();
        }
    }

    public void getPreperationTime() {
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (!playerHasDead(online))
                scoreboard.PreperationScoreboard(sec, online);
            else
                scoreboard.DeadPreparationScoreboard(sec, online);
        }
    }

    public boolean playerHasDead(Player p) {
        return p.getGameMode() == GameMode.SPECTATOR;
    }

    public boolean FightTime(int sec) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            WorldBorder border = online.getWorld().getWorldBorder();
            if ((sec % 2) ==  1)
                border.setSize(border.getSize() - 2);
            if (!playerHasDead(online))
                scoreboard.FightStartScoreboard(sec, online);
            else
                scoreboard.DeadFightStartScoreboard(sec, online);
        }
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getGameMode() == GameMode.SURVIVAL)
                count++;
        }
        if (count == 1)
            return true;
        count = 0;
        return false;
    }
}

package Canjas;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

public class DisconnectListener implements Listener {

    private final ArrayList<Villager> VillagerList = new ArrayList<>();
    final ArrayList<String> tagList = new ArrayList<>();
    private final UHCRunManager plugin;

    public DisconnectListener(UHCRunManager plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerDisconnect(PlayerQuitEvent event) {
        //if (!plugin.isHasStarted())
          //  return;
        Player p = event.getPlayer();
        Villager villager = new Villager(p);
        VillagerList.add(villager);
        villager.addTag(p.getName());
    }

    public void fillTagList() {
        try {  for (Entity e : Objects.requireNonNull(Bukkit.getWorld("world")).getEntitiesByClasses(org.bukkit.entity.Villager.class)) {
            if (e.getScoreboardTags().size() > 0) {
                Set<String> tmpSet = e.getScoreboardTags();
                tagList.addAll(tmpSet);
            }
        }} catch(Exception ignored) {}
    }

    public void removeIfNotInList() {
        int y = 0;

        try { for (Villager tmp : VillagerList) {
            Set<String> set = tmp.getTag();
            for (String t : set) {
                if (tagList.contains(t))
                    continue;
                else
                    VillagerList.remove(VillagerList.get(y));
                y++;
            }
        }} catch(Exception ignored) {}
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerReconnect(PlayerJoinEvent event) {
        //if (!plugin.isHasStarted())
          //  return;
        boolean check = false;
        int i = 0;
        //this.fillTagList();
        //this.removeIfNotInList();

        Player player = event.getPlayer();
        try { for (Villager tmp : VillagerList) {
            System.out.println("LAAAAA");
            Set<String> tmpSet = tmp.getTag();
            for (String t : tmpSet) {
                if (player.getName().equals(t)) {
                    check = true;
                    tmp.killEntity();
                    System.out.println("i : " + i);
                    VillagerList.remove(VillagerList.get(i));
                    System.out.println(VillagerList.size());
                }
            }
            i++;
        }} catch(Exception ignored) {}
        if (!check)
            Bukkit.getScheduler().runTaskLater(this.plugin, () -> player.setGameMode(GameMode.SPECTATOR), 3L);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void entityKilled(EntityDeathEvent event) {
        //if (!plugin.isHasStarted())
          //  return;
        int i = 0;
        Set<String> set = event.getEntity().getScoreboardTags();
        for (String name : set) {
            try { for (Villager tmp : VillagerList) {
                Set<String> tmpSet = tmp.getTag();
                for (String t : tmpSet) {
                    if (t.equals(name)) {
                        tmp.dropItems();
                        VillagerList.remove(VillagerList.get(i));
                    }
                } i++;
            } } catch(Exception ignored) {}
        }
    }
}

package Canjas;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameRule;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class UHCRunManager extends JavaPlugin {
    private int sec = 1230;
    private int s = 630;
    private int victory = 30;
    private int next = 0;
    private ManageScoreboard scoreboard1;
    private ManageScoreboard scoreboard2;
    private boolean hasStarted = false;

    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new DamagesListener(this), this);
        Bukkit.getPluginManager().registerEvents(new CutCleanListener(this), this);
        Bukkit.getPluginManager().registerEvents(new TimberListener(this), this);
        Bukkit.getPluginManager().registerEvents(new WorldListener(this), this);
        Bukkit.getPluginManager().registerEvents(new DisconnectListener(this), this);
        Bukkit.getPluginManager().registerEvents(new GenerationListeners(), this);
        Bukkit.getPluginManager().registerEvents(new StatisticsListener(this), this);
        Objects.requireNonNull(getCommand("runstart")).setExecutor(new CommandManager(this));
        Objects.requireNonNull(Bukkit.getServer().getWorld("world")).setPVP(false);
        Objects.requireNonNull(Bukkit.getWorld("world")).setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        Objects.requireNonNull(Bukkit.getWorld("world")).setGameRule(GameRule.NATURAL_REGENERATION, false);
    }

    public boolean isHasStarted() { return hasStarted; }

    public void start() {
        this.scoreboard1 = new ManageScoreboard(this);
        this.scoreboard2 = new ManageScoreboard(this);
        sec = 1230;
        new BukkitRunnable() {
            @Override
            public void run() {
                TimeManager timeManager = new TimeManager(sec, scoreboard1);
                if (sec == -1) {
                    this.cancel();
                    next = 1;
                    return;
                }
                if (sec == 1230) { timeManager.clearPlayers(); }
                if (sec == 1220) { timeManager.SpawnTeleport(); }
                if (sec == 1200) {
                    hasStarted = true;
                    timeManager.StartTeleport(); }
                if (sec < 1200) { timeManager.ManageMiningTime(); }
                else { timeManager.ManageSound(); }
                sec--;
            }
        }.runTaskTimer(this, 20L, 20L);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (next == 1) {
                    FightTimeManager fighttimeManager = new FightTimeManager(s, scoreboard2);
                    if (s == 600) { fighttimeManager.EnablePvp(); }
                    if (s == 630) { fighttimeManager.SpawnTeleport(); }
                    if (s < 630 && s > 600) { fighttimeManager.getPreperationTime(); }
                    if (s < 600) {
                        if (fighttimeManager.FightTime(s)) {
                            this.cancel();
                            next = 2;
                            return;
                        }
                    }
                    s--;
                }
            }
        }.runTaskTimer( this, 20L, 20L);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (next == 2) {
                    if (victory == 30) {
                        Bukkit.getServer().broadcastMessage(ChatColor.DARK_GREEN + "--- FIN DU JEU ---");
                    }
                    if (victory == 0) {
                        for(String key : getConfig().getKeys(false))
                            getConfig().set(key,null);
                        saveConfig();
                        this.cancel();
                        return;
                    }
                    victory--;
                }
            }
        }.runTaskTimer( this, 20L, 20L);
    }
}

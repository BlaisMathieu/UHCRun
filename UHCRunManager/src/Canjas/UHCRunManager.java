package Canjas;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;
import org.bukkit.util.Vector;

public class UHCRunManager extends JavaPlugin {
    private int sec = 1230;
    private int NumberOnline = 0;
    private int x;
    private int z;
    private boolean pvp = false;
    private int borderSize;
    private Vector dir;
    private ScoreboardManager manager = Bukkit.getScoreboardManager();
    private Scoreboard board = manager.getNewScoreboard();

    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new PluginListener(this), this);
        getCommand("runstart").setExecutor(new CommandManager(this));
        getCommand("runstop").setExecutor(new CommandManager(this));
    }
    public boolean getPvp() {
        return pvp;
    }
    public void stop() {
        sec = 1230;
        for(Player online : Bukkit.getOnlinePlayers()) {
            board.resetScores(online);
        }
    }
    public void start() {
        sec = 1230;
        NumberOnline = 0;

        for (Player ignored : Bukkit.getOnlinePlayers()) {
            NumberOnline++;
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                if (--sec <= 0) {
                    for(Player online : Bukkit.getOnlinePlayers()) {
                        if (!playerHasDead(online)) {
                            Location location = new Location(online.getWorld(), 100, 150, 100);
                            online.teleport(location);
                            pvp = true;
                        }
                    }
                    this.cancel();
                    return;
                }
                if (sec == 1220) {
                    for(Player online : Bukkit.getOnlinePlayers()) {
                        Location location = new Location(online.getWorld(), 150, 300, 30);
                        online.teleport(location);
                        online.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 2000, 3));
                    }
                }
                if (sec == 1200) {
                    for (Player online : Bukkit.getOnlinePlayers()) {
                        for (PotionEffect effect : online.getActivePotionEffects()) {
                            online.removePotionEffect(effect.getType());
                        }
                        online.sendTitle(ChatColor.BLUE + "Bonne chance !", "",  1, 40, 1);
                    }
                }
                if (sec < 1200) {
                    for (Player online : Bukkit.getOnlinePlayers()) {
                        dir = online.getLocation().getDirection();
                        Location loc = online.getLocation();
                        WorldBorder border = online.getWorld().getWorldBorder();
                        borderSize = (int) border.getSize();
                        x = loc.getBlockX();
                        z = loc.getBlockZ();
                        if (!playerHasDead(online))
                            CaveScoreboard(online);
                        else
                            DeadScoreboard(online);
                    }
                }
                else {
                    for (Player online : Bukkit.getOnlinePlayers()) {
                        StartScoreboard(sec, online);
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

            }
        }.runTaskTimer(this, 20L, 20L);
    }

    public String getTime(int time) {
        String value;

        if (time < 10) {
            value = "0" + time;
        }
        else
            value = Integer.toString(time);
        return (value);
    }

    public boolean playerHasDead(Player p) {
        if (p.getGameMode() == GameMode.SPECTATOR)
            return (true);
        return false;
    }

    public void CaveScoreboard(Player player) {
        manager = Bukkit.getScoreboardManager();
        board = manager.getNewScoreboard();
        Objective objective = board.registerNewObjective(ChatColor.RED + "UCHRun 1v1", "dummy");
        Score score2 = objective.getScore(ChatColor.GRAY + "---Infos---   ");
        Score score1 = objective.getScore(ChatColor.GOLD + "Joueurs: " + ChatColor.YELLOW + NumberOnline + "   ");
        Score score3 = objective.getScore(ChatColor.GOLD + "Centre: " + ChatColor.BLUE + " " + ChatColor.YELLOW + (Math.abs(x) + Math.abs(z)) + "   ");
        Score score5 = objective.getScore(ChatColor.GOLD + "Bordures: " + ChatColor.YELLOW + "-" + (borderSize / 2) + " +" + (borderSize / 2) + "   ");
        Score score4 = objective.getScore(ChatColor.GOLD + "Kills: " + ChatColor.YELLOW + "3   ");
        Score score6 = objective.getScore(ChatColor.GRAY + "---Timer---   ");
        Score score7 = objective.getScore(ChatColor.RED + "Téléportation: " + ChatColor.YELLOW + getTime(sec / 60) + ":" + getTime(sec % 60) + "   ");
        Score score8 = objective.getScore(" ");
        Score score9 = objective.getScore(ChatColor.YELLOW + "miokara.net");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        score2.setScore(9);
        score3.setScore(8);
        score5.setScore(7);
        score1.setScore(6);
        score4.setScore(5);
        score6.setScore(4);
        score7.setScore(3);
        score8.setScore(2);
        score9.setScore(1);
        player.setScoreboard(board);
    }

    public void DeadScoreboard(Player player) {
        manager = Bukkit.getScoreboardManager();
        board = manager.getNewScoreboard();
        Objective objective = board.registerNewObjective(ChatColor.RED + "UCHRun 1v1", "dummy");
        Score score2 = objective.getScore(ChatColor.GRAY + "---Infos---   ");
        Score score1 = objective.getScore(ChatColor.GOLD + "Joueurs: " + ChatColor.YELLOW + NumberOnline + "   ");
        Score score3 = objective.getScore(ChatColor.GOLD + "Centre: " + ChatColor.BLUE + " " + ChatColor.YELLOW + (Math.abs(x) + Math.abs(z)) + "   ");
        Score score5 = objective.getScore(ChatColor.GOLD + "Bordures: " + ChatColor.YELLOW + "-" + (borderSize / 2) + " +" + (borderSize / 2) + "   ");
        Score score4 = objective.getScore(ChatColor.GOLD + "Kills: " + ChatColor.YELLOW + "3   ");
        Score score6 = objective.getScore(ChatColor.GRAY + "---Timer---   ");
        Score score7 = objective.getScore(ChatColor.RED + "DEAD" + "   ");
        Score score8 = objective.getScore(" ");
        Score score9 = objective.getScore(ChatColor.YELLOW + "miokara.net");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        score2.setScore(9);
        score3.setScore(8);
        score5.setScore(7);
        score1.setScore(6);
        score4.setScore(5);
        score6.setScore(4);
        score7.setScore(3);
        score8.setScore(2);
        score9.setScore(1);
        player.setScoreboard(board);
    }

    public void StartScoreboard(int time, Player player) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective objective = board.registerNewObjective(ChatColor.RED + "UCHRun 1v1", "dummy");
        Score score2 = objective.getScore(ChatColor.GRAY + " ");
        Score score1 = objective.getScore(ChatColor.GOLD + "Demarrage: " + ChatColor.YELLOW + (time - 1200) + "   ");
        Score score3 = objective.getScore(ChatColor.GRAY + " ");
        Score score4 = objective.getScore(ChatColor.YELLOW + "miokara.net");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        score2.setScore(4);
        score1.setScore(3);
        score3.setScore(2);
        score4.setScore(1);
        player.setScoreboard(board);
    }
}

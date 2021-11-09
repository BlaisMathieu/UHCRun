package Canjas;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.Objects;

public class ManageScoreboard {
    private final UHCRunManager plugin;
    private int NumberOnline = 0;
    private int borderSize;
    private int x;
    private int z;
    private ScoreboardManager manager = Bukkit.getScoreboardManager();
    private Scoreboard board = Objects.requireNonNull(manager).getNewScoreboard();

    public ManageScoreboard(UHCRunManager uhcRunManager) {
        this.plugin = uhcRunManager;
    }

    public void actualizeScoreboard() {
        NumberOnline = 0;
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.getGameMode() == GameMode.SURVIVAL)
                NumberOnline++;
            Location loc = online.getLocation();
            WorldBorder border = online.getWorld().getWorldBorder();
            borderSize = (int) border.getSize();
            x = loc.getBlockX();
            z = loc.getBlockZ();
        }
    }

    public void CaveScoreboard(int sec, Player online) {
        actualizeScoreboard();
        String uuid = online.getUniqueId().toString();
        manager = Bukkit.getScoreboardManager();
        board = Objects.requireNonNull(manager).getNewScoreboard();
        Objective objective = board.registerNewObjective(ChatColor.AQUA  + "UCHRun 1v1", "dummy");
        Score score2 = objective.getScore(ChatColor.GRAY + "---Infos---   ");
        Score score1 = objective.getScore(ChatColor.GOLD + "Joueurs: " + ChatColor.YELLOW + NumberOnline + "   ");
        Score score3 = objective.getScore(ChatColor.GOLD + "Centre: " + ChatColor.BLUE + " " + ChatColor.YELLOW + (Math.abs(x) + Math.abs(z)) + "   ");
        Score score5 = objective.getScore(ChatColor.GOLD + "Bordures: " + ChatColor.YELLOW + "-" + (borderSize / 2) + " +" + (borderSize / 2) + "   ");
        Score score4 = objective.getScore(ChatColor.GOLD + "Kills: " + ChatColor.YELLOW + plugin.getConfig().getInt(uuid + ".Kills"));
        Score score6 = objective.getScore(ChatColor.GRAY + "---Timer---   ");
        Score score7 = objective.getScore(ChatColor.AQUA + "Téléportation: " + ChatColor.YELLOW + getTime(sec / 60) + ":" + getTime(sec % 60) + "   ");
        Score score8 = objective.getScore(" ");
        Score score9 = objective.getScore(ChatColor.BLUE + "miokara.hosterfy.eu");
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
        online.setScoreboard(board);
    }

    public void DeadScoreboard(int sec, Player player) {
        actualizeScoreboard();
        String uuid = player.getUniqueId().toString();
        manager = Bukkit.getScoreboardManager();
        board = Objects.requireNonNull(manager).getNewScoreboard();
        Objective objective = board.registerNewObjective(ChatColor.AQUA + "UCHRun 1v1", "dummy");
        Score score2 = objective.getScore(ChatColor.GRAY + "---Infos---   ");
        Score score1 = objective.getScore(ChatColor.GOLD + "Joueurs: " + ChatColor.YELLOW + NumberOnline + "   ");
        Score score3 = objective.getScore(ChatColor.GOLD + "Centre: " + ChatColor.BLUE + " " + ChatColor.YELLOW + (Math.abs(x) + Math.abs(z)) + "   ");
        Score score5 = objective.getScore(ChatColor.GOLD + "Bordures: " + ChatColor.YELLOW + "-" + (borderSize / 2) + " +" + (borderSize / 2) + "   ");
        Score score4 = objective.getScore(ChatColor.GOLD + "Kills: " + ChatColor.YELLOW + ChatColor.YELLOW + plugin.getConfig().getInt(uuid + ".Kills"));
        Score score6 = objective.getScore(ChatColor.GRAY + "---Timer---   ");
        Score score7 = objective.getScore(ChatColor.RED + "DEAD" + "   ");
        Score score8 = objective.getScore(ChatColor.AQUA + "Téléportation: " + ChatColor.YELLOW + getTime(sec / 60) + ":" + getTime(sec % 60) + "   ");
        Score score9 = objective.getScore(" ");
        Score score10 = objective.getScore(ChatColor.BLUE + "miokara.hosterfy.eu");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        score2.setScore(10);
        score3.setScore(9);
        score5.setScore(8);
        score1.setScore(7);
        score4.setScore(6);
        score6.setScore(5);
        score7.setScore(4);
        score8.setScore(3);
        score9.setScore(2);
        score10.setScore(1);
        player.setScoreboard(board);
    }

    public String getTime(int time) {
        String value;

        if (time < 10) {
            value = "0" + time;
        } else
            value = Integer.toString(time);
        return (value);
    }

    public void StartScoreboard(int time, Player player) {
        manager = Bukkit.getScoreboardManager();
        board = Objects.requireNonNull(manager).getNewScoreboard();
        Objective objective = board.registerNewObjective(ChatColor.AQUA + "UCHRun 1v1", "dummy");
        Score score2 = objective.getScore(ChatColor.GRAY + " ");
        Score score1 = objective.getScore(ChatColor.GOLD + "Demarrage: " + ChatColor.YELLOW + (time - 1200) + "   ");
        Score score3 = objective.getScore(ChatColor.GRAY + " ");
        Score score4 = objective.getScore(ChatColor.BLUE + "miokara.hosterfy.eu");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        score2.setScore(4);
        score1.setScore(3);
        score3.setScore(2);
        score4.setScore(1);
        player.setScoreboard(board);
    }

    public void PreperationScoreboard(int time, Player player) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = Objects.requireNonNull(manager).getNewScoreboard();
        Objective objective = board.registerNewObjective(ChatColor.AQUA + "UCHRun 1v1", "dummy");
        Score score2 = objective.getScore(ChatColor.GRAY + " ");
        Score score1 = objective.getScore(ChatColor.GOLD + "Fin de préparation: " + ChatColor.YELLOW + (time - 600) + "   ");
        Score score3 = objective.getScore(ChatColor.GRAY + " ");
        Score score4 = objective.getScore(ChatColor.BLUE + "miokara.hosterfy.eu");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        score2.setScore(4);
        score1.setScore(3);
        score3.setScore(2);
        score4.setScore(1);
        player.setScoreboard(board);
    }

    public void DeadPreparationScoreboard(int time, Player player) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = Objects.requireNonNull(manager).getNewScoreboard();
        Objective objective = board.registerNewObjective(ChatColor.AQUA + "UCHRun 1v1", "dummy");
        Score score2 = objective.getScore(ChatColor.GRAY + " ");
        Score score1 = objective.getScore(ChatColor.GOLD + "Fin de préparation: " + ChatColor.YELLOW + (time - 600) + "   ");
        Score score3 = objective.getScore(ChatColor.RED + "DEAD" + "   ");
        Score score4 = objective.getScore(ChatColor.GRAY + " ");
        Score score5 = objective.getScore(ChatColor.BLUE + "miokara.hosterfy.eu");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        score2.setScore(4);
        score1.setScore(3);
        score3.setScore(3);
        score4.setScore(2);
        score5.setScore(1);
        player.setScoreboard(board);
    }

    public void FightStartScoreboard(int sec, Player player) {
        actualizeScoreboard();
        String uuid = player.getUniqueId().toString();
        manager = Bukkit.getScoreboardManager();
        board = Objects.requireNonNull(manager).getNewScoreboard();
        Objective objective = board.registerNewObjective(ChatColor.AQUA + "UCHRun 1v1", "dummy");
        Score score2 = objective.getScore(ChatColor.GRAY + "---Infos---   ");
        Score score1 = objective.getScore(ChatColor.GOLD + "Joueurs: " + ChatColor.YELLOW + NumberOnline + "   ");
        Score score3 = objective.getScore(ChatColor.GOLD + "Centre: " + ChatColor.BLUE + " " + ChatColor.YELLOW + (Math.abs(x) + Math.abs(z)) + "   ");
        Score score5 = objective.getScore(ChatColor.GOLD + "Bordures: " + ChatColor.YELLOW + "-" + (borderSize / 2) + " +" + (borderSize / 2) + "   ");
        Score score4 = objective.getScore(ChatColor.GOLD + "Kills: " + ChatColor.YELLOW + ChatColor.YELLOW + ChatColor.YELLOW + plugin.getConfig().getInt(uuid + ".Kills"));
        Score score6 = objective.getScore(ChatColor.GRAY + "---Timer---   ");
        Score score7 = objective.getScore(ChatColor.AQUA + "Fin de réduction: " + ChatColor.YELLOW + getTime(sec / 60) + ":" + getTime(sec % 60) + "   ");
        Score score8 = objective.getScore(" ");
        Score score9 = objective.getScore(ChatColor.BLUE + "miokara.hosterfy.eu");
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

    public void DeadFightStartScoreboard(int sec, Player player) {
        actualizeScoreboard();
        String uuid = player.getUniqueId().toString();
        manager = Bukkit.getScoreboardManager();
        board = Objects.requireNonNull(manager).getNewScoreboard();
        Objective objective = board.registerNewObjective(ChatColor.RED + "UCHRun 1v1", "dummy");
        Score score2 = objective.getScore(ChatColor.GRAY + "---Infos---   ");
        Score score1 = objective.getScore(ChatColor.GOLD + "Joueurs: " + ChatColor.YELLOW + NumberOnline + "   ");
        Score score3 = objective.getScore(ChatColor.GOLD + "Centre: " + ChatColor.BLUE + " " + ChatColor.YELLOW + (Math.abs(x) + Math.abs(z)) + "   ");
        Score score5 = objective.getScore(ChatColor.GOLD + "Bordures: " + ChatColor.YELLOW + "-" + (borderSize / 2) + " +" + (borderSize / 2) + "   ");
        Score score4 = objective.getScore(ChatColor.GOLD + "Kills: " + ChatColor.YELLOW + ChatColor.YELLOW + ChatColor.YELLOW + plugin.getConfig().getInt(uuid + ".Kills"));
        Score score6 = objective.getScore(ChatColor.GRAY + "---Timer---   ");
        Score score7 = objective.getScore(ChatColor.RED + "DEAD" + "   ");
        Score score8 = objective.getScore(ChatColor.AQUA + "Fin de réduction: " + ChatColor.YELLOW + getTime(sec / 60) + ":" + getTime(sec % 60) + "   ");
        Score score9 = objective.getScore(" ");
        Score score10 = objective.getScore(ChatColor.BLUE + "miokara.hosterfy.eu");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        score2.setScore(10);
        score3.setScore(9);
        score5.setScore(8);
        score1.setScore(7);
        score4.setScore(6);
        score6.setScore(5);
        score7.setScore(4);
        score8.setScore(3);
        score9.setScore(2);
        score10.setScore(1);
        player.setScoreboard(board);
    }
}


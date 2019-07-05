package bukkitplugin.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.List;

public class CustomScoreboard {

    private ArrayList<String> lines = new ArrayList<>();
    private Objective objective;
    private Scoreboard scoreboard;

    public CustomScoreboard(String title, List<String> lines) {
        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        scoreboard = scoreboardManager.getNewScoreboard();
        objective = scoreboard.registerNewObjective(title.toLowerCase(), "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(title);
        this.lines.addAll(lines);
        addScoreboardLines(objective, this.lines);
    }

    private void addScoreboardLines(Objective objective, List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            addLine(list.get(i), i);
        }
    }

    public void updateLine(int listIndex, String newText) {
        removeLine(lines.get(listIndex));
        addLine(newText, listIndex);
        lines.set(listIndex, newText);
    }

    public void appendLine(String text) {
        addLine(text, lines.size());
        lines.add(text);
    }

    private void addLine(String text, int i) {
        Score score = objective.getScore(text);
        score.setScore(12 - i);
    }

    private void removeLine(String text) {
        objective.getScoreboard().resetScores(text);
    }

    public void assignToPlayer(Player player) {
        player.setScoreboard(scoreboard);
    }

}

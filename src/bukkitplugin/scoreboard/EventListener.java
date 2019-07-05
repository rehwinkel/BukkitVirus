package bukkitplugin.scoreboard;

import com.earth2me.essentials.api.UserDoesNotExistException;
import net.ess3.api.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class EventListener implements Listener {

    private final String title = "§6§lZockerTown";
    private HashMap<UUID, CustomScoreboard> boards = new HashMap<>();
    private BukkitPlugin plugin;
    private ArrayList<String> text = new ArrayList<>();

    public EventListener(BukkitPlugin bukkitPlugin) {
        this.plugin = bukkitPlugin;
        text.add("    ");
        text.add("§7> §a§lOnline:");
        text.add("§f" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers());
        text.add("   ");
        text.add("§7> §a§lTeamSpeak:");
        text.add("§f188.165.56.125:24840");
        text.add("  ");
        text.add("§7> §a§lKontostand:");
        text.add("§f" + 0 + "$");
        text.add(" ");
        Bukkit.getScheduler().scheduleSyncRepeatingTask(bukkitPlugin, () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                BigDecimal money = new BigDecimal(0);
                try {
                    money = Economy.getMoneyExact(p.getName());
                } catch (UserDoesNotExistException e) {
                    e.printStackTrace();
                }
                updateScoreboardFor(p, 8, "§f" + money.toString() + "$");
            }
        }, 0, 20);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        initScoreboardFor(event.getPlayer(), title, text);
        updateScoreboardForAll(2, "§f" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers());
    }

    private void initScoreboardFor(Player player, String titl, List<String> tex) {
        if (!boards.containsKey(player.getUniqueId())) {
            CustomScoreboard board = new CustomScoreboard(titl, tex);
            boards.put(player.getUniqueId(), board);
        }
        boards.get(player.getUniqueId()).assignToPlayer(player);
    }

    private void updateScoreboardForAll(int line, String newText) {
        for (UUID uid : boards.keySet()) {
            updateScoreboardFor(uid, line, newText);
        }
    }

    private void updateScoreboardFor(Player player, int line, String newText) {
        updateScoreboardFor(player.getUniqueId(), line, newText);
    }

    private void updateScoreboardFor(UUID player, int line, String newText) {
        if (boards.containsKey(player)) {
            boards.get(player).updateLine(line, newText);
        }
    }
}

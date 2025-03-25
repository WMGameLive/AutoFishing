package wm.vdr.autofishing;

import org.bukkit.plugin.java.JavaPlugin;

public class ServerTypeUtil {
    private static Boolean isFolia = null;

    public static boolean isFolia() {
        if (isFolia == null) {
            try {
                Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
                isFolia = true;
            } catch (ClassNotFoundException e) {
                isFolia = false;
            }
        }
        return isFolia;
    }

    public static void runAtPlayer(JavaPlugin plugin, org.bukkit.entity.Player player, Runnable runnable, long delay) {
        if (isFolia()) {
            player.getScheduler().runDelayed(plugin, scheduledTask -> runnable.run(), null, delay);
        } else {
            plugin.getServer().getScheduler().runTaskLater(plugin, runnable, delay);
        }
    }
}
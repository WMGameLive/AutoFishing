package wm.vdr.autofishing;

import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public final class AutoFishing extends JavaPlugin {

    public static AutoFishing instance;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        instance = this;
        getServer().getPluginManager().registerEvents(new Listeners(), this);
        getCommand("autofishing").setExecutor(new Executors());
        getCommand("autofishing").setTabCompleter(new Executors());

        int pluginId = 16050;
        Metrics metrics = new Metrics(this, pluginId);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

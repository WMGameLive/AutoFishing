package wm.vdr.autofishing;

import org.bukkit.plugin.java.JavaPlugin;

public final class AutoFishing extends JavaPlugin {

    public static AutoFishing instance;

    @Override
    public void onEnable() {
        instance = this;
        getServer().getPluginManager().registerEvents(new Listeners(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

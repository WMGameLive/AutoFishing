package wm.vdr.autofishing;

import net.md_5.bungee.api.ChatColor;
import org.bstats.bukkit.Metrics;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class AutoFishing extends JavaPlugin {

    public static AutoFishing instance;

    private PlayerDataUtil playerDataUtil;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        instance = this;
        getServer().getPluginManager().registerEvents(new Listeners(), this);
        getCommand("autofishing").setExecutor(new Executors());
        getCommand("autofishing").setTabCompleter(new Executors());

        int pluginId = 16050;
        Metrics metrics = new Metrics(this, pluginId);

        playerDataUtil = new PlayerDataUtil();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public PlayerDataUtil getPlayerDataUtil() {
        return playerDataUtil;
    }

    public NamespacedKey key = new NamespacedKey(this, "FishingRod");
    public ItemStack getSpecificRod() {
        ItemStack item = new ItemStack(Material.FISHING_ROD);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',getConfig().getString("Only_Specific_Rod.Rod_Name")));
        List<String> lore = new ArrayList<>();
        getConfig().getStringList("Only_Specific_Rod.Rod_Lore").forEach(s -> {
            lore.add(ChatColor.translateAlternateColorCodes('&',s));
        });
        meta.setLore(lore);

        int customModelData = getConfig().getInt("Only_Specific_Rod.Rod_Model_Data");
        if(customModelData != 0) {
            meta.setCustomModelData(customModelData);
        }
        meta.getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, true);
        item.setItemMeta(meta);
        return item;

    }
}

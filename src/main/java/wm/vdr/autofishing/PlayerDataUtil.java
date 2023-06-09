package wm.vdr.autofishing;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class PlayerDataUtil {

    private AutoFishing main = AutoFishing.instance;
    private File playerDataFile;
    private YamlConfiguration playerData;

    public PlayerDataUtil() {
        reloadPlayerData();
    }

    public void reloadPlayerData() {
        playerDataFile = new File(main.getDataFolder() + "/PlayerData.yml");
        playerData = YamlConfiguration.loadConfiguration(playerDataFile);
    }

    public void savePlayerData() {
        try {
            playerData.save(playerDataFile);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public YamlConfiguration getPlayerData() {
        return playerData;
    }

    public boolean isAuto(UUID uuid) {
        return getPlayerData().get(uuid.toString() + ".toggle") == null || playerData.getBoolean(uuid.toString() + ".toggle");
    }

    public void setAuto(UUID uuid, boolean auto) {
        getPlayerData().set(uuid.toString() + ".toggle", auto);
        savePlayerData();
    }
}

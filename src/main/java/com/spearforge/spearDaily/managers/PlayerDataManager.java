package com.spearforge.spearDaily.managers;

import com.spearforge.spearDaily.SpearDaily;
import com.spearforge.spearDaily.models.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class PlayerDataManager {
    private final JavaPlugin plugin;
    private File dataFile;
    private FileConfiguration dataConfig;

    public PlayerDataManager(JavaPlugin plugin) {
        this.plugin = plugin;
        createDataFile();
    }

    private void createDataFile() {
        dataFile = new File(plugin.getDataFolder(), "data.yml");
        if (!dataFile.exists()) {
            try {
                dataFile.getParentFile().mkdirs();
                dataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                Bukkit.getLogger().severe("Could not create data.yml file!");
            }
        }
        dataConfig = YamlConfiguration.loadConfiguration(dataFile);
    }

    public PlayerData getPlayerData(Player player) {
        UUID uuid = player.getUniqueId();

        String path = "players." + uuid.toString();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String lastRewardString = dataConfig.getString(path + ".lastReward");
        LocalDate lastReward = lastRewardString != null ? LocalDate.parse(lastRewardString, formatter) : null;
        int currentStreak = dataConfig.getInt(path + ".currentStreak", 0);

        return new PlayerData(lastReward, currentStreak);
    }

    public void savePlayerData(UUID uuid, PlayerData playerData) {
        Bukkit.getScheduler().runTaskAsynchronously(SpearDaily.getPlugin(), () -> {
            String path = "players." + uuid.toString();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = playerData.getLastReward().format(formatter);

            dataConfig.set(path + ".lastReward", formattedDate);
            dataConfig.set(path + ".currentStreak", playerData.getCurrentStreak());

            try {
                dataConfig.save(dataFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


}
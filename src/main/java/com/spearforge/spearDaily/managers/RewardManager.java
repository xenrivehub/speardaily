package com.spearforge.spearDaily.managers;

import com.spearforge.spearDaily.SpearDaily;
import com.spearforge.spearDaily.models.RewardData;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RewardManager {

    public static void loadRewards() {
        ConfigurationSection rewardsSection = SpearDaily.getPlugin().getConfig().getConfigurationSection("rewards");

        // Eğer rewards bölümü yoksa hata mesajı ver ve çık
        if (rewardsSection == null) {
            SpearDaily.getPlugin().getLogger().warning("No rewards found in the config file!");
            return;
        }

        // Haritayı temizle ve yeniden yükle
        SpearDaily.getRewards().clear();

        for (String day : rewardsSection.getKeys(false)) {
            ConfigurationSection daySection = rewardsSection.getConfigurationSection(day);
            if (daySection == null) continue;

            int dayInt = Integer.parseInt(day);
            Map<String, List<String>> rewardList = SpearDaily.getRewards().getOrDefault(dayInt, new HashMap<>());

            for (String group : daySection.getKeys(false)) {
                System.out.println("Loading reward for day: " + day + " and group: " + group);
                List<String> commands = daySection.getStringList(group + ".commands");

                rewardList.put(group.toLowerCase(), commands);
            }

            SpearDaily.getRewards().put(dayInt, rewardList);
        }

        SpearDaily.getPlugin().getLogger().info("Daily Rewards loaded successfully!");
    }

}

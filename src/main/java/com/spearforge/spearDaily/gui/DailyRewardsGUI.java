package com.spearforge.spearDaily.gui;

import com.spearforge.spearDaily.SpearDaily;
import com.spearforge.spearDaily.managers.PlayerDataManager;
import com.spearforge.spearDaily.models.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DailyRewardsGUI {


    public void openRewardGUI(Player player) {
        Inventory gui = Bukkit.createInventory(null, 27, ChatColor.translateAlternateColorCodes('&', SpearDaily.getPlugin().getConfig().getString("title")));

        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        PlayerDataManager playerDataManager = SpearDaily.getPlayerDataManager();
        PlayerData playerData = playerDataManager.getPlayerData(player);


        boolean claimedToday = playerData.getLastReward() != null && playerData.getLastReward().isEqual(today);
        boolean claimedYesterday = playerData.getLastReward() != null && playerData.getLastReward().isEqual(yesterday); // Checking for streak

        Duration timeUntilMidnight = Duration.between(LocalTime.now(), LocalTime.MIDNIGHT);
        if (timeUntilMidnight.isNegative()) {
            timeUntilMidnight = timeUntilMidnight.plusHours(24);
        }

        long hours = timeUntilMidnight.toHours();
        long minutes = timeUntilMidnight.toMinutesPart();
        long seconds = timeUntilMidnight.toSecondsPart();


        for (int i = 1; i <= 7; i++) {
            ItemStack rewardItem = new ItemStack(Material.CHEST_MINECART);
            ItemMeta rewardMeta = rewardItem.getItemMeta();
            rewardMeta.setDisplayName(ChatColor.BLACK + "Günlük Ödül #" + i);

            List<String> lore = new ArrayList<>();

            // Bugünkü gün
            if (i == today.getDayOfWeek().getValue()) {
                if (claimedToday) {
                    rewardItem.setType(Material.MINECART);
                } else {
                    rewardItem.setType(Material.CHEST_MINECART);
                    lore.add(ChatColor.RED + " Ödül alınabilir!");
                }
            }
            // Geçmiş günler
            else if (i < today.getDayOfWeek().getValue()) {
                if (claimedYesterday) {
                    rewardItem.setType(Material.MINECART);
                } else {
                    rewardItem.setType(Material.TNT_MINECART);
                }
            }
            // Gelecek günler
            else {
                rewardItem.setType(Material.CHEST);
                lore.add(ChatColor.RED + " Kalan süre: " + hours + " saat " + minutes + " dakika " + seconds + " saniye");
            }
            lore.add("");

            for (String line : SpearDaily.getPlugin().getConfig().getStringList("gui." + i + ".lore")){
                lore.add(translateHexAndColorCodes(line));
            }

            rewardMeta.setLore(lore);
            rewardItem.setItemMeta(rewardMeta);
            gui.setItem(i + 9, rewardItem);
        }

        player.openInventory(gui);
    }


    public static String translateHexAndColorCodes(String message) {
        if (message == null) return "";

        // 1. Adım: #RRGGBB olan kodları &#RRGGBB formatına dönüştür
        message = message.replaceAll("(?i)(?<!&)#([A-Fa-f0-9]{6})", "&#$1");

        // 2. Adım: Hex Renk Kodlarını (&#RRGGBB) çevir
        Pattern hexPattern = Pattern.compile("(?i)&#([A-Fa-f0-9]{6})");
        Matcher matcher = hexPattern.matcher(message);
        StringBuffer buffer = new StringBuffer();

        while (matcher.find()) {
            String hexCode = matcher.group(1);
            StringBuilder minecraftColor = new StringBuilder("§x");

            // Hex kodunu Minecraft formatına çevir
            for (char c : hexCode.toCharArray()) {
                minecraftColor.append("§").append(c);
            }

            matcher.appendReplacement(buffer, minecraftColor.toString());
        }
        matcher.appendTail(buffer);
        message = buffer.toString();

        // 3. Adım: Klasik Renk Kodlarını (&2, &a, &b) çevir
        message = ChatColor.translateAlternateColorCodes('&', message);

        return message;
    }
}

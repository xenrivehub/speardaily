package com.spearforge.spearDaily.listener;

import com.spearforge.spearDaily.SpearDaily;
import com.spearforge.spearDaily.managers.PlayerDataManager;
import com.spearforge.spearDaily.models.PlayerData;
import com.spearforge.spearDaily.models.RewardData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DailyRewardListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(ChatColor.translateAlternateColorCodes('&', SpearDaily.getPlugin().getConfig().getString("title")))) {
            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();
            UUID uuid = player.getUniqueId();
            PlayerDataManager dataManager = SpearDaily.getPlayerDataManager();

            PlayerData playerData = dataManager.getPlayerData(player);

            if (playerData == null){
                playerData = new PlayerData();
            }

            int slot = event.getSlot();
            if (slot < 10 || slot > 16) return; // Sadece 10-16 slotları ödüller
            Map<String, List<String>> todaysReward = SpearDaily.getRewards().get(LocalDate.now().getDayOfWeek().getValue());

            if (todaysReward == null){
                player.sendMessage(color("&cÖdül sisteminde bir hata mevcut, ONUR ulaşın!"));
                return;
            }
            List<String> todayCommands = todaysReward.get(getPlayerType(player));

            if (todayCommands == null){
                SpearDaily.getPlugin().getLogger().warning("No reward found for player type: " + getPlayerType(player));
                return;
            }

            if (event.getCurrentItem().getType() == Material.CHEST_MINECART){
                player.sendMessage(color("&6ꜱᴘᴇᴀʀ &fɴᴇᴛᴡᴏʀᴋ &8» &fHaftanın " + slot + ". günü ödülünü başarıyla aldın!"));
                for (String command : todayCommands){
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName()));
                }
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                playerData.setLastReward(LocalDate.now());
                if (playerData.getCurrentStreak() <= 7){
                    playerData.setCurrentStreak(playerData.getCurrentStreak() + 1);
                } else {
                    playerData.setCurrentStreak(0);
                }
                dataManager.savePlayerData(uuid, playerData);
            } else if (event.getCurrentItem().getType() == Material.CHEST){
                player.sendMessage(color(" &6ꜱᴘᴇᴀʀ &fɴᴇᴛᴡᴏʀᴋ &8» &cHenüz bu ödülü alamazsın maalesef!"));
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
            } else {
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
            }

        }
    }

    private String getPlayerType(Player player) {
        if (player.hasPermission("speardaily.spearvip+")) return "spearvip+";
        if (player.hasPermission("speardaily.spearvip")) return "spearvip";
        if (player.hasPermission("speardaily.ultravip")) return "ultravip";
        if (player.hasPermission("speardaily.megavip")) return "megavip";
        if (player.hasPermission("speardaily.vip")) return "vip";
        return "default";
    }

    private String color(String msg){
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
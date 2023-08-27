package org.lone64.xtitle.listener;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.lone64.xtitle.XTitle;
import org.lone64.xtitle.util.adventure.AdventureUtil;
import org.lone64.xtitle.util.color.ColorUtil;
import org.lone64.xtitle.util.item.ItemUtil;

import java.util.Arrays;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onEditorClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (!event.getView().getTitle().equals(ColorUtil.fetchColor("&8칭호 수정"))) return;

        ItemStack item = event.getCurrentItem();
        if (item == null) return;

        event.setCancelled(true);
        if (XTitle.getStrMap().get(player) == null) return;
        if (item.isSimilar(new ItemUtil(Material.BIRCH_SIGN)
                .setDisplayName(AdventureUtil.fetchGradient("8301f6", "01f6a6", "&l칭호 표시 이름 설정"))
                .setLore(Arrays.asList(
                        ColorUtil.fetchColor(""),
                        ColorUtil.fetchColor(" &8&l┏ &f칭호의 displayName 부분을 설정합니다."),
                        ColorUtil.fetchColor(" &8&l┗ &e표시 이름을 설정하려면 클릭하여 주세요!"),
                        ColorUtil.fetchColor("")
                )).getItemStack())) {
            player.closeInventory();
            XTitle.getIntMap().put(player, 0);
            AdventureUtil.playerSound(player, Sound.BLOCK_NOTE_BLOCK_PLING);
            AdventureUtil.playerMessage(player, "{@p} &e설정할 표시 이름&f을 채팅에 입력해주세요!");
            AdventureUtil.playerMessage(player, "{@p} &f설정을 취소하려면 &e취소&f를 입력하여 주세요!");
            AdventureUtil.playerMessage(player, "{@p} &f표시 이름을 빈 칸으로 하려면 &e_&f를 입력하여 주세요!");
        } else if (item.isSimilar(new ItemUtil(Material.JUNGLE_SIGN)
                .setDisplayName(AdventureUtil.fetchGradient("8301f6", "01f6a6", "&l칭호 접두사 설정"))
                .setLore(Arrays.asList(
                        ColorUtil.fetchColor(""),
                        ColorUtil.fetchColor(" &8&l┏ &f칭호의 prefix 부분을 설정합니다."),
                        ColorUtil.fetchColor(" &8&l┗ &e접두사를 설정하려면 클릭하여 주세요!"),
                        ColorUtil.fetchColor("")
                )).getItemStack())) {
            player.closeInventory();
            XTitle.getIntMap().put(player, 1);
            AdventureUtil.playerSound(player, Sound.BLOCK_NOTE_BLOCK_PLING);
            AdventureUtil.playerMessage(player, "{@p} &e설정할 접두사&f를 채팅에 입력해주세요!");
            AdventureUtil.playerMessage(player, "{@p} &f설정을 취소하려면 &e취소&f를 입력하여 주세요!");
            AdventureUtil.playerMessage(player, "{@p} &f접두사를 빈 칸으로 하려면 &e_&f를 입력하여 주세요!");
        } else if (item.isSimilar(new ItemUtil(Material.WARPED_SIGN)
                .setDisplayName(AdventureUtil.fetchGradient("8301f6", "01f6a6", "&l칭호 접미사 설정"))
                .setLore(Arrays.asList(
                        ColorUtil.fetchColor(""),
                        ColorUtil.fetchColor(" &8&l┏ &f칭호의 suffix 부분을 설정합니다."),
                        ColorUtil.fetchColor(" &8&l┗ &e접미사를 설정하려면 클릭하여 주세요!"),
                        ColorUtil.fetchColor("")
                )).getItemStack())) {
            player.closeInventory();
            XTitle.getIntMap().put(player, 2);
            AdventureUtil.playerSound(player, Sound.BLOCK_NOTE_BLOCK_PLING);
            AdventureUtil.playerMessage(player, "{@p} &e설정할 접미사&f를 채팅에 입력해주세요!");
            AdventureUtil.playerMessage(player, "{@p} &f설정을 취소하려면 &e취소&f를 입력하여 주세요!");
            AdventureUtil.playerMessage(player, "{@p} &f접미사를 빈 칸으로 하려면 &e_&f를 입력하여 주세요!");
        }
    }

    @EventHandler
    public void onListClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals(ColorUtil.fetchColor("&8칭호 목록"))) return;

        ItemStack item = event.getCurrentItem();
        if (item == null) return;

        event.setCancelled(true);
    }

}

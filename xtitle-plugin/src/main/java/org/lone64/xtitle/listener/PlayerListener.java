package org.lone64.xtitle.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.lone64.xtitle.XTitle;
import org.lone64.xtitle.api.AddedPrefixEvent;
import org.lone64.xtitle.api.PrefixChatFormatEvent;
import org.lone64.xtitle.api.cross.prefix.IPrefix;
import org.lone64.xtitle.util.Util;
import org.lone64.xtitle.util.adventure.AdventureUtil;
import org.lone64.xtitle.util.color.ColorUtil;
import org.lone64.xtitle.util.nbt.ItemNmsUtil;

public class PlayerListener implements Listener {

    @EventHandler
    public void onChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        if (XTitle.getIntMap().get(player) == null || XTitle.getStrMap().get(player) == null) return;

        event.setCancelled(true);
        if (event.getMessage().equals("취소") || event.getMessage().equals("cancel")) {
            XTitle.getIntMap().remove(player);
            XTitle.getStrMap().remove(player);
            AdventureUtil.playerMessage(player, "{@p} &f설정을 성공적으로 취소하셨습니다!");
            return;
        }

        String s = event.getMessage();
        String name = XTitle.getStrMap().get(player);
        if (XTitle.getIntMap().get(player) == 0) {
            XTitle.getIntMap().remove(player);
            XTitle.getStrMap().remove(player);
            XTitle.getTeamManager().setDisplayName(name, s);
            XTitle.getTitleManager().setDisplayName(name, s);
            AdventureUtil.playerSound(player, Sound.ENTITY_PLAYER_LEVELUP);
            AdventureUtil.playerMessage(player, "{@p} &e칭호 표시 이름&f을 업데이트하셨습니다!");
        } else if (XTitle.getIntMap().get(player) == 1) {
            XTitle.getIntMap().remove(player);
            XTitle.getStrMap().remove(player);

            if (s.contains("<") && s.contains(">")) {
                String ss = s;
                s = Util.fetchString(s, "<", ">");
                String s1 = s.split(":")[0];
                String firstColor = s1.split(",")[0], twiceColor = s1.split(",")[1];
                s = AdventureUtil.fetchGradient(firstColor, twiceColor, s.split(":")[1]);
                s += ss.split(">")[1];
            }

            if (s.equals("_")) {
                s = "";
            }

            XTitle.getTeamManager().setPrefix(name, s);
            XTitle.getTitleManager().setPrefix(name, s);
            AdventureUtil.playerSound(player, Sound.ENTITY_PLAYER_LEVELUP);
            AdventureUtil.playerMessage(player, "{@p} &e칭호 접두사&f을 업데이트하셨습니다!");
        } else if (XTitle.getIntMap().get(player) == 2) {
            XTitle.getIntMap().remove(player);
            XTitle.getStrMap().remove(player);

            if (s.contains("<") && s.contains(">")) {
                String ss = s;
                s = Util.fetchString(s, "<", ">");
                String s1 = s.split(":")[0];
                String firstColor = s1.split(",")[0], twiceColor = s1.split(",")[1];
                s = AdventureUtil.fetchGradient(firstColor, twiceColor, s.split(":")[1]);
                s += ss.split(">")[1];
            }

            if (s.equals("_")) {
                s = "";
            }

            XTitle.getTeamManager().setSuffix(name, s);
            XTitle.getTitleManager().setSuffix(name, s);
            AdventureUtil.playerSound(player, Sound.ENTITY_PLAYER_LEVELUP);
            AdventureUtil.playerMessage(player, "{@p} &e칭호 접미사&f을 업데이트하셨습니다!");
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getHand() == null || event.getHand().equals(EquipmentSlot.OFF_HAND)) return;
        if (!(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) return;

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType().equals(Material.AIR)) return;

        ItemNmsUtil itemNms = new ItemNmsUtil(item);
        if (itemNms.asTag("NAME") == null) return;

        String name = itemNms.asTag("NAME");
        if (!XTitle.getTitleManager().isPrefix(name)) {
            AdventureUtil.playerSound(player, Sound.ENTITY_VILLAGER_NO);
            AdventureUtil.playerMessage(player, "{@p} &7존재하지 않는 칭호입니다!");
            return;
        }

        IPrefix prefix = XTitle.getTitleManager().fetchPrefix(name);
        if (prefix.getPlayerList().contains(player.getUniqueId())) {
            AdventureUtil.playerSound(player, Sound.ENTITY_VILLAGER_NO);
            AdventureUtil.playerMessage(player, "{@p} &7당신은 이미 해당 칭호를 소지하고 있습니다!");
            return;
        }

        XTitle.getTitleManager().addNewPlayer(name, player);
        player.getInventory().setItemInMainHand(Util.getUsed(item, 1));
        AdventureUtil.playerSound(player, Sound.ENTITY_PLAYER_LEVELUP);
        AdventureUtil.playerMessage(player, "{@p} &e" + prefix.getDisplayName() + "&f(을)를 이제부터 사용하실 수 있습니다!");
        Bukkit.getPluginManager().callEvent(new AddedPrefixEvent(player, prefix));
    }

    @EventHandler
    public void onChatAPI(PlayerChatEvent event) {
        IPrefix prefix;
        String s1 = "", s2 = "";
        if (XTitle.getPlayerManager().isPrefixPlayer(event.getPlayer())) {
            prefix = XTitle.getTitleManager().fetchPrefix(XTitle.getPlayerManager().getPrefixPlayer(event.getPlayer()).getName());
            s1 = prefix.getPrefix();
            s2 = prefix.getSuffix();
        }

        PrefixChatFormatEvent e = new PrefixChatFormatEvent(event.getPlayer(), s1, s2, event.getMessage(),
                (ColorUtil.fetchColor(s1 + event.getPlayer().getName() + s2 + "&f: ") + event.getMessage()), event.isCancelled());
        Bukkit.getPluginManager().callEvent(e);
        event.setFormat(e.getFormat());
        event.setCancelled(e.isCancelled());
    }

}

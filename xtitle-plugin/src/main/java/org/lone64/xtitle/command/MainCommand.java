package org.lone64.xtitle.command;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.jetbrains.annotations.NotNull;
import org.lone64.xtitle.XTitle;
import org.lone64.xtitle.api.ClearedPrefixEvent;
import org.lone64.xtitle.api.CreatedPrefixEvent;
import org.lone64.xtitle.api.DeletedPrefixEvent;
import org.lone64.xtitle.api.SelectedPrefixEvent;
import org.lone64.xtitle.api.cross.prefix.IPrefix;
import org.lone64.xtitle.inventory.InvPrefixEditor;
import org.lone64.xtitle.inventory.InvPrefixList;
import org.lone64.xtitle.util.adventure.AdventureUtil;
import org.lone64.xtitle.util.color.ColorUtil;
import org.lone64.xtitle.util.item.ItemUtil;
import org.lone64.xtitle.util.nbt.ItemNmsUtil;

import java.util.Arrays;

public class MainCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String arg, @NotNull String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            AdventureUtil.consoleMessage("{@p} &7해당 명령어는 플레이어만 사용할 수 있습니다!");
            return true;
        }

        Player player = (Player) sender;
        if (args.length == 0) {
            AdventureUtil.playerSound(player, Sound.ENTITY_VILLAGER_NO);
            AdventureUtil.playerMessage(player, "{@p} &7올바르지 않는 명령어입니다!");
            return true;
        }

        String name, displayName;
        switch (args[0]) {
            default:
                AdventureUtil.playerSound(player, Sound.ENTITY_VILLAGER_NO);
                AdventureUtil.playerMessage(player, "{@p} &7올바르지 않는 명령어입니다!");
                break;
            case "적용":
                if (args.length < 2) {
                    AdventureUtil.playerSound(player, Sound.ENTITY_VILLAGER_NO);
                    AdventureUtil.playerMessage(player, "{@p} &7생성할 칭호 이름을 입력하여 주세요!");
                    return true;
                } else if (XTitle.getPlayerManager().isPrefixPlayer(player)) {
                    AdventureUtil.playerSound(player, Sound.ENTITY_VILLAGER_NO);
                    AdventureUtil.playerMessage(player, "{@p} &7이미 칭호를 적용하셨습니다!");
                    return true;
                }

                name = args[1];
                if (!XTitle.getTitleManager().isDisplayName(name)) {
                    AdventureUtil.playerSound(player, Sound.ENTITY_VILLAGER_NO);
                    AdventureUtil.playerMessage(player, "{@p} &e" + name + "&7(은)는 존재하지 않는 칭호 이름입니다!");
                    return true;
                }

                IPrefix prefix = XTitle.getTitleManager().fetchDisplayName(name);
                if (!prefix.getPlayerList().contains(player.getUniqueId())) {
                    AdventureUtil.playerSound(player, Sound.ENTITY_VILLAGER_NO);
                    AdventureUtil.playerMessage(player, "{@p} &e" + name + "&7(을)를 소지하고 있지 않습니다!");
                    return true;
                }

                XTitle.getTeamManager().enable(player, name);
                XTitle.getPlayerManager().addPrefixPlayer(player, XTitle.getTitleManager().fetchDisplayName(name).getName());
                AdventureUtil.playerSound(player, Sound.ENTITY_PLAYER_LEVELUP);
                AdventureUtil.playerMessage(player, "{@p} &e" + name + "&f(을)를 적용하셨습니다!");
                Bukkit.getPluginManager().callEvent(new SelectedPrefixEvent(player, prefix));
                break;
            case "해제":
                if (!XTitle.getPlayerManager().isPrefixPlayer(player)) {
                    AdventureUtil.playerSound(player, Sound.ENTITY_VILLAGER_NO);
                    AdventureUtil.playerMessage(player, "{@p} &7칭호를 적용하지 않으셨습니다!");
                    return true;
                }

                prefix = XTitle.getTitleManager().fetchPrefix(XTitle.getSQLPrefixPlayer().fetchPrefix(player));
                XTitle.getTeamManager().disable(player);
                XTitle.getPlayerManager().delPrefixPlayer(player);
                AdventureUtil.playerSound(player, Sound.ENTITY_PLAYER_LEVELUP);
                AdventureUtil.playerMessage(player, "{@p} &e적용한 칭호&f를 해제하셨습니다!");
                Bukkit.getPluginManager().callEvent(new ClearedPrefixEvent(player, prefix));
                break;
            case "목록":
                new InvPrefixList(player).load();
                break;
            case "생성":
                if (!player.hasPermission("xtitle.admin")) {
                    AdventureUtil.playerSound(player, Sound.ENTITY_VILLAGER_NO);
                    AdventureUtil.playerMessage(player, "{@p} &7당신은 해당 명령어를 사용할 권한이 없습니다!");
                    return true;
                } else if (args.length < 2) {
                    AdventureUtil.playerSound(player, Sound.ENTITY_VILLAGER_NO);
                    AdventureUtil.playerMessage(player, "{@p} &7생성할 칭호 이름을 입력하여 주세요!");
                    return true;
                } else if (args.length < 3) {
                    AdventureUtil.playerSound(player, Sound.ENTITY_VILLAGER_NO);
                    AdventureUtil.playerMessage(player, "{@p} &7생성할 칭호 표시 이름을 입력하여 주세요!");
                    return true;
                }

                name = args[1];
                displayName = args[2];
                if (XTitle.getTitleManager().isPrefix(name)) {
                    AdventureUtil.playerSound(player, Sound.ENTITY_VILLAGER_NO);
                    AdventureUtil.playerMessage(player, "{@p} &e" + name + "&7(은)는 이미 존재하는 칭호 이름입니다!");
                    return true;
                }

                XTitle.getTitleManager().addNewPrefix(name, displayName);
                XTitle.getTeamManager().enable(name);
                AdventureUtil.playerSound(player, Sound.ENTITY_PLAYER_LEVELUP);
                AdventureUtil.playerMessage(player, "{@p} &e" + name + "&f(을)를 생성하셨습니다!");
                prefix = XTitle.getTitleManager().fetchPrefix(name);
                Bukkit.getPluginManager().callEvent(new CreatedPrefixEvent(player, prefix));
                break;
            case "삭제":
                if (!player.hasPermission("xtitle.admin")) {
                    AdventureUtil.playerSound(player, Sound.ENTITY_VILLAGER_NO);
                    AdventureUtil.playerMessage(player, "{@p} &7당신은 해당 명령어를 사용할 권한이 없습니다!");
                    return true;
                } else if (args.length < 2) {
                    AdventureUtil.playerSound(player, Sound.ENTITY_VILLAGER_NO);
                    AdventureUtil.playerMessage(player, "{@p} &7삭제할 칭호 이름을 입력하여 주세요!");
                    return true;
                }

                name = args[1];
                if (!XTitle.getTitleManager().isPrefix(name)) {
                    AdventureUtil.playerSound(player, Sound.ENTITY_VILLAGER_NO);
                    AdventureUtil.playerMessage(player, "{@p} &e" + name + "&7(은)는 존재하지 않는 칭호 이름입니다!");
                    return true;
                } else if (XTitle.getTitleManager().getPrefixList().size() == 54) {
                    AdventureUtil.playerSound(player, Sound.ENTITY_VILLAGER_NO);
                    AdventureUtil.playerMessage(player, "{@p} &7칭호 생성 횟수는 &e최대 54회&7입니다!");
                    return true;
                }

                prefix = XTitle.getTitleManager().fetchPrefix(name);
                XTitle.getTitleManager().deletePrefix(name);
                XTitle.getTeamManager().disable(name);
                AdventureUtil.playerSound(player, Sound.ENTITY_PLAYER_LEVELUP);
                AdventureUtil.playerMessage(player, "{@p} &e" + name + "&f(을)를 삭제하셨습니다!");
                Bukkit.getPluginManager().callEvent(new DeletedPrefixEvent(player, prefix));
                break;
            case "수정":
                if (!player.hasPermission("xtitle.admin")) {
                    AdventureUtil.playerSound(player, Sound.ENTITY_VILLAGER_NO);
                    AdventureUtil.playerMessage(player, "{@p} &7당신은 해당 명령어를 사용할 권한이 없습니다!");
                    return true;
                } else if (args.length < 2) {
                    AdventureUtil.playerSound(player, Sound.ENTITY_VILLAGER_NO);
                    AdventureUtil.playerMessage(player, "{@p} &7수정할 칭호 이름을 입력하여 주세요!");
                    return true;
                }

                name = args[1];
                if (!XTitle.getTitleManager().isPrefix(name)) {
                    AdventureUtil.playerSound(player, Sound.ENTITY_VILLAGER_NO);
                    AdventureUtil.playerMessage(player, "{@p} &e" + name + "&7(은)는 존재하지 않는 칭호 이름입니다!");
                    return true;
                }

                new InvPrefixEditor(player).load();
                XTitle.getStrMap().put(player, name);
                break;
            case "지급":
                if (!player.hasPermission("xtitle.admin")) {
                    AdventureUtil.playerSound(player, Sound.ENTITY_VILLAGER_NO);
                    AdventureUtil.playerMessage(player, "{@p} &7당신은 해당 명령어를 사용할 권한이 없습니다!");
                    return true;
                } else if (args.length < 2) {
                    AdventureUtil.playerSound(player, Sound.ENTITY_VILLAGER_NO);
                    AdventureUtil.playerMessage(player, "{@p} &7수정할 칭호 이름을 입력하여 주세요!");
                    return true;
                }

                name = args[1];
                if (!XTitle.getTitleManager().isPrefix(name)) {
                    AdventureUtil.playerSound(player, Sound.ENTITY_VILLAGER_NO);
                    AdventureUtil.playerMessage(player, "{@p} &e" + name + "&7(은)는 존재하지 않는 칭호 이름입니다!");
                    return true;
                }

                IPrefix pf = XTitle.getTitleManager().fetchPrefix(name);
                ItemUtil item = new ItemUtil(Material.ENCHANTED_BOOK);
                item.setDisplayName(AdventureUtil.fetchGradient("fe82e7", "7d04df", "&l칭호북"));
                item.setLore(Arrays.asList(
                        ColorUtil.fetchColor(""),
                        ColorUtil.fetchColor(" &8&l┏ &f미리보기 : &r" + pf.getPrefix() + "Player" + pf.getSuffix()),
                        ColorUtil.fetchColor(" &8&l┗ &7칭호북을 사용하려면 들고 우클릭하여 주세요!"),
                        ColorUtil.fetchColor("")
                ));
                item.setItemFlag(ItemFlag.HIDE_ATTRIBUTES);

                ItemNmsUtil itemNms = new ItemNmsUtil(item.getItemStack());
                itemNms.asTag("NAME", name);

                player.getInventory().addItem(itemNms.asItemStack());
                AdventureUtil.playerSound(player, Sound.ENTITY_PLAYER_LEVELUP);
                AdventureUtil.playerMessage(player, "{@p} &e" + name + "&f(을)를 지급받으셨습니다!");
                break;
        }
        return false;
    }

}

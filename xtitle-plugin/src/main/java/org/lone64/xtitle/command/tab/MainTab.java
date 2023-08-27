package org.lone64.xtitle.command.tab;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lone64.xtitle.XTitle;
import org.lone64.xtitle.api.cross.prefix.IPrefix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainTab implements TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String arg, @NotNull String[] args) {
        List<String> tab = new ArrayList<>();
        if (sender instanceof Player player) {
            if (args.length == 1) {
                if (player.hasPermission("xtitle.admin"))
                    tab.addAll(Arrays.asList("적용", "해제", "목록", "생성", "삭제", "수정", "지급"));
                else tab.addAll(Arrays.asList("적용", "해제", "목록"));
            } else if (args.length == 2) {
                if (player.hasPermission("xtitle.admin")) {
                    if (args[0].equals("적용")) tab.addAll(getDisplayNames(player));
                    if (args[0].equals("생성")) tab.add("[ 생성할 칭호 이름 ]");
                    if (args[0].equals("삭제") || args[0].equals("수정") || args[0].equals("지급")) tab.addAll(getPrefixList());
                } else {
                    if (args[0].equals("적용")) tab.addAll(getDisplayNames(player));
                }
            } else if (args.length == 3) {
                if (args[0].equals("생성")) tab.add("[ 생성할 칭호 표시 이름 ]");
            }
        }
        return tab;
    }

    private List<String> getPrefixList() {
        List<String> stringList = new ArrayList<>();
        for (IPrefix prefix : XTitle.getTitleManager().getPrefixList()) {
            stringList.add(prefix.getName());
        }
        return stringList;
    }

    private List<String> getDisplayNames(Player player) {
        List<String> stringList = new ArrayList<>();
        for (IPrefix prefix : XTitle.getTitleManager().getPrefixList()) {
            if (!prefix.getPlayerList().contains(player.getUniqueId())) continue;
            stringList.add(prefix.getDisplayName());
        }
        return stringList;
    }

}

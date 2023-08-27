package org.lone64.xtitle.inventory;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.lone64.xtitle.XTitle;
import org.lone64.xtitle.api.cross.prefix.IPrefix;
import org.lone64.xtitle.util.adventure.AdventureUtil;
import org.lone64.xtitle.util.color.ColorUtil;
import org.lone64.xtitle.util.inventory.InvUtil;
import org.lone64.xtitle.util.item.ItemUtil;

import java.util.Arrays;

public class InvPrefixList extends InvUtil {

    public InvPrefixList(Player player) {
        super(player, "&8칭호 목록", 54);

        for (IPrefix prefix : XTitle.getTitleManager().getPrefixList()) {
            this.addItem(new ItemUtil(Material.ENCHANTED_BOOK)
                    .setDisplayName(AdventureUtil.fetchGradient("8301f6", "01f6a6", "&l" + prefix.getDisplayName()))
                    .setLore(Arrays.asList(
                            ColorUtil.fetchColor(""),
                            ColorUtil.fetchColor(" &8&l┏ &f미리보기 : &r" + prefix.getPrefix() + player.getName() + prefix.getSuffix()),
                            ColorUtil.fetchColor(" &8&l┗ &e칭호 적용은 &7/칭호 적용&e을 사용해주세요!"),
                            ColorUtil.fetchColor("")
                    )).setItemFlag(ItemFlag.HIDE_ATTRIBUTES).getItemStack());
        }
    }

}

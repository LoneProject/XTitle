package org.lone64.xtitle.inventory;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.lone64.xtitle.util.adventure.AdventureUtil;
import org.lone64.xtitle.util.color.ColorUtil;
import org.lone64.xtitle.util.inventory.InvUtil;
import org.lone64.xtitle.util.item.ItemUtil;

import java.util.Arrays;

public class InvPrefixEditor extends InvUtil {

    public InvPrefixEditor(Player player) {
        super(player, "&8칭호 수정", 27);

        this.setItem(10, new ItemUtil(Material.BIRCH_SIGN)
                .setDisplayName(AdventureUtil.fetchGradient("8301f6", "01f6a6", "&l칭호 표시 이름 설정"))
                .setLore(Arrays.asList(
                        ColorUtil.fetchColor(""),
                        ColorUtil.fetchColor(" &8&l┏ &f칭호의 displayName 부분을 설정합니다."),
                        ColorUtil.fetchColor(" &8&l┗ &e표시 이름을 설정하려면 클릭하여 주세요!"),
                        ColorUtil.fetchColor("")
                )).getItemStack());

        this.setItem(13, new ItemUtil(Material.JUNGLE_SIGN)
                .setDisplayName(AdventureUtil.fetchGradient("8301f6", "01f6a6", "&l칭호 접두사 설정"))
                .setLore(Arrays.asList(
                        ColorUtil.fetchColor(""),
                        ColorUtil.fetchColor(" &8&l┏ &f칭호의 prefix 부분을 설정합니다."),
                        ColorUtil.fetchColor(" &8&l┗ &e접두사를 설정하려면 클릭하여 주세요!"),
                        ColorUtil.fetchColor("")
                )).getItemStack());

        this.setItem(16, new ItemUtil(Material.WARPED_SIGN)
                .setDisplayName(AdventureUtil.fetchGradient("8301f6", "01f6a6", "&l칭호 접미사 설정"))
                .setLore(Arrays.asList(
                        ColorUtil.fetchColor(""),
                        ColorUtil.fetchColor(" &8&l┏ &f칭호의 suffix 부분을 설정합니다."),
                        ColorUtil.fetchColor(" &8&l┗ &e접미사를 설정하려면 클릭하여 주세요!"),
                        ColorUtil.fetchColor("")
                )).getItemStack());
    }

}

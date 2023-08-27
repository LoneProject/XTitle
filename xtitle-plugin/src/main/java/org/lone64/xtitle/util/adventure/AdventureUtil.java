package org.lone64.xtitle.util.adventure;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.lone64.xtitle.XTitle;
import org.lone64.xtitle.util.color.ColorUtil;
import org.lone64.xtitle.util.gradient.builders.GradientTextBuilder;

public class AdventureUtil {

    public static String getPrefix() {
        return new GradientTextBuilder().text(XTitle.getPrefix())
                .addColor("#00ffff")
                .addColor("#6a5acd")
                .blur(0.12)
                .build()
                .getJsonText();
    }

    public static void consoleMessage(String s) {
        if (s == null) return;
        String name = getPrefix();
        Bukkit.getConsoleSender().sendMessage(ColorUtil.fetchColor(s.replace("{@p}", name)));
    }

    public static void playerMessage(Player player, String s) {
        if (s == null) return;
        String name = getPrefix();
        player.sendMessage(ColorUtil.fetchColor(s.replace("{@p}", name)));
    }

    public static void playerSound(Player player, Sound sound) {
        if (sound == null) return;
        player.playSound(player.getLocation(), sound, 1, 2);
    }

    public static String fetchGradient(String firstColor, String twiceColor, String message) {
        return new GradientTextBuilder().text(message)
                .addColor("#" + firstColor)
                .addColor("#" + twiceColor)
                .blur(0.12)
                .build()
                .getJsonText();
    }

}

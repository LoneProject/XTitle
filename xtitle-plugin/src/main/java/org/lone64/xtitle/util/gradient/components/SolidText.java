package org.lone64.xtitle.util.gradient.components;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.lone64.xtitle.util.gradient.AdvancedColor;
import org.lone64.xtitle.util.gradient.BaseTextComponent;

public class SolidText extends BaseTextComponent {

    private AdvancedColor _color;
    private String _text;

    @Override
    public TextComponent renderComponent() {
        TextComponent txt = new TextComponent(_text.replaceAll("&","\u00A7"));
        txt.setColor(ChatColor.of("#"+_color.getHex()));

        txt.addExtra(super.renderComponent());
        return txt;
    }

    public SolidText(String text, AdvancedColor color){
        _color = color;
        _text = text;
    }

}
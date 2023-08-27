package org.lone64.xtitle.util.gradient.builders;

import org.lone64.xtitle.util.gradient.AdvancedColor;
import org.lone64.xtitle.util.gradient.components.SolidText;

public class SolidTextBuilder {

    private AdvancedColor color;
    private String text;

    public SolidTextBuilder text(String text){
        this.text = text;
        return this;
    }

    public SolidTextBuilder color(AdvancedColor color){
        this.color = color;
        return this;
    }

    public SolidTextBuilder color(String color){
        this.color = new AdvancedColor(color);
        return this;
    }

    public SolidText build(){
        return new SolidText(text,color);
    }

}
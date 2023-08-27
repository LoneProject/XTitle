package org.lone64.xtitle.util.gradient.builders;

import org.lone64.xtitle.util.gradient.AdvancedColor;
import org.lone64.xtitle.util.gradient.components.GradientedText;

import java.util.ArrayList;
import java.util.Arrays;

public class GradientTextBuilder {

    private ArrayList<AdvancedColor> colors = new ArrayList<>();
    private String text;
    private double blur;

    public GradientTextBuilder text(String text){
        this.text = text;
        return this;
    }
    public GradientTextBuilder blur(double x){
        blur = x;
        return this;
    }

    public GradientTextBuilder addColor(AdvancedColor color){
        colors.add(color);
        return this;
    }

    public GradientTextBuilder addColor(String hex){
        colors.add(new AdvancedColor(hex));
        return this;
    }

    public GradientTextBuilder addColors(ArrayList<AdvancedColor> colors){
        this.colors.addAll(colors);
        return this;
    }

    public GradientTextBuilder addColors(AdvancedColor[] colors){
        this.colors.addAll(Arrays.asList(colors));
        return this;
    }

    public GradientedText build(){
        return new GradientedText(text,colors,blur);
    }

}
package org.lone64.xtitle.util.nbt;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemNmsUtil {

    private final ItemStack stack;

    public ItemNmsUtil(ItemStack stack) {
        this.stack = stack;
    }

    public ItemNmsUtil asTag(String name, String val) {
        ItemMeta meta = this.stack.getItemMeta();
        String tag = meta.getLocalizedName();
        meta.setLocalizedName(tag + "[" + name + ":" + val + "],");
        this.stack.setItemMeta(meta);
        return this;
    }

    public String asTag(String name) {
        ItemMeta meta = this.stack.getItemMeta();
        for (String s : meta.getLocalizedName().split(",")) {
            if (s.contains(name)) return s.replace(name + ":", "").replace("[", "")
                    .replace("]", "").replace(",", "");
        }
        return null;
    }

    public ItemStack asItemStack() {
        return stack;
    }

    public String toString() {
        return this.stack.getItemMeta().getLocalizedName();
    }

}

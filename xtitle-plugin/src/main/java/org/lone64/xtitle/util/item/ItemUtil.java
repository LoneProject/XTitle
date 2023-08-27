package org.lone64.xtitle.util.item;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

public class ItemUtil {

    private ItemStack item;

    public ItemUtil(Material material) {
        this.item = new ItemStack(material);
    }

    public ItemUtil(ItemStack item) {
        this.item = item;
    }

    public ItemUtil setDisplayName(String displayName) {
        ItemMeta meta = this.item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemUtil setLore(String lore) {
        ItemMeta meta = this.item.getItemMeta();
        List<String> lores = new ArrayList<>();
        lores.add(ChatColor.translateAlternateColorCodes('&', lore));
        meta.setLore(lores);
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemUtil setLore(List<String> lores) {
        ItemMeta meta = this.item.getItemMeta();
        meta.setLore(lores);
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemUtil setLore(ArrayList<String> lores) {
        ItemMeta meta = this.item.getItemMeta();
        meta.setLore(lores);
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemUtil setCustomPotion(PotionEffect pe) {
        PotionMeta meta = (PotionMeta) this.item.getItemMeta();
        meta.addCustomEffect(pe, true);
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemUtil setId(int id) {
        ItemMeta meta = this.item.getItemMeta();

        assert meta != null;
        meta.setLocalizedName(String.valueOf(id));

        this.item.setItemMeta(meta);
        return this;
    }

    public ItemUtil addAttributeModifier(Attribute attribute, AttributeModifier modifier) {
        ItemMeta meta = this.item.getItemMeta();

        assert meta != null;
        meta.addAttributeModifier(attribute, modifier);

        this.item.setItemMeta(meta);
        return this;
    }

    public int getId() {
        ItemMeta meta = this.item.getItemMeta();
        return Integer.parseInt(meta.getLocalizedName());
    }

    public ItemUtil setAmount(int amount) {
        this.item.setAmount(amount);
        return this;
    }

    public ItemUtil setEnchant(Enchantment e, int i) {
        ItemMeta meta = this.item.getItemMeta();
        meta.addEnchant(e, i, true);
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemUtil setEnchant(Enchantment e, int i, boolean b) {
        ItemMeta meta = this.item.getItemMeta();
        meta.addEnchant(e, i, b);
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemUtil setItemFlag(ItemFlag iF) {
        ItemMeta meta = this.item.getItemMeta();
        if (meta.hasItemFlag(iF)){
            meta.removeItemFlags(iF);
        } else meta.addItemFlags(iF);
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemUtil setLeatherColor(Color color) {
        if (this.item.getType() != Material.LEATHER_BOOTS && this.item.getType() != Material.LEATHER_CHESTPLATE && this.item.getType() != Material.LEATHER_HELMET && this.item.getType() != Material.LEATHER_LEGGINGS) {
            throw new IllegalArgumentException("color() only applicable for leather armor!");
        } else {
            LeatherArmorMeta meta = (LeatherArmorMeta) this.item.getItemMeta();
            meta.setColor(color);
            this.item.setItemMeta(meta);
            return this;
        }
    }

    public ItemUtil setUnbreakable(boolean b) {
        ItemMeta meta = this.item.getItemMeta();
        meta.setUnbreakable(b);
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemUtil setOwner(String name) {
        SkullMeta meta = (SkullMeta) this.item.getItemMeta();
        meta.setOwner(name);
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemStack getItemStack() {
        return this.item;
    }

}

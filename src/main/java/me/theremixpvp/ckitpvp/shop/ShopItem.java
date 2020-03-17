package me.theremixpvp.ckitpvp.shop;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ShopItem {

    private String name;

    private List<String> info;

    private ItemStack item;

    private int price;

    public ShopItem(String name, ItemStack item, List<String> info, int price) {
        this.name = name;
        this.info = info;
        this.item = item;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public List<String> getInfo() {
        return info;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}

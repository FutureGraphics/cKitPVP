package me.theremixpvp.ckitpvp.shop;

import com.flouet.code.base.utils.MapUtils;
import com.flouet.code.utilities.minecraft.api.item.parse.ItemParser;
import com.flouet.code.utilities.minecraft.api.utilities.InventoryUtils;
import me.theremixpvp.ckitpvp.kit.Kit;
import me.theremixpvp.ckitpvp.User;
import me.theremixpvp.ckitpvp.exceptions.ShopParsingException;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShopItem {

    private final ShopType type;
    private final ItemStack item;
    private final int price;

    public ShopItem(ShopType type, ItemStack item, int price) {
        this.type = type;
        this.item = item;
        this.price = price;
    }


    public ItemStack getItem() {
        return item;
    }

    public int getPrice() {
        return price;
    }

    public ShopType getType() {
        return type;
    }

    public static ShopItem parse(String item) throws ShopParsingException {
        Map<String, String> map = MapUtils.splitString(item, ",", ":");

        if(!MapUtils.containsKeys(map, "price")) {
            throw new ShopParsingException("Could not parse item because price key is not given");
        }

        ItemStack stack = ItemParser.DEFAULT_PARSER.parse(item);

        int price = Integer.parseInt(map.get("price"));

        ShopType type = ShopType.COMMON;
        if(map.containsKey("type"))
            type = ShopType.valueOf(map.get("type"));

        return new ShopItem(type, stack, price);
    }

    public ItemStack createItem(Player player) {
        User user = User.byPlayer(player);

        ItemStack stack = item.clone();
        ItemMeta meta = stack.getItemMeta();
        List<String> lore;
        if(meta.hasLore())
            lore = meta.getLore();
        else
            lore = new ArrayList<>();

        lore.add("");
        lore.add("§dPrice: §a" + this.price);

        if (type == ShopType.KIT && user.kitUnlocked(getKitName()))
            lore.add("&a&lKit Owned");

        stack.setItemMeta(meta);

        return stack;
    }

    public void onClick(User user) {
        Player player = user.getPlayer();
        if(user.getBalance() < price) {
            player.sendMessage("&cYou don't have enough credits");
            return;
        }

        if(type == ShopType.COMMON) {
            user.removeBalance(price);
            InventoryUtils.giveOrDrop(player, item.clone());
            player.playSound(player.getLocation(), Sound.LEVEL_UP, 1f, 1f);
            player.sendMessage("&aPurchase successful");
            return;
        }

        if(type == ShopType.KIT) {
            if(user.kitUnlocked(getKitName())) {
                player.sendMessage("&aYou already own this kit");
                return;
            }

            user.removeBalance(price);
            Kit kit = Kit.byName(getKitName());
            user.addkit(kit);
            player.closeInventory();
            player.sendMessage("&a" + kit.getName() + " unlocked");
            return;
        }

        if(type == ShopType.ENCHANTMENT) {
            ItemStack itemInHand = player.getItemInHand();
            if(itemInHand == null) {
               player.sendMessage("&cYou need to hold an item in your hand");
               return;
            }

            user.removeBalance(price);
            for (Map.Entry<Enchantment, Integer> enchantments : item.getEnchantments().entrySet()) {
                itemInHand.addUnsafeEnchantment(enchantments.getKey(), enchantments.getValue());
            }

            player.sendMessage("&aEnchantment applied");
            return;
        }

    }

    private String getKitName() {
        if(!item.hasItemMeta() || !item.getItemMeta().hasDisplayName())
            return null;

        return item.getItemMeta().getDisplayName().toLowerCase().replace(" ", "");
    }

    public enum ShopType {
        COMMON,
        ENCHANTMENT,
        KIT
    }

}

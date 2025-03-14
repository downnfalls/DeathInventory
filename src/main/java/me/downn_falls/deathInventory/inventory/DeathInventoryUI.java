package me.downn_falls.deathInventory.inventory;

import me.downn_falls.deathInventory.DeathInventory;
import me.downn_falls.deathInventory.manager.ItemData;
import me.downn_falls.deathInventory.manager.PlayerData;
import me.downn_falls.deathInventory.utils.Utils;
import me.downn_falls.guiapi.GUI;
import me.downn_falls.guiapi.ItemStackBuilder;
import me.downn_falls.guiapi.component.GuiButton;
import me.downn_falls.guiapi.component.GuiListPage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.units.qual.K;

import java.math.BigDecimal;

public class DeathInventoryUI extends GUI {

    private final ConfigurationSection configGUI = DeathInventory.getInstance().getConfig().getConfigurationSection("gui");
    private final ConfigurationSection configSetting = DeathInventory.getInstance().getConfig().getConfigurationSection("setting");

    public DeathInventoryUI(Player player) {
        super(DeathInventory.getInstance().getConfig().getString("gui.title"), 6);

        PlayerData playerData = PlayerData.get(player);
        double price = configSetting.getDouble("price_per_slot");

        GuiButton button = new GuiButton(this, "close", configGUI.getInt("close_button.slot"));
        button.setDisplayItem(new ItemStackBuilder(Material.valueOf(configGUI.getString("close_button.material")), 1)
                .setDisplayName(configGUI.getString("close_button.display"))
                .addLore(configGUI.getStringList("close_button.lore").toArray(String[]::new))
                .setModel(configGUI.getInt("close_button.model")).build());
        button.addListener((id, nbt, event) -> {
            event.getWhoClicked().closeInventory();
        });

        addComponent(button);

        GuiListPage pageItems = new GuiListPage(this, "pageItems", configGUI.getInt("page_items.slot"), configGUI.getInt("page_items.row"), configGUI.getInt("page_items.column"), configGUI.getInt("page_items.prev_button.slot"), configGUI.getInt("page_items.next_button.slot"));
        pageItems.setPrevButton(new ItemStackBuilder(Material.valueOf(configGUI.getString("page_items.prev_button.material")), 1).setDisplayName(configGUI.getString("page_items.prev_button.display")).addLore(configGUI.getStringList("page_items.prev_button.lore").toArray(String[]::new)).setModel(configGUI.getInt("page_items.prev_button.model")).build());
        pageItems.setNextButton(new ItemStackBuilder(Material.valueOf(configGUI.getString("page_items.next_button.material")), 1).setDisplayName(configGUI.getString("page_items.next_button.display")).addLore(configGUI.getStringList("page_items.next_button.lore").toArray(String[]::new)).setModel(configGUI.getInt("page_items.next_button.model")).build());

        pageItems.setNotAvailableButton(new ItemStackBuilder(Material.AIR, 1).build());
        pageItems.setNotAvailableComponent(new ItemStackBuilder(Material.AIR, 1).build());

        for (ItemData itemData : playerData.getItems()) {

            GuiButton item = new GuiButton(this, itemData.getItemUuid().toString(), 0);
            item.setDisplayItem(new ItemStackBuilder(itemData.getItem())
                    .addLore(configGUI.getStringList("item.additional_lore").stream().map(line -> line.replace("{price}", String.valueOf(price))).toArray(String[]::new)).build());

            item.addListener((id, nbt, event) -> {
                if (DeathInventory.getXConomyAPI().changePlayerBalance(player.getUniqueId(), player.getName(), BigDecimal.valueOf(price), false, DeathInventory.getInstance().getName()) == 0) {
                    if (canFitItem(player, itemData.getItem())) {
                        playerData.removeItem(itemData);
                        player.getInventory().addItem(itemData.getItem());
                        pageItems.removeComponent(itemData.getItemUuid().toString());
                        for (String command : configSetting.getStringList("command_on_claim")) {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("{player}", player.getName()));
                        }
                        repaint();
                    }
                }
            });

            pageItems.addComponent(item);
        }

        addComponent(pageItems);

    }
    public boolean canFitItem(Player player, ItemStack item) {
        Inventory inventory = player.getInventory();

        // Check if there's an empty slot
        if (inventory.firstEmpty() != -1) {
            return true;
        }

        // Check if the item can stack with existing items
        for (ItemStack content : inventory.getContents()) {
            if (content != null && content.isSimilar(item) && content.getAmount() + item.getAmount() <= content.getMaxStackSize()) {
                return true;
            }
        }

        return false; // No space for the item
    }


}

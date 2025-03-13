package me.downn_falls.deathInventory.inventory;

import me.downn_falls.deathInventory.DeathInventory;
import me.downn_falls.deathInventory.manager.PlayerData;
import me.downn_falls.deathInventory.utils.Utils;
import me.downn_falls.guiapi.GUI;
import me.downn_falls.guiapi.ItemStackBuilder;
import me.downn_falls.guiapi.component.GuiButton;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class DeathInventoryUI extends GUI {

    private final ConfigurationSection configGUI = DeathInventory.getInstance().getConfig().getConfigurationSection("gui");
    private final ConfigurationSection configSetting = DeathInventory.getInstance().getConfig().getConfigurationSection("setting");

    public DeathInventoryUI(Player player) {
        super(Utils.getColoredMessage("gui-title", false), 1);

        assert configGUI != null;
        assert configSetting != null;

        PlayerData playerData = PlayerData.get(player);

        for (int i = 1; i <= 3; i++) {
            GuiButton death = new GuiButton(this, "death"+i, configGUI.getInt("death"+i+".slot"));
            int finalI = i;
            death.whenUpdate(() -> {
                DeathData deathData = playerData.getDeathData().getList().get(0);
                if (player.hasPermission("deathinventory.death.death"+ finalI)) {
                    if (playerData.getDeathData().getList().get(0) != null) {
                        death.setDisplayItem(new ItemStackBuilder(
                                Material.valueOf(configGUI.getString("death"+finalI+".full.material")), 1)
                                .setDisplayName(configGUI.getString("death"+finalI+".full.display_name").replace("{death}", "" + deathData.getDeathRound()))
                                .addLore(configGUI.getStringList("death"+finalI+".full.lore").stream().map(s -> s
                                                .replace("{date}", Utils.getDateFormatted(deathData.getDate(), "dd/MM/yyyy | HH:mm"))
                                                .replace("{price}", "" + configSetting.getInt("claim_price")))
                                        .toArray(String[]::new)).build());
                    } else {
                        death.setDisplayItem(new ItemStackBuilder(
                                Material.valueOf(configGUI.getString("death"+finalI+".empty.material")), 1)
                                .setDisplayName(configGUI.getString("death"+finalI+".empty.display_name"))
                                .addLore(configGUI.getStringList("death"+finalI+".empty.lore").toArray(String[]::new)).build());
                    }
                } else {
                    if (playerData.getDeathData().getList().get(0) != null) {
                        death.setDisplayItem(new ItemStackBuilder(
                                Material.valueOf(configGUI.getString("death"+finalI+".full_lock.material")), 1)
                                .setDisplayName(configGUI.getString("death"+finalI+".full_lock.display_name").replace("{death}", "" + deathData.getDeathRound()))
                                .addLore(configGUI.getStringList("death"+finalI+".full_lock.lore").stream().map(s -> s
                                                .replace("{date}", Utils.getDateFormatted(deathData.getDate(), "dd/MM/yyyy | HH:mm"))
                                                .replace("{price}", "" + configSetting.getInt("claim_price")))
                                        .toArray(String[]::new)).build());
                    } else {
                        death.setDisplayItem(new ItemStackBuilder(
                                Material.valueOf(configGUI.getString("death"+finalI+".empty_lock.material")), 1)
                                .setDisplayName(configGUI.getString("death"+finalI+".empty_lock.display_name"))
                                .addLore(configGUI.getStringList("death"+finalI+".empty_lock.lore").toArray(String[]::new)).build());
                    }
                }

            });

            addComponent(death);
        }


    }
}

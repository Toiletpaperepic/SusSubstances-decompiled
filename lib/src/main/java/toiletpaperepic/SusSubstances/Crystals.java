package toiletpaperepic.SusSubstances;

import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;

import com.google.common.collect.Maps;

import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;


public class Crystals implements Item {
    public HashMap<UUID, Integer> crystalList = Maps.newHashMap();
    public Plugin plugin = (Plugin)Main.getPlugin(Main.class);

    private ItemValues itemvalues = new ItemValues();

    public ItemValues getitemvalues() {
        itemvalues.block = Material.AMETHYST_SHARD;
        itemvalues.material = Material.AMETHYST_SHARD;
        itemvalues.deathmessage = "used a Crystal and died";
        itemvalues.name = "Crystals";
        itemvalues.ItemList = crystalList;
        return itemvalues;
    };

    public ItemStack getitemsstack() {
        ItemStack crystal = new ItemStack(getitemvalues().material);
        crystal.setAmount(1);

        ItemMeta m = crystal.getItemMeta();
        assert m != null;

        m.setDisplayName(ChatColor.DARK_PURPLE + getitemvalues().name);
        crystal.setItemMeta(m);
        return crystal;
    }

    public void PlayerInteract(final Player p, ItemStack item) {
        p.sendMessage(ChatColor.RED + "Crystals is not implemented!");
		Main.log.log(Level.SEVERE, "Crystals is not implemented!");
    }
}
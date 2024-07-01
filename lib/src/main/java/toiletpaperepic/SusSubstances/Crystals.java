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
    public HashMap<UUID, Integer> CrystalList = Maps.newHashMap();
    public Plugin plugin = (Plugin)Main.getPlugin(Main.class);

    public String getname() { return "Crystals"; };
    public Material getmaterial() { return Material.AMETHYST_SHARD; };

    public ItemStack getitem() {
        ItemStack crystal = new ItemStack(getmaterial());
        crystal.setAmount(1);

        ItemMeta m = crystal.getItemMeta();
        assert m != null;

        m.setDisplayName(ChatColor.DARK_PURPLE + getname());
        crystal.setItemMeta(m);
        return crystal;
    }

    private void triggerHigh(Player p) {
		Main.log.log(Level.SEVERE, "Crystals is not implemented!");
	}

    public void PlayerInteract(final Player p, ItemStack item) {
        p.sendMessage(ChatColor.RED + "Crystals is not implemented!");
		Main.log.log(Level.SEVERE, "Crystals is not implemented!");
    }
}
package toiletpaperepic.SusSubstances;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

// for use in all items
public interface Item {
    public String getname();
    public Material getmaterial();

    ItemStack getitem();
    void PlayerInteract(final Player p, ItemStack item);
}
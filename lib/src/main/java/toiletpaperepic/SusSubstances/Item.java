package toiletpaperepic.SusSubstances;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

// for use in all items
public interface Item {
    ItemStack getitemsstack();

    // I'm a bit freaked out about changing return values, also changes in the class.
    // .getitemvalues().rate = 9.0;
    ItemValues getitemvalues();
    //Ew...

    void PlayerInteract(final Player p, ItemStack item);

    default void HandlePlayerMove(Player p) {
        // Main.log.warning(getitemvalues().name + " Does not implement HandlePlayerMove!");
    }

    default void HandleVelEvent(Player p) {
        // Main.log.warning(getitemvalues().name + " Does not implement PlayerMove!");
    }
}
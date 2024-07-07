package toiletpaperepic.SusSubstances;

import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.command.Command;
import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

//TODO: remove all handles functions in here.

public class Events implements Listener, CommandExecutor {
    public Events(Main main) {
        Main.getPlugin(Main.class).getServer().getPluginManager().registerEvents(this, main);
        Objects.requireNonNull(Bukkit.getPluginCommand("sapi")).setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equals("sapi"))
            //console has permission too.
            if (sender.hasPermission("substances.perms")) {
                if (args.length == 0) {
                    sender.sendMessage(ChatColor.RED + "Invalid Syntax!" + ChatColor.WHITE + " try /sapi help");
                } else {
                    if (args[0].equals("help")) {
                        sender.sendMessage(ChatColor.AQUA + "Options:");
                        sender.sendMessage("");
                        sender.sendMessage(ChatColor.GRAY + "> " + ChatColor.WHITE + "/sapi reload");
                        sender.sendMessage(ChatColor.GRAY + "> " + ChatColor.WHITE + "/sapi help");

                        return true;
                    }
                    if (args[0].equals("reload")) {
                        Main.getInstance().reloadConfig();
                        Main.registerConfig();
                        sender.sendMessage("Reloaded!");
                        return true;
                    }
                }
            }
        return true;
    }

    @EventHandler
    public void onVelEvent(PlayerVelocityEvent event) {
        Player p = event.getPlayer();
        for (int i = 0; i < Main.itemslist.size(); i++) {
            Main.itemslist.get(i).HandleVelEvent(p);
        }
    }

    //Leaving these two separate
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player p = event.getPlayer();
        for (int i = 0; i < Main.itemslist.size(); i++) {
            //handled by the item
            Main.itemslist.get(i).HandlePlayerMove(p);
        }
    }

    @EventHandler
    public void onToggleElytra(PlayerToggleFlightEvent e) {
        for (int i = 0; i < Main.itemslist.size(); i++) {
            ItemValues itemvals = Main.itemslist.get(i).getitemvalues();
            if (itemvals.flylist != null)
                if (itemvals.flylist.contains(e.getPlayer().getUniqueId()))
                    e.setCancelled(true);
            if (itemvals.ItemList.containsKey(e.getPlayer().getUniqueId()))
                e.setCancelled(true);
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        for (int i = 0; i < Main.itemslist.size(); i++) {
            ItemValues itemvals = Main.itemslist.get(i).getitemvalues();
            handleleave(e, itemvals.ItemList, itemvals.flylist);
        }
    }

    private void handleleave(PlayerQuitEvent quit, HashMap<UUID, Integer> list, ArrayList<UUID> flylist) {
        Player player = quit.getPlayer();
        if (list.containsKey(player.getUniqueId())) {
            list.remove(player.getUniqueId());

            if (!(flylist == null))
                list.remove(player.getUniqueId());
            for (PotionEffect effect : player.getActivePotionEffects())
                player.removePotionEffect(effect.getType());
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        for (int i = 0; i < Main.itemslist.size(); i++) {
            ItemValues itemvals = Main.itemslist.get(i).getitemvalues();
            handledeath(e, itemvals.ItemList, itemvals.deathmessage, itemvals.flylist);
        }
    }

    private void handledeath(PlayerDeathEvent death, HashMap<UUID, Integer> list, String message, ArrayList<UUID> flylist) {
        Player deadplayer = death.getEntity();
        if (list.containsKey(deadplayer.getUniqueId())) {
            if ((list.get(deadplayer.getUniqueId())).intValue() >= 5)
                death.setDeathMessage(deadplayer.getName() + " " + message + ".");
            list.remove(deadplayer.getUniqueId());

            if (!(flylist == null))
                flylist.remove(deadplayer.getUniqueId());
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        for (int i = 0; i < Main.itemslist.size(); i++) {
            handlebreak(e, Main.itemslist.get(i));
        }
    }

    private void handlebreak(BlockBreakEvent event, Item item) {
        if (item.getitemvalues().status == true && event.getBlock().getType() == item.getitemvalues().block && Math.random() < item.getitemvalues().rate) {
            Location loc = event.getBlock().getLocation();
            World world = loc.getWorld();
            assert world != null;
            world.dropItem(loc, item.getitemsstack());
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        for (int i = 0; i < Main.itemslist.size(); i++) {
            handleplayerinteract(e, Main.itemslist.get(i));
        }
    }

    private void handleplayerinteract(PlayerInteractEvent event, Item item) {
        Player p = event.getPlayer();
        ItemStack itemstack = p.getInventory().getItemInMainHand();

        // Main.log.info("" + itemstack.getType().equals(item.getmaterial()));

        if (itemstack.getType() != Material.AIR && (event.getAction().equals(Action.RIGHT_CLICK_AIR) | event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
            if (itemstack.getType().equals(item.getitemvalues().material) &&
                Objects.requireNonNull(itemstack.getItemMeta())
                    .getDisplayName()
                    .equals(ChatColor.DARK_PURPLE + item.getitemvalues().name)) {
                item.PlayerInteract(p, itemstack);
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        for (int i = 0; i < Main.itemslist.size(); i++) 
            handleplace(e, Main.itemslist.get(i));
    }

    private void handleplace(BlockPlaceEvent e, Item item) {
        Player p = e.getPlayer();
        if (p.getInventory().getItemInMainHand().getType() != Material.AIR) {
            ItemStack itemstack = p.getInventory().getItemInMainHand();
            if (itemstack.getType().equals(item.getitemvalues().material) &&
                Objects.requireNonNull(itemstack.getItemMeta())
                    .getDisplayName()
                    .equals(ChatColor.DARK_PURPLE + item.getitemvalues().name)) {
                e.setCancelled(true);
                e.getBlock()
                    .getWorld()
                    .getBlockAt(e.getBlock().getLocation())
                    .setType(Material.AIR);
            }
        }
    }
}
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

import java.util.Objects;

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
            Player player = e.getPlayer();
            if (itemvals.ItemList.containsKey(player.getUniqueId())) {
                itemvals.ItemList.remove(player.getUniqueId());

                if (!(itemvals.flylist == null))
                    itemvals.ItemList.remove(player.getUniqueId());
                for (PotionEffect effect : player.getActivePotionEffects())
                    player.removePotionEffect(effect.getType());
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        for (int i = 0; i < Main.itemslist.size(); i++) {
            ItemValues itemvals = Main.itemslist.get(i).getitemvalues();
            Player deadplayer = e.getEntity();
            if (itemvals.ItemList.containsKey(deadplayer.getUniqueId())) {
                if ((itemvals.ItemList.get(deadplayer.getUniqueId())).intValue() >= 5)
                    e.setDeathMessage(deadplayer.getName() + " " + itemvals.deathmessage + ".");
                itemvals.ItemList.remove(deadplayer.getUniqueId());

                if (!(itemvals.flylist == null))
                    itemvals.flylist.remove(deadplayer.getUniqueId());
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        for (int i = 0; i < Main.itemslist.size(); i++) {
            ItemValues itemvals = Main.itemslist.get(i).getitemvalues();
            if (itemvals.status == true && e.getBlock().getType() == itemvals.block && Math.random() < itemvals.rate) {
                Location loc = e.getBlock().getLocation();
                World world = loc.getWorld();
                assert world != null;
                world.dropItem(loc, Main.itemslist.get(i).getitemsstack());
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        for (int i = 0; i < Main.itemslist.size(); i++) {
            Player p = e.getPlayer();
            ItemStack itemstack = p.getInventory().getItemInMainHand();
            ItemValues itemvals = Main.itemslist.get(i).getitemvalues();

            // Main.log.info("" + itemstack.getType().equals(item.getmaterial()));

            if (itemstack.getType() != Material.AIR && (e.getAction().equals(Action.RIGHT_CLICK_AIR) | e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
                if (itemstack.getType().equals(itemvals.material) &&
                    Objects.requireNonNull(itemstack.getItemMeta())
                        .getDisplayName()
                        .equals(ChatColor.DARK_PURPLE + itemvals.name)) {
                    Main.itemslist.get(i).PlayerInteract(p, itemstack);
                }
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        for (int i = 0; i < Main.itemslist.size(); i++) {
            Player p = e.getPlayer();
            ItemValues itemvals = Main.itemslist.get(i).getitemvalues();
            if (p.getInventory().getItemInMainHand().getType() != Material.AIR) {
                ItemStack itemstack = p.getInventory().getItemInMainHand();
                if (itemstack.getType().equals(itemvals.material) &&
                    Objects.requireNonNull(itemstack.getItemMeta())
                        .getDisplayName()
                        .equals(ChatColor.DARK_PURPLE + itemvals.name)) {
                    e.setCancelled(true);
                    e.getBlock()
                        .getWorld()
                        .getBlockAt(e.getBlock().getLocation())
                        .setType(Material.AIR);
                }
            }
        }
    }
}
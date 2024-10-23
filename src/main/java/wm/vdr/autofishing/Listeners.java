package wm.vdr.autofishing;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_21_R2.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;

public class Listeners implements Listener {

    private AutoFishing main = AutoFishing.instance;

    @EventHandler
    public void onFishing(PlayerFishEvent e) {
        if(e.isCancelled()) return;

        Player player = e.getPlayer();

        if(!player.hasPermission("autofishing.use")) return;
        if(!main.getPlayerDataUtil().isAuto(player.getUniqueId())) return;

        if(e.getState() == State.BITE || e.getState() == State.CAUGHT_FISH) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    InteractionHand hand = getHand(player);
                    if(hand == null) return;
                    doRightClick(player, hand);
                }
            }.runTaskLater(main, e.getState() == State.BITE ? main.getConfig().getInt("Ticks_After_Bitten") : main.getConfig().getInt("Ticks_After_Caught"));
            return;
        }
    }

    private void doRightClick(Player player, InteractionHand hand) {
        ServerPlayer serverPlayer = null;
        try {
            serverPlayer = (ServerPlayer) player.getClass().getMethod("getHandle").invoke(player);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        if(main.getConfig().getBoolean("Only_Specific_Rod.Enable")) {
            ItemStack item = CraftItemStack.asBukkitCopy(serverPlayer.getItemInHand(hand));
            if(!item.hasItemMeta() || !item.getItemMeta().getPersistentDataContainer().has(main.key, PersistentDataType.BOOLEAN)) return;
        }

        serverPlayer.gameMode.useItem(serverPlayer, serverPlayer.level(), serverPlayer.getItemInHand(hand), hand);
        serverPlayer.swing(hand, true);
    }

    private InteractionHand getHand(Player player) {
        return player.getInventory().getItemInMainHand().getType().equals(Material.FISHING_ROD) ?
                InteractionHand.MAIN_HAND : player.getInventory().getItemInOffHand().getType().equals(Material.FISHING_ROD) ?
                InteractionHand.OFF_HAND : null;
    }

}

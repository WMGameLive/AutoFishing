package wm.vdr.autofishing;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;

public class Listeners implements Listener {

    private AutoFishing main = AutoFishing.instance;

    @EventHandler
    public void onFishing(PlayerFishEvent e) {
        if(e.isCancelled()) return;

        Player player = e.getPlayer();

        if(!player.hasPermission("autofishing.use")) return;

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
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        serverPlayer.gameMode.useItem(serverPlayer, serverPlayer.getLevel(), serverPlayer.getItemInHand(hand), hand);
        serverPlayer.swing(hand, true);
    }

    private InteractionHand getHand(Player player) {
        return player.getInventory().getItemInMainHand().getType().equals(Material.FISHING_ROD) ?
                InteractionHand.MAIN_HAND : player.getInventory().getItemInOffHand().getType().equals(Material.FISHING_ROD) ?
                InteractionHand.OFF_HAND : null;
    }
}

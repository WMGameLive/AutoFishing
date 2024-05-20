package wm.vdr.autofishing;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Executors implements CommandExecutor, TabCompleter {

    private AutoFishing main = AutoFishing.instance;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0) {
            String[] helpMessage = new String[]{
                    "§7]§8----- §eAutoFishing §8-----§7[",
                    "",
                    "§a/autofishing toggle §7- §etoggle the auto fishing ability",
                    "§a/autofishing give <player> §7- §egive the specific rod to someone",
                    "§a/autofishing reload §7- §ereload the config file",
                    "",
                    "§7[§8-----------------------§7["
            };
            for (String helpMessageLine : helpMessage) sender.sendMessage(helpMessageLine);
            return true;
        }
        if(args[0].equalsIgnoreCase("reload")) {
            if(!sender.hasPermission("autofishing.admin")) {
                sender.sendMessage("§cYou do not have permission to do that!");
                return true;
            }
            main.reloadConfig();
            main.getPlayerDataUtil().reloadPlayerData();
            sender.sendMessage("§aSuccessfully reloaded.");
            return true;
        }
        if(args[0].equalsIgnoreCase("give")) {
            if(!sender.hasPermission("autofishing.admin")) {
                sender.sendMessage("§cYou do not have permission to do that!");
                return true;
            }
            Player player;
            if(args.length < 2) {
                if(!(sender instanceof Player)) {
                    sender.sendMessage("§cUsage: /autofishing give <Player>");
                    return true;
                }
                player = (Player) sender;
            }else {
                player = main.getServer().getPlayer(args[1]);
            }

            if(player == null) {
                sender.sendMessage("§cThe player does not exist.");
                return true;
            }

            player.getInventory().addItem(main.getSpecificRod()).values().forEach(itemStack -> {
                player.getWorld().dropItem(player.getLocation(), itemStack);
            });

            sender.sendMessage("§aGave the fishing rod to §e" + player.getName());
            return true;
        }
        if(args[0].equalsIgnoreCase("toggle")) {
            if(!(sender instanceof Player)) {
                sender.sendMessage("§cOnly players can use this command!");
                return true;
            }
            Player player = (Player) sender;
            if(!player.hasPermission("autofishing.use")) {
                player.sendMessage("§cYou do not have permission to do that!");
                return true;
            }
            boolean auto = main.getPlayerDataUtil().isAuto(player.getUniqueId());
            main.getPlayerDataUtil().setAuto(player.getUniqueId(), !auto);

            player.sendMessage(auto ? "§eAuto-fishing is now disabled." : "§aAuto-fishing is now enabled.");
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        final List<String> completions = new ArrayList<>();
        List<String> COMMANDS = new ArrayList<>();
        if(args.length == 1) {
            if(sender.hasPermission("autofishing.use")) {
                COMMANDS.add("toggle");
            }
            if(sender.hasPermission("autofishing.admin")) {
                COMMANDS.add("reload");
                COMMANDS.add("give");
            }
            StringUtil.copyPartialMatches(args[0], COMMANDS, completions);
            Collections.sort(completions);
            return completions;
        }
        if(args.length == 2) {
            if(sender.hasPermission("autofishing.admin")) {
                if(args[0].equalsIgnoreCase("give")) {
                    return null;
                }
            }
        }
        return completions;
    }
}

package wm.vdr.autofishing;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Executors implements CommandExecutor, TabCompleter {

    private AutoFishing main = AutoFishing.instance;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0) {
            return false;
        }
        if(args[0].equalsIgnoreCase("reload")) {
            main.reloadConfig();
            sender.sendMessage("§aconfig.yml successfully reloaded.");
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        final List<String> completions = new ArrayList<>();
        List<String> COMMANDS = new ArrayList<>();
        if(args.length == 1) {
            COMMANDS.add("reload");
            StringUtil.copyPartialMatches(args[0], COMMANDS, completions);
            Collections.sort(completions);
            return completions;
        }
        return completions;
    }
}

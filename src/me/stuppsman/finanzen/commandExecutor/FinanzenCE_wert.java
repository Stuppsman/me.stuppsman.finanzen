package me.stuppsman.finanzen.commandExecutor;

import me.stuppsman.finanzen.Finanzen;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FinanzenCE_wert implements CommandExecutor {

	private Finanzen plugin;

	public FinanzenCE_wert(Finanzen plugin) {
		this.plugin=plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("Wert")) {
			if (!(sender instanceof Player)) {
				System.out.println("Guck in die Config!");
				return true;
			}
			Player player = (Player) sender;
			if (!player.hasPermission("finanzen.member")) {
				player.sendMessage(ChatColor.RED + "Dazu fehlt dir die Berechtigung!");  
				return true;
			}
			
			if (args.length == 0) {
				int ID = player.getItemInHand().getTypeId();
				int Data = player.getItemInHand().getData().getData();
				if (plugin.getConfig().isConfigurationSection(ID+"."+Data)){
					player.sendMessage(ChatColor.DARK_GREEN + plugin.getConfig().getString(ID+"."+Data+".name") +ChatColor.DARK_GRAY + " ("+ID+"."+Data+")"+ ChatColor.GREEN +  " wird von den Händlern üblicherweise für " + ChatColor.DARK_GREEN + Finanzen.f.format(plugin.getConfig().getDouble(ID+"."+Data+".EK"))  + Finanzen.kuerzel+ ChatColor.GREEN + " eingekauft und für " + ChatColor.DARK_GREEN + Finanzen.f.format(plugin.getConfig().getDouble(ID+"."+Data+".VK"))  + Finanzen.kuerzel + ChatColor.GREEN + " verkauft.");
				}else {
					player.sendMessage(ChatColor.RED + "Mit diesem Item wird nicht gehandelt!");
				}
				return true;
			}
			if (args.length == 1) {
				try {
					if (!args[0].contains(":")) {
						args[0] = args[0]+":0";
					}
					int ID;
					byte Data;
					String[] idA = args[0].split(":");
					ID = Integer.parseInt(idA[0]);
					Data = (byte) Integer.parseInt(idA[1]);
					if (plugin.getConfig().isConfigurationSection(ID+"."+Data)){
						player.sendMessage(ChatColor.DARK_GREEN + plugin.getConfig().getString(ID+"."+Data+".name") +ChatColor.DARK_GRAY + " ("+ID+"."+Data+")"+ ChatColor.GREEN +  " wird von den Händlern üblicherweise für " + ChatColor.DARK_GREEN + Finanzen.f.format(plugin.getConfig().getDouble(ID+"."+Data+".EK"))  + Finanzen.kuerzel+ ChatColor.GREEN + " eingekauft und für " + ChatColor.DARK_GREEN + Finanzen.f.format(plugin.getConfig().getDouble(ID+"."+Data+".VK"))  + Finanzen.kuerzel + ChatColor.GREEN + " verkauft.");
					}else {
						player.sendMessage(ChatColor.RED + "Mit diesem Item wird nicht gehandelt!");
					}
					return true;
				}catch (NumberFormatException e) {
					player.sendMessage(ChatColor.RED + "ID falsch angegeben! Data-Werte werden über einen Doppelpunkt abgetrennt!");
				}
			}
		}	
		return false;
	}

}

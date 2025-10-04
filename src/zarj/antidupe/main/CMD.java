package zarj.antidupe.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;



public class CMD implements CommandExecutor, TabCompleter  {
	private static final String TAG = "MMOITEMS_UNSTACKABLE_UUID";
	public static boolean isNumeric(String str) { 
		  try {  
		    Double.parseDouble(str);  
		    return true;
		  } catch(NumberFormatException e){  
		    return false;  
		  }  
	}
	public CMD(){
		AntiDupe.inst.getCommand("antidupe").setExecutor(this);
		AntiDupe.inst.getCommand("antidupe").setTabCompleter(this);
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// TODO Auto-generated method stub
		
		if( args.length<1) {
			List<ItemToPlayer> itp = new ArrayList<>();
			for(Player player : Bukkit.getServer().getOnlinePlayers()) {
				for(ItemStack item : player.getInventory().getContents()) {
					if(item==null) {
						continue;
					}

					net.minecraft.world.item.ItemStack tempitmsk_temp = CraftItemStack.asNMSCopy(item);
					
					if(!tempitmsk_temp.u().e(TAG)) {
						continue;
					}
					String uuid = tempitmsk_temp.u().c(TAG).e_();
					itp.add(new ItemToPlayer(player,item,uuid));
				}
			}
			List<Zaebalo> zaeb = new ArrayList<>();
			
			for(ItemToPlayer item : itp) {
				Catch(zaeb,item.uuid,item);
			}
			for(Zaebalo za : zaeb) {
				if(za.has>1) {
					sender.sendMessage("�������� ��������!!!! ("+za.has+") "+za.uuid);
					for(ItemToPlayer item : za.items) {
						sender.sendMessage("	["+item.player.getName()+"] item: "+item.item.getItemMeta().getDisplayName());
					}
					
				}
			}
			
			
		}else if(args.length==2&&args[0].equalsIgnoreCase("player")) {
			Player player = Bukkit.getServer().getPlayer(args[1]);
			if(player==null) {
				sender.sendMessage("����� ����� �� ������!");
				return false;
			}
			PlayerInventory inv = player.getInventory();
			List<ItemHolder> find = new ArrayList<ItemHolder>();
			int i = 0;
			
			for(ItemStack item : inv.getContents()) {
				i++;
				if(item==null) {
					continue;
				}

				net.minecraft.world.item.ItemStack tempitmsk_temp = CraftItemStack.asNMSCopy(item);
				
				if(!tempitmsk_temp.u().e(TAG)) {
					continue;
				}
				String uuid = tempitmsk_temp.u().c(TAG).e_();
				
				
				contain(find,uuid,item);
				
			}
			for(ItemHolder holder : find) {
				if(holder.howMuch()>1) {
					sender.sendMessage("������� �������! ["+holder.has+"] "+holder.uuid);
					for(ItemStack item : holder.items) {
						sender.sendMessage("      "+item.getItemMeta().getDisplayName());
					}
				}
			}
		}else if(args.length==1&&args[0].equalsIgnoreCase("log")){
			AntiDupe.Popavsya("CMD CATCH");
		}
		return false;
	}
	public static void Catch(List<Zaebalo> list, String uuid, ItemToPlayer item) {
		boolean find = false;
		for(Zaebalo zaeb : list) {
			if(zaeb.checkId(uuid)) {
				find = true;
				zaeb.plus();
				zaeb.addItem(item);
				return;
				//Bukkit.getConsoleSender().sendMessage("������ �� ���� ��� ������� "+item.getItemMeta().getDisplayName()+" | "+uuid);
			}
		}
		if(!find) {
			list.add(new Zaebalo(uuid,item));
			
		}
	}
	public static void contain(List<ItemHolder> list, String uuid, ItemStack item) {
		boolean find = false;
		for(ItemHolder holder : list) {
			if(holder.checkId(uuid)) {
				find = true;
				holder.plus();
				holder.additem(item);
				Bukkit.getConsoleSender().sendMessage("������ �� ���� ��� ������� "+item.getItemMeta().getDisplayName()+" | "+uuid);
				return;
			}
		}
		if(!find) {
			list.add(new ItemHolder(item,uuid));
			
		}
	}
	
	private ItemHolder getById(HashMap<ItemHolder,Integer> map,String uuid) {
		for(Entry<ItemHolder, Integer> entry : map.entrySet()) {
			ItemHolder holder = entry.getKey();
			if(holder.checkId(uuid)) {
				return holder;
			}
			
		}
		return null;
	}
	private int contain(HashMap<ItemHolder,Integer> map, String uuid) {
		int has = 0;
		for(Entry<ItemHolder, Integer> entry : map.entrySet()) {
			ItemHolder holder = entry.getKey();
			if(holder.checkId(uuid)) {
				
				has++;
			}
			
		}
		return has;
	}
	private void check(HashMap<Integer,ItemHolder> map) {
		
	}
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length==1) {
			return Arrays.asList("player","log");
		}
		return null;
	}
}

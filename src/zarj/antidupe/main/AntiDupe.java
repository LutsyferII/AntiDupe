package zarj.antidupe.main;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;


public class AntiDupe extends JavaPlugin {
	
	public static AntiDupe inst;
	public static CMD cmd;
	public static File main_folder;
	public static BukkitTask task;
	static final long tick = 20;
	private static final String TAG = "MMOITEMS_UNSTACKABLE_UUID";

	@Override
	public void onEnable() {
		inst = this;
		cmd = new CMD();
		main_folder = AntiDupe.inst.getDataFolder();
		if(!main_folder.exists()) {
			main_folder.mkdirs();
		}
		reload();
		
		findDupers();
	}
	@Override
	public void onLoad() {
		
	}
	public void reload() {
	}
	@Override
	public void onDisable() {
	}
	public void findDupers() {
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		task = scheduler.runTaskTimer(AntiDupe.inst,new Runnable() {

			@Override
			public void run() {
				Bukkit.getServer().getConsoleSender().sendMessage("Lets Find them!!");
				Popavsya("AUTO CATCH");
				
			}
			
		},tick*60,tick*3600);

		Bukkit.getServer().getConsoleSender().sendMessage("Dupers scaner is enable!");
		
	}
	public static void Popavsya(String log_tag) {
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
			CMD.Catch(zaeb,item.uuid,item);
		}
		String log_data = "CATCHed: ";
		boolean has = false;
		for(Zaebalo za : zaeb) {
			if(za.has>1) {
				log_data+="\n   ("+za.has+")  "+za.uuid;
				has = true;
				
				Bukkit.getServer().getConsoleSender().sendMessage("Cath him!!!! ("+za.has+") "+za.uuid);
				for(ItemToPlayer item : za.items) {
					log_data+="\n      ["+item.player.getName()+"] item: "+item.item.getItemMeta().getDisplayName();
					Bukkit.getServer().getConsoleSender().sendMessage("	["+item.player.getName()+"] item: "+item.item.getItemMeta().getDisplayName());
				}
				
			}
		}
		if(has) {
			log(log_tag,log_data);
		}else {
			Bukkit.getServer().getConsoleSender().sendMessage("Not find(");
		}
	}
	public static void log(String log_tag,String log_data){
		
		File logs_folder = new File(main_folder,"logs");
		if(!logs_folder.exists()) {
			logs_folder.mkdirs();
		}
		
		//String log_name =log_tag+

		String timeStamp = new SimpleDateFormat("dd.MM.yyyy_HH;mm;ss").format(Calendar.getInstance().getTime());
		File log = new File(logs_folder.getPath()+"/["+log_tag+"]"+timeStamp+".txt");
		//String charset = "UTF-8"; 
		try {
			log.createNewFile();
			/*BufferedReader in = new BufferedReader( 
				      new InputStreamReader (new FileInputStream(log), charset));*/
	        FileOutputStream fos = new FileOutputStream(log);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
	        //Writer out = new BufferedWriter(osw);
			osw.append(log_data);
			osw.close();
			fos.close();
			//Files.write(log.toPath(), log_data.getBytes(), StandardOpenOption.APPEND);
			
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
}


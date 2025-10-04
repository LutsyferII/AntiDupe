package zarj.antidupe.main;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemToPlayer {
	Player player;
	ItemStack item;
	String uuid;
	
	public ItemToPlayer(Player p, ItemStack i, String id) {
		player = p;
		item = i;
		uuid = id;
	}
	
} 

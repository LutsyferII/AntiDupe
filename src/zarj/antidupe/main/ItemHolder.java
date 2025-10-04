package zarj.antidupe.main;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemHolder {
	List<ItemStack> items = new ArrayList<ItemStack>();
	String uuid;
	int has = 1;
	
	public ItemHolder( String id) {
		uuid = id;
	}
	public ItemHolder(ItemStack item, String id) {
		items.add(item);
		uuid = id;
		
	}
	public boolean has() {
		return (has>0);
	}
	public void plus() {
		has++;
	}
	public boolean checkId(String id) {
		return uuid.equals(id);
	}
	public int howMuch() {
		return has;
	}
	public String getUUID() {
		return uuid;
	}
	public void additem(ItemStack item) {
		items.add(item);
	}
}

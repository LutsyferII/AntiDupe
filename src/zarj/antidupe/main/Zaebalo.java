package zarj.antidupe.main;

import java.util.ArrayList;
import java.util.List;

public class Zaebalo {
	String uuid;
	List<ItemToPlayer> items = new ArrayList<>();
	int has = 1;
	public Zaebalo(String id, ItemToPlayer item) {
		uuid = id;
		items.add(item);
	}
	public void plus() {
		has++;
	}
	public boolean checkId(String id) {
		return uuid.equals(id);
	}
	public void addItem(ItemToPlayer item) {
		items.add(item);
	}
}

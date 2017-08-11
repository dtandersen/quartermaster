package foxhole.repository;

import java.util.List;
import foxhole.entity.Item;
import foxhole.entity.Item.ItemBuilder;

public interface ItemRepository
{
	List<Item> all();

	void create(ItemBuilder itemBuilder);
}

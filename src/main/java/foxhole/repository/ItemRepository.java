package foxhole.repository;

import java.util.List;
import java.util.Optional;
import foxhole.entity.Item;
import foxhole.entity.Item.ItemBuilder;

public interface ItemRepository
{
	List<Item> all();

	Item create(ItemBuilder itemBuilder);

	Optional<Item> findByName(String itemName);

	void clean();
}

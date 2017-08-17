package foxhole.bot;

import java.util.List;
import java.util.Optional;
import foxhole.entity.Item;
import foxhole.entity.Item.ItemBuilder;
import foxhole.repository.ItemRepository;

public class InMemoryItemRepository implements ItemRepository
{
	private final DataStore dataStore;

	public InMemoryItemRepository(final DataStore dataStore)
	{
		this.dataStore = dataStore;
	}

	@Override
	public List<Item> all()
	{
		return dataStore.items.all();
	}

	@Override
	public Item create(final ItemBuilder itemBuilder)
	{
		return dataStore.items.add(itemBuilder.build());
	}

	@Override
	public Optional<Item> findByName(final String itemName)
	{
		return dataStore.items.findFirst(item -> itemName.equalsIgnoreCase(item.getName()));
	}

	@Override
	public void clean()
	{
		dataStore.items.clear();
	}
}

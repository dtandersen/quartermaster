package foxhole.service;

import java.util.List;
import foxhole.entity.Item;
import foxhole.repository.ItemRepository;

public class ItemService
{
	private final ItemRepository itemRepository;

	public ItemService(final ItemRepository itemRepository)
	{
		this.itemRepository = itemRepository;
	}

	public List<Item> items()
	{
		final List<Item> items = itemRepository.all();
		return items;
	}
}

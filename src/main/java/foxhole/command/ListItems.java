package foxhole.command;

import java.util.List;
import foxhole.command.BaseCommand.VoidRequest;
import foxhole.command.ListItems.ListItemsResult;
import foxhole.entity.Item;
import foxhole.repository.ItemRepository;

public class ListItems extends BaseCommand<VoidRequest, ListItemsResult>
{
	private final ItemRepository itemRepository;

	public ListItems(final ItemRepository itemRepository)
	{
		this.itemRepository = itemRepository;
	}

	@Override
	public void execute()
	{
		result.setItems(itemRepository.all());
	}

	public interface ListItemsResult
	{
		void setItems(List<Item> items);
	}
}

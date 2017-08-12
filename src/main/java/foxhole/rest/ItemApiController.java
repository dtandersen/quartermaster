package foxhole.rest;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import foxhole.command.CommandFactory;
import foxhole.command.ListItems;
import foxhole.command.ListItems.ListItemsResult;
import foxhole.entity.Item;

@RestController
public class ItemApiController
{
	@Autowired
	private CommandFactory commandFactory;

	@RequestMapping("/api/items")
	public List<ItemRest> getcyx()
	{
		final ListItems command = commandFactory.listItems();
		final ListItemsResultImplementation result = new ListItemsResultImplementation();
		command.setResult(result);
		command.execute();
		return result.getItems().stream()
				.map(item -> new ItemRest(item))
				.collect(Collectors.toList());
	}

	private final class ListItemsResultImplementation implements ListItemsResult
	{
		private List<Item> items;

		@Override
		public void setItems(final List<Item> items)
		{
			this.items = items;
		}

		public List<Item> getItems()
		{
			return items;
		}
	}

	static class ItemRest
	{
		private final Item item;

		public ItemRest(final Item item)
		{
			this.item = item;
		}

		public String getName()
		{
			return item.getName();
		}

		public String getItemId()
		{
			return item.getItemId().toString();
		}

		public int getPack()
		{
			return item.getPack();
		}
	}
}

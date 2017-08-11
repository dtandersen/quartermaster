package foxhole.command;

import java.util.UUID;
import foxhole.command.BaseCommand.VoidResult;
import foxhole.command.CreateItem.CreateItemRequest;
import foxhole.entity.Item.ItemBuilder;
import foxhole.repository.ItemRepository;

public class CreateItem extends BaseCommand<CreateItemRequest, VoidResult>
{
	private final ItemRepository itemRepository;

	CreateItem(final ItemRepository itemRepository)
	{
		this.itemRepository = itemRepository;
	}

	@Override
	public void execute()
	{
		itemRepository.create(ItemBuilder.item()
				.withItemId(UUID.randomUUID())
				.withName(request.getName()));
	}

	public interface CreateItemRequest
	{
		String getName();
	}
}

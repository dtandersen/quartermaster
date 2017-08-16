package foxhole.command;

import java.util.List;
import java.util.UUID;
import foxhole.command.BaseCommand.VoidResult;
import foxhole.command.CreateOutpost.CreateOutpostRequest;
import foxhole.entity.Item;
import foxhole.entity.Outpost;
import foxhole.entity.Outpost.OutpostBuilder;
import foxhole.repository.ItemRepository;
import foxhole.repository.OutpostRepository;

public class CreateOutpost extends BaseCommand<CreateOutpostRequest, VoidResult>
{
	private final OutpostRepository outpostRepository;

	private final ItemRepository itemRepository;

	public CreateOutpost(
			final OutpostRepository outpostRepository,
			final ItemRepository itemRepository)
	{
		this.outpostRepository = outpostRepository;
		this.itemRepository = itemRepository;
	}

	@Override
	public void execute()
	{
		final Outpost outpost = outpostRepository.create(OutpostBuilder.outpost()
				.withId(UUID.randomUUID())
				.withName(request.getName()));

		final List<Item> items = itemRepository.all();
		items.forEach(item -> outpost.updateStock(item, 0));

		outpostRepository.createStock(outpost);
	}

	public static interface CreateOutpostRequest
	{
		String getName();
	}
}

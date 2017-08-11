package foxhole.command;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import foxhole.command.DescribeOutpost.DescribeOutpostRequest;
import foxhole.command.DescribeOutpost.DescribeOutpostResult;
import foxhole.command.model.OutpostModel;
import foxhole.entity.Item;
import foxhole.entity.Outpost;
import foxhole.repository.ItemRepository;
import foxhole.repository.OutpostRepository;

public class DescribeOutpost extends BaseCommand<DescribeOutpostRequest, DescribeOutpostResult>
{
	private final OutpostRepository outpostRepository;

	private final ItemRepository itemRepository;

	public DescribeOutpost(
			final OutpostRepository outpostRepository,
			final ItemRepository itemRepository)
	{
		this.outpostRepository = outpostRepository;
		this.itemRepository = itemRepository;
	}

	@Override
	public void execute()
	{
		final Outpost find = outpostRepository.find(UUID.fromString(request.getOutpostId()));
		final Map<UUID, Item> items = new HashMap<>();
		itemRepository.all().forEach(item -> items.put(item.getItemId(), item));
		result.setOutpost(new OutpostModel(find, items));
	}

	public static interface DescribeOutpostRequest
	{
		String getOutpostId();
	}

	public static interface DescribeOutpostResult
	{
		void setOutpost(OutpostModel outpost);
	}
}

package foxhole.command;

import foxhole.repository.ItemRepository;
import foxhole.repository.OutpostRepository;

public class CommandFactory
{
	private final ItemRepository itemRepository;

	private final OutpostRepository outpostRepository;

	public CommandFactory(
			final ItemRepository itemRepository,
			final OutpostRepository outpostRepository)
	{
		this.itemRepository = itemRepository;
		this.outpostRepository = outpostRepository;
	}

	public CreateItem createItem()
	{
		return new CreateItem(itemRepository);
	}

	public ListOutposts listOutposts()
	{
		return new ListOutposts(outpostRepository);
	}

	public CreateOutpost createOutpost()
	{
		return new CreateOutpost(outpostRepository, itemRepository);
	}

	public DescribeOutpost describeOutpost()
	{
		return new DescribeOutpost(outpostRepository, itemRepository);
	}
}

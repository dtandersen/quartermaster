package foxhole.bot;

import foxhole.command.CommandFactory;
import foxhole.repository.ItemRepository;
import foxhole.repository.OutpostRepository;

public class InMemoryQuartermasterEnvironment implements QuartermasterEnvironment
{
	private final DataStore dataStore;

	private final ItemRepository itemRepository;

	private final OutpostRepository outpostRepository;

	public InMemoryQuartermasterEnvironment()
	{
		dataStore = new DataStore();
		itemRepository = new InMemoryItemRepository(dataStore);
		outpostRepository = new InMemoryOutpostRepository(dataStore);
	}

	@Override
	public ItemRepository itemRepository()
	{
		return itemRepository;
	}

	@Override
	public OutpostRepository outpostRepository()
	{
		return outpostRepository;
	}

	@Override
	public CommandFactory commandFactory()
	{
		return new CommandFactory(itemRepository, outpostRepository);
	}
}

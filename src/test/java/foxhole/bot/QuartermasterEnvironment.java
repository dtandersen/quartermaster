package foxhole.bot;

import foxhole.command.CommandFactory;
import foxhole.repository.ItemRepository;
import foxhole.repository.OutpostRepository;

public interface QuartermasterEnvironment
{
	ItemRepository itemRepository();

	OutpostRepository outpostRepository();

	CommandFactory commandFactory();
}

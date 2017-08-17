package foxhole.bot;

import java.util.UUID;
import foxhole.entity.Item;
import foxhole.entity.Outpost;
import foxhole.entity.Stock;

public class DataStore
{
	public BasicInMemoryDb<UUID, Outpost> outposts = new BasicInMemoryDb<UUID, Outpost>() {
		@Override
		UUID keyOf(final Outpost outpost)
		{
			return outpost.getOutpostId();
		}
	};

	public BasicInMemoryDb<UUID, Item> items = new BasicInMemoryDb<UUID, Item>() {
		@Override
		UUID keyOf(final Item item)
		{
			return item.getItemId();
		}
	};

	public BasicInMemoryDb<String, Stock> stock = new BasicInMemoryDb<String, Stock>() {
		@Override
		String keyOf(final Stock stock)
		{
			return stock.getItemId().toString() + stock.getOutpostId().toString();
		}
	};
}

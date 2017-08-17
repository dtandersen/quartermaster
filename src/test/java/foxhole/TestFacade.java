package foxhole;

import java.util.List;
import java.util.UUID;
import javax.sql.DataSource;
import foxhole.bot.QuartermasterEnvironment;
import foxhole.entity.Item;
import foxhole.entity.Item.ItemBuilder;
import foxhole.entity.Outpost;
import foxhole.entity.Outpost.OutpostBuilder;
import foxhole.entity.Stock;
import foxhole.entity.Stock.StockBuilder;
import foxhole.repository.ItemRepository;
import foxhole.repository.OutpostRepository;

public class TestFacade
{
	private final OutpostRepository outpostRepository;

	private final ItemRepository itemRepository;

	private final DataSource dataSource;

	public TestFacade(final TestEnvironment env)
	{
		dataSource = env.dataSource();
		itemRepository = env.itemRepository();
		outpostRepository = env.outpostRepository();
	}

	public TestFacade(final QuartermasterEnvironment env)
	{
		itemRepository = env.itemRepository();
		outpostRepository = env.outpostRepository();
		dataSource = null;
	}

	public Outpost createOutpost(final OutpostBuilder outpostBuilder)
	{
		final Outpost outpost = outpostRepository.create(outpostBuilder);
		outpostRepository.createStock(outpost);

		return outpost;
	}

	public Item createItem(final ItemBuilder withName)
	{
		final Item item = itemRepository.create(withName);
		return item;
	}

	public void runFlyway()
	{
		FlywayRunner.runFlyway(dataSource);
	}

	public void givenItem(final String name)
	{
		createItem(ItemBuilder.item().withName(name));
	}

	public void givenOutpost(final String name)
	{
		createOutpost(OutpostBuilder.outpost().withName(name));
	}

	public UUID itemIdOf(final String itemName)
	{
		return itemRepository.findByName(itemName).orElse(null).getItemId();
	}

	public UUID outpostIdOf(final String outpostName)
	{
		return outpostRepository.findByName(outpostName).orElse(null).getOutpostId();
	}

	public void createStock(final StockBuilder stockBuilder)
	{
		outpostRepository.createStock(stockBuilder);
	}

	public List<Stock> stock()
	{
		return outpostRepository.stock();
	}

	public void cleanDb()
	{
		itemRepository.clean();
		outpostRepository.clean();
	}

	public Item itemByName(final String trimmed)
	{
		return itemRepository.findByName(trimmed).get();
	}

	public Outpost outpostByName(final String trimmed)
	{
		return outpostRepository.findByName(trimmed).get();
	}
}

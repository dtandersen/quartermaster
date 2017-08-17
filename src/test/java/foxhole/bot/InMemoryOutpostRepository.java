package foxhole.bot;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import foxhole.entity.Outpost;
import foxhole.entity.Outpost.OutpostBuilder;
import foxhole.entity.Stock;
import foxhole.entity.Stock.StockBuilder;
import foxhole.repository.OutpostRepository;

public class InMemoryOutpostRepository implements OutpostRepository
{
	private final DataStore dataStore;

	public InMemoryOutpostRepository(final DataStore dataStore)
	{
		this.dataStore = dataStore;
	}

	@Override
	public List<Outpost> all()
	{
		return dataStore.outposts.all();
	}

	@Override
	public Outpost create(final OutpostBuilder outpost)
	{
		return dataStore.outposts.add(outpost.build());
	}

	@Override
	public Outpost find(final UUID outpostId)
	{
		return dataStore.outposts.get(outpostId);
	}

	@Override
	public void createStock(final Outpost outpost)
	{
		outpost.getStock().values().stream()
				.forEach(stock -> dataStore.stock.add(stock));
	}

	@Override
	public void updateStock(final UUID outpostId, final Stock stock)
	{
		dataStore.stock.add(stock);
	}

	@Override
	public void delete(final UUID outpostId)
	{
		dataStore.outposts.remove(outpostId);
	}

	@Override
	public Optional<Outpost> findByName(final String outpostName)
	{
		final Optional<Outpost> outpost = dataStore.outposts.findFirst(o -> outpostName.equalsIgnoreCase(o.getName()));
		if (outpost.isPresent())
		{
			final Outpost o2 = outpost.get();
			o2.setStock(stock().stream().filter(s -> s.getOutpostId() == o2.getOutpostId())
					.collect(Collectors.toList()));
		}
		return outpost;
	}

	@Override
	public void createStock(final StockBuilder stockBuilder)
	{
		final Stock stock = stockBuilder.build();
		dataStore.stock.add(stock);
	}

	@Override
	public List<Stock> stock()
	{
		return dataStore.stock.all();
	}

	@Override
	public void clean()
	{
		dataStore.stock.clear();
		dataStore.outposts.clear();
	}
}

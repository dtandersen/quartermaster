package foxhole.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import foxhole.command.ToStringBuilder;
import foxhole.entity.Stock.StockBuilder;

public class Outpost
{
	private final UUID outpostId;

	private final String name;

	private final Map<UUID, Stock> stock;

	public Outpost(final OutpostBuilder outpostBuilder)
	{
		outpostId = outpostBuilder.outpostId;
		name = outpostBuilder.name;
		stock = outpostBuilder.stock;
	}

	public UUID getOutpostId()
	{
		return outpostId;
	}

	public String getName()
	{
		return name;
	}

	public Map<UUID, Stock> getStock()
	{
		return stock;
	}

	public void setStock(final List<Stock> stockOf)
	{
		stockOf.forEach(s -> stock.put(s.getItemId(), s));
	}

	public void updateStock(final Item item, final int quantity)
	{
		stock.put(item.getItemId(), StockBuilder.stock()
				.withItemId(item.getItemId())
				.withQuantity(quantity)
				.build());
	}

	public Optional<Stock> find(final Item item)
	{
		return Optional.ofNullable(stock.get(item.getItemId()));
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder(this)
				.withProperty("outpostId", outpostId)
				.withProperty("name", name)
				.toString();
	}

	public static class OutpostBuilder
	{
		private UUID outpostId;

		private String name;

		private Map<UUID, Stock> stock = new HashMap<>();

		public static OutpostBuilder outpost()
		{
			return new OutpostBuilder().withRandomOutpostId();
		}

		private OutpostBuilder withRandomOutpostId()
		{
			outpostId = UUID.randomUUID();
			return this;
		}

		public OutpostBuilder withId(final String outpostId)
		{
			this.outpostId = UUID.fromString(outpostId);
			return this;
		}

		public OutpostBuilder withName(final String name)
		{
			this.name = name;
			return this;
		}

		public OutpostBuilder withId(final UUID outpostId)
		{
			this.outpostId = outpostId;
			return this;
		}

		public OutpostBuilder withStock(final List<Stock> stock)
		{
			this.stock = new HashMap<>();
			for (final Stock s : stock)
			{
				this.stock.put(s.getItemId(), s);
			}
			return this;
		}

		public OutpostBuilder withStock(final StockBuilder... stockBuilders)
		{
			this.stock = new HashMap<>();
			for (final StockBuilder s : stockBuilders)
			{
				final Stock s2 = s.build();
				this.stock.put(s2.getItemId(), s2);
			}
			return this;
		}

		public Outpost build()
		{
			return new Outpost(this);
		}
	}

	public void addStock(final Stock stock)
	{
		this.stock.put(stock.getItemId(), stock);
	}
}

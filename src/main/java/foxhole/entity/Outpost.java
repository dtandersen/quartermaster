package foxhole.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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

	public void updateStock(final Item item, final int quantity)
	{
		stock.put(item.getItemId(), StockBuilder.stock()
				.withItemId(item.getItemId())
				.withQuantity(quantity)
				.build());
	}

	public static class OutpostBuilder
	{
		private UUID outpostId;

		private String name;

		private Map<UUID, Stock> stock;

		public static OutpostBuilder outpost()
		{
			return new OutpostBuilder();
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

		public Outpost build()
		{
			return new Outpost(this);
		}
	}
}

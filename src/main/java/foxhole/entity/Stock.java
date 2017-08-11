package foxhole.entity;

import java.util.UUID;

public class Stock
{
	private final UUID itemId;

	private final int quantity;

	public Stock(final Item item, final int quantity)
	{
		this.itemId = item.getItemId();
		this.quantity = quantity;
	}

	public Stock(final StockBuilder stockBuilder)
	{
		this.itemId = stockBuilder.itemId;
		this.quantity = stockBuilder.quantity;
	}

	public UUID getItemId()
	{
		return itemId;
	}

	public int getQuantity()
	{
		return quantity;
	}

	public static class StockBuilder
	{
		private UUID itemId;

		private int quantity;

		public static StockBuilder stock()
		{
			return new StockBuilder();
		}

		public StockBuilder withItemId(final UUID itemId)
		{
			this.itemId = itemId;
			return this;
		}

		public StockBuilder withQuantity(final int quantity)
		{
			this.quantity = quantity;
			return this;
		}

		public Stock build()
		{
			return new Stock(this);
		}
	}
}

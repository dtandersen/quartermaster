package foxhole.entity;

import java.util.UUID;

public class Stock
{
	private final UUID itemId;

	private final int quantity;

	private final int shipping;

	public Stock(final StockBuilder stockBuilder)
	{
		this.itemId = stockBuilder.itemId;
		this.quantity = stockBuilder.quantity;
		this.shipping = stockBuilder.shipping;
	}

	public UUID getItemId()
	{
		return itemId;
	}

	public int getQuantity()
	{
		return quantity;
	}

	public int getShipping()
	{
		return shipping;
	}

	public static class StockBuilder
	{
		private UUID itemId;

		private int quantity;

		private int shipping;

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

		public StockBuilder withShipping(final int shipping)
		{
			this.shipping = shipping;
			return this;
		}
	}
}

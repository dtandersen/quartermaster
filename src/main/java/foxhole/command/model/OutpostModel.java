package foxhole.command.model;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import foxhole.entity.Item;
import foxhole.entity.Outpost;
import foxhole.entity.Stock;

public class OutpostModel
{
	private final Outpost outpost;

	private final Map<UUID, Item> items;

	public OutpostModel(
			final Outpost outpost,
			final Map<UUID, Item> items)
	{
		this.outpost = outpost;
		this.items = items;
	}

	public String getName()
	{
		return outpost.getName();
	}

	public String getOutpostId()
	{
		return outpost.getOutpostId().toString();
	}

	public List<StockModel> getStock()
	{
		return outpost.getStock().values().stream()
				.map(stock -> new StockModel(stock, items.get(stock.getItemId()), outpost))
				.sorted(new Comparator<StockModel>() {
					@Override
					public int compare(final StockModel o1, final StockModel o2)
					{
						return o1.getSortOrder() - o2.getSortOrder();
					}
				})
				.collect(Collectors.toList());
	}

	public static class StockModel
	{
		private final Stock stock;

		private final Item item;

		private final Outpost outpost;

		public StockModel(final Stock stock, final Item item, final Outpost outpost)
		{
			this.stock = stock;
			this.item = item;
			this.outpost = outpost;
		}

		int getSortOrder()
		{
			return item.getSortOrder();
		}

		public int getDeficit()
		{
			return item.getMinQty() - stock.getQuantity();
		}

		public int getDeficitStacks()
		{
			return (int)Math.ceil((float)getDeficit() / (float)item.getPack());
		}

		public int getMinQuantity()
		{
			return item.getMinQty();
		}

		public String getItemId()
		{
			return item.getItemId().toString();
		}

		public String getItemName()
		{
			return item.getName();
		}

		public int getQuantity()
		{
			return stock.getQuantity();
		}

		public int getShipping()
		{
			return stock.getShipping();
		}

		public String getOutpostName()
		{
			return outpost.getName();
		}
	}
}

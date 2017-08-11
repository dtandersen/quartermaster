package foxhole.entity;

import java.util.UUID;

public class Item
{
	private final UUID id;

	private final String name;

	private final int bmat;

	private final int rmat;

	private final int emat;

	private final int minQty;

	private final int pack;

	private final int sortOrder;

	public Item(final ItemBuilder itemBuilder)
	{
		this.id = itemBuilder.itemId;
		this.name = itemBuilder.name;
		this.bmat = itemBuilder.bmat;
		this.rmat = itemBuilder.rmat;
		this.emat = itemBuilder.emat;
		this.minQty = itemBuilder.minQty;
		this.pack = itemBuilder.pack;
		this.sortOrder = itemBuilder.sortOrder;
	}

	public UUID getItemId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public int getBmat()
	{
		return bmat;
	}

	public int getRmat()
	{
		return rmat;
	}

	public int getEmat()
	{
		return emat;
	}

	public int getMinQty()
	{
		return minQty;
	}

	public int getPack()
	{
		return pack;
	}

	public int getSortOrder()
	{
		return sortOrder;
	}

	public static class ItemBuilder
	{
		public int sortOrder;

		private String name;

		private int bmat;

		private int minQty;

		private int rmat;

		private int emat;

		private int pack;

		private UUID itemId;

		public static ItemBuilder item()
		{
			return new ItemBuilder();
		}

		public Item build()
		{
			return new Item(this);
		}

		public ItemBuilder withName(final String name)
		{
			this.name = name;
			return this;
		}

		public ItemBuilder withBmat(final int bmat)
		{
			this.bmat = bmat;
			return this;
		}

		public ItemBuilder withEmat(final int emat)
		{
			this.emat = emat;
			return this;
		}

		public ItemBuilder withRmat(final int rmat)
		{
			this.rmat = rmat;
			return this;
		}

		public ItemBuilder withMinQty(final int minQty)
		{
			this.minQty = minQty;
			return this;
		}

		public ItemBuilder withPack(final int pack)
		{
			this.pack = pack;
			return this;
		}

		public ItemBuilder withItemId(final UUID itemId)
		{
			this.itemId = itemId;
			return this;
		}

		public ItemBuilder withSortOrder(final int sortOrder)
		{
			this.sortOrder = sortOrder;
			return this;
		}
	}
}

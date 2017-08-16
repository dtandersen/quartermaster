package foxhole.command;

import java.util.Optional;
import foxhole.command.ItemInventoryAtOutpost.ItemInventoryAtOutpostRequest;
import foxhole.command.ItemInventoryAtOutpost.ItemInventoryAtOutpostResult;
import foxhole.command.model.OutpostModel.StockModel;
import foxhole.entity.Item;
import foxhole.entity.Outpost;
import foxhole.entity.Stock;
import foxhole.entity.Stock.StockBuilder;
import foxhole.repository.ItemRepository;
import foxhole.repository.NotFoundException;
import foxhole.repository.OutpostRepository;

public class ItemInventoryAtOutpost extends BaseCommand<ItemInventoryAtOutpostRequest, ItemInventoryAtOutpostResult>
{
	private final OutpostRepository outpostRepository;

	private final ItemRepository itemRepository;

	public ItemInventoryAtOutpost(
			final OutpostRepository outpostRepository,
			final ItemRepository itemRepository)
	{
		this.outpostRepository = outpostRepository;
		this.itemRepository = itemRepository;
	}

	@Override
	public void execute()
	{
		try
		{
			final String outpostName = request.getOutpostName();
			final String itemName = request.getItemName();
			final Outpost outpost = outpostRepository.findByName(outpostName).orElseThrow(() -> new NotFoundException());
			final Item item = itemRepository.findByName(itemName).orElseThrow(() -> new NotFoundException());
			final Optional<Stock> stock = outpost.find(item);
			if (stock.isPresent())
			{
				result.setStock(stock.get(), new StockModel(stock.get(), item, outpost));
			}
			else
			{
				final Stock build = StockBuilder.stock()
						.withOutpostId(outpost.getOutpostId())
						.withItemId(item.getItemId())
						.withQuantity(0)
						.build();
				result.setStock(build,
						new StockModel(build, item, outpost));
			}
		}
		catch (final NotFoundException e)
		{
		}
	}

	public interface ItemInventoryAtOutpostRequest
	{
		String getOutpostName();

		String getItemName();
	}

	public interface ItemInventoryAtOutpostResult
	{
		void setStock(Stock stock, StockModel stockModel);
	}

	// static class StockModel
	// {
	//
	// }
}

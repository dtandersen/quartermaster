package foxhole.command;

import java.util.List;
import java.util.UUID;
import foxhole.command.BaseCommand.VoidResult;
import foxhole.command.UpdateStock.UpdateStockRequest;
import foxhole.entity.Stock.StockBuilder;
import foxhole.repository.OutpostRepository;

public class UpdateStock extends BaseCommand<UpdateStockRequest, VoidResult>
{
	private final OutpostRepository outpostRepository;

	public UpdateStock(final OutpostRepository outpostRepository)
	{
		this.outpostRepository = outpostRepository;
	}

	@Override
	public void execute()
	{
		for (final StockUpdate update : request.getStockUpdates())
		{
			outpostRepository.updateStock(request.getOutpostId(), StockBuilder.stock()
					.withItemId(update.getItemId())
					.withQuantity(update.getQuantity())
					.build());
		}
	}

	public interface UpdateStockRequest
	{
		UUID getOutpostId();

		List<StockUpdate> getStockUpdates();
	}

	public static class StockUpdate
	{
		private final UUID itemId;

		private final Integer quantity;

		public StockUpdate(final String itemId, final Integer quantity)
		{
			this.itemId = UUID.fromString(itemId);
			this.quantity = quantity;
		}

		public UUID getItemId()
		{
			return itemId;
		}

		public int getQuantity()
		{
			return quantity;
		}
	}
}

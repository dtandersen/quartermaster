package foxhole.command;

import java.util.UUID;
import foxhole.command.BaseCommand.VoidResult;
import foxhole.command.UpdateStockItem.UpdateStockItemRequest;
import foxhole.entity.Stock.StockBuilder;
import foxhole.repository.OutpostRepository;

public class UpdateStockItem extends BaseCommand<UpdateStockItemRequest, VoidResult>
{
	private final OutpostRepository outpostRepository;

	public UpdateStockItem(final OutpostRepository outpostRepository)
	{
		this.outpostRepository = outpostRepository;
	}

	@Override
	public void execute()
	{
		outpostRepository.updateStock(request.getOutpostId(), StockBuilder.stock()
				.withItemId(request.getItemId())
				.withQuantity(request.getQuantity())
				.withShipping(request.getShipping())
				.build());
	}

	public interface UpdateStockItemRequest
	{
		UUID getOutpostId();

		int getShipping();

		int getQuantity();

		UUID getItemId();
	}
}

package foxhole.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import foxhole.command.CommandFactory;
import foxhole.command.DescribeOutpost;
import foxhole.command.DescribeOutpost.DescribeOutpostRequest;
import foxhole.command.UpdateStock;
import foxhole.command.UpdateStock.StockUpdate;
import foxhole.command.UpdateStock.UpdateStockRequest;
import foxhole.controller.OutpostController.DescribeOutpostResultImplementation;

@Controller
public class StockController
{
	@Autowired
	CommandFactory commandFactory;

	@GetMapping("/outposts/{id}/stock")
	public ModelAndView edit(@PathVariable("id") final String outpostId)
	{
		final DescribeOutpost command = commandFactory.describeOutpost();
		command.setRequest(new DescribeOutpostRequest() {
			@Override
			public String getOutpostId()
			{
				return outpostId;
			}
		});
		final DescribeOutpostResultImplementation result = new DescribeOutpostResultImplementation();
		command.setResult(result);
		command.execute();

		final ModelAndView modelAndView = new ModelAndView("outposts/stock/edit");
		modelAndView.addObject("outpost", result.getOutpost());
		return modelAndView;
	}

	@PostMapping("/outposts/{outpostId}/stock")
	public String update(final MyStuff stuff)
	{
		final UpdateStock command = commandFactory.updateStock();
		command.setRequest(stuff);
		command.execute();
		return "redirect:/outposts/" + stuff.getOutpostId();
	}

	static class MyStuff implements UpdateStockRequest
	{
		private UUID outpostId;

		private List<String> itemIds;

		private List<Integer> quantities;

		@Override
		public UUID getOutpostId()
		{
			return outpostId;
		}

		public void setItemId(final List<String> itemIds)
		{
			this.itemIds = itemIds;

		}

		public void setQuantity(final List<Integer> quantities)
		{
			this.quantities = quantities;

		}

		public void setOutpostId(final String outpostId)
		{
			this.outpostId = UUID.fromString(outpostId);
		}

		@Override
		public List<StockUpdate> getStockUpdates()
		{
			final List<StockUpdate> stockUpdates = new ArrayList<>();
			for (int i = 0; i < itemIds.size(); i++)
			{
				stockUpdates.add(new StockUpdate(itemIds.get(i), quantities.get(i)));
			}
			return stockUpdates;
		}
	}
}

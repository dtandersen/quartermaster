package foxhole.rest;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import foxhole.OutpostController.DescribeOutpostResultImplementation;
import foxhole.command.CommandFactory;
import foxhole.command.DescribeOutpost;
import foxhole.command.DescribeOutpost.DescribeOutpostRequest;
import foxhole.command.model.OutpostModel;
import foxhole.command.model.OutpostModel.StockModel;

@RestController
public class StockApiController
{
	@Autowired
	private CommandFactory commandFactory;

	@RequestMapping("/api/outposts/{outpostId}")
	public @ResponseBody OutpostRest getcyx(@PathVariable("outpostId") final String outpostId)
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

		final ModelAndView modelAndView = new ModelAndView("outposts/get");
		modelAndView.addObject("outpost", result.getOutpost());
		return new OutpostRest(result.getOutpost());
	}

	public final static class OutpostRest
	{
		private final OutpostModel outpost;

		public OutpostRest(final OutpostModel outpost)
		{
			this.outpost = outpost;
		}

		public String getOutpostId()
		{
			return outpost.getOutpostId();
		}

		public String getName()
		{
			return outpost.getName();
		}

		public List<StockRest> getStock()
		{
			return outpost.getStock().stream()
					.map(stock -> new StockRest(stock))
					.collect(Collectors.toList());
		}

		static class StockRest
		{
			private final StockModel stock;

			public StockRest(final StockModel stock)
			{
				this.stock = stock;
			}

			public int getQuantity()
			{
				return stock.getQuantity();
			}

			public String getItemId()
			{
				return stock.getItemId();
			}
		}
	}
}

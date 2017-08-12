package foxhole.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import foxhole.command.CommandFactory;
import foxhole.command.CreateOutpost;
import foxhole.command.CreateOutpost.CreateOutpostRequest;
import foxhole.command.DeleteOutpost;
import foxhole.command.DeleteOutpost.DeleteOutpostRequest;
import foxhole.command.DescribeOutpost;
import foxhole.command.DescribeOutpost.DescribeOutpostRequest;
import foxhole.command.DescribeOutpost.DescribeOutpostResult;
import foxhole.command.ListOutposts;
import foxhole.command.ListOutposts.ListOutpostsResult;
import foxhole.command.model.OutpostModel;
import foxhole.entity.Outpost;

@Controller
public class OutpostController
{
	@Autowired
	CommandFactory commandFactory;

	@GetMapping("/outposts")
	ModelAndView list()
	{
		final ListOutposts command = commandFactory.listOutposts();
		final ListOutpostsResultImplementation result = new ListOutpostsResultImplementation();
		command.setResult(result);
		command.execute();

		final ModelAndView modelAndView = new ModelAndView("outposts/list");
		modelAndView.addObject("outposts", result.outposts);
		return modelAndView;
	}

	@GetMapping("/outposts/new")
	String editor()
	{
		return "outposts/new";
	}

	@GetMapping("/outposts/{id}")
	ModelAndView get(@PathVariable("id") final String outpostId)
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
		return modelAndView;
	}

	@PostMapping("/outposts")
	String save(final OutpostForm form)
	{
		final CreateOutpost command = commandFactory.createOutpost();
		command.setRequest(form);
		command.execute();

		return "redirect:/outposts";
	}

	@PostMapping("/outposts/{outpostId}/delete")
	public String delete(@PathVariable("outpostId") final String outpostId)
	{
		final DeleteOutpost command = commandFactory.deleteOutpost();
		command.setRequest(new DeleteOutpostRequest() {
			@Override
			public UUID getOutpostId()
			{
				return UUID.fromString(outpostId);
			}
		});
		command.execute();

		return "redirect:/outposts";
	}

	public static class DescribeOutpostResultImplementation implements DescribeOutpostResult
	{
		private OutpostModel outpost;

		@Override
		public void setOutpost(final OutpostModel outpost)
		{
			this.outpost = outpost;
		}

		public OutpostModel getOutpost()
		{
			return outpost;
		}
	}

	private final class ListOutpostsResultImplementation implements ListOutpostsResult
	{
		private List<Outpost> outposts;

		@Override
		public void setOutposts(final List<Outpost> outposts)
		{
			this.outposts = outposts;
		}
	}

	static class OutpostForm implements CreateOutpostRequest
	{
		private String name;

		@Override
		public String getName()
		{
			return name;
		}

		public void setName(final String name)
		{
			this.name = name;
		}
	}
}

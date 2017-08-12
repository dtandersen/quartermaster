package foxhole.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import foxhole.command.CommandFactory;
import foxhole.command.CreateItem;
import foxhole.command.CreateItem.CreateItemRequest;
import foxhole.service.ItemService;

@Controller
public class ItemController
{
	@Autowired
	private ItemService itemService;

	@Autowired
	private CommandFactory commandFactory;

	@RequestMapping("/items")
	public ModelAndView items()
	{
		final ModelAndView model = new ModelAndView("items/list");
		model.addObject("items", itemService.items());
		return model;
	}

	@GetMapping("/items/new")
	public String enter()
	{
		return "items/new";
	}

	@PostMapping("/items/new")
	public String create(final ItemForm form)
	{
		final CreateItem command = commandFactory.createItem();
		command.setRequest(form);
		command.execute();
		return "redirect:/items";
	}

	static class ItemForm implements CreateItemRequest
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

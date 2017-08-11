package foxhole;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import foxhole.command.CommandFactory;
import foxhole.repository.ItemRepository;
import foxhole.repository.JdbcItemRepository;
import foxhole.repository.JdbcOutpostRepository;
import foxhole.repository.OutpostRepository;
import foxhole.service.ItemService;

@SpringBootApplication
public class Application
{
	public static void main(final String[] args) throws Exception
	{
		SpringApplication.run(Application.class, args);
	}

	@Bean
	ItemService itemService(final ItemRepository itemRepository)
	{
		return new ItemService(itemRepository);
	}

	@Bean
	ItemRepository itemRepository(final NamedParameterJdbcTemplate jdbcTemplate)
	{
		return new JdbcItemRepository(jdbcTemplate);
	}

	@Bean
	OutpostRepository outpostRepository(final NamedParameterJdbcTemplate jdbcTemplate)
	{
		return new JdbcOutpostRepository(jdbcTemplate);
	}

	@Bean
	CommandFactory commandFactory(final ItemRepository itemRepository, final OutpostRepository outpostRepository)
	{
		return new CommandFactory(itemRepository, outpostRepository);
	}
}

package foxhole;

import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import com.opentable.db.postgres.junit.PreparedDbRule;
import com.opentable.db.postgres.junit.SingleInstancePostgresRule;
import foxhole.command.CommandFactory;
import foxhole.repository.ItemRepository;
import foxhole.repository.JdbcItemRepository;
import foxhole.repository.JdbcOutpostRepository;
import foxhole.repository.OutpostRepository;

public class TestEnvironment
{
	private final DataSource dataSource;

	private final NamedParameterJdbcTemplate jdbcTemplate;

	private ItemRepository itemRepository;

	private OutpostRepository outpostRepository;

	public TestEnvironment(final SingleInstancePostgresRule pg)
	{
		dataSource = pg.getEmbeddedPostgres().getPostgresDatabase();
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public TestEnvironment(final PreparedDbRule pg)
	{
		dataSource = pg.getTestDatabase();
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		init();
	}

	public TestEnvironment(final EmbeddedPostgres pg)
	{
		dataSource = pg.getPostgresDatabase();
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		init();
	}

	private void init()
	{
		itemRepository = new JdbcItemRepository(jdbcTemplate());
		outpostRepository = new JdbcOutpostRepository(jdbcTemplate());
	}

	public NamedParameterJdbcTemplate jdbcTemplate()
	{
		return jdbcTemplate;
	}

	public DataSource dataSource()
	{
		return dataSource;
	}

	public ItemRepository itemRepository()
	{
		return itemRepository;
	}

	public OutpostRepository outpostRepository()
	{
		return outpostRepository;
	}

	public CommandFactory commandFactory()
	{
		return new CommandFactory(itemRepository, outpostRepository);
	}
}

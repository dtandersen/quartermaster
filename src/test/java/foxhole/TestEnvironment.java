package foxhole;

import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import com.opentable.db.postgres.junit.PreparedDbRule;
import com.opentable.db.postgres.junit.SingleInstancePostgresRule;
import foxhole.repository.JdbcItemRepository;
import foxhole.repository.JdbcOutpostRepository;

public class TestEnvironment
{
	private final DataSource dataSource;

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public TestEnvironment(final SingleInstancePostgresRule pg)
	{
		dataSource = pg.getEmbeddedPostgres().getPostgresDatabase();
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public TestEnvironment(final PreparedDbRule pg)
	{
		dataSource = pg.getTestDatabase();
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public TestEnvironment(final EmbeddedPostgres pg)
	{
		dataSource = pg.getPostgresDatabase();
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public NamedParameterJdbcTemplate jdbcTemplate()
	{
		return jdbcTemplate;
	}

	public DataSource dataSource()
	{
		return dataSource;
	}

	public JdbcItemRepository itemRepository()
	{
		return new JdbcItemRepository(jdbcTemplate());
	}

	public JdbcOutpostRepository outpostRepository()
	{
		return new JdbcOutpostRepository(jdbcTemplate());
	}
}

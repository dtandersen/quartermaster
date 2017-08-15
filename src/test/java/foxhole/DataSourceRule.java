package foxhole;

import javax.sql.DataSource;
import org.junit.rules.ExternalResource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import com.opentable.db.postgres.junit.SingleInstancePostgresRule;

public class DataSourceRule extends ExternalResource
{
	private final SingleInstancePostgresRule pg;

	private NamedParameterJdbcTemplate jdbcTemplate;

	private DataSource dataSource;

	public DataSourceRule(final SingleInstancePostgresRule pg)
	{
		this.pg = pg;
	}

	@Override
	protected void before() throws Throwable
	{
		dataSource = pg.getEmbeddedPostgres().getPostgresDatabase();
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public NamedParameterJdbcTemplate getJdbcTemplate()
	{
		return jdbcTemplate;
	}

	public DataSource getDataSource()
	{
		return dataSource;
	}
}

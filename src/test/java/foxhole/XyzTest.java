package foxhole;

import org.flywaydb.core.Flyway;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.SingleInstancePostgresRule;
import foxhole.command.ProcessChatMessage;
import foxhole.command.ProcessChatMessage.ProcessChatMessageRequest;
import foxhole.entity.Outpost.OutpostBuilder;
import foxhole.repository.JdbcOutpostRepository;

public class XyzTest
{
	@Rule
	public SingleInstancePostgresRule pg = EmbeddedPostgresRules.singleInstance();

	@Rule
	public DataSourceRule dbrule = new DataSourceRule(pg);

	private NamedParameterJdbcTemplate jdbcTemplate;

	private JdbcOutpostRepository outpostRepository;

	@Before
	public void setUp()
	{
		jdbcTemplate = dbrule.getJdbcTemplate();
		outpostRepository = new JdbcOutpostRepository(jdbcTemplate);
		final Flyway f = new Flyway();
		f.setLocations(new String[] { "db/migration" });
		f.setDataSource(dbrule.getDataSource());
		f.migrate();
	}

	@Test
	public void test()
	{
		outpostRepository.create(OutpostBuilder.outpost().withName("HQ").build());

		processMessage("!inv rifle @ hq");
	}

	private void processMessage(final String message)
	{
		final ProcessChatMessage command = new ProcessChatMessage();
		command.setRequest(new ProcessChatMessageRequest() {});
		command.execute();
	}
}

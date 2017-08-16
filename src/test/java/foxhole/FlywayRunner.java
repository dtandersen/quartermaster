package foxhole;

import javax.sql.DataSource;
import org.flywaydb.core.Flyway;

public class FlywayRunner
{
	public static void runFlyway(final DataSource dataSource)
	{
		final Flyway f = new Flyway();
		f.setLocations(new String[] { "db/migration" });
		f.setDataSource(dataSource);
		f.migrate();
	}
}

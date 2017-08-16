package foxhole.command;

import java.io.IOException;
import com.opentable.db.postgres.embedded.EmbeddedPostgres;

public class PostgressRunner
{
	public static EmbeddedPostgres pg;

	public static void run() throws IOException
	{
		pg = EmbeddedPostgres
				.builder()
				.setCleanDataDirectory(true)
				.setPort(9997)
				// .setDataDirectory(new File(System.getProperty("java.io.tmpdir") + "\\embedded-pg\\foxhole"))
				.start();
	}

	public static boolean started()
	{
		return pg != null;
	}
}

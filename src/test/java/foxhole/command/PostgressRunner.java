package foxhole.command;

import java.io.File;
import java.io.IOException;
import com.opentable.db.postgres.embedded.EmbeddedPostgres;

public class PostgressRunner
{
	public static EmbeddedPostgres pg;

	public static void run() throws IOException
	{
		pg = EmbeddedPostgres
				.builder()
				.setCleanDataDirectory(false)
				.setDataDirectory(new File("C:\\Users\\dta001\\AppData\\Local\\Temp\\embedded-pg\\foxhole"))
				.start();
	}

	public static boolean started()
	{
		return pg != null;
	}
}

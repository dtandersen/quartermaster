package foxhole.bot;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import java.io.IOException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import foxhole.FlywayRunner;
import foxhole.TestEnvironment;
import foxhole.TestFacade;
import foxhole.command.PostgressRunner;
import foxhole.entity.Stock.StockBuilder;
import foxhole.util.MarkdownStream;
import foxhole.util.MarkdownStream.Row;

public class QuartermasterBotTest
{
	private TestEnvironment env;

	private TestFacade testFacade;

	private MockDiscord mockDiscord;

	@BeforeClass
	public static void prepDb() throws IOException
	{
		if (!PostgressRunner.started())
		{
			PostgressRunner.run();
			FlywayRunner.runFlyway(PostgressRunner.pg.getPostgresDatabase());
		}
	}

	@Before
	public void setUp() throws IOException
	{
		env = new TestEnvironment(PostgressRunner.pg);
		testFacade = new TestFacade(env);

		testFacade.cleanDb();

		mockDiscord = new MockDiscord();
		testFacade.givenOutpost("HQ");
		testFacade.givenOutpost("FOB");
		testFacade.givenItem("SMG");
		testFacade.givenItem("Rifle");
		givenInventory(
				"outpost | item  | quantity",
				"HQ      | Rifle | 10",
				"FOB     | SMG   | 20");
	}

	@Test
	public void rifleInventoryAtHq() throws BotException
	{
		processMessage("!inv rifle @ hq");

		assertThat(theOutput(), is("Rifle 10"));
	}

	@Test
	public void smgInventoryAtFob() throws BotException
	{
		processMessage("!inv smg @ fob");

		assertThat(theOutput(), is("SMG 20"));
	}

	private void givenInventory(final String... testData) throws IOException
	{
		MarkdownStream.of(testData)
				.map(row -> x(row))
				.forEach(stockBuilder -> testFacade.createStock(stockBuilder));
	}

	private StockBuilder x(final Row row)
	{
		return StockBuilder.stock()
				.withOutpostId(testFacade.outpostIdOf(row.trimmed("outpost")))
				.withItemId(testFacade.itemIdOf(row.trimmed("item")))
				.withQuantity(row.asInteger("quantity"));
	}

	private String theOutput()
	{
		return mockDiscord.getMessage(0);
	}

	private void processMessage(final String message) throws BotException
	{
		final QuartermasterBot bot = new QuartermasterBot(mockDiscord, env.commandFactory());
		bot.start();
		mockDiscord.injectMessage(message);
	}
}

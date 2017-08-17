package foxhole.bot;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import foxhole.TestFacade;
import foxhole.entity.Stock.StockBuilder;
import foxhole.util.MarkdownStream;
import foxhole.util.MarkdownStream.Row;

public class QuartermasterBotTest
{
	private QuartermasterEnvironment env;

	private TestFacade testFacade;

	private MockDiscord mockDiscord;

	@Before
	public void setUp() throws IOException
	{
		env = new InMemoryQuartermasterEnvironment();
		testFacade = new TestFacade(env);

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

		assertThat(theOutput(), is("There is 10 Rifle at HQ"));
	}

	@Test
	public void smgInventoryAtFob() throws BotException
	{
		processMessage("!inv smg @ fob");

		assertThat(theOutput(), is("There is 20 SMG at FOB"));
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

package foxhole;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import java.io.IOException;
import java.util.UUID;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import foxhole.bot.BotException;
import foxhole.bot.MockDiscord;
import foxhole.bot.QuartermasterBot;
import foxhole.command.PostgressRunner;
import foxhole.entity.Item;
import foxhole.entity.Item.ItemBuilder;
import foxhole.entity.Outpost.OutpostBuilder;
import foxhole.entity.Stock.StockBuilder;
import foxhole.util.MarkdownStream;
import foxhole.util.MarkdownStream.Row;

public class QuartermasterBotTest
{
	// @Rule
	// public SingleInstancePostgresRule pg = EmbeddedPostgresRules.singleInstance();

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

		// testFacade.runFlyway();
		testFacade.cleanDb();

		mockDiscord = new MockDiscord();
		testFacade.givenOutpost("HQ");
		testFacade.givenItem("SMG");
		testFacade.givenItem("Rifle");
		givenInventory(
				"outpost | item  | quantity",
				"HQ      | Rifle | 10");
	}

	@Test
	public void rifleInventoryAtHq() throws BotException
	{
		final ItemBuilder withName = ItemBuilder.item()
				.withItemId(UUID.randomUUID())
				.withName("Rifle");
		final Item item = testFacade.createItem(withName);

		final OutpostBuilder withStock = OutpostBuilder.outpost()
				.withName("HQ")
				.withStock(StockBuilder.stock()
						.withItemId(item.getItemId())
						.withQuantity(10));
		testFacade.createOutpost(withStock);

		processMessage("!inv rifle @ hq");

		assertThat(theOutput(), is("Rifle 10"));
	}

	// @Test
	// public void smgInventoryAtFob() throws BotException
	// {
	// final UUID itemId = UUID.randomUUID();
	// itemRepository.create(ItemBuilder.item().withItemId(itemId).withName("SMG"));
	// final Outpost outpost = OutpostBuilder.outpost()
	// .withName("FOB")
	// .withStock(Arrays.asList(StockBuilder.stock()
	// .withItemId(itemId)
	// .withQuantity(20)
	// .build()))
	// .build();
	// outpostRepository.create(outpost);
	// outpostRepository.createStock(outpost);
	//
	// processMessage("!inv smg @ fob");
	//
	// assertThat(theOutput(), is("SMG 20"));
	// }

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
		final QuartermasterBot bot = new QuartermasterBot(mockDiscord);
		bot.start();
		mockDiscord.injectMessage(message);
	}
}

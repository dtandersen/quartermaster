package foxhole.command;

import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import java.io.IOException;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import foxhole.FlywayRunner;
import foxhole.TestEnvironment;
import foxhole.TestFacade;
import foxhole.command.ItemInventoryAtOutpost.ItemInventoryAtOutpostRequest;
import foxhole.command.ItemInventoryAtOutpost.ItemInventoryAtOutpostResult;
import foxhole.command.model.OutpostModel.StockModel;
import foxhole.entity.Stock;
import foxhole.entity.Stock.StockBuilder;
import foxhole.util.ComposeBuilder;
import foxhole.util.MarkdownStream;
import foxhole.util.MarkdownStream.Row;

public class ItemInventoryAtOutpostTest
{
	private TestEnvironment env;

	private TestFacade testFacade;

	private ItemInventoryAtOutpostResultImplementation result;

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

		testFacade.runFlyway();
		testFacade.cleanDb();

		testFacade.givenOutpost("HQ");
		testFacade.givenItem("SMG");
		testFacade.givenItem("Rifle");
		givenInventory(
				"outpost | item  | quantity",
				"HQ      | Rifle | 10");
	}

	@Test
	public void test() throws IOException
	{
		whenItemInventoryRequested("Rifle", "HQ");

		assertThat(stock(), matches(
				"outpost | item  | quantity",
				"HQ      | Rifle | 10"));
	}

	@Test
	public void ignoreCase() throws IOException
	{
		whenItemInventoryRequested("rifle", "hq");

		assertThat(stock(), matches(
				"outpost | item  | quantity",
				"HQ      | Rifle | 10"));
	}

	@Test
	public void missingStock() throws IOException
	{
		whenItemInventoryRequested("SMG", "HQ");

		assertThat(stock(), matches(
				"outpost | item  | quantity",
				"HQ      | SMG   | 0"));
	}

	@Test
	public void missingItem() throws IOException
	{
		whenItemInventoryRequested("Invalid", "HQ");

		assertThat(stock(), nullValue());
	}

	@Test
	public void missingOutpost() throws IOException
	{
		whenItemInventoryRequested("Rifle", "Invalid");

		assertThat(stock(), nullValue());
	}

	private Matcher<StockModel> matches(final String... testData) throws IOException
	{
		final Matcher<StockModel> matchers = MarkdownStream.of(testData)
				.map(row -> rowToStockModel(row))
				// .map(stockBuilder -> stockBuilder.build())
				.map(stock -> stockMatcher(stock))
				.findFirst().get();

		return matchers;
	}

	private Matcher<StockModel> stockMatcher(final StockModel stock)
	{
		return ComposeBuilder.compose(StockModel.class)
				.withFeature("outpostName", StockModel::getOutpostName, stock.getOutpostName())
				.withFeature("outpostName", StockModel::getItemName, stock.getItemName())
				// .withFeature("itemId", StockModel::getItemId, stock.getItemId())
				.withFeature("quantity", StockModel::getQuantity, stock.getQuantity())
				// .withFeature("shipping", StockModel::getShipping, stock.getShipping())
				.build();
	}

	private StockModel stock()
	{
		return result.stock();
	}

	private void givenInventory(final String... testData) throws IOException
	{
		MarkdownStream.of(testData)
				.map(row -> StockBuilder.stock()
						.withOutpostId(testFacade.outpostIdOf(row.trimmed("outpost")))
						.withItemId(testFacade.itemIdOf(row.trimmed("item")))
						.withQuantity(row.asInteger("quantity")))
				.forEach(stockBuilder -> testFacade.createStock(stockBuilder));
	}

	private StockModel rowToStockModel(final Row row)
	{
		return new StockModel(StockBuilder.stock()
				.withOutpostId(testFacade.outpostIdOf(row.trimmed("outpost")))
				.withItemId(testFacade.itemIdOf(row.trimmed("item")))
				.withQuantity(row.asInteger("quantity"))
				.build(),
				testFacade.itemByName(row.trimmed("item")),
				testFacade.outpostByName(row.trimmed("outpost"))

		);
		// return StockBuilder.stock()
		// .withOutpostId(testFacade.outpostIdOf(row.trimmed("outpost")))
		// .withItemId(testFacade.itemIdOf(row.trimmed("item")))
		// .withQuantity(row.asInteger("quantity"));
	}

	private void whenItemInventoryRequested(final String itemName, final String outpostName)
	{
		result = new ItemInventoryAtOutpostResultImplementation();

		final ItemInventoryAtOutpost command = new ItemInventoryAtOutpost(env.outpostRepository(), env.itemRepository());
		command.setRequest(new ItemInventoryAtOutpostRequest() {
			@Override
			public String getOutpostName()
			{
				return outpostName;
			}

			@Override
			public String getItemName()
			{
				return itemName;
			}
		});
		command.setResult(result);
		command.execute();
	}

	private final class ItemInventoryAtOutpostResultImplementation implements ItemInventoryAtOutpostResult
	{
		private StockModel stock;

		public StockModel stock()
		{
			return stock;
		}

		@Override
		public void setStock(final Stock stockx, final StockModel stock)
		{
			this.stock = stock;
		}
	}
}

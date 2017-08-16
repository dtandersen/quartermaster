package foxhole.bot;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import foxhole.bot.DiscordClient.DiscordEventListener;
import foxhole.command.CommandFactory;
import foxhole.command.ItemInventoryAtOutpost;
import foxhole.command.ItemInventoryAtOutpost.ItemInventoryAtOutpostRequest;
import foxhole.command.ItemInventoryAtOutpost.ItemInventoryAtOutpostResult;
import foxhole.command.model.OutpostModel.StockModel;
import foxhole.entity.Stock;

public class QuartermasterBot implements DiscordEventListener
{
	private final DiscordClient discordClient;

	private final CommandFactory commandFactory;

	public QuartermasterBot(
			final DiscordClient discordClient,
			final CommandFactory commandFactory)
	{
		this.discordClient = discordClient;
		this.commandFactory = commandFactory;
		discordClient.setDiscordEventListener(this);
	}

	@Override
	public void onMessageReceived(final String message, final String snowflakeId)
	{
		System.out.println(message);
		final Pattern p = Pattern.compile("!inv ([a-zA-Z\\.]+) @ ([a-zA-Z]+)");
		final Matcher x = p.matcher(message);
		if (x.find())
		{
			final ItemInventoryAtOutpost command = commandFactory.inventory();
			command.setRequest(new ItemInventoryAtOutpostRequest() {
				@Override
				public String getOutpostName()
				{
					return x.group(2);
				}

				@Override
				public String getItemName()
				{
					return x.group(1);
				}
			});
			command.setResult(new ItemInventoryAtOutpostResult() {
				@Override
				public void setStock(final Stock stock, final StockModel stockModel)
				{
					discordClient.sendMessage(stockModel.getItemName() + " " + stock.getQuantity(), snowflakeId);
				}
			});
			command.execute();
		}
	}

	public void start() throws BotException
	{
		discordClient.connect();
	}
}

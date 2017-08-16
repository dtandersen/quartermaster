package foxhole.bot;

import foxhole.bot.DiscordClient.DiscordEventListener;

public class QuartermasterBot implements DiscordEventListener
{
	private final DiscordClient discordClient;

	public QuartermasterBot(final DiscordClient discordClient)
	{
		this.discordClient = discordClient;
		discordClient.setDiscordEventListener(this);
	}

	@Override
	public void onMessageReceived(final String message)
	{
		discordClient.sendMessage("Rifle 10");
	}

	public void start() throws BotException
	{
		discordClient.connect();
	}
}

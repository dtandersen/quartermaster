package foxhole.bot;

import java.util.ArrayList;
import java.util.List;

public class MockDiscord implements DiscordClient
{
	private final List<String> messages = new ArrayList<>();

	private boolean started;

	private DiscordEventListener listener;

	public String getMessage(final int i)
	{
		return messages.get(i);
	}

	@Override
	public void sendMessage(final String message, final String snowflakeId)
	{
		if (started)
		{
			messages.add(message);
		}
	}

	@Override
	public void connect() throws BotException
	{
		started = true;
	}

	public void injectMessage(final String message)
	{
		listener.onMessageReceived(message, null);
	}

	@Override
	public void setDiscordEventListener(final DiscordEventListener listener)
	{
		this.listener = listener;
	}
}

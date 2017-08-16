package foxhole.bot;

public interface DiscordClient
{
	void connect() throws BotException;

	void sendMessage(String message);

	void setDiscordEventListener(DiscordEventListener listener);

	public interface DiscordEventListener
	{
		void onMessageReceived(String message);
	}
}

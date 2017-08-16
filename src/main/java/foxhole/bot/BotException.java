package foxhole.bot;

@SuppressWarnings("serial")
public class BotException extends Exception
{
	public BotException(final Exception e)
	{
		super(e);
	}
}

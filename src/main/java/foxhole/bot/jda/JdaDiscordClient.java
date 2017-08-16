package foxhole.bot.jda;

import javax.security.auth.login.LoginException;
import foxhole.bot.BotException;
import foxhole.bot.DiscordClient;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class JdaDiscordClient implements DiscordClient
{
	private JDA jda;

	private DiscordEventListener discordEventListener;

	private final String token;

	public JdaDiscordClient(final String token)
	{
		this.token = token;
	}

	@Override
	public void setDiscordEventListener(final DiscordEventListener discordEventListener)
	{
		this.discordEventListener = discordEventListener;
	}

	@Override
	public void connect() throws BotException
	{
		try
		{
			jda = new JDABuilder(AccountType.BOT)
					.setToken(token)
					.addEventListener(new JdaEventListenerAdapter(discordEventListener))
					.buildBlocking();
		}
		catch (LoginException | IllegalArgumentException | RateLimitedException | InterruptedException e)
		{
			throw new BotException(e);
		}
	}

	@Override
	public void sendMessage(final String message, final String snowflakeId)
	{
		jda.getTextChannelById(snowflakeId).sendMessage(message).queue();
	}

	private static class JdaEventListenerAdapter extends ListenerAdapter
	{
		private final DiscordEventListener listener;

		public JdaEventListenerAdapter(final DiscordEventListener listener)
		{
			this.listener = listener;
		}

		@Override
		public void onMessageReceived(final MessageReceivedEvent event)
		{
			// event.getChannel().getId();
			listener.onMessageReceived(event.getMessage().getContent(), event.getChannel().getId());
		}
	}
}

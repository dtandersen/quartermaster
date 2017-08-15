package foxhole;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

@Configuration
public class DiscordBotConfig
{
	@Bean
	public CommandLineRunner schedulingRunner()
	{
		return new CommandLineRunner() {
			@Override
			public void run(final String... args) throws Exception
			{
				final JDA jda = new JDABuilder(AccountType.BOT)
						.setToken("MzQ2ODM5MDcxNzg5Njc4NjE0.DHPqrw.AdBkaw9RBL50Gr7bnQQ0nX0zq00")
						.addEventListener(new DiscordBotConfig())
						.buildAsync();
			}
		};
	}

	static class MyAdapter extends ListenerAdapter
	{
		@Override
		public void onMessageReceived(final MessageReceivedEvent event)
		{
		}
	}
}
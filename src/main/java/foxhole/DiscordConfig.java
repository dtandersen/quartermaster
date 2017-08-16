package foxhole;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import foxhole.bot.DiscordClient;
import foxhole.bot.QuartermasterBot;
import foxhole.bot.jda.JdaDiscordClient;

@Configuration
public class DiscordConfig
{
	@Bean
	public CommandLineRunner schedulingRunner()
	{
		return new CommandLineRunner() {
			@Override
			public void run(final String... args) throws Exception
			{
				final QuartermasterBot bot = new QuartermasterBot(discordClient());
				bot.start();
			}
		};
	}

	@Bean
	DiscordClient discordClient()
	{
		final String token = "MzQ2ODM5MDcxNzg5Njc4NjE0.DHPqrw.AdBkaw9RBL50Gr7bnQQ0nX0zq00";

		return new JdaDiscordClient(token);
	}
}

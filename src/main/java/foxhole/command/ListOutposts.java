package foxhole.command;

import java.util.List;
import foxhole.command.ListOutposts.ListOutpostsResult;
import foxhole.entity.Outpost;
import foxhole.repository.OutpostRepository;

public class ListOutposts extends BaseCommand<foxhole.command.BaseCommand.VoidRequest, ListOutpostsResult>
{
	private final OutpostRepository outpostRepository;

	ListOutposts(final OutpostRepository outpostRepository)
	{
		this.outpostRepository = outpostRepository;
	}

	@Override
	public void execute()
	{
		result.setOutposts(outpostRepository.all());
	}

	public static interface ListOutpostsResult
	{
		void setOutposts(List<Outpost> outposts);
	}
}

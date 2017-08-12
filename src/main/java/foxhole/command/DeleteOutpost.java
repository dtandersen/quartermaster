package foxhole.command;

import java.util.UUID;
import foxhole.command.BaseCommand.VoidResult;
import foxhole.command.DeleteOutpost.DeleteOutpostRequest;
import foxhole.repository.OutpostRepository;

public class DeleteOutpost extends BaseCommand<DeleteOutpostRequest, VoidResult>
{
	private final OutpostRepository outpostRepository;

	public DeleteOutpost(final OutpostRepository outpostRepository)
	{
		this.outpostRepository = outpostRepository;
	}

	@Override
	public void execute()
	{
		outpostRepository.delete(request.getOutpostId());
	}

	public interface DeleteOutpostRequest
	{
		UUID getOutpostId();
	}
}

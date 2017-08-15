package foxhole.command;

import foxhole.command.BaseCommand.VoidResult;
import foxhole.command.ProcessChatMessage.ProcessChatMessageRequest;

public class ProcessChatMessage extends BaseCommand<ProcessChatMessageRequest, VoidResult>
{
	@Override
	public void execute()
	{
	}

	public interface ProcessChatMessageRequest
	{
	}
}

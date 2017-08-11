package foxhole.command;

public abstract class BaseCommand<REQ, RES>
{
	protected REQ request;

	protected RES result;

	public void setRequest(final REQ request)
	{
		this.request = request;
	}

	public void setResult(final RES result)
	{
		this.result = result;
	}

	abstract public void execute();

	public static class VoidRequest
	{
	}

	public static class VoidResult
	{
	}
}

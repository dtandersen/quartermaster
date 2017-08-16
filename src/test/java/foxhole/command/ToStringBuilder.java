package foxhole.command;

import java.util.ArrayList;
import java.util.List;

public class ToStringBuilder
{
	private final String className;

	private final List<Property> props = new ArrayList<>();

	public ToStringBuilder(final String className)
	{
		this.className = className;
	}

	public ToStringBuilder(final Object object)
	{
		this(object.getClass().getSimpleName());
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder();
		sb.append(className);
		if (!props.isEmpty())
		{
			int i = 0;
			for (final Property prop : props)
			{
				if (i == 0)
				{
					sb.append(" ");
				}
				else if (i == props.size() - 1)
				{
					sb.append(",\nand ");
				}
				else
				{
					sb.append(",\n");
				}
				sb.append(prop.toString());
				i++;
			}
		}

		return sb.toString();
	}

	public ToStringBuilder withProperty(final String string, final Object value)
	{
		if (value == null)
		{
			props.add(new Property(string, "null"));
		}
		else
		{
			props.add(new Property(string, value.toString()));
		}
		return this;
	}

	public static ToStringBuilder of(final Object object)
	{
		return new ToStringBuilder(object);
	}

	static class Property
	{
		private final String property;

		private final String value;

		public Property(final String property, final String value)
		{
			this.property = property;
			this.value = value;
		}

		@Override
		public String toString()
		{
			return property + "=" + value;
		}
	}
}

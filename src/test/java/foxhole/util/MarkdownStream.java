package foxhole.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MarkdownStream
{
	public static Stream<Row> of(final String markdown) throws IOException
	{
		final BufferedReader reader = new BufferedReader(new StringReader(markdown));

		final List<Row> rows = new ArrayList<>();

		final Map<String, Integer> columnDefs = new HashMap<>();

		String line;
		boolean first = true;
		while ((line = reader.readLine()) != null)
		{
			if (first)
			{
				int i = 0;
				final String[] split = line.split("\\|");
				for (final String def : split)
				{
					columnDefs.put(def.trim(), i++);
				}
				first = false;
			}
			rows.add(new Row(line, columnDefs));
		}

		return rows.stream().skip(1);
	}

	public static Stream<Row> of(final String... markdown) throws IOException
	{
		return of(join(markdown));
	}

	private static String join(final String... markdown)
	{
		return Arrays.stream(markdown).collect(Collectors.joining("\n"));
	}

	public static class Row
	{
		private final String[] elements;

		private final Map<String, Integer> columnDefs;

		public Row(final String line, final Map<String, Integer> columnDefs)
		{
			this.columnDefs = columnDefs;
			elements = line.split("[^\\\\]\\|");
		}

		public String trimmed(final int index)
		{
			if (index > elements.length - 1) { return null; }

			final String trim = elements[index].trim();
			if (trim.isEmpty()) { return null; }

			return trim;
		}

		@Override
		public String toString()
		{
			return "\"" + Arrays.stream(elements).collect(Collectors.joining("|")) + "\"";
		}

		public double asDouble(final int index)
		{
			return Double.valueOf(elements[index]);
		}

		public int asInteger(final int index)
		{
			return Integer.valueOf(trimmed(index));
		}

		public String trimmed(final String column)
		{
			final Integer index = columnDefs.get(column);
			if (index == null) { throw new RuntimeException("Column " + column + " not found"); }
			return trimmed(index);
		}

		public int asInteger(final String column)
		{
			final Integer index = columnDefs.get(column);
			return asInteger(index);
		}
	}
}

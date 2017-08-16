package foxhole.util;

import static org.hamcrest.Matchers.equalTo;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import org.hamcrest.Matcher;
import org.hobsoft.hamcrest.compose.ComposeMatchers;

public class ComposeBuilder<T>
{
	private String compositeDescription;

	private final List<Matcher<T>> matchers = new ArrayList<>();

	public ComposeBuilder<T> withDescription(final String compositeDescription)
	{
		this.compositeDescription = compositeDescription;
		return this;
	}

	public <U> ComposeBuilder<T> withFeature(
			final String featureName,
			final Function<T, U> featureFunction,
			final Matcher<? super U> featureMatcher)
	{
		final Matcher<T> matcher = ComposeMatchers.hasFeature(featureName, featureFunction, featureMatcher);
		matchers.add(matcher);

		return this;
	}

	public <U> ComposeBuilder<T> withFeature(
			final String featureName,
			final Function<T, U> featureFunction,
			final Object value)
	{
		return withFeature(featureName, featureFunction, equalTo(value));
	}

	public Matcher<T> build()
	{
		final Matcher<T> matcher = ComposeMatchers.compose(compositeDescription, matchers);

		return matcher;
	}

	public static <T> ComposeBuilder<T> compose()
	{
		return new ComposeBuilder<>();
	}

	public static <T> ComposeBuilder<T> compose(final Class<T> class1)
	{
		return new ComposeBuilder<>();
	}
}

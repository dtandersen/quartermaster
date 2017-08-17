package foxhole.bot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

public abstract class BasicInMemoryDb<KEY, OBJ>
{
	private final Map<KEY, OBJ> entities = new HashMap<>();

	public OBJ add(final OBJ entity)
	{
		entities.put(keyOf(entity), entity);
		return entity;
	}

	public OBJ get(final KEY key)
	{
		return entities.get(key);
	}

	abstract KEY keyOf(final OBJ entity);

	public void remove(final KEY key)
	{
		entities.remove(key);
	}

	public Optional<OBJ> findFirst(final Predicate<OBJ> predicate)
	{
		return entities.values().stream()
				.filter(predicate)
				.findFirst();
	}

	public List<OBJ> all()
	{
		return new ArrayList<>(entities.values());
	}

	public void clear()
	{
		entities.clear();
	}
}

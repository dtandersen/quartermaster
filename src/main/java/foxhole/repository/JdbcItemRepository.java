package foxhole.repository;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import foxhole.entity.Item;
import foxhole.entity.Item.ItemBuilder;

public class JdbcItemRepository implements ItemRepository
{
	@Autowired
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public JdbcItemRepository(final NamedParameterJdbcTemplate jdbcTemplate)
	{
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Item create(final ItemBuilder itemBuilder)
	{
		final String sql = "" +
				"  insert into item" +
				"  (" +
				"    item_id, " +
				"    item_name," +
				"    bmat," +
				"    rmat," +
				"    emat," +
				"    pack," +
				"    min_qty" +
				"  ) VALUES (" +
				"    :id," +
				"    :name," +
				"    :bmat," +
				"    :rmat," +
				"    :emat," +
				"    :pack," +
				"    :minQty" +
				"  )";

		final Map<String, Object> parameters = new HashMap<>();
		final Item item = itemBuilder.build();
		parameters.put("id", item.getItemId());
		parameters.put("name", item.getName());
		parameters.put("bmat", item.getBmat());
		parameters.put("rmat", item.getRmat());
		parameters.put("emat", item.getEmat());
		parameters.put("pack", item.getPack());
		parameters.put("minQty", item.getMinQty());

		jdbcTemplate.update(sql, parameters);

		return item;
	}

	@Override
	public List<Item> all()
	{
		final List<Item> items = jdbcTemplate.query("select * from item",
				(rs, rownum) -> ItemBuilder.item()
						.withItemId(UUID.fromString(rs.getString("item_id")))
						.withName(rs.getString("item_name"))
						.withBmat(rs.getInt("bmat"))
						.withRmat(rs.getInt("rmat"))
						.withEmat(rs.getInt("emat"))
						.withPack(rs.getInt("pack"))
						.withMinQty(rs.getInt("min_qty"))
						.withSortOrder(rs.getInt("sort_order"))
						.build());

		return items;
	}

	@Override
	public Optional<Item> findByName(final String itemName)
	{
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("itemName", itemName);

		try
		{
			final Item item = jdbcTemplate.queryForObject("" +
					"  select *" +
					"  from item" +
					"  where lower(item_name) = lower(:itemName)",
					parameters,
					(final ResultSet rs, final int rownum) -> ItemBuilder.item()
							.withItemId(UUID.fromString(rs.getString("item_id")))
							.withName(rs.getString("item_name"))
							.withBmat(rs.getInt("bmat"))
							.withRmat(rs.getInt("rmat"))
							.withEmat(rs.getInt("emat"))
							.withPack(rs.getInt("pack"))
							.withMinQty(rs.getInt("min_qty"))
							.withSortOrder(rs.getInt("sort_order"))
							.build());

			return Optional.of(item);
		}
		catch (final EmptyResultDataAccessException e)
		{
			return Optional.empty();
		}
	}

	@Override
	public void clean()
	{
		jdbcTemplate.update("delete from item", new HashMap<>());
	}
}

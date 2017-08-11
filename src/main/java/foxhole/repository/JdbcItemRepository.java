package foxhole.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
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
	public List<Item> all()
	{
		final List<Item> items = jdbcTemplate.query(
				"select * from item",
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
	public void create(final ItemBuilder itemBuilder)
	{
		final String sql = "" +
				"  insert into item" +
				"  (" +
				"    item_id, " +
				"    item_name" +
				"  ) VALUES (" +
				"    :id," +
				"    :name" +
				"  )";

		final Map<String, Object> parameters = new HashMap<>();
		final Item item = itemBuilder.build();
		parameters.put("id", item.getItemId());
		parameters.put("name", item.getName());

		jdbcTemplate.update(sql, parameters);
	}
}

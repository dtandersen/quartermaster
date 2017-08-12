package foxhole.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import foxhole.entity.Outpost;
import foxhole.entity.Outpost.OutpostBuilder;
import foxhole.entity.Stock;
import foxhole.entity.Stock.StockBuilder;

public class JdbcOutpostRepository implements OutpostRepository
{
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public JdbcOutpostRepository(final NamedParameterJdbcTemplate jdbcTemplate)
	{
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Outpost> all()
	{
		return jdbcTemplate
				.query("select * from outpost", new OutpostMapper())
				.stream()
				.map(outpostBuilder -> outpostBuilder.build())
				.collect(Collectors.toList());
	}

	@Override
	public void create(final Outpost outpost)
	{
		final String sql = "" +
				"  insert into outpost" +
				"  (" +
				"    outpost_id, " +
				"    outpost_name" +
				"  ) VALUES (" +
				"    :id," +
				"    :name" +
				"  )";

		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("id", outpost.getOutpostId());
		parameters.put("name", outpost.getName());

		jdbcTemplate.update(sql, parameters);
	}

	@Override
	public Outpost find(final UUID outpostId)
	{
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("outpostId", outpostId);

		final String sql = "" +
				"  select *" +
				"  from outpost" +
				"  where outpost_id = :outpostId";

		final OutpostBuilder outpostBuilder = jdbcTemplate.queryForObject(sql, parameters, new OutpostMapper());

		final List<Stock> stock = stockOf(outpostId);
		outpostBuilder.withStock(stock);

		return outpostBuilder.build();
	}

	private List<Stock> stockOf(final UUID outpostId)
	{
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("outpostId", outpostId);

		final String sql = "" +
				"  select *" +
				"  from outpost_item" +
				"  where outpost_id = :outpostId";

		return jdbcTemplate.query(sql, parameters, new StockMapper());
	}

	static class OutpostMapper implements RowMapper<OutpostBuilder>
	{
		@Override
		public OutpostBuilder mapRow(final ResultSet rs, final int rowNum) throws SQLException
		{
			return OutpostBuilder.outpost()
					.withId(rs.getString("outpost_id"))
					.withName(rs.getString("outpost_name"));
		}
	}

	@Override
	public void createStock(final Outpost outpost)
	{
		final String sql = "" +
				"  insert into outpost_item (" +
				"    outpost_id," +
				"    item_id," +
				"    stock" +
				"  ) VALUES (" +
				"    :outpostId," +
				"    :itemId," +
				"    :quantity" +
				"  )";

		for (final Stock stock : outpost.getStock().values())
		{
			// updateStock2(outpost.getOutpostId(), stock);
			final Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("outpostId", outpost.getOutpostId());
			paramMap.put("itemId", stock.getItemId());
			paramMap.put("quantity", stock.getQuantity());

			jdbcTemplate.update(sql, paramMap);
		}
	}

	@Override
	public void updateStock(final UUID outpostId, final Stock stock)
	{
		final String sql = "" +
				"  update outpost_item " +
				"  set stock = :quantity" +
				"  where outpost_id = :outpostId" +
				"    AND item_id = :itemId";

		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("outpostId", outpostId);
		paramMap.put("itemId", stock.getItemId());
		paramMap.put("quantity", stock.getQuantity());

		jdbcTemplate.update(sql, paramMap);
	}

	static class StockMapper implements RowMapper<Stock>
	{
		@Override
		public Stock mapRow(final ResultSet rs, final int rowNum) throws SQLException
		{
			return StockBuilder.stock()
					.withItemId(UUID.fromString(rs.getString("item_id")))
					.withQuantity(rs.getInt("stock"))
					.build();
		}
	}
}

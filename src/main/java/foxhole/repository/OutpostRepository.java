package foxhole.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import foxhole.entity.Outpost;
import foxhole.entity.Outpost.OutpostBuilder;
import foxhole.entity.Stock;
import foxhole.entity.Stock.StockBuilder;

public interface OutpostRepository
{
	List<Outpost> all();

	Outpost create(OutpostBuilder outpost);

	Outpost find(UUID outpostId);

	void createStock(Outpost outpost);

	void updateStock(UUID outpostId, Stock stock);

	void delete(UUID outpostId);

	Optional<Outpost> findByName(String outpostName);

	void createStock(StockBuilder stockBuilder);

	List<Stock> stock();

	void clean();
}

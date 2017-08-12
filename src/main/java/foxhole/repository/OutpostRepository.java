package foxhole.repository;

import java.util.List;
import java.util.UUID;
import foxhole.entity.Outpost;
import foxhole.entity.Stock;

public interface OutpostRepository
{
	List<Outpost> all();

	void create(Outpost outpost);

	Outpost find(UUID outpostId);

	void createStock(Outpost outpost);

	void updateStock(UUID outpostId, Stock stock);
}

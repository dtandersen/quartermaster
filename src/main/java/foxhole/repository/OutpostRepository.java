package foxhole.repository;

import java.util.List;
import java.util.UUID;
import foxhole.entity.Outpost;

public interface OutpostRepository
{
	List<Outpost> all();

	void create(Outpost outpost);

	Outpost find(UUID outpostId);

	void updateStock(Outpost outpost);
}

package be.kdg.airene.ports.in;

import be.kdg.airene.domain.data.Data;

import java.util.Collection;

@FunctionalInterface
public interface SaveDataEntryBatchesUseCase {
	void saveDataEntryBatches(Collection<Data> data);
}

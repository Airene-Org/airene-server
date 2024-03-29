package be.kdg.airene.ports.out;

import be.kdg.airene.domain.data.Data;

import java.util.List;

@FunctionalInterface
public interface GetAllRecentLocationsPort {
	List<Data> getAllRecentLocations();
}

package be.kdg.airene.ports.in;

import be.kdg.airene.adapters.out.db.data.DataJPAInfo;
import be.kdg.airene.domain.location.Location;

import java.time.LocalDate;
import java.util.List;

public interface GetTotalDataForLocationPerHourInARadiusUseCase {
	List<DataJPAInfo> getTotalDataForLocationPerHourInARadius(LocalDate date, Location location, double radiusKm);
}

package be.kdg.airene.ports.out;

import be.kdg.airene.adapters.out.db.data.DataJPAInfo;
import be.kdg.airene.domain.location.Location;

import java.time.LocalDate;
import java.util.List;

@FunctionalInterface
public interface GetTotalForLocationPerDayPerHourWithinRadiusPort {
	List<DataJPAInfo> getTotalForLocationPerDayPerHourWithinRadius(LocalDate date, Location location, double radiusKm);
}

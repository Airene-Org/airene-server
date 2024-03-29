package be.kdg.airene.adapters.out.mapper;

import be.kdg.airene.adapters.in.web.dto.CoordinateDTO;
import be.kdg.airene.adapters.in.web.dto.SubscriptionRequestDTO;
import be.kdg.airene.domain.location.Location;
import be.kdg.airene.domain.location.SubscribedLocation;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LocationMapper {
	LocationMapper INSTANCE = Mappers.getMapper(LocationMapper.class);
	SubscribedLocation toDomain (SubscriptionRequestDTO subscriptionRequestDTO);
	Location toLocation (SubscribedLocation subscribedLocation);
	SubscribedLocation toSubscribedLocation (Location location);
	Location toLocation (CoordinateDTO coordinateDTO);

	default Location toLocation (double latitude, double longitude){
		return new Location(latitude, longitude);
	}
}

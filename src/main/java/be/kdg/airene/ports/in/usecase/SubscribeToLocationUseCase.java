package be.kdg.airene.ports.in.usecase;

import be.kdg.airene.domain.Location;
import be.kdg.airene.domain.user.UserID;

@FunctionalInterface
public interface SubscribeToLocationUseCase {
	void subscribeToLocation(UserID userID, Location location);
}

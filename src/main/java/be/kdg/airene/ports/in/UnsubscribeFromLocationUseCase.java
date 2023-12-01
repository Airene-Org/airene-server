package be.kdg.airene.ports.in;

import be.kdg.airene.domain.Location;
import be.kdg.airene.domain.user.UserID;

@FunctionalInterface
public interface UnsubscribeFromLocationUseCase {
	void unsubscribeFromLocation(UserID userID, Location location);
}
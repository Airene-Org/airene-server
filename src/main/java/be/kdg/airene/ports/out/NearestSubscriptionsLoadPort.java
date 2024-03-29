package be.kdg.airene.ports.out;

import be.kdg.airene.domain.subscription.Subscription;

import java.util.Set;

@FunctionalInterface
public interface NearestSubscriptionsLoadPort {
	Set<Subscription> loadNearestSubscriptions(double latitude, double longitude);
}

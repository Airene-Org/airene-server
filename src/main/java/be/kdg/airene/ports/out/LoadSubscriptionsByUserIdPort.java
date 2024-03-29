package be.kdg.airene.ports.out;

import be.kdg.airene.domain.subscription.Subscription;

import java.util.Set;
import java.util.UUID;

@FunctionalInterface
public interface LoadSubscriptionsByUserIdPort {
	Set<Subscription> loadSubscriptionsByUserId(UUID userId, String sort);
}

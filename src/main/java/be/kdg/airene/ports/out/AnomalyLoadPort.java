package be.kdg.airene.ports.out;

import be.kdg.airene.domain.anomaly.Anomaly;

import java.util.Optional;
import java.util.UUID;

@FunctionalInterface
public interface AnomalyLoadPort {
	Optional<Anomaly> loadAnomaly(UUID id);
}

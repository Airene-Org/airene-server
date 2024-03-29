package be.kdg.airene.adapters.in.web;

import be.kdg.airene.adapters.in.web.dto.LocationDTO;
import be.kdg.airene.adapters.out.mapper.DataEntryMapper;
import be.kdg.airene.adapters.out.mapper.LocationMapper;
import be.kdg.airene.ports.in.GetAllRecentAnomalyLocationsUseCase;
import be.kdg.airene.ports.in.GetAnomaliesForDayAndLocationWithinRadiusKmUseCase;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/anomalies")
public class AnomalyController {
	private final CacheManager cacheManager;
	private final GetAnomaliesForDayAndLocationWithinRadiusKmUseCase anomalyUseCase;
	private final GetAllRecentAnomalyLocationsUseCase getAllRecentAnomalyLocationsUseCase;
	private final DataEntryMapper dataEntryMapper = DataEntryMapper.INSTANCE;
	private final LocationMapper locationMapper = LocationMapper.INSTANCE;

	@Cacheable (value = "anomalyCache", key = "{#date, #latitude, #longitude, #radius}")
	@GetMapping
	public ResponseEntity<?>
	getAvgMedianTotalDataForDayAndLocationWithinRadiusKm
			(@NotNull @RequestParam ("date") @DateTimeFormat (iso = DateTimeFormat.ISO.DATE) LocalDate date,
			 @Valid @RequestParam("latitude") double latitude,
			 @Valid @RequestParam("longitude") double longitude,
			 @RequestParam("radius") double radius) {
		var data = anomalyUseCase.getAnomaliesForDayAndLocationWithinRadiusKm(date, locationMapper.toLocation(latitude, longitude), radius);
		return ResponseEntity.ok(
				dataEntryMapper.mapToDTO(data)
		);
	}

	@Cacheable(value = "anomalyLocations", key = "{#timestamp}")
	@GetMapping("/at")
	public ResponseEntity<List<LocationDTO>> getLocationsAnomaly(@RequestParam("timestamp") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime timestamp){
		return ResponseEntity.ok(dataEntryMapper.mapToDTO(getAllRecentAnomalyLocationsUseCase.getAllRecentAnomalyLocations(timestamp)));
	}


	@Scheduled (fixedRateString = "1", timeUnit = TimeUnit.HOURS)
	public void clearCache() {
		LocalDate currentDate = LocalDate.now();
		Cache cache = cacheManager.getCache("anomalyCache");
		if (cache == null) return;
		ConcurrentHashMap<?, ?> map = (ConcurrentHashMap<?, ?>) cache.getNativeCache();
		ConcurrentHashMap.KeySetView<?, ?> keySet = map.keySet();
		log.debug("keySet: " + keySet);
		if (keySet == null || keySet.stream().toList().isEmpty()) return;
		keySet.forEach(key -> {
			var date = (LocalDate) ((List<Object>) key).getFirst();
			if (!date.isBefore(currentDate)) {
				log.debug("evicting key: " + key);
				cache.evict(key);
			}
		});
	}

	@Scheduled (fixedRateString = "20", timeUnit = TimeUnit.MINUTES)
	public void clearCacheLocations() {
		LocalDateTime currentDate = LocalDateTime.now();
		Cache cache = cacheManager.getCache("anomalyLocations");
		if (cache == null) return;
		ConcurrentHashMap<?, ?> map = (ConcurrentHashMap<?, ?>) cache.getNativeCache();
		ConcurrentHashMap.KeySetView<?, ?> keySet = map.keySet();
		log.debug("keySet: " + keySet);
		if (keySet == null || keySet.stream().toList().isEmpty()) return;
		keySet.forEach(key -> {
			var date = (LocalDateTime) ((List<Object>) key).getFirst();
			if (!date.isBefore(currentDate)) {
				log.debug("evicting key: " + key);
				cache.evict(key);
			}
		});
	}
}

package be.kdg.airene.adapters.in.web;

import be.kdg.airene.adapters.in.web.dto.SubmitFeedbackDTO;
import be.kdg.airene.adapters.out.mapper.FeedbackMapper;
import be.kdg.airene.ports.in.SubmitAnomalyFeedbackUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping ("/api/notifications")
public class NotificationsController {
	private final SubmitAnomalyFeedbackUseCase submitAnomalyFeedbackUseCase;
	private final FeedbackMapper mapper = FeedbackMapper.INSTANCE;
	@PostMapping ("/{id}")
	public ResponseEntity<Void> submitFeedback(@RequestBody SubmitFeedbackDTO submitFeedbackDTO, @PathVariable UUID id) {
		submitAnomalyFeedbackUseCase.submitAnomalyFeedback(id, submitFeedbackDTO.getAnomalyId(),mapper.mapToDomain(submitFeedbackDTO));
		return ResponseEntity.ok().build();
	}
}

package be.kdg.airene.adapters.out.db.notification;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<NotificationJPA, UUID> {

	@Query("SELECT n FROM NotificationJPA n WHERE n.userId = ?1 AND n.timestamp > CURRENT_DATE - 14")
	List<NotificationJPA> findNotificationsByUserId(UUID userId, Sort sort);
}

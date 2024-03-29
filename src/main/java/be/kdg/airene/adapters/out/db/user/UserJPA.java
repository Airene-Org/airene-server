package be.kdg.airene.adapters.out.db.user;

import be.kdg.airene.adapters.out.db.subscription.SubscriptionJPA;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserJPA {
	@Id
	private UUID id;
	private String name;
	private String email;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private Set<SubscriptionJPA> subscribedLocations;
}

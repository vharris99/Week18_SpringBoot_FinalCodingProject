package ecomm.store.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class EcommStore {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ecommStoreId;
	
	private String ecommStoreName;
	private String ecommStoreWebsite;
	private String ecommStoreEmail;
	private String ecommStorePhone;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany (mappedBy = "ecommStore", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Product> products = new HashSet<>();
	
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "ecomm_store_user",
			   joinColumns = @JoinColumn(name = "ecomm_store_id"),
			   inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> users = new HashSet<>();
}

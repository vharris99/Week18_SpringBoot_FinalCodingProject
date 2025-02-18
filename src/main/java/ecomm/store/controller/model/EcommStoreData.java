package ecomm.store.controller.model;

import java.util.HashSet;
import java.util.Set;

import ecomm.store.entity.EcommStore;
import ecomm.store.entity.Product;
import ecomm.store.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

//Jackson will use this to transform the object to and from JSON
@Data
@NoArgsConstructor
public class EcommStoreData {
	private Long ecommStoreId;
	
	private String ecommStoreName;
	private String ecommStoreWebsite;
	private String ecommStoreEmail;
	private String ecommStorePhone;
	private Set<EcommStoreUser> users = new HashSet<EcommStoreUser>();
	private Set<EcommStoreProduct> products = new HashSet<EcommStoreProduct>();
	
	
	public EcommStoreData(EcommStore ecommStore) {
		ecommStoreId = ecommStore.getEcommStoreId();
		ecommStoreName = ecommStore.getEcommStoreName();
		ecommStoreWebsite = ecommStore.getEcommStoreWebsite();
		ecommStoreEmail = ecommStore.getEcommStoreEmail();
		ecommStorePhone = ecommStore.getEcommStorePhone();
	
		
		for (User user : ecommStore.getUsers()) {
			users.add(new EcommStoreUser(user));
		}
	
		for (Product product : ecommStore.getProducts()) {
			products.add(new EcommStoreProduct(product));
		}
	
	}
}

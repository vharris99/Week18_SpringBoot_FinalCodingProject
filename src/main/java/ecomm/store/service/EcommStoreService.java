package ecomm.store.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ecomm.store.controller.model.EcommStoreData;
import ecomm.store.controller.model.EcommStoreProduct;
import ecomm.store.controller.model.EcommStoreUser;
import ecomm.store.dao.EcommStoreDao;
import ecomm.store.dao.ProductDao;
import ecomm.store.dao.UserDao;
import ecomm.store.entity.EcommStore;
import ecomm.store.entity.Product;
import ecomm.store.entity.User;


@Service
public class EcommStoreService {
	@Autowired
	private EcommStoreDao ecommStoreDao;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private UserDao userDao;

	@Transactional(readOnly = false)
	public EcommStoreData saveEcommStore(EcommStoreData ecommStoreData) {
	
			Long ecommStoreId = ecommStoreData.getEcommStoreId();
			
			EcommStore ecommStore = findOrCreateEcommStore(ecommStoreId);
			
			copyEcommStoreFields(ecommStore, ecommStoreData);
			
			return new EcommStoreData(ecommStoreDao.save(ecommStore));
	}
	
	// copies matching fields from the EcommStoreData object to the EcommStore object
	// does'nt copy user or product fields
	private void copyEcommStoreFields(EcommStore ecommStore, EcommStoreData ecommStoreData) {
		ecommStore.setEcommStoreId(ecommStoreData.getEcommStoreId());
		ecommStore.setEcommStoreName(ecommStoreData.getEcommStoreName());
		ecommStore.setEcommStoreWebsite(ecommStoreData.getEcommStoreWebsite());
		ecommStore.setEcommStorePhone(ecommStoreData.getEcommStorePhone());
		ecommStore.setEcommStoreEmail(ecommStoreData.getEcommStoreEmail());
	}
	
	// returns new EcommStore object if ecomm store ID is not null
	// if not null, calls findEcommStoreById()
	private EcommStore findOrCreateEcommStore(Long ecommStoreId) {
		EcommStore ecommStore;
		
		if(Objects.isNull(ecommStoreId)) {
			ecommStore = new EcommStore();
		} else {
			ecommStore = findEcommStoreById(ecommStoreId);
		}
		return ecommStore;
	}
	
	// returns a EcommStore object if ecomm store w/ matching ID exists
	// if no matching ecomm store found, throw NoSuchElEx.
	private EcommStore findEcommStoreById(Long ecommStoreId) {
		return ecommStoreDao.findById(ecommStoreId)
				.orElseThrow(() -> new NoSuchElementException(
						"Ecommerce Store with ID=" + ecommStoreId + " not found."));
	}
	
	@Transactional(readOnly = false)
	public EcommStoreProduct saveProduct(Long ecommStoreId, EcommStoreProduct ecommStoreProduct) {
		EcommStore ecommStore = findEcommStoreById(ecommStoreId);
		Product product = findOrCreateProduct(ecommStoreId, ecommStoreProduct.getProductId());
		
		copyProductFields(product, ecommStoreProduct);
		
		product.setEcommStore(ecommStore);
		ecommStore.getProducts().add(product);
		
		return new EcommStoreProduct(productDao.save(product));
	}
	
	//Copies the data in the ecomm store product parameter to the Product object.
	private static void copyProductFields(Product product, EcommStoreProduct ecommStoreProduct) {
		if(Objects.isNull(product) || Objects.isNull(ecommStoreProduct)) {
			throw new IllegalArgumentException("Both product and ecommStoreProduct must be non-null");
		}
		product.setProductId(ecommStoreProduct.getProductId());
		product.setProductName(ecommStoreProduct.getProductName());
		product.setProductDescription(ecommStoreProduct.getProductDescription());
		product.setProductPrice(ecommStoreProduct.getProductPrice());		
	}
	
	//Retrieves an existing product or creates a new one
	private Product findOrCreateProduct(Long ecommStoreId, Long productId) {
		if(Objects.isNull(productId)) {
			return new Product();
		} else {
			return findProductById(ecommStoreId, productId);
		}
	}
	
	//Fetch's the Product object by it's ID
	private Product findProductById(Long ecommStoreId, Long productId) {
		Product product = productDao.findById(productId)
				.orElseThrow(() -> new NoSuchElementException("Product with ID=" + productId + " not found."));
		
		if(!product.getEcommStore().getEcommStoreId().equals(ecommStoreId)) {
			throw new IllegalArgumentException("Product with ID=" + productId + " is not sold by Ecommerce Store with ID=" + ecommStoreId);
		}
		return product;
	}
	
	@Transactional(readOnly = false)
	public EcommStoreUser saveUser(Long ecommStoreId, EcommStoreUser ecommStoreUser) {
		EcommStore ecommStore = findEcommStoreById(ecommStoreId);
		User user = findOrCreateUser(ecommStoreId, ecommStoreUser.getUserId());
		
		copyUserFields(user, ecommStoreUser);
		
		user.getEcommStores().add(ecommStore);
		ecommStore.getUsers().add(user);
		
		return new EcommStoreUser(userDao.save(user));
	}

	private void copyUserFields(User user, EcommStoreUser ecommStoreUser) {
		if(Objects.isNull(user) || Objects.isNull(ecommStoreUser)) {
			throw new IllegalArgumentException("Both user and ecommStoreUser must be non-null");
		}
		user.setUserFirstName(ecommStoreUser.getUserFirstName());
		user.setUserLastName(ecommStoreUser.getUserLastName());
		user.setUserEmail(ecommStoreUser.getUserEmail());		
	}

	private User findOrCreateUser(Long ecommStoreId, Long userId) {
		if(Objects.isNull(userId)) {
			return new User();
		} else {
			return findUserById(ecommStoreId, userId);
		}
	}

	private User findUserById(Long ecommStoreId, Long userId) {
		User user = userDao.findById(userId)
				.orElseThrow(() -> new NoSuchElementException("User with ID=" + userId + " not found."));
		
		for(EcommStore ecommStore : user.getEcommStores()) {
			if(ecommStore.getEcommStoreId().equals(ecommStoreId)) {
				
			return user;
			}
		}
		throw new IllegalArgumentException("User with ID=" + userId + " does not have an account with EcommStore with ID=" + ecommStoreId);
	}
	
	//Returns summary data for all ecomm stores
	@Transactional(readOnly = true)
	public List<EcommStoreData> retrieveAllEcommStores() {
		//Fetches all Ecomm Store entities
		List<EcommStore> ecommStores = ecommStoreDao.findAll();
		
		//Converts List of EcommStore objects to List of EcommStoreData objects
		List<EcommStoreData> result = new ArrayList<>();
        for (EcommStore ecommStore : ecommStores) {
            EcommStoreData ecommStoreData = new EcommStoreData(ecommStore);
        
        //Clear the list of users and products
            ecommStoreData.getUsers().clear();
            ecommStoreData.getProducts().clear();
        
            //Add to result List
        result.add(ecommStoreData);
        }
        return result;
	}
	
	@Transactional(readOnly = true)
	public EcommStoreData retrieveSingleEcommStore(Long ecommStoreId) {
		EcommStore ecommStore = ecommStoreDao.findById(ecommStoreId)
				.orElseThrow(() -> new NoSuchElementException("Ecomm Store with ID=" + ecommStoreId + " not found."));
		
		return new EcommStoreData(ecommStore);
	}

	 public void deleteEcommStoreById(Long ecommStoreId) {
		EcommStore ecommStore = findEcommStoreById(ecommStoreId);
		ecommStoreDao.delete(ecommStore);		
	}
}

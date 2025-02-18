package ecomm.store.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ecomm.store.controller.model.EcommStoreData;
import ecomm.store.controller.model.EcommStoreProduct;
import ecomm.store.controller.model.EcommStoreUser;
import ecomm.store.service.EcommStoreService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/ecomm_store")
@Slf4j
public class EcommStoreController {
	@Autowired
	private EcommStoreService ecommStoreService;
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public EcommStoreData insertEcommStoreData(@RequestBody EcommStoreData ecommStoreData) {
		log.info("Inserting ecommStoreData {} ", ecommStoreData);
		return ecommStoreService.saveEcommStore(ecommStoreData);
	}
	
	@PutMapping("/{ecommStoreId}")
	public EcommStoreData updateEcommStoreData(@PathVariable Long ecommStoreId, @RequestBody EcommStoreData ecommStoreData) {
		ecommStoreData.setEcommStoreId(ecommStoreId);
		log.info("Updating ecommStoreData {} ", ecommStoreData);
		return ecommStoreService.saveEcommStore(ecommStoreData);
	}
	
	@PostMapping("/{ecommStoreId}/product")
	@ResponseStatus(code = HttpStatus.CREATED)
	public EcommStoreProduct addProduct(@PathVariable Long ecommStoreId, @RequestBody EcommStoreProduct ecommStoreProduct) {
		log.info("Adding Product {} ", ecommStoreProduct);
		return ecommStoreService.saveProduct(ecommStoreId, ecommStoreProduct);
	}
	
	@PostMapping("/{ecommStoreId}/user")
	@ResponseStatus(code = HttpStatus.CREATED)
	public EcommStoreUser addUser(@PathVariable Long ecommStoreId, @RequestBody EcommStoreUser ecommStoreUser) {
		log.info("Adding User {} ", ecommStoreUser);
		return ecommStoreService.saveUser(ecommStoreId, ecommStoreUser);
	}
	
	@GetMapping
	public List<EcommStoreData> listOfEcommStores() {
		return ecommStoreService.retrieveAllEcommStores();
	}
	
	@GetMapping("/{ecommStoreId}")
	public EcommStoreData retrieveSingleEcommStore(@PathVariable Long ecommStoreId) {
		return ecommStoreService.retrieveSingleEcommStore(ecommStoreId);
	}
	
	@DeleteMapping("/{ecommStoreId}")
	public Map<String, String> deleteEcommStoreById(@PathVariable Long ecommStoreId) {
		// Log the request
		System.out.println("Request received to delete Ecommerce Store with ID=" + ecommStoreId);
		
		// Call to service method to delete pet store
		ecommStoreService.deleteEcommStoreById(ecommStoreId);
		
		// Return's confirmation message
		Map<String, String> response = new HashMap<>();
		response.put("message", "Ecommerce Store with ID=" + ecommStoreId + " succesfully deleted.");
		  
		return response;
	}
}

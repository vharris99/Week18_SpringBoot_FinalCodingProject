package ecomm.store.controller.model;

import ecomm.store.entity.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

//Jackson will use this to transform the object to and from JSON
@Data
@NoArgsConstructor
public class EcommStoreProduct {
	private Long productId;
	private String productName;
	private String productDescription;
	private double productPrice;
	
	public EcommStoreProduct(Product product) {
		if(product != null) {
			this.productId = product.getProductId();
			this.productName = product.getProductName();
            this.productDescription = product.getProductDescription();
            this.productPrice = product.getProductPrice();
		}
	}
}

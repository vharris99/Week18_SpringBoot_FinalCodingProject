package ecomm.store.controller.model;

import ecomm.store.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

//Jackson will use this to transform the object to and from JSON
@Data
@NoArgsConstructor 
public class EcommStoreUser {
	private Long userId;
	private String userFirstName;
	private String userLastName;
	private String userEmail;
	
	
	public EcommStoreUser(User user) {
		if(user != null) {
			this.userId = user.getUserId();
			this.userFirstName = user.getUserFirstName();
            this.userLastName = user.getUserLastName();
            this.userEmail = user.getUserEmail();
		}
	}
}

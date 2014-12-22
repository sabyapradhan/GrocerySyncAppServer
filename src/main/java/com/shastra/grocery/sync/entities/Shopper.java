package com.shastra.grocery.sync.entities;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
@Cache
public class Shopper {

	@Id
	private String shopperId; 
	
	private String shopperEmail; 
	
	private String fourDigitCode; 
	
	private List<String> groceryItemKeys = new ArrayList<String>(0);
	
	public Shopper( String email, String code ) {
		this.shopperEmail = email; 
		this.fourDigitCode = code;
		this.shopperId = email+code; 
	}
	
	public void addToShoppingList(String itemKey) {
		this.groceryItemKeys.add(itemKey);
	}
	
	public void removeFromShoppingList(String itemKey) {
		this.groceryItemKeys.remove(itemKey);
	}
	
	public List<String> getItemsToBuy(){
		return this.groceryItemKeys;
	}
	
	public void addItemsToBuy(List<String> itemsToBuy) {
		this.groceryItemKeys = itemsToBuy; 
	}
	public void deleteAllFromList(){
		//this.groceryItemKeys.r
	}
}

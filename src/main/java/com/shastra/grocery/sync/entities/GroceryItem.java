package com.shastra.grocery.sync.entities;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
@Cache
public class GroceryItem {
	
    @Id
    private long id;
	
    @Index
	private String itemName;
	
	private String itemDescription; 
	
	@Index
	private boolean isBought; 
	
	public GroceryItem(String itemName, String itemDescription, boolean isBought) { 
		this.itemName = itemName;
		this.itemDescription = itemDescription; 
		this.isBought = isBought; 
	}
	
	private GroceryItem(){}

	public long getId(){
		return this.id;
	}
	
	public String getName(){
		return this.itemName;
	}
	
	public String getDescription(){
		return this.itemDescription;
	}
	
	public boolean hasItemBeenBought(){
		return this.isBought;
	}
}

package com.shastra.grocery.sync.api;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.Named;
import com.googlecode.objectify.Key;
import com.shastra.grocery.sync.Constants;
import com.shastra.grocery.sync.entities.GroceryItem;
import com.shastra.grocery.sync.entities.Shopper;
import com.shastra.grocery.sync.service.OfyService;

/**
 * Defines Grocery Sync APIs.
 */
@Api(   
	name = "grocery", 
        version = "v1", 
        scopes = { Constants.EMAIL_SCOPE }, 
        clientIds = { Constants.WEB_CLIENT_ID, Constants.API_EXPLORER_CLIENT_ID,Constants.ANDROID_CLIENT_ID }, 
		audiences = { Constants.ANDROID_AUDIENCE }, 
		description = "API for the Grocery Sync Backend application.")
public class SyncApi {
	
	private static final Logger log = Logger.getLogger(SyncApi.class.getName());
	
	// Declare this method as a method available externally through Endpoints
	@ApiMethod(name = "addGroceryList", path = "grocery", httpMethod = HttpMethod.POST)
	public WrappedBoolean addGroceryList( @Named("email") String email, @Named("code") String code, GroceryItem item) {
		Shopper shopper = null; 
		Key<Shopper> key = Key.create(Shopper.class, email+code); // TODO
	    shopper = (Shopper) OfyService.ofy().load().key(key).now(); 
		
        if ( shopper == null ) {
        	shopper = new Shopper( email, code );
        } 
	    // do this in a transaction 
      	Key<GroceryItem> result =  OfyService.ofy().save().entity(item).now();
       	shopper.addToShoppingList(result.toString());
        //shopper.
        OfyService.ofy().save().entities(shopper).now();
		
		return new WrappedBoolean(true);
	}

	@ApiMethod(name = "getGroceryList", path = "grocery", httpMethod = HttpMethod.GET)
	public List<GroceryItem> getGroceryList( @Named("email") String email, @Named("code") String code ) {
	
		Shopper shopper = null;
		List<GroceryItem> items = new ArrayList<>();
		Key<Shopper> key = Key.create(Shopper.class, email+code);
	    shopper = (Shopper) OfyService.ofy().load().key(key).now(); 
		
	    if ( shopper != null) {
	    	List<String> itemKeys = shopper.getItemsToBuy();
	    	Key<GroceryItem> itemKey = null; 
	    	GroceryItem groceryItem = null; 
	    	for ( String itemKeyStr : itemKeys ) {
	    		itemKey = Key.create(GroceryItem.class, itemKeyStr);
	    		groceryItem = (GroceryItem) OfyService.ofy().load().key(itemKey).now(); 
	    		items.add(groceryItem);
	    	}
	    }
		return items; 
	}
	
	/**
	 * Just a wrapper for Boolean. We need this wrapped Boolean because
	 * endpoints functions must return an object instance, they can't return a
	 * Type class such as String or Integer or Boolean
	 */
	public static class WrappedBoolean {

		private final Boolean result;
		private final String reason;

		public WrappedBoolean(Boolean result) {
			this.result = result;
			this.reason = "";
		}

		public WrappedBoolean(Boolean result, String reason) {
			this.result = result;
			this.reason = reason;
		}

		public Boolean getResult() {
			return result;
		}

		public String getReason() {
			return reason;
		}
	}


}

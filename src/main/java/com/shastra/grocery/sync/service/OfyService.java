package com.shastra.grocery.sync.service;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.shastra.grocery.sync.entities.GroceryItem;
import com.shastra.grocery.sync.entities.Shopper;

/**
 * Custom Objectify Service that this application should use.
 */
public class OfyService {
    /**
     * This static block ensure the entity registration.
     */
    static {
    	factory().register(GroceryItem.class);
    	factory().register(Shopper.class);
    }

    /**
     * Use this static method for getting the Objectify service object in order to make sure the
     * above static block is executed before using Objectify.
     * @return Objectify service object.
     */
    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    /**
     * Use this static method for getting the Objectify service factory.
     * @return ObjectifyFactory.
     */
    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}


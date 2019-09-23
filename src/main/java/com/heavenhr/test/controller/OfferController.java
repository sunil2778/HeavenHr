package com.heavenhr.test.controller;

import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.heavenhr.test.exception.DuplicateEntryException;
import com.heavenhr.test.exception.ResourceNotFoundException;
import com.heavenhr.test.model.Application;
import com.heavenhr.test.model.Offer;
import com.heavenhr.test.repository.ApplicationRepository;
import com.heavenhr.test.repository.OfferRepository;


@RestController
@RequestMapping("api/offers") // offers endpoint
public class OfferController {
	 @Autowired 
	    private OfferRepository offers;
	    
	    @Autowired
	    private ApplicationRepository applications;
	    
	    @PostMapping() // Validates the request body as a Lecturer type
	    public Offer createOffer(@Valid @RequestBody Offer offer){
	        // Saves and return the new Offer
	    	Offer offer1=this.offers.save(offer);
	    	if(offer1==null)
	    	{
	    		throw new DuplicateEntryException(offer.getJobTitle() +"already exist in database");
	    		
	    	}
	        return offer1;
	    }
	    
	    @GetMapping() // Finds all stored Offers in a pageable format
	    public Page<Offer> getOffers(Pageable pageable){
	        return this.offers.findAll(pageable);
	    }
	    
	    @GetMapping("/{id}") // Finds a offer by id (the variable must be wrapped by "{}" and match the @PathVariable name
	    public Offer getOffers(@PathVariable Long id){
	        // If the record exists by id return it, otherwise throw an exception
	        return this.offers.findById(id).orElseThrow(
	                () -> new ResourceNotFoundException("offer", id)
	        );
	    }
	    
	    @PutMapping() // Validates the request body as a Lecturer type
	    public Offer updateOffer(@Valid @RequestBody Offer offer){
	        // Finds offer by id, maps it's content, update new values and save. Throws an exception if not found.
	        return this.offers.findById(offer.getId()).map((toUpdate) -> {
	            toUpdate.setJobTitle(offer.getJobTitle());
	            toUpdate.setOffer(offer.getOffer());
	            
	            return this.offers.save(toUpdate);
	        }).orElseThrow(() -> new ResourceNotFoundException("offer", offer.getId()));
	    }
	    
	    @DeleteMapping("/{id}") // Finds lecturer by id
	    public ResponseEntity deleteOffer(@PathVariable Long id){
	        // If id exists, delete the record and return a response message, otherwise throws exception
	        return this.offers.findById(id).map((toDelete) -> {
	            this.offers.delete(toDelete);
	            return ResponseEntity.ok("offer id " + id + " deleted");
	        }).orElseThrow(() -> new ResourceNotFoundException("offer", id));
	    }
	    
	    @GetMapping("/{offerId}/applications")
	    public Set<Application> getApplications(@PathVariable Long offerId){
	        // Finds offer by id and returns it's recorded applications, otherwise throws exception 
	        return this.offers.findById(offerId).map((offer) -> {
	            return offer.getApplications();
	        }).orElseThrow(() -> new ResourceNotFoundException("offer", offerId));
	    }
	    
	    @PostMapping("/{id}/applications/{applicationId}") // Path variable names must match with method's signature variables.
	    public Set<Application> addApplication(@PathVariable Long id, @PathVariable Long applicationId){
	        // Finds a persisted application
	    	Application application = this.applications.findById(applicationId).orElseThrow(
	                () -> new ResourceNotFoundException("Application", applicationId)
	        );
	        
	        // Finds a offer and adds the given application to the offer's set.
	        return this.offers.findById(id).map((offer) -> {
	        	offer.getApplications().add(application);
	            return this.offers.save(offer).getApplications(); 
	        }).orElseThrow(() -> new ResourceNotFoundException("offer", id));
	    }
	    

}

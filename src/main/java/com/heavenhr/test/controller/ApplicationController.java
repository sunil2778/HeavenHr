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
@RequestMapping("/api/applications") // applications endpoint
public class ApplicationController {
	@Autowired
    private ApplicationRepository applications;
    
    @Autowired
    private OfferRepository offers;
    
    @PostMapping() // Validates the request body as a Application type
    public Application createApplication(@Valid @RequestBody Application application) throws DuplicateEntryException{
        // Saves and return the new application
    	
        return this.applications.save(application);
    }
    
    @GetMapping("/{id}") // Finds a application by id (the variable must be wrapped by "{}" and match the @PathVariable name
    public Application getApplication(@PathVariable Long id){
        // If the record exists by id return it, otherwise throw an exception
        return this.applications.findById(id).orElseThrow(() -> 
                new ResourceNotFoundException("application", id)
        );
    }
    
    @GetMapping() // Finds all stored applications in a pageable format
    public Page<Application> getApplications(Pageable pageable){
        return this.applications.findAll(pageable);
    }
    
    @PutMapping() // Validates the request body as a application type
    public Application updateApplication(@Valid @RequestBody Application application){
        // Finds student by id, maps it's content, updates new values and save. Throws an exception if not found.
        return this.applications.findById(application.getId()).map((toUpdate) -> {
            toUpdate.setEmail(application.getEmail());
            toUpdate.setResumeText(application.getResumeText());
            toUpdate.setStatus(application.getStatus());
            return this.applications.save(toUpdate);
        }).orElseThrow(() -> new ResourceNotFoundException("Application", application.getId()));
    }
    
    @DeleteMapping("/{id}") // Find application by id
    public ResponseEntity deleteApplication(@PathVariable Long id){
        // If id exists, delete the record and return a response message, otherwise throws exception
        return this.applications.findById(id).map((toDelete) -> {
            this.applications.delete(toDelete);
            return ResponseEntity.ok("Application id " + id + " deleted");
        }).orElseThrow(() -> new ResourceNotFoundException("Application", id));
    }
    
    @GetMapping("/{id}/offers")
    public Set<Offer> getOffers(@PathVariable Long id){
        // Finds application by id and returns it's recorded offers, otherwise throws exception
        return this.applications.findById(id).map((application) -> {
            return application.getOffers();
        }).orElseThrow(() -> new ResourceNotFoundException("Application", id));
    }
    

}

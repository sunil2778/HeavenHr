package com.heavenhr.test.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table
(name = "applications",uniqueConstraints = {@UniqueConstraint(columnNames ="email" )})
public class Application {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @JsonIgnore
	private Long id;
	 @Column(nullable = false)
	private String email;
	 @Column(nullable = false)
	private String resumeText;
	 @Column(nullable = false)
	private String status;
	 
	 @ManyToMany(
	            fetch = FetchType.LAZY,
	            cascade = {CascadeType.MERGE, CascadeType.PERSIST}, 
	            mappedBy = "applications"
	    )
	    @OnDelete(action = OnDeleteAction.CASCADE)
	    @JsonIgnore
	    private Set<Offer> offers = new HashSet<>();

	public Application(String email, String resumeText, String status) {
		super();
		this.email = email;
		this.resumeText = resumeText;
		this.status = status;
	}

	public Application() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getResumeText() {
		return resumeText;
	}

	public void setResumeText(String resumeText) {
		this.resumeText = resumeText;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set<Offer> getOffers() {
		return offers;
	}

	public void setOffers(Set<Offer> offers) {
		this.offers = offers;
	}

}

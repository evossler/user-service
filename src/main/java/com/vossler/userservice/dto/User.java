package com.vossler.userservice.dto;

import java.util.LinkedHashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class User {

	private String id;
	private String city;
	private String company;
	private String country;
	private String firstName;
	private String lastName;
	private String organizationType;
	private String phone;
	private String state;
	private String zipCode;
	private Boolean disclaimerAccepted;
	private String languageCode;
	private String emailAddress;
	private String registrationId;
	private String registrationIdGeneratedTime;
	
	private Set<String> projectIds = new LinkedHashSet<>();

	public String getId() {
		return id;
	}

	public User setId(String id) {
		this.id = id;
		return this;
	}

	public String getCity() {
		return city;
	}

	public User setCity(String city) {
		this.city = city;
		return this;
	}

	public String getCompany() {
		return company;
	}

	public User setCompany(String company) {
		this.company = company;
		return this;
	}

	public String getCountry() {
		return country;
	}

	public User setCountry(String country) {
		this.country = country;
		return this;
	}

	public String getFirstName() {
		return firstName;
	}

	public User setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public User setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public String getOrganizationType() {
		return organizationType;
	}

	public User setOrganizationType(String organizationType) {
		this.organizationType = organizationType;
		return this;
	}

	public String getPhone() {
		return phone;
	}

	public User setPhone(String phone) {
		this.phone = phone;
		return this;
	}

	public String getState() {
		return state;
	}

	public User setState(String state) {
		this.state = state;
		return this;
	}

	public String getZipCode() {
		return zipCode;
	}

	public User setZipCode(String zipCode) {
		this.zipCode = zipCode;
		return this;
	}

	public Boolean getDisclaimerAccepted() {
		return disclaimerAccepted;
	}

	public User setDisclaimerAccepted(Boolean disclaimerAccepted) {
		this.disclaimerAccepted = disclaimerAccepted;
		return this;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public User setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
		return this;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public User setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
		return this;
	}

	public String getRegistrationId() {
		return registrationId;
	}

	public User setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
		return this;
	}

	public String getRegistrationIdGeneratedTime() {
		return registrationIdGeneratedTime;
	}

	public User setRegistrationIdGeneratedTime(String registrationIdGeneratedTime) {
		this.registrationIdGeneratedTime = registrationIdGeneratedTime;
		return this;
	}
	
	public Set<String> getProjectIds() {
		return projectIds;
	}

	public User setProjectIds(Set<String> projectIds) {
		this.projectIds = projectIds;
		return this;
	}
	
	@JsonIgnore
	public User addProjectId(String projectId) {
		this.projectIds.add(projectId);
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((disclaimerAccepted == null) ? 0 : disclaimerAccepted.hashCode());
		result = prime * result + ((emailAddress == null) ? 0 : emailAddress.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((languageCode == null) ? 0 : languageCode.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((organizationType == null) ? 0 : organizationType.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((projectIds == null) ? 0 : projectIds.hashCode());
		result = prime * result + ((registrationId == null) ? 0 : registrationId.hashCode());
		result = prime * result + ((registrationIdGeneratedTime == null) ? 0 : registrationIdGeneratedTime.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((zipCode == null) ? 0 : zipCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (disclaimerAccepted == null) {
			if (other.disclaimerAccepted != null)
				return false;
		} else if (!disclaimerAccepted.equals(other.disclaimerAccepted))
			return false;
		if (emailAddress == null) {
			if (other.emailAddress != null)
				return false;
		} else if (!emailAddress.equals(other.emailAddress))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (languageCode == null) {
			if (other.languageCode != null)
				return false;
		} else if (!languageCode.equals(other.languageCode))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (organizationType == null) {
			if (other.organizationType != null)
				return false;
		} else if (!organizationType.equals(other.organizationType))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (projectIds == null) {
			if (other.projectIds != null)
				return false;
		} else if (!projectIds.equals(other.projectIds))
			return false;
		if (registrationId == null) {
			if (other.registrationId != null)
				return false;
		} else if (!registrationId.equals(other.registrationId))
			return false;
		if (registrationIdGeneratedTime == null) {
			if (other.registrationIdGeneratedTime != null)
				return false;
		} else if (!registrationIdGeneratedTime.equals(other.registrationIdGeneratedTime))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (zipCode == null) {
			if (other.zipCode != null)
				return false;
		} else if (!zipCode.equals(other.zipCode))
			return false;
		return true;
	}
}

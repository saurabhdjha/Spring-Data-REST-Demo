package com.demo.dto;

import java.time.LocalDate;
import java.util.List;

public class CustomerDTO {

	private Integer customerId;
	private String name;
	private String phoneNo;
	private String emailId;
	private LocalDate birthDate;
	private String password;
	private AddressDTO address;
	private List<CardDTO> cards;
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public LocalDate getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public AddressDTO getAddress() {
		return address;
	}
	public void setAddress(AddressDTO address) {
		this.address = address;
	}
	public List<CardDTO> getCards() {
		return cards;
	}
	public void setCards(List<CardDTO> cards) {
		this.cards = cards;
	}

}

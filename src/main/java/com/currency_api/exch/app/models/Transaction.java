package com.currency_api.exch.app.models;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="transactions")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Double amountToChange;
	private Double amountChanged;
	
	@DateTimeFormat
	private Date createAt;
	
	@ManyToOne
	@JoinColumn(name = "exchange_id")
	private ExchangeType exchangeType;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	@JsonIgnoreProperties(value = "transactions")
	private User user;
	
	public Transaction(Double amountToChange, Double amountChanged, ExchangeType exchangeType, User user) {
		this.amountToChange = amountToChange;
		this.amountChanged = amountChanged;
		this.exchangeType = exchangeType;
		this.user = user;
	}
	
	public Transaction() {}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getAmountToChange() {
		return amountToChange;
	}
	public void setAmountToChange(Double amountToChange) {
		this.amountToChange = amountToChange;
	}
	public Double getAmountChanged() {
		return amountChanged;
	}
	public void setAmountChanged(Double amountChanged) {
		this.amountChanged = amountChanged;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	public ExchangeType getExchangeType() {
		return exchangeType;
	}
	public void setExchangeType(ExchangeType exchangeType) {
		this.exchangeType = exchangeType;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}

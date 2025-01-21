package com.currency_api.exch.app.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="exchange_types")
public class ExchangeType {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String sourceCurrency;
	private String finalCurrency;
	private Double conversion;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSourceCurrency() {
		return sourceCurrency;
	}
	public void setSourceCurrency(String sourceCurrency) {
		this.sourceCurrency = sourceCurrency;
	}
	public String getFinalCurrency() {
		return finalCurrency;
	}
	public void setFinalCurrency(String finalCurrency) {
		this.finalCurrency = finalCurrency;
	}
	public Double getConversion() {
		return conversion;
	}
	public void setConversion(Double conversion) {
		this.conversion = conversion;
	}
	
}

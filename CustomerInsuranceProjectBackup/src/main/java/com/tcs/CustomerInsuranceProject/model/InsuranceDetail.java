package com.tcs.CustomerInsuranceProject.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="InsuranceDetail")
public class InsuranceDetail {
	@Id
	private String insuranceId;
	private String insuranceType;
	private String insuranceCategory;
	private int coveragePeriod;
	private double amount;
	
	public InsuranceDetail() {
		super();
	}

	public InsuranceDetail(int coveragePeriod, double amount) {
		super();
		this.coveragePeriod = coveragePeriod;
		this.amount = amount;
	}
	
	public InsuranceDetail(String insuranceId, String insuranceType, String insuranceCategory, int coveragePeriod,
			double amount) {
		super();
		this.insuranceId = insuranceId;
		this.insuranceType = insuranceType;
		this.insuranceCategory = insuranceCategory;
		this.coveragePeriod = coveragePeriod;
		this.amount = amount;
	}

	public String getInsuranceId() {
		return insuranceId;
	}
	public void setInsuranceId(String insuranceId) {
		this.insuranceId = insuranceId;
	}
	public String getInsuranceType() {
		return insuranceType;
	}
	public void setInsuranceType(String insuranceType) {
		this.insuranceType = insuranceType;
	}
	public String getInsuranceCategory() {
		return insuranceCategory;
	}
	public void setInsuranceCategory(String insuranceCategory) {
		this.insuranceCategory = insuranceCategory;
	}
	public int getCoveragePeriod() {
		return coveragePeriod;
	}
	public void setCoveragePeriod(int coveragePeriod) {
		this.coveragePeriod = coveragePeriod;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	
}

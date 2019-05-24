package com.tcs.CustomerInsuranceProject.model;

public class CustomerInsurance {
	private String insuranceId;
	private String insuranceType;
	private String insuranceCategory;
	private int coveragePeriod;
	private double amount;
	private String nominee;
	private String nomineeRelation;
	
	
	public CustomerInsurance() {
		super();
	}
	public CustomerInsurance(String insuranceId, String insuranceType, String insuranceCategory, int coveragePeriod,
			double amount, String nominee, String nomineeRelation) {
		super();
		this.insuranceId = insuranceId;
		this.insuranceType = insuranceType;
		this.insuranceCategory = insuranceCategory;
		this.coveragePeriod = coveragePeriod;
		this.amount = amount;
		this.nominee = nominee;
		this.nomineeRelation = nomineeRelation;
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
	public String getNominee() {
		return nominee;
	}
	public void setNominee(String nominee) {
		this.nominee = nominee;
	}
	public String getNomineeRelation() {
		return nomineeRelation;
	}
	public void setNomineeRelation(String nomineeRelation) {
		this.nomineeRelation = nomineeRelation;
	}
	
	
}

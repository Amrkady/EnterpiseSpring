package com.models;



public class CustomerModel {
	
	private Integer customerId;


	private Integer natNo;
	
	private String customerName;

	
	private String address;

	
	private String phone;

	
	private String notes;
	

	private Integer deptId;

	private String deptName;


	private String deptManager;


	public Integer getCustomerId() {
		return customerId;
	}


	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}


	public Integer getNatNo() {
		return natNo;
	}


	public void setNatNo(Integer natNo) {
		this.natNo = natNo;
	}


	public String getCustomerName() {
		return customerName;
	}


	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getNotes() {
		return notes;
	}


	public void setNotes(String notes) {
		this.notes = notes;
	}


	public Integer getDeptId() {
		return deptId;
	}


	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}


	public String getDeptName() {
		return deptName;
	}


	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}


	public String getDeptManager() {
		return deptManager;
	}


	public void setDeptManager(String deptManager) {
		this.deptManager = deptManager;
	}



}

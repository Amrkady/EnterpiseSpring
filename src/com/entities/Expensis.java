package com.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "expensis")
public class Expensis {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "id")
	private Integer id;

	@Column(name = "expensis_type")
	private Integer expensisType;

	@Column(name = "company_id")
	private Integer companyId;

	@Column(name = "expensis_quantity")
	private double expensisQuantity;

	@Column(name = "month_mdate")
	private Date monthDate;

	@Column(name = "month_hdate")
	private String monthhDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getExpensisQuantity() {
		return expensisQuantity;
	}

	public void setExpensisQuantity(double expensisQuantity) {
		this.expensisQuantity = expensisQuantity;
	}

	public Integer getExpensisType() {
		return expensisType;
	}

	public void setExpensisType(Integer expensisType) {
		this.expensisType = expensisType;
	}

	public Date getMonthDate() {
		return monthDate;
	}

	public void setMonthDate(Date monthDate) {
		this.monthDate = monthDate;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getMonthhDate() {
		return monthhDate;
	}

	public void setMonthhDate(String monthhDate) {
		this.monthhDate = monthhDate;
	}

}

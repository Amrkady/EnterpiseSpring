package com.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "contracts")
public class Contracts {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "id")
	private Integer id;

	@Column(name = "startmDate")
	private Date startmDate;

	@Column(name = "starthDate")
	private String starthDate;

	@Column(name = "endmDate")
	private Date endmDate;

	@Column(name = "endhDate")
	private String endhDate;

	@Column(name = "lastbillNo")
	private Long lastbillNo;

	@Column(name = "hourNo")
	private double hourNo;

	@Column(name = "con_amount")
	private double conAmount;

	@Column(name = "rest_amount")
	private double restAmount;

	@Column(name = "company_Id")
	private Integer companyId;

	@Formula("(select s.name from companies s where s.id = company_Id)")
	private String comName;

	@Column(name = "workType")
	private Integer workType;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getStartmDate() {
		return startmDate;
	}

	public void setStartmDate(Date startmDate) {
		this.startmDate = startmDate;
	}

	public String getStarthDate() {
		return starthDate;
	}

	public void setStarthDate(String starthDate) {
		this.starthDate = starthDate;
	}

	public Date getEndmDate() {
		return endmDate;
	}

	public void setEndmDate(Date endmDate) {
		this.endmDate = endmDate;
	}

	public String getEndhDate() {
		return endhDate;
	}

	public void setEndhDate(String endhDate) {
		this.endhDate = endhDate;
	}

	public Long getLastbillNo() {
		return lastbillNo;
	}

	public void setLastbillNo(Long lastbillNo) {
		this.lastbillNo = lastbillNo;
	}

	public double getHourNo() {
		return hourNo;
	}

	public void setHourNo(double hourNo) {
		this.hourNo = hourNo;
	}

	public double getConAmount() {
		return conAmount;
	}

	public void setConAmount(double conAmount) {
		this.conAmount = conAmount;
	}

	public double getRestAmount() {
		return restAmount;
	}

	public void setRestAmount(double restAmount) {
		this.restAmount = restAmount;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getComName() {
		return comName;
	}

	public void setComName(String comName) {
		this.comName = comName;
	}

	public Integer getWorkType() {
		return workType;
	}

	public void setWorkType(Integer workType) {
		this.workType = workType;
	}

}

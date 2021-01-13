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
@Table(name = "snd_srf_qbd")
public class SndSrfQbd {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "id")
	private Integer id;

	// 1 srf // 2 qabd
	@Column(name = "snd_type")
	private Integer sndType;

	@Column(name = "tax")
	private Integer tax;

	@Column(name = "company_Id")
	private Integer companyId;

	@Column(name = "enterprise_Id")
	private Integer enterpriseId;

	@Column(name = "expensis_types_id")
	private Integer expensisTypesId;

	@Column(name = "snd_mdate")
	private Date sndDate;

	@Column(name = "snd_hdate")
	private String monthhDate;

	@Column(name = "pay_type")
	private String payType;

	@Column(name = "name")
	private String name;

	@Column(name = "amount")
	private double amount;

	@Column(name = "for_reason")
	private String forReason;

	@Formula("(select g.name from expensis_types g where g.id = expensis_types_id)")
	private String expensisTypesName;

	@Formula("(select s.name from companies s where s.id = company_Id)")
	private String comName;

	@Formula("(select s.name from enterprise s where s.id = enterprise_Id)")
	private String entName;

	@Column(name = "taxAmoun")
	private Double taxAmoun;

	@Column(name = "billNo")
	private String billNo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSndType() {
		return sndType;
	}

	public void setSndType(Integer sndType) {
		this.sndType = sndType;
	}

	public Integer getExpensisTypesId() {
		return expensisTypesId;
	}

	public void setExpensisTypesId(Integer expensisTypesId) {
		this.expensisTypesId = expensisTypesId;
	}

	public Date getSndDate() {
		return sndDate;
	}

	public void setSndDate(Date sndDate) {
		this.sndDate = sndDate;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getForReason() {
		return forReason;
	}

	public void setForReason(String forReason) {
		this.forReason = forReason;
	}

	public String getExpensisTypesName() {
		return expensisTypesName;
	}

	public void setExpensisTypesName(String expensisTypesName) {
		this.expensisTypesName = expensisTypesName;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getComName() {
		return comName;
	}

	public void setComName(String comName) {
		this.comName = comName;
	}

	public String getEntName() {
		return entName;
	}

	public void setEntName(String entName) {
		this.entName = entName;
	}

	public Integer getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public Integer getTax() {
		return tax;
	}

	public void setTax(Integer tax) {
		this.tax = tax;
	}

	public Double getTaxAmoun() {
		return taxAmoun;
	}

	public void setTaxAmoun(Double taxAmoun) {
		this.taxAmoun = taxAmoun;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

}

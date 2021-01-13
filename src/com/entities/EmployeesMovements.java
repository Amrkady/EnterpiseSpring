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
@Table(name = "employees_movements")
public class EmployeesMovements {

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

	@Column(name = "moveType")
	private String moveType;

	@Formula("(select s.name from companies s where s.id = typeId)")
	private String comName;

	// companyId or vacationId
	@Column(name = "typeId")
	private Integer typeId;

	@Column(name = "contractId")
	private Integer contractId;

	@Column(name = "empId")
	private Integer empId;

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

	public String getMoveType() {
		return moveType;
	}

	public void setMoveType(String moveType) {
		this.moveType = moveType;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Integer getContractId() {
		return contractId;
	}

	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public String getComName() {
		return comName;
	}

	public void setComName(String comName) {
		this.comName = comName;
	}

}

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
@Table(name = "vacations")
public class Vacations {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "id")
	private Integer id;

	@Column(name = "dayNo")
	private Integer dayNo;

	@Column(name = "emp_id")
	private Integer empId;

	@Column(name = "vac_mDate")
	private Date vacmDate;

	@Column(name = "vac_hDate")
	private String vachDate;

	@Column(name = "vac_endmDate")
	private Date vacEndmDate;

	@Column(name = "vac_endhDate")
	private String vacEndhDate;

	@Formula("(select s.name from employees s where s.id = emp_id)")
	private String empName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDayNo() {
		return dayNo;
	}

	public void setDayNo(Integer dayNo) {
		this.dayNo = dayNo;
	}

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public Date getVacmDate() {
		return vacmDate;
	}

	public void setVacmDate(Date vacmDate) {
		this.vacmDate = vacmDate;
	}

	public String getVachDate() {
		return vachDate;
	}

	public void setVachDate(String vachDate) {
		this.vachDate = vachDate;
	}

	public Date getVacEndmDate() {
		return vacEndmDate;
	}

	public void setVacEndmDate(Date vacEndmDate) {
		this.vacEndmDate = vacEndmDate;
	}

	public String getVacEndhDate() {
		return vacEndhDate;
	}

	public void setVacEndhDate(String vacEndhDate) {
		this.vacEndhDate = vacEndhDate;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

}

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
@Table(name = "expensis")
public class Expensis {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "id")
	private Integer id;

	@Column(name = "expensis_type")
	private Integer expensisType;

	@Column(name = "station_id")
	private Integer stationId;

	@Column(name = "expensis_quantity")
	private double expensisQuantity;

	@Column(name = "month_date")
	private Date monthDate;

	@Formula("(select et.name from expensis_types et where et.id = expensis_type)")
	private String expensisTypeName;

	@Formula("(select s.station_name from stations s where s.id = station_id)")
	private String stationName;

	@Column(name = "asoulId")
	private Integer asoulId;

	@Formula("(select s.name from Constantsasoul s where s.id = asoulId)")
	private String asoulName;

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

	public Integer getStationId() {
		return stationId;
	}

	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}

	public String getExpensisTypeName() {
		return expensisTypeName;
	}

	public void setExpensisTypeName(String expensisTypeName) {
		this.expensisTypeName = expensisTypeName;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public Integer getAsoulId() {
		return asoulId;
	}

	public void setAsoulId(Integer asoulId) {
		this.asoulId = asoulId;
	}

	public String getAsoulName() {
		return asoulName;
	}

	public void setAsoulName(String asoulName) {
		this.asoulName = asoulName;
	}

}

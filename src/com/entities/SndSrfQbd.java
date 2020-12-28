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

	@Column(name = "snd_type")
	private Integer sndType;

	@Column(name = "station_id")
	private Integer stationId;

	@Column(name = "expensis_types_id")
	private Integer expensisTypesId;

	@Column(name = "snd_date")
	private Date sndDate;

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

	public Integer getSndType() {
		return sndType;
	}

	public void setSndType(Integer sndType) {
		this.sndType = sndType;
	}

	public Integer getStationId() {
		return stationId;
	}

	public void setStationId(Integer stationId) {
		this.stationId = stationId;
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

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

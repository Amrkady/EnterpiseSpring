package com.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "expensis_types")
public class ExpensisTypes {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "id")
	private Integer Id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "is_general")
	private Integer general;

	@Transient
	private boolean generalType;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getGeneral() {
		return general;
	}

	public void setGeneral(Integer general) {
		this.general = general;
	}

	public boolean isGeneralType() {
		return generalType;
	}

	public void setGeneralType(boolean generalType) {
		this.generalType = generalType;
	}

}

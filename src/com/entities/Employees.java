package com.entities;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "employees")
public class Employees {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "id")
	private Integer id;

	@Column(name = "empNo")
	private Integer empNo;

	@Column(name = "NATIONALITY", nullable = false)
	private String nationality;

	@Column(name = "iqama_NO", nullable = false)
	private String iqamaNO;

	@Column(name = "name")
	private String name;

	@Column(name = "ENTRANCE_mDATE")
	private Date enterancemDate;

	@Column(name = "ENTRANCE_hdate")
	private String enterancehDate;

	@Column(name = "bankNo")
	private Long bankNo;

	@Column(name = "work_location")
	private String workLocation;

	@Column(name = "work_startmDate")
	private Date workStartmDate;

	@Column(name = "work_starthDate")
	private String workStarthDate;

	@Column(name = "birthmDate")
	private Date birthmDate;

	@Column(name = "birthhDate")
	private String birthhDate;

	@Column(name = "iqamaEndmDate")
	private Date iqamaEndmDate;

	@Column(name = "iqamaEndhDate")
	private String iqamaEndhDate;

	@Column(name = "salary")
	private double salary;

	@Column(name = "enterpriseId")
	private Integer enterpriseId;

	@Column(name = "active")
	private String active;

	@Formula("(select s.name from enterprise s where s.id = enterpriseId)")
	private String entName;

	@Transient
	private String iqamaStatus;
	@Transient
	private int colorStatus;

	@Transient
	private double hourNoAdd;

	@Transient
	private double totalHour;

	@Transient
	private double priceHourNoAdd;

	@Transient
	private double contractHourNo;

	@Transient
	private double hourMinus;

	@Transient
	private double priceAbsentDays;

	@Transient
	private double totalSalary;

	@Transient
	private double monthSalary;

	@Transient
	private double bonus;

	@Transient
	private double hourPrice;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getIqamaNO() {
		return iqamaNO;
	}

	public void setIqamaNO(String iqamaNO) {
		this.iqamaNO = iqamaNO;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getEnterancemDate() {
		return enterancemDate;
	}

	public void setEnterancemDate(Date enterancemDate) {
		this.enterancemDate = enterancemDate;
	}

	public Long getBankNo() {
		return bankNo;
	}

	public void setBankNo(Long bankNo) {
		this.bankNo = bankNo;
	}

	public String getWorkLocation() {
		return workLocation;
	}

	public void setWorkLocation(String workLocation) {
		this.workLocation = workLocation;
	}

	public Date getWorkStartmDate() {
		return workStartmDate;
	}

	public void setWorkStartmDate(Date workStartmDate) {
		this.workStartmDate = workStartmDate;
	}

	public String getWorkStarthDate() {
		return workStarthDate;
	}

	public void setWorkStarthDate(String workStarthDate) {
		this.workStarthDate = workStarthDate;
	}

	public Date getBirthmDate() {
		return birthmDate;
	}

	public void setBirthmDate(Date birthmDate) {
		this.birthmDate = birthmDate;
	}

	public String getBirthhDate() {
		return birthhDate;
	}

	public void setBirthhDate(String birthhDate) {
		this.birthhDate = birthhDate;
	}

	public Date getIqamaEndmDate() {
		return iqamaEndmDate;
	}

	public void setIqamaEndmDate(Date iqamaEndmDate) {
		this.iqamaEndmDate = iqamaEndmDate;
	}

	public String getIqamaEndhDate() {
		return iqamaEndhDate;
	}

	public void setIqamaEndhDate(String iqamaEndhDate) {
		this.iqamaEndhDate = iqamaEndhDate;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public Integer getEmpNo() {
		return empNo;
	}

	public void setEmpNo(Integer empNo) {
		this.empNo = empNo;
	}

	public String getEnterancehDate() {
		return enterancehDate;
	}

	public void setEnterancehDate(String enterancehDate) {
		this.enterancehDate = enterancehDate;
	}

	public void iqamaStatus() {
		Date currentDate = iqamaEndmDate;
		LocalDateTime localDateTime = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		localDateTime = localDateTime.minusMonths(1);
		Date beforMonth = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		if (iqamaEndmDate != null) {

			if (iqamaEndmDate.before(new Date())) {
				this.iqamaStatus = "„‰ ÂÌ…";
				colorStatus = 1;
			} else {
				if (beforMonth.before(new Date())) {
					this.iqamaStatus = "ﬁ«—»  «·«‰ Â«¡";
					colorStatus = 2;
				} else {
					this.iqamaStatus = "”«—Ì…";
					colorStatus = 3;
				}
			}

		}
	}
//	if (cont.getEndDate() != null && cont.getEndDate() != null
//			&& Utils.convertHDateToGDate(cont.getEndDate()).before(new Date())) {
//		cont.getActionsList().add(ContractOperationEnum.RENEW);
//		cont.setFinished(1);
//	} else {
//		cont.setFinished(0);
//		if (cont.getStatus() != ContractOperationEnum.CANCEL.getAction())
//			cont.getActionsList().add(ContractOperationEnum.CANCEL);
//	}

	public String getIqamaStatus() {
		iqamaStatus();
		return iqamaStatus;
	}

	public void setIqamaStatus(String iqamaStatus) {
		this.iqamaStatus = iqamaStatus;
	}

	public int getColorStatus() {
		// iqamaStatus();
		return colorStatus;
	}

	public void setColorStatus(int colorStatus) {
		this.colorStatus = colorStatus;
	}

	public Integer getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getEntName() {
		return entName;
	}

	public void setEntName(String entName) {
		this.entName = entName;
	}

	public double getHourNoAdd() {
		return hourNoAdd;
	}

	public void setHourNoAdd(double hourNoAdd) {
		this.hourNoAdd = hourNoAdd;
	}

	public double getHourMinus() {
		return hourMinus;
	}

	public void setHourMinus(double hourMinus) {
		this.hourMinus = hourMinus;
	}

	public double getTotalSalary() {
		if (totalSalary == 0) {
			totalSalary = salary;
		}
		return totalSalary;
	}

	public void setTotalSalary(double totalSalary) {
		this.totalSalary = totalSalary;
	}

	public double getBonus() {
		return bonus;
	}

	public void setBonus(double bonus) {
		this.bonus = bonus;
	}

	public double getMonthSalary() {
		return monthSalary;
	}

	public void setMonthSalary(double monthSalary) {
		this.monthSalary = monthSalary;
	}

	public double getPriceHourNoAdd() {
		return priceHourNoAdd;
	}

	public void setPriceHourNoAdd(double priceHourNoAdd) {
		this.priceHourNoAdd = priceHourNoAdd;
	}

	public double getContractHourNo() {
		return contractHourNo;
	}

	public void setContractHourNo(double contractHourNo) {
		this.contractHourNo = contractHourNo;
	}

	public double getPriceAbsentDays() {
		return priceAbsentDays;
	}

	public void setPriceAbsentDays(double priceAbsentDays) {
		this.priceAbsentDays = priceAbsentDays;
	}

	public double getHourPrice() {
		return hourPrice;
	}

	public void setHourPrice(double hourPrice) {
		this.hourPrice = hourPrice;
	}

	public double getTotalHour() {
		return totalHour;
	}

	public void setTotalHour(double totalHour) {
		this.totalHour = totalHour;
	}
}

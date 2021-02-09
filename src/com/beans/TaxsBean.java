package com.beans;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.entities.Enterprise;
import com.entities.ExpensisTypes;
import com.entities.SndSrfQbd;
import com.services.DepartmentService;

import common.util.MsgEntry;
import common.util.Utils;

@ManagedBean(name = "taxsBean")
@ViewScoped
public class TaxsBean {
	@ManagedProperty(value = "#{departmentServiceImpl}")
	private DepartmentService departmentServiceImpl;
	private List<SndSrfQbd> expensisList = new ArrayList<SndSrfQbd>();
	private List<ExpensisTypes> expensisTypesList = new ArrayList<ExpensisTypes>();
	private SndSrfQbd expensisAdd = new SndSrfQbd();
	private boolean dateBoolean;
	private boolean dateAddBoolean;
	private boolean vat;
	private double taxValue;
	private double totalValue;
	private Integer supType;
	private List<Enterprise> enterpriseList = new ArrayList<Enterprise>();
	private Integer enterpriseId;
	private double listTotalSum;
	private BigDecimal listTotalSumDecimal;
	private double totalRevTaxs;
	private BigDecimal totalRevTaxsDecimal;
	private double totalDiff;
	private BigDecimal totalDiffDecimal;
	private Date dateFrom;
	private Date dateTo;
	private boolean vatDisabled;

	@PostConstruct
	public void init() {
		listTotalSum = 0.0d;
		listTotalSumDecimal = new BigDecimal(0);
		totalRevTaxs = 0.0d;
		totalRevTaxsDecimal = new BigDecimal(0);
		totalDiff = 0.0d;
		totalDiffDecimal = new BigDecimal(0);
		List ls = departmentServiceImpl.findAll(SndSrfQbd.class);
		expensisList = ls;
		expensisList = expensisList.stream().filter(fdet -> fdet.getSndType() != 3 && fdet.getTax() == 1)
				.collect(Collectors.toList());
		expensisTypesList = departmentServiceImpl.loadExpTypes();
		ls = departmentServiceImpl.findAll(Enterprise.class);
		enterpriseList = ls;
		if (expensisList != null && expensisList.size() > 0) {
			listTotalSum = expensisList.stream()
					.filter(fdet -> fdet.getTax() == 1 && (fdet.getSndType() == 1 || fdet.getSndType() == 4))
					.mapToDouble(fdet -> fdet.getTaxAmoun()).sum();

			listTotalSumDecimal = new BigDecimal(listTotalSum).setScale(2, RoundingMode.HALF_UP);
			totalRevTaxs = expensisList.stream().filter(fdet -> fdet.getTax() == 1 && fdet.getSndType() == 2)
					.mapToDouble(fdet -> fdet.getTaxAmoun()).sum();
			totalRevTaxsDecimal = new BigDecimal(totalRevTaxs).setScale(2, RoundingMode.HALF_UP);
			totalDiff = totalRevTaxs - listTotalSum;
			totalDiffDecimal = new BigDecimal(totalDiff).setScale(2, RoundingMode.HALF_UP);
		}

	}

	public String filter() {
		listTotalSum = 0.0d;
		listTotalSumDecimal = new BigDecimal(0);
		totalRevTaxs = 0.0d;
		totalRevTaxsDecimal = new BigDecimal(0);
		totalDiff = 0.0d;
		totalDiffDecimal = new BigDecimal(0);
		List ls = departmentServiceImpl.findAll(SndSrfQbd.class);
		expensisList = ls;
		expensisList = expensisList.stream().filter(fdet -> fdet.getSndType() != 3 && fdet.getTax() == 1)
				.collect(Collectors.toList());
		if (dateFrom != null || dateTo != null) {
			expensisList = new ArrayList<SndSrfQbd>();
			List<SndSrfQbd> valList = new ArrayList<SndSrfQbd>();
			valList = departmentServiceImpl.LoadAllSands(dateFrom, dateTo, 1);
			if (valList != null && valList.size() > 0) {
				expensisList.addAll(valList);
			}

			valList = departmentServiceImpl.LoadAllSands(dateFrom, dateTo, 2);
			if (valList != null && valList.size() > 0) {
				expensisList.addAll(valList);
			}

			valList = departmentServiceImpl.LoadAllSands(dateFrom, dateTo, 4);
			if (valList != null && valList.size() > 0) {
				expensisList.addAll(valList);
			}
			expensisList = expensisList.stream().filter(fdet -> fdet.getSndType() != 3 && fdet.getTax() == 1)
					.collect(Collectors.toList());
		}
		if (enterpriseId != null) {
			expensisList = expensisList.stream()
					.filter(fdet -> fdet.getEnterpriseId() != null && fdet.getEnterpriseId().equals(enterpriseId))
					.collect(Collectors.toList());
		}
		if (expensisList != null && expensisList.size() > 0) {
			listTotalSum = expensisList.stream()
					.filter(fdet -> fdet.getTax() == 1 && (fdet.getSndType() == 1 || fdet.getSndType() == 4))
					.mapToDouble(fdet -> fdet.getTaxAmoun()).sum();

			listTotalSumDecimal = new BigDecimal(listTotalSum).setScale(2, RoundingMode.HALF_UP);
			totalRevTaxs = expensisList.stream().filter(fdet -> fdet.getTax() == 1 && fdet.getSndType() == 2)
					.mapToDouble(fdet -> fdet.getTaxAmoun()).sum();

			totalRevTaxsDecimal = new BigDecimal(totalRevTaxs).setScale(2, RoundingMode.HALF_UP);
			totalDiff = totalRevTaxs - listTotalSum;
			totalDiffDecimal = new BigDecimal(totalDiff).setScale(2, RoundingMode.HALF_UP);
		}
		return "";
	}

	public String addCompany() {
		try {
			if (expensisAdd != null) {
				expensisAdd.setSndType(4); // 1 srf // 2 qabd // 3 bill // 4 expensis taxs
				expensisAdd.setExpensisTypesId(-1);
				expensisAdd.setTax(1);
				if (dateAddBoolean) {
					expensisAdd.setSndDate(Utils.convertHDateToGDate(expensisAdd.getMonthhDate()));
				} else {
					expensisAdd.setMonthhDate(Utils.grigDatesConvert(expensisAdd.getSndDate()));
				}
				if (expensisAdd.getId() == null) {
					departmentServiceImpl.save(expensisAdd);
					MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.operation"));
					init();
					expensisAdd = new SndSrfQbd();

				} else {
					departmentServiceImpl.update(expensisAdd);
					MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.operation"));
					init();
					expensisAdd = new SndSrfQbd();

				}

			}
		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			e.printStackTrace();
		}
		return "";
	}

	public String deleteCompany(SndSrfQbd com) {
		if (com != null) {
			try {
				departmentServiceImpl.delete(com);
				MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.delete"));
				init();
			} catch (Exception e) {
				MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.delete"));
				e.printStackTrace();
			}
		}
		return "";
	}

	public DepartmentService getDepartmentServiceImpl() {
		return departmentServiceImpl;
	}

	public void setDepartmentServiceImpl(DepartmentService departmentServiceImpl) {
		this.departmentServiceImpl = departmentServiceImpl;
	}

	public List<SndSrfQbd> getExpensisList() {
		return expensisList;
	}

	public void setExpensisList(List<SndSrfQbd> expensisList) {
		this.expensisList = expensisList;
	}

	public SndSrfQbd getExpensisAdd() {
		return expensisAdd;
	}

	public void setExpensisAdd(SndSrfQbd expensisAdd) {
		this.expensisAdd = expensisAdd;
	}

	public boolean isDateBoolean() {
		return dateBoolean;
	}

	public void setDateBoolean(boolean dateBoolean) {
		this.dateBoolean = dateBoolean;
	}

	public boolean isDateAddBoolean() {
		return dateAddBoolean;
	}

	public void setDateAddBoolean(boolean dateAddBoolean) {
		this.dateAddBoolean = dateAddBoolean;
	}

	public boolean isVat() {
		return vat;
	}

	public void setVat(boolean vat) {
		this.vat = vat;
	}

	public double getTaxValue() {
		return taxValue;
	}

	public void setTaxValue(double taxValue) {
		this.taxValue = taxValue;
	}

	public double getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(double totalValue) {
		this.totalValue = totalValue;
	}

	public List<ExpensisTypes> getExpensisTypesList() {
		return expensisTypesList;
	}

	public void setExpensisTypesList(List<ExpensisTypes> expensisTypesList) {
		this.expensisTypesList = expensisTypesList;
	}

	public Integer getSupType() {
		return supType;
	}

	public void setSupType(Integer supType) {
		this.supType = supType;
	}

	public List<Enterprise> getEnterpriseList() {
		return enterpriseList;
	}

	public void setEnterpriseList(List<Enterprise> enterpriseList) {
		this.enterpriseList = enterpriseList;
	}

	public Integer getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public double getListTotalSum() {
		return listTotalSum;
	}

	public void setListTotalSum(double listTotalSum) {
		this.listTotalSum = listTotalSum;
	}

	public BigDecimal getListTotalSumDecimal() {
		return listTotalSumDecimal;
	}

	public void setListTotalSumDecimal(BigDecimal listTotalSumDecimal) {
		this.listTotalSumDecimal = listTotalSumDecimal;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public boolean isVatDisabled() {
		return vatDisabled;
	}

	public void setVatDisabled(boolean vatDisabled) {
		this.vatDisabled = vatDisabled;
	}

	public double getTotalRevTaxs() {
		return totalRevTaxs;
	}

	public void setTotalRevTaxs(double totalRevTaxs) {
		this.totalRevTaxs = totalRevTaxs;
	}

	public BigDecimal getTotalRevTaxsDecimal() {
		return totalRevTaxsDecimal;
	}

	public void setTotalRevTaxsDecimal(BigDecimal totalRevTaxsDecimal) {
		this.totalRevTaxsDecimal = totalRevTaxsDecimal;
	}

	public double getTotalDiff() {
		return totalDiff;
	}

	public void setTotalDiff(double totalDiff) {
		this.totalDiff = totalDiff;
	}

	public BigDecimal getTotalDiffDecimal() {
		return totalDiffDecimal;
	}

	public void setTotalDiffDecimal(BigDecimal totalDiffDecimal) {
		this.totalDiffDecimal = totalDiffDecimal;
	}

}

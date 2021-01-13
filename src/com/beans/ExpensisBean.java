package com.beans;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import com.entities.Enterprise;
import com.entities.ExpensisTypes;
import com.entities.SndSrfQbd;
import com.services.DepartmentService;

import common.util.MsgEntry;
import common.util.Utils;

@ManagedBean(name = "expensisBean")
@ViewScoped
public class ExpensisBean {
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
	private Date dateFrom;
	private Date dateTo;
	private boolean vatDisabled;

	@PostConstruct
	public void init() {
		List ls = departmentServiceImpl.findAll(SndSrfQbd.class);
		expensisList = ls;
		expensisList = expensisList.stream().filter(fdet -> fdet.getSndType() == 1).collect(Collectors.toList());
		expensisTypesList = departmentServiceImpl.loadExpTypes();
		ls = departmentServiceImpl.findAll(Enterprise.class);
		enterpriseList = ls;
		if (expensisList != null && expensisList.size() > 0) {
			listTotalSum = expensisList.stream().filter(fdet -> fdet.getAmount() != 0.0d)
					.mapToDouble(fdet -> fdet.getAmount()).sum();

			listTotalSumDecimal = new BigDecimal(listTotalSum).setScale(3, RoundingMode.HALF_UP);
		}
		vat = false;
		taxValue = 0.0;
		totalValue = 0.0;
	}

	public String filter() {
		List ls = departmentServiceImpl.findAll(SndSrfQbd.class);
		expensisList = ls;
		expensisList = expensisList.stream().filter(fdet -> fdet.getSndType() == 1).collect(Collectors.toList());
		if (dateFrom != null) {
			expensisList = departmentServiceImpl.LoadAllSands(dateFrom, dateTo, 1);
		}
		if (dateTo != null) {
			expensisList = departmentServiceImpl.LoadAllSands(dateFrom, dateTo, 1);
		}

		if (supType != null) {
			expensisList = expensisList.stream()
					.filter(fdet -> fdet.getExpensisTypesId() != null && fdet.getExpensisTypesId().equals(supType))
					.collect(Collectors.toList());
		}
		if (enterpriseId != null) {
			expensisList = expensisList.stream()
					.filter(fdet -> fdet.getEnterpriseId() != null && fdet.getEnterpriseId().equals(enterpriseId))
					.collect(Collectors.toList());
		}
		if (expensisList != null && expensisList.size() > 0) {
			listTotalSum = expensisList.stream().filter(fdet -> fdet.getAmount() != 0.0d)
					.mapToDouble(fdet -> fdet.getAmount()).sum();

			listTotalSumDecimal = new BigDecimal(listTotalSum).setScale(3, RoundingMode.HALF_UP);
		}
		return "";
	}

	public void updateCom() {
		if (expensisAdd.getAmount() > 0 && vat == true) {
			expensisAdd.setTaxAmoun((expensisAdd.getAmount() / 1.15) * 0.15);
			expensisAdd.setTaxAmoun(Math.round(expensisAdd.getTaxAmoun() * 100) / 100.00d);
			totalValue = expensisAdd.getAmount() + expensisAdd.getTaxAmoun();
			totalValue = Math.round(totalValue * 100) / 100.00d;
			expensisAdd.setTax(1); // have tax
		} else {
			expensisAdd.setTaxAmoun(0.0);
			totalValue = expensisAdd.getAmount();
			expensisAdd.setTax(0); // not have tax
		}
	}

	public String addCompany() {
		try {
			if (expensisAdd != null) {
				expensisAdd.setSndType(1); // 1 srf // 2 qabd
				expensisAdd.setAmount(totalValue);
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

//

	public String update(SndSrfQbd com) {
		expensisAdd = com;
		totalValue = expensisAdd.getAmount();
		vatDisabled = true;
		if (expensisAdd.getTax() == 1) {
			vat = true;
		}
		Utils.openDialog("whsdlAdd");
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

//
	public void onEdit() {
		try {
			expensisAdd.setAmount(totalValue);
			if (dateAddBoolean) {
				expensisAdd.setSndDate(Utils.convertHDateToGDate(expensisAdd.getMonthhDate()));
			} else {
				expensisAdd.setMonthhDate(Utils.grigDatesConvert(expensisAdd.getSndDate()));
			}
			departmentServiceImpl.update(expensisAdd);
			MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.update"));
			init();
			expensisAdd = new SndSrfQbd();
		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.update"));
			e.printStackTrace();
		}

	}

//
	public void onRowCancel(RowEditEvent event) {
//		
		FacesMessage msg = new FacesMessage("·„ Ì „ «· ⁄œÌ·", "");
		FacesContext.getCurrentInstance().addMessage(null, msg);

	}

	public String printSandQabd(SndSrfQbd sm) {

		if (sm != null) {
			try {
				String reportName = "/reports/sand_sarf.jasper";
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("custName", sm.getName());
				String reyal = String.valueOf(sm.getAmount());
				if (reyal.contains(".")) {
					reyal = reyal.substring(0, reyal.indexOf("."));
					parameters.put("reyal", Integer.parseInt(reyal));
					String hall = String.valueOf(sm.getAmount());
					hall = hall.substring(hall.indexOf(".") + 1);
					parameters.put("halaa", Integer.parseInt(hall));
				} else {
					parameters.put("reyal", (int) sm.getAmount());
					parameters.put("halaa", 00);
				}
				parameters.put("for", sm.getForReason() == null ? " " : sm.getForReason());
				parameters.put("payType", sm.getPayType() == null ? "" : sm.getPayType());

				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				String grigDate = sdf.format(sm.getSndDate());
				parameters.put("date", grigDate);
				parameters.put("dateH", sm.getMonthhDate());
				parameters.put("costByLet", sm.getAmount());
				Utils.printPdfReport(reportName, parameters);

			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return "";
	}

	public String printAll() {
		try {
			String reportName = "/reports/expensis.jasper";
			Map<String, Object> parameters = new HashMap<String, Object>();
			String fromD = "1";
			String toD = "1";
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			if (dateFrom != null) {
				fromD = sdf.format(dateFrom);
			}
			if (dateTo != null) {
				toD = sdf.format(dateTo);
			}

			parameters.put("dateF", fromD);
			parameters.put("dateT", toD);
			Utils.printPdfReportFromListDataSource(reportName, parameters, expensisList);

		} catch (Exception e) {
			e.printStackTrace();
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

}

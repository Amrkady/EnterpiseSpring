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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.entities.Companies;
import com.entities.Enterprise;
import com.entities.SndSrfQbd;
import com.services.DepartmentService;

import common.util.MsgEntry;
import common.util.NumberToArabic;
import common.util.Utils;

@ManagedBean(name = "revenueBean")
@ViewScoped
public class RevenueBean {
	@ManagedProperty(value = "#{departmentServiceImpl}")
	private DepartmentService departmentServiceImpl;
	private List<SndSrfQbd> revList = new ArrayList<SndSrfQbd>();

	private SndSrfQbd revAdd = new SndSrfQbd();
	private boolean dateBoolean;
	private boolean dateAddBoolean;
	private boolean vat;
	private double taxValue;
	private double totalValue;
	private Integer supType;
	private List<Enterprise> enterpriseList = new ArrayList<Enterprise>();
	private Integer enterpriseId;
	private Integer companyId;
	private double listTotalSum;
	private BigDecimal listTotalSumDecimal;
	private List<Companies> companiesList = new ArrayList<Companies>();
	private boolean vatDisabled;
	private Date dateFrom;
	private Date dateTo;
	private String sadad;

	@PostConstruct
	public void init() {
		List ls = departmentServiceImpl.findAll(SndSrfQbd.class);
		revList = ls;
		ls = departmentServiceImpl.findAll(Companies.class);
		companiesList = ls;
		revList = revList.stream().filter(fdet -> fdet.getSndType() == 2).collect(Collectors.toList());
		ls = departmentServiceImpl.findAll(Enterprise.class);
		enterpriseList = ls;
		if (revList != null && revList.size() > 0) {
			listTotalSum = revList.stream().filter(fdet -> fdet.getAmount() != 0.0d)
					.mapToDouble(fdet -> fdet.getAmount()).sum();

			listTotalSumDecimal = new BigDecimal(listTotalSum).setScale(3, RoundingMode.HALF_UP);
		}
		vat = false;
		taxValue = 0.0;
		totalValue = 0.0;
	}

	public String filter() {
		List ls = departmentServiceImpl.findAll(SndSrfQbd.class);
		revList = ls;
		listTotalSumDecimal = new BigDecimal(0);
		revList = revList.stream().filter(fdet -> fdet.getSndType() == 2).collect(Collectors.toList());
		if (dateFrom != null) {
			revList = departmentServiceImpl.LoadAllSands(dateFrom, dateTo, 2);
		}
		if (dateTo != null) {
			revList = departmentServiceImpl.LoadAllSands(dateFrom, dateTo, 2);
		}

		if (sadad != null && !sadad.isEmpty()) {
			if ("3".equalsIgnoreCase(sadad.trim())) {
				revList = revList.stream()
						.filter(fdet -> fdet.getPayType() != null && "3".equalsIgnoreCase(fdet.getPayType().trim()))
						.collect(Collectors.toList());
			} else {
				revList = revList.stream()
						.filter(fdet -> fdet.getPayType() != null && !("3".equalsIgnoreCase(fdet.getPayType().trim())))
						.collect(Collectors.toList());

			}
		}
		if (companyId != null) {
			revList = revList.stream()
					.filter(fdet -> fdet.getCompanyId() != null && fdet.getCompanyId().equals(companyId))
					.collect(Collectors.toList());
		}
		if (revList != null && revList.size() > 0) {
			listTotalSum = revList.stream().filter(fdet -> fdet.getAmount() != 0.0d)
					.mapToDouble(fdet -> fdet.getAmount()).sum();

			listTotalSumDecimal = new BigDecimal(listTotalSum).setScale(3, RoundingMode.HALF_UP);
		}
		return "";
	}

	public void updateCom() {
		if (revAdd.getAmount() > 0 && vat == true) {
			revAdd.setTaxAmoun((revAdd.getAmount() / 1.15) * 0.15);
			revAdd.setTaxAmoun(Math.round(revAdd.getTaxAmoun() * 100) / 100.00d);
			totalValue = revAdd.getAmount() + revAdd.getTaxAmoun();
			totalValue = Math.round(totalValue * 100) / 100.00d;
			revAdd.setTax(1); // have tax
		} else {
			revAdd.setTaxAmoun(0.0);
			totalValue = revAdd.getAmount();
			revAdd.setTax(0); // not have tax
		}
	}

	public String addCompany() {
		try {
			vatDisabled = false;
			if (revAdd != null) {
				revAdd.setSndType(2); // 1 srf // 2 qabd
				revAdd.setAmount(totalValue);
				revAdd.setExpensisTypesId(-1);
				if (dateAddBoolean) {
					revAdd.setSndDate(Utils.convertHDateToGDate(revAdd.getMonthhDate()));
				} else {
					revAdd.setMonthhDate(Utils.grigDatesConvert(revAdd.getSndDate()));
				}
				if (revAdd.getId() == null) {
					departmentServiceImpl.save(revAdd);
					MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.operation"));
					init();
					revAdd = new SndSrfQbd();

				} else {
					departmentServiceImpl.update(revAdd);
					MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.operation"));
					init();
					revAdd = new SndSrfQbd();

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
		revAdd = com;
		totalValue = revAdd.getAmount();
		vatDisabled = true;
		if (revAdd.getTax() == 1) {
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

	public String printBill(SndSrfQbd sm) {

		if (sm != null) {
			try {
				String reportName = "/reports/bills.jasper";
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("comName", sm.getComName());
				parameters.put("billNo", sm.getBillNo());
				double amountWithoutTaxs = sm.getAmount() - sm.getTaxAmoun();
				amountWithoutTaxs = Utils.formatDouble(amountWithoutTaxs);
				parameters.put("amount", amountWithoutTaxs);
				parameters.put("tax", sm.getTaxAmoun());
				parameters.put("total", sm.getAmount());
				SimpleDateFormat sdf = new SimpleDateFormat("MM");
				String month = sdf.format(sm.getSndDate());
				sdf = new SimpleDateFormat("yyyy");
				month = loadMonth(month);
				String yearM = month + " " + sdf.format(sm.getSndDate());
				parameters.put("yearMonth", yearM);
				String amountLitters = NumberToArabic.convertToArabic(new BigDecimal(sm.getAmount()), "SAR");
				parameters.put("amountLitters", amountLitters);
				// String reyal = String.valueOf(sm.getAmount());
//				if (reyal.contains(".")) {
//					reyal = reyal.substring(0, reyal.indexOf("."));
//					Long ryal = new Long(reyal);
//					String reyl = Utils.convertNumberToWords(ryal);
//					String hall = String.valueOf(sm.getAmount());
//					hall = hall.substring(hall.indexOf(".") + 1);
//					Long hll = new Long(hall);
//					if (hll > 0) {
//						String hll2 = Utils.convertNumberToWords(hll);
//						String amountLitters = reyl + "  —Ì«·  " + " Ê " + hll2 + "  Â··… ";
//						parameters.put("amountLitters", amountLitters);
//					} else {
//						String amountLitters = reyl + "  —Ì«·  ";
//						parameters.put("amountLitters", amountLitters);
//					}
//
//				} else {
//					reyal = reyal.substring(0, reyal.indexOf("."));
//					Long ryal = new Long(reyal);
//					String reyl = Utils.convertNumberToWords(ryal);
//					String amountLitters = reyl + "  —Ì«·  ";
//					parameters.put("amountLitters", amountLitters);
//				}
				Utils.printPdfReport(reportName, parameters);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	public String loadMonth(String month) {
		int monthInt = 0;
		if (month != null && !month.isEmpty()) {
			monthInt = Integer.parseInt(month);
			month = "";
			switch (monthInt) {
			case 1:
				month = "Ì‰«Ì—";
				break;
			case 2:
				month = "›»—«Ì—";
				break;
			case 3:
				month = "„«—”";
				break;
			case 4:
				month = "«»—Ì·";
				break;
			case 5:
				month = "„«ÌÊ";
				break;
			case 6:
				month = "ÌÊ‰ÌÊ";
				break;
			case 7:
				month = "ÌÊ·ÌÂ";
				break;
			case 8:
				month = "«€”ÿ”";
				break;
			case 9:
				month = "”» „»—";
				break;
			case 10:
				month = "«ﬂ Ê»—";
				break;
			case 11:
				month = "‰Ê›„»—";
				break;
			case 12:
				month = "œÌ”„»—";
				break;
			}

		}
		return month;
	}

	public String printSandQabd(SndSrfQbd sm) {

		if (sm != null) {
			try {
				String reportName = "/reports/sand_qabd.jasper";
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
			String reportName = "/reports/revenues.jasper";
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
			Utils.printPdfReportFromListDataSource(reportName, parameters, revList);

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

	public List<SndSrfQbd> getRevList() {
		return revList;
	}

	public void setRevList(List<SndSrfQbd> revList) {
		this.revList = revList;
	}

	public SndSrfQbd getRevAdd() {
		return revAdd;
	}

	public void setRevAdd(SndSrfQbd revAdd) {
		this.revAdd = revAdd;
	}

	public List<Companies> getCompaniesList() {
		return companiesList;
	}

	public void setCompaniesList(List<Companies> companiesList) {
		this.companiesList = companiesList;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public boolean isVatDisabled() {
		return vatDisabled;
	}

	public void setVatDisabled(boolean vatDisabled) {
		this.vatDisabled = vatDisabled;
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

	public String getSadad() {
		return sadad;
	}

	public void setSadad(String sadad) {
		this.sadad = sadad;
	}

}

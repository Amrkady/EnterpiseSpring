package com.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "financailBean")
@ViewScoped
public class FinancailYearBean {
//	@ManagedProperty(value = "#{departmentServiceImpl}")
//	private DepartmentService departmentServiceImpl;
//
//	@ManagedProperty(value = "#{accountsServiceImpl}")
//	private AccountsService accountsServiceImpl;
//
//	private Date dateFrom;
//	private Date dateTo;
//	private boolean enablePrint = false;
//	private double debtor;
//	private List<Integer> years;
//	private Integer year;
//	private List<SndSrfQbd> sandsList = new ArrayList<>();
//
//	private double bankSum;
//	private double boxSum;
//	private double totalBankSum;
//	private double totalBoxSum;
//	private double bankSumSrf;
//	private double boxSumSrf;
//	private double paysTotalSum;
//	private double expensisTotalSum;
//	private double totalRev;
//
//	private double asoulSum;
//	private double suppliersSum;
//
//	private List<RevuensModel> revList = new ArrayList<RevuensModel>();
//	private List<SndSrfQbd> asoulList = new ArrayList<SndSrfQbd>();
//	private List<SndSrfQbd> sndList = new ArrayList<SndSrfQbd>();
//
//	@PostConstruct
//	public void init() {
//
//	}
//
//	public void getBoxAndBank() {
//		// yyyy-MM-dd
//
//	}
//
//	public void loadData() {
//		if (year != null) {
//			DateFormat dateFormat = new SimpleDateFormat("yyyy");
//			try {
//				dateFrom = dateFormat.parse(year.toString());
//				year += 1;
//				dateTo = dateFormat.parse(year.toString());
//				DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
//				dateFrom = dateFormat.parse(dateFormat2.format(dateFrom));
//				dateTo = dateFormat.parse(dateFormat2.format(dateTo));
//				year--;
//			} catch (java.text.ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//
//			}
//			// snd qapd type = 1
//			sandsList = departmentServiceImpl.LoadAllSands(dateFrom, dateTo, 1);
//			// pay type = 1 bank
//			bankSum = sandsList.stream().filter(fdet -> fdet.getPayType().equalsIgnoreCase("1"))
//					.mapToDouble(fdet -> fdet.getAmount()).sum();
//			// pay type = 2 box
//			boxSum = sandsList.stream().filter(fdet -> fdet.getPayType().equalsIgnoreCase("2"))
//					.mapToDouble(fdet -> fdet.getAmount()).sum();
//			paysTotalSum = sandsList.stream().mapToDouble(fdet -> fdet.getAmount()).sum();
//
//			// snd sarf type = 2
//			sandsList = departmentServiceImpl.LoadAllSands(dateFrom, dateTo, 2);
//
//			expensisTotalSum = sandsList.stream()
//					.filter(fdet -> fdet.getExpensisTypesId() != 9 && fdet.getExpensisTypesId() != 1)
//					.mapToDouble(fdet -> fdet.getAmount()).sum();
//
//			bankSumSrf = sandsList.stream().filter(fdet -> fdet.getPayType().equalsIgnoreCase("1"))
//					.mapToDouble(fdet -> fdet.getAmount()).sum();
//			// pay type = 2 box
//			boxSumSrf = sandsList.stream().filter(fdet -> fdet.getPayType().equalsIgnoreCase("2"))
//					.mapToDouble(fdet -> fdet.getAmount()).sum();
//			//////////////// totalBankSum ////////////////
//			totalBankSum = bankSumSrf - bankSum;
//			totalBoxSum = boxSumSrf - boxSum;
//			////////////
//			enablePrint = true;
//			revList = accountsServiceImpl.getFinancialMuneDates(dateFrom, dateTo);
//			totalRev = revList.stream().mapToDouble(fdet -> fdet.getProfit().doubleValue()).sum();
//			asoulList = accountsServiceImpl.LoadAllAsoul(dateFrom, dateTo);
//			asoulSum = asoulList.stream().mapToDouble(fdet -> fdet.getAmount()).sum();
//			sndList = accountsServiceImpl.LoadAllSndsWithoutTaxa(dateFrom, dateTo);
//			// (rev.sndType == 2 and rev.expensisTypesId == 1) ///////////// suppliers
//			suppliersSum = sandsList.stream().filter(fdet -> (fdet.getSndType() == 2 && fdet.getExpensisTypesId() == 1))
//					.mapToDouble(fdet -> fdet.getAmount()).sum();
//
//			Calendar c = Calendar.getInstance();
//			c.setTime(dateFrom); // Now use from date.
//			c.add(Calendar.DATE, 29); // Adding 29 days
//			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//			Date to = null;
//			try {
//				to = sdf.parse(sdf.format(c.getTime()));
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			// String output = sdf.format(c.getTime());
//			accountsServiceImpl.findFirstAmountByDates(dateFrom, to, null, null);
//
//		}
//
//	}
//
//	public void calcFineValSum(RevuensModel rm) {
//		if (rm != null) {
//			BigDecimal monValue = rm.getRevAmount();
//			BigDecimal accountValue = rm.getExpAmount();
//			BigDecimal zak = rm.getZakah();
//
//			if (monValue != null && accountValue != null && zak != null) {
//				double dif = monValue.doubleValue() - accountValue.doubleValue() - zak.doubleValue();
//				rm.setProfit(new BigDecimal(dif).setScale(2, RoundingMode.HALF_UP));
//				// totalRev = totalRev - rm.getZakah().doubleValue();
//			}
//		}
//		FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("tot");
//		FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("ble");
//		// PrimeFaces.current().ajax().update("foo:bar");
//		RequestContext.getCurrentInstance().update("tot");
//		RequestContext.getCurrentInstance().update("ble");
//
//	}
//
////	public String addTaxs() {
////		totalProfitAfterTaxs = totalProfitAfterTaxs - zakahTax;
////		totalProfitAfterTaxs = Math.round(totalProfitAfterTaxs * 100) / 100.00d;
////		ownerComm = totalProfitAfterTaxs / 2;
////		ownerComm = Math.round(ownerComm * 100) / 100.00d;
////		System.out.println(">>>>>>>>" + totalProfitAfterTaxs);
////		Utils.updateUIComponent("form:pro");
////		Utils.updateUIComponent("form:one");
////		Utils.updateUIComponent("form:two");
////		Utils.updateUIComponent("form:total1");
////		return "";
////	}
//
//	public String printReviewSystem() {
//		System.out.print("print >>>>>>>.");
//		try {
//			String reportName = "/reports/review_system.jasper";
//			Map<String, Object> parameters = new HashMap<String, Object>();
//			parameters.put("boxqbd", boxSum);
//			parameters.put("bankqbd", bankSum);
//			parameters.put("boxsrf", boxSumSrf);
//			parameters.put("banksrf", bankSumSrf);
//			parameters.put("income", paysTotalSum);
//			parameters.put("outcome", expensisTotalSum);
//			parameters.put("year", year.toString());
//			parameters.put("asoulSum", asoulSum);
//			parameters.put("suppliersSum", suppliersSum);
//			String headerPath = FacesContext.getCurrentInstance().getExternalContext()
//					.getRealPath("/reports/logoreport.png");
//			parameters.put("header", headerPath);
//			Utils.printPdfReport(reportName, parameters);
//
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		return "";
//	}
//
//	public String printGenaralMouzna() {
//		System.out.print("print >>>>>>>.");
//		try {
//			String reportName = "/reports/generalMouzna.jasper";
//			Map<String, Object> parameters = new HashMap<String, Object>();
//			parameters.put("bank", totalBankSum);
//			parameters.put("boxs", totalBoxSum);
//			double sum = revList.stream().mapToDouble(fdet -> fdet.getProfit().doubleValue()).sum();
//			parameters.put("part", sum);
//			parameters.put("year", year.toString());
//			String headerPath = FacesContext.getCurrentInstance().getExternalContext()
//					.getRealPath("/reports/logoreport.png");
//			parameters.put("header", headerPath);
//			Utils.printPdfReportFromListDataSource(reportName, parameters, asoulList);
//
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		return "";
//	}
//
//	public String printIcomeMenu() {
//		System.out.print("print >>>>>>>.");
//		try {
//			String reportName = "/reports/revenuesIncome.jasper";
//			Map<String, Object> parameters = new HashMap<String, Object>();
//			parameters.put("year", year.toString());
//			String headerPath = FacesContext.getCurrentInstance().getExternalContext()
//					.getRealPath("/reports/logoreport.png");
//			parameters.put("header", headerPath);
//			Utils.printPdfReportFromListDataSource(reportName, parameters, revList);
//
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		return "";
//	}
//
////	public String printFinacialCenter() {
////		System.out.print("print >>>>>>>.");
////		try {
////			String reportName = "/reports/financial_center_menu.jasper";
////			Map<String, Object> parameters = new HashMap<String, Object>();
////			parameters.put("box", boxValue);
////			parameters.put("bank", bankValue);
////			double total = boxValue + bankValue;
////			total = Math.round(total * 100) / 100.00d;
////			parameters.put("total", total);
////			parameters.put("first", ownerComm);
////			parameters.put("second", ownerComm);
////			parameters.put("third", partnerCommision);
////			parameters.put("year", year.toString());
////			String footerPath = FacesContext.getCurrentInstance().getExternalContext()
////					.getRealPath("/reports/footer.png");
////			parameters.put("footer", footerPath);
////			String headerPath = FacesContext.getCurrentInstance().getExternalContext()
////					.getRealPath("/reports/header.png");
////			parameters.put("header", headerPath);
////			Utils.printPdfReport(reportName, parameters);
////
////		} catch (Exception e) {
////			// TODO: handle exception
////		}
////		return "";
////	}
//
//	public List<Integer> getYears() {
//		years = new ArrayList<Integer>();
//		for (int i = 2020; i < 2121; i++) {
//			years.add(i);
//		}
//		return years;
//	}
//
//	public void setYears(List<Integer> years) {
//
//		this.years = years;
//	}
//
//	public Integer getYear() {
//		return year;
//	}
//
//	public void setYear(Integer year) {
//		this.year = year;
//	}
//
//	public DepartmentService getDepartmentServiceImpl() {
//		return departmentServiceImpl;
//	}
//
//	public void setDepartmentServiceImpl(DepartmentService departmentServiceImpl) {
//		this.departmentServiceImpl = departmentServiceImpl;
//	}
//
//	public List<SndSrfQbd> getSandsList() {
//		return sandsList;
//	}
//
//	public void setSandsList(List<SndSrfQbd> sandsList) {
//		this.sandsList = sandsList;
//	}
//
//	public double getBankSum() {
//		return bankSum;
//	}
//
//	public void setBankSum(double bankSum) {
//		this.bankSum = bankSum;
//	}
//
//	public double getBoxSum() {
//		return boxSum;
//	}
//
//	public void setBoxSum(double boxSum) {
//		this.boxSum = boxSum;
//	}
//
//	public double getPaysTotalSum() {
//		return paysTotalSum;
//	}
//
//	public void setPaysTotalSum(double paysTotalSum) {
//		this.paysTotalSum = paysTotalSum;
//	}
//
//	public double getExpensisTotalSum() {
//		return expensisTotalSum;
//	}
//
//	public void setExpensisTotalSum(double expensisTotalSum) {
//		this.expensisTotalSum = expensisTotalSum;
//	}
//
//	public boolean isEnablePrint() {
//		return enablePrint;
//	}
//
//	public void setEnablePrint(boolean enablePrint) {
//		this.enablePrint = enablePrint;
//	}
//
//	public double getDebtor() {
//		return debtor;
//	}
//
//	public void setDebtor(double debtor) {
//		this.debtor = debtor;
//	}
//
//	public List<RevuensModel> getRevList() {
//		return revList;
//	}
//
//	public void setRevList(List<RevuensModel> revList) {
//		this.revList = revList;
//	}
//
//	public AccountsService getAccountsServiceImpl() {
//		return accountsServiceImpl;
//	}
//
//	public void setAccountsServiceImpl(AccountsService accountsServiceImpl) {
//		this.accountsServiceImpl = accountsServiceImpl;
//	}
//
//	public List<SndSrfQbd> getAsoulList() {
//		return asoulList;
//	}
//
//	public void setAsoulList(List<SndSrfQbd> asoulList) {
//		this.asoulList = asoulList;
//	}
//
//	public List<SndSrfQbd> getSndList() {
//		return sndList;
//	}
//
//	public void setSndList(List<SndSrfQbd> sndList) {
//		this.sndList = sndList;
//	}
//
//	public double getBankSumSrf() {
//		return bankSumSrf;
//	}
//
//	public void setBankSumSrf(double bankSumSrf) {
//		this.bankSumSrf = bankSumSrf;
//	}
//
//	public double getBoxSumSrf() {
//		return boxSumSrf;
//	}
//
//	public void setBoxSumSrf(double boxSumSrf) {
//		this.boxSumSrf = boxSumSrf;
//	}
//
//	public Date getDateFrom() {
//		return dateFrom;
//	}
//
//	public void setDateFrom(Date dateFrom) {
//		this.dateFrom = dateFrom;
//	}
//
//	public Date getDateTo() {
//		return dateTo;
//	}
//
//	public void setDateTo(Date dateTo) {
//		this.dateTo = dateTo;
//	}
//
//	public double getTotalBankSum() {
//		return totalBankSum;
//	}
//
//	public void setTotalBankSum(double totalBankSum) {
//		this.totalBankSum = totalBankSum;
//	}
//
//	public double getTotalBoxSum() {
//		return totalBoxSum;
//	}
//
//	public void setTotalBoxSum(double totalBoxSum) {
//		this.totalBoxSum = totalBoxSum;
//	}
//
//	public double getTotalRev() {
//		return totalRev;
//	}
//
//	public void setTotalRev(double totalRev) {
//		this.totalRev = totalRev;
//	}
//
//	public double getAsoulSum() {
//		return asoulSum;
//	}
//
//	public void setAsoulSum(double asoulSum) {
//		this.asoulSum = asoulSum;
//	}
//
//	public double getSuppliersSum() {
//		return suppliersSum;
//	}
//
//	public void setSuppliersSum(double suppliersSum) {
//		this.suppliersSum = suppliersSum;
//	}

}

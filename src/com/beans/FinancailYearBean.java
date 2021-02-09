package com.beans;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.entities.SndSrfQbd;
import com.services.AccountsService;
import com.services.DepartmentService;

import common.util.Utils;

@ManagedBean(name = "financailBean")
@ViewScoped
public class FinancailYearBean {
	@ManagedProperty(value = "#{departmentServiceImpl}")
	private DepartmentService departmentServiceImpl;

	@ManagedProperty(value = "#{accountsServiceImpl}")
	private AccountsService accountsServiceImpl;

	private Date dateFrom;
	private Date dateTo;
	private boolean enablePrint = false;
	private double debtor;
	private List<Integer> years;
	private Integer year;
	private List<SndSrfQbd> sandsList = new ArrayList<>();

	private double bankSum;
	private double boxSum;
	private double totalBankSum;
	private double totalBoxSum;
	private double bankSumSrf;
	private double boxSumSrf;
	private double paysTotalSum;
	private double expensisTotalSum;
	private double totalRev;

	private List<SndSrfQbd> sndList = new ArrayList<SndSrfQbd>();
	private List<SndSrfQbd> revList = new ArrayList<SndSrfQbd>();

	@PostConstruct
	public void init() {

	}

//	public void getBoxAndBank() {
//		// yyyy-MM-dd
//
//	}
//
	public void loadData() {
		if (year != null) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy");
			try {
				dateFrom = dateFormat.parse(year.toString());
				year += 1;
				dateTo = dateFormat.parse(year.toString());
				DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
				dateFrom = dateFormat.parse(dateFormat2.format(dateFrom));
				dateTo = dateFormat.parse(dateFormat2.format(dateTo));
				year--;
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
			// 1 srf // 2 qabd
			sndList = new ArrayList<SndSrfQbd>();
			sandsList = departmentServiceImpl.LoadAllSands(dateFrom, dateTo, 2);
			sndList.addAll(sandsList);
			// pay type = 1 bank
			bankSum = sandsList.stream().filter(fdet -> fdet.getPayType().equalsIgnoreCase("1"))
					.mapToDouble(fdet -> fdet.getAmount()).sum();
			bankSum += sandsList.stream().filter(fdet -> fdet.getPayType().equalsIgnoreCase("1"))
					.mapToDouble(fdet -> fdet.getTaxAmoun()).sum();
			// pay type = 2 box
			boxSum = sandsList.stream().filter(fdet -> fdet.getPayType().equalsIgnoreCase("2"))
					.mapToDouble(fdet -> fdet.getAmount()).sum();
			boxSum += sandsList.stream().filter(fdet -> fdet.getPayType().equalsIgnoreCase("2"))
					.mapToDouble(fdet -> fdet.getTaxAmoun()).sum();

			paysTotalSum = sandsList.stream().mapToDouble(fdet -> fdet.getAmount()).sum();
			paysTotalSum += sandsList.stream().mapToDouble(fdet -> fdet.getTaxAmoun()).sum();
			// 1 srf // 2 qabd
			// sndList = new ArrayList<SndSrfQbd>();
			sandsList = departmentServiceImpl.LoadAllSands(dateFrom, dateTo, 1);
			sndList.addAll(sandsList);
			// pay type = 1 bank
			bankSumSrf = sandsList.stream().filter(fdet -> fdet.getPayType().equalsIgnoreCase("1"))
					.mapToDouble(fdet -> fdet.getAmount()).sum();
			bankSumSrf += sandsList.stream().filter(fdet -> fdet.getPayType().equalsIgnoreCase("1"))
					.mapToDouble(fdet -> fdet.getTaxAmoun()).sum();

			// pay type = 2 box
			boxSumSrf = sandsList.stream().filter(fdet -> fdet.getPayType().equalsIgnoreCase("2"))
					.mapToDouble(fdet -> fdet.getAmount()).sum();
			boxSumSrf += sandsList.stream().filter(fdet -> fdet.getPayType().equalsIgnoreCase("2"))
					.mapToDouble(fdet -> fdet.getTaxAmoun()).sum();

			expensisTotalSum = sandsList.stream().mapToDouble(fdet -> fdet.getAmount()).sum();
			expensisTotalSum += sandsList.stream().mapToDouble(fdet -> fdet.getTaxAmoun()).sum();
			//////////////// totalBankSum ////////////////
			totalBankSum = bankSum - bankSumSrf;
			totalBoxSum = boxSum - boxSumSrf;
			////////////
			enablePrint = true;
//			revList = accountsServiceImpl.getFinancialMuneDates(dateFrom, dateTo);
//			totalRev = revList.stream().mapToDouble(fdet -> fdet.getProfit().doubleValue()).sum();
			// sndList = accountsServiceImpl.LoadAllSndsWithoutTaxa(dateFrom, dateTo);

		}

	}

	public String printReviewSystem() {
		System.out.print("print >>>>>>>.");
		try {
			String reportName = "/reports/review_system.jasper";
			Map<String, Object> parameters = new HashMap<String, Object>();
			boxSum = Math.round(boxSum * 100) / 100.00d;
			parameters.put("boxqbd", boxSum);
			bankSum = Math.round(bankSum * 100) / 100.00d;
			parameters.put("bankqbd", bankSum);
			boxSumSrf = Math.round(boxSumSrf * 100) / 100.00d;
			parameters.put("boxsrf", boxSumSrf);
			bankSumSrf = Math.round(bankSumSrf * 100) / 100.00d;
			parameters.put("banksrf", bankSumSrf);
			paysTotalSum = Math.round(paysTotalSum * 100) / 100.00d;
			parameters.put("income", paysTotalSum);
			expensisTotalSum = Math.round(expensisTotalSum * 100) / 100.00d;
			parameters.put("outcome", expensisTotalSum);
			parameters.put("year", year.toString());
			Utils.printPdfReport(reportName, parameters);

		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}

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
	public List<Integer> getYears() {
		years = new ArrayList<Integer>();
		for (int i = 2021; i < 2121; i++) {
			years.add(i);
		}
		return years;
	}

	public void setYears(List<Integer> years) {

		this.years = years;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public DepartmentService getDepartmentServiceImpl() {
		return departmentServiceImpl;
	}

	public void setDepartmentServiceImpl(DepartmentService departmentServiceImpl) {
		this.departmentServiceImpl = departmentServiceImpl;
	}

	public List<SndSrfQbd> getSandsList() {
		return sandsList;
	}

	public void setSandsList(List<SndSrfQbd> sandsList) {
		this.sandsList = sandsList;
	}

	public double getBankSum() {
		return bankSum;
	}

	public void setBankSum(double bankSum) {
		this.bankSum = bankSum;
	}

	public double getBoxSum() {
		return boxSum;
	}

	public void setBoxSum(double boxSum) {
		this.boxSum = boxSum;
	}

	public double getPaysTotalSum() {
		return paysTotalSum;
	}

	public void setPaysTotalSum(double paysTotalSum) {
		this.paysTotalSum = paysTotalSum;
	}

	public double getExpensisTotalSum() {
		return expensisTotalSum;
	}

	public void setExpensisTotalSum(double expensisTotalSum) {
		this.expensisTotalSum = expensisTotalSum;
	}

	public boolean isEnablePrint() {
		return enablePrint;
	}

	public void setEnablePrint(boolean enablePrint) {
		this.enablePrint = enablePrint;
	}

	public double getDebtor() {
		return debtor;
	}

	public void setDebtor(double debtor) {
		this.debtor = debtor;
	}

	public AccountsService getAccountsServiceImpl() {
		return accountsServiceImpl;
	}

	public void setAccountsServiceImpl(AccountsService accountsServiceImpl) {
		this.accountsServiceImpl = accountsServiceImpl;
	}

	public List<SndSrfQbd> getSndList() {
		return sndList;
	}

	public void setSndList(List<SndSrfQbd> sndList) {
		this.sndList = sndList;
	}

	public double getBankSumSrf() {
		return bankSumSrf;
	}

	public void setBankSumSrf(double bankSumSrf) {
		this.bankSumSrf = bankSumSrf;
	}

	public double getBoxSumSrf() {
		return boxSumSrf;
	}

	public void setBoxSumSrf(double boxSumSrf) {
		this.boxSumSrf = boxSumSrf;
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

	public double getTotalBankSum() {
		return totalBankSum;
	}

	public void setTotalBankSum(double totalBankSum) {
		this.totalBankSum = totalBankSum;
	}

	public double getTotalBoxSum() {
		return totalBoxSum;
	}

	public void setTotalBoxSum(double totalBoxSum) {
		this.totalBoxSum = totalBoxSum;
	}

	public double getTotalRev() {
		return totalRev;
	}

	public void setTotalRev(double totalRev) {
		this.totalRev = totalRev;
	}

	public List<SndSrfQbd> getRevList() {
		return revList;
	}

	public void setRevList(List<SndSrfQbd> revList) {
		this.revList = revList;
	}

}

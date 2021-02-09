package com.beans;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
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
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.common.Constant;
import com.entities.Companies;
import com.entities.Contracts;
import com.entities.ContractsEmployees;
import com.entities.Employees;
import com.entities.EmployeesMovements;
import com.entities.Enterprise;
import com.services.DepartmentService;
import com.services.UserService;

import common.util.MsgEntry;
import common.util.Utils;

@ManagedBean(name = "employeesBean")
@ViewScoped
public class EmployeesBean {
	@ManagedProperty(value = "#{userServiceImpl}")
	private UserService userServiceImpl;
	@ManagedProperty(value = "#{departmentServiceImpl}")
	private DepartmentService departmentServiceImpl;
	private List<Employees> employeesList = new ArrayList<Employees>();
	private List<Employees> employeesFilterList;
	private Employees employee;
	private boolean dateBoolean;
	private List<Enterprise> enterpriseList = new ArrayList<Enterprise>();
	private List<EmployeesMovements> empMovList = new ArrayList<EmployeesMovements>();
	private List<Companies> companiesList = new ArrayList<Companies>();
	private Integer enterpriseId;
	private Integer active;
	private BigDecimal totalSalary;
	private double listTotalSum;
	private boolean dateTableBoolean;
	private boolean tableRend;
	private BigDecimal totalSalaryAfter;
	private Integer companyId;
	private List<String> enterpriseAdd = new ArrayList<>();
	private Date dateFrom;
	private Date dateTo;
	private Contracts con;
	private long days;

	@PostConstruct
	public void init() {
		List ls = departmentServiceImpl.findAll(Employees.class);
		employeesList = ls;
		totalSalary = new BigDecimal(0);
		if (employeesList != null && employeesList.size() > 0) {
			listTotalSum = employeesList.stream().filter(fdet -> fdet.getSalary() != 0.0d)
					.mapToDouble(fdet -> fdet.getSalary()).sum();

			totalSalary = new BigDecimal(listTotalSum).setScale(3, RoundingMode.HALF_UP);

			System.out.println("" + totalSalary);

		}
		ls = departmentServiceImpl.findAll(Enterprise.class);
		enterpriseList = ls;
		ls = departmentServiceImpl.findAll(Companies.class);
		companiesList = ls;
		if (companiesList != null && companiesList.size() > 0) {
			companyId = companiesList.get(0).getId();
		}
	}

	@SuppressWarnings("unchecked")
	public String search() {
		init();
		if (enterpriseId != null) {
			employeesList = employeesList.stream().filter(fdet -> fdet.getEnterpriseId().equals(enterpriseId))
					.collect(Collectors.toList());
		}
		if (active != null) {
			employeesList = employeesList.stream()
					.filter(fdet -> (!fdet.getIqamaStatus().isEmpty()) && fdet.getColorStatus() == active)
					.collect(Collectors.toList());

		}
		totalSalary = new BigDecimal(0);
		if (employeesList != null && employeesList.size() > 0) {
			listTotalSum = employeesList.stream().filter(fdet -> fdet.getSalary() != 0.0d)
					.mapToDouble(fdet -> fdet.getSalary()).sum();

			totalSalary = new BigDecimal(listTotalSum).setScale(3, RoundingMode.HALF_UP);

			System.out.println("" + totalSalary);

		}
		// employeesFilterList = employeesList;
		return "";
	}

	public void initAddEdit() {
		HttpServletRequest httprequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		HttpSession httpSession = httprequest.getSession(false);
		employee = (Employees) httpSession.getAttribute("employee");
		httpSession.removeAttribute("employee");
		if (employee != null) {
			empMovList = departmentServiceImpl.findEmpMovements(employee.getId());
			tableRend = true;
		}
		if (employee == null) {
			employee = new Employees();
		}
		System.out.println(employee.getName());
	}

	public String addEmployee() {
		if (employee != null) {
			try {
				if (dateBoolean) {
					employee.setBirthmDate(Utils.convertHDateToGDate(employee.getBirthhDate()));
					employee.setEnterancemDate(Utils.convertHDateToGDate(employee.getEnterancehDate()));
					employee.setIqamaEndmDate(Utils.convertHDateToGDate(employee.getIqamaEndhDate()));
					employee.setWorkStartmDate(Utils.convertHDateToGDate(employee.getWorkStarthDate()));
				} else {
					// SimpleDateFormat localDateFormat = new SimpleDateFormat("dd/MM/yyyy");
					employee.setBirthhDate(Utils.grigDatesConvert(employee.getBirthmDate()));
					employee.setEnterancehDate(Utils.grigDatesConvert(employee.getEnterancemDate()));
					employee.setIqamaEndhDate(Utils.grigDatesConvert(employee.getIqamaEndmDate()));
					employee.setWorkStarthDate(Utils.grigDatesConvert(employee.getWorkStartmDate()));

				}
				if (employee.getId() == null) {
					departmentServiceImpl.save(employee);
					MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.operation"));
				} else {
					departmentServiceImpl.update(employee);
					MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.update"));
				}

				employee = new Employees();
			} catch (Exception e) {
				MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
				e.printStackTrace();
			}
		}
		return "";

	}

	public String deleteEmployee(Employees employee) {
		if (employee != null) {
			try {
				departmentServiceImpl.delete(employee);
				MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.delete"));
			} catch (Exception e) {
				MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.delete"));
				e.printStackTrace();
			}
		}
		return "";
	}

	public String updateEmployee(Employees employee) {
		if (employee == null) {
			return "";
		}
		HttpServletRequest httprequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		HttpSession httpSession = httprequest.getSession(false);
		httpSession.setAttribute("employee", employee);
//		try {
//			MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.update"));
//		} catch (Exception e) {
//			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.update"));
//			e.printStackTrace();
//		}
		return "addEditEmp";
	}

	public void initSalarys() {
//		LocalDate date = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//		int days = date.lengthOfMonth();
//		System.out.println("////////////" + days);
		// Long days =ChronoUnit.DAYS.between(new Date().toInstant(), new
		// Date().toInstant());
		List ls = departmentServiceImpl.findAll(Employees.class);
		employeesList = ls;
		employeesList = employeesList.stream().filter(fdet -> "0".equalsIgnoreCase(fdet.getActive()))
				.collect(Collectors.toList());
		totalSalary = new BigDecimal(0);
		totalSalaryAfter = new BigDecimal(0);
		con = new Contracts();
		if (companyId != null) {
			employeesList = new ArrayList<Employees>();
			con = departmentServiceImpl.loadContractByCompanyId(companyId);
			if (con != null) {
				List<ContractsEmployees> empIds = departmentServiceImpl.loadEmpByContractId(con.getId());
				for (ContractsEmployees id : empIds) {
					Employees emp = (Employees) departmentServiceImpl.findEntityById(Employees.class, id.getEmpId());
					employeesList.add(emp);
					employeesList = employeesList.stream().filter(fdet -> "0".equalsIgnoreCase(fdet.getActive()))
							.collect(Collectors.toList());
				}
			}
		}

		if (enterpriseAdd != null && enterpriseAdd.size() > 0) {
			List<Employees> empAllList = new ArrayList<>();
			for (String entId : enterpriseAdd) {
				// enterpriseId = Integer.parseInt(entId);
				List<Employees> empList = employeesList.stream()
						.filter(fdet -> fdet.getEnterpriseId().equals(Integer.parseInt(entId)))
						.collect(Collectors.toList());
				if (empList != null && empList.size() > 0) {
					empAllList.addAll(empList);
				}
			}

			employeesList = empAllList;
		}

//		if (employeesList != null && employeesList.size() > 0) {
//			listTotalSum = employeesList.stream().filter(fdet -> fdet.getSalary() != 0.0d)
//					.mapToDouble(fdet -> fdet.getSalary()).sum();
//			totalSalary = new BigDecimal(listTotalSum).setScale(3, RoundingMode.HALF_UP);
		// totalSalaryAfter = totalSalary;
//			System.out.println("" + totalSalary);
//
//		}
		if (con != null) {
			loadSalaries();
		}

//		ls = departmentServiceImpl.findAll(Enterprise.class);
//		enterpriseList = ls;
	}

	private void loadSalaries() {
		days = 30L;
		if (dateFrom != null && dateTo != null) {
			days = ChronoUnit.DAYS.between(dateFrom.toInstant(), dateTo.toInstant());
			days += 1;
			System.out.println("//////////" + days);
		}
		for (Employees emp : employeesList) {
			emp.setMonthSalary(emp.getSalary() * con.getHourNo() * days);
			emp.setContractHourNo(con.getHourNo());
			emp.setBonus(100);
			emp.setHourPrice(Constant.HOUR_PRICE);
			// emp.setTotalSalary(emp.getMonthSalary());
		}
//		compaerFeesAndBillDates();
	}

	public void calcFineValSum(Employees emp) {
		if (emp != null) {
			Double salaryValue = emp.getSalary();
			Double daysMinus = emp.getHourMinus();
			if (daysMinus != null) {
				emp.setPriceAbsentDays(salaryValue * emp.getContractHourNo() * daysMinus);
			}

		}
	}

	public void calcValSum(Employees emp) {
		if (emp != null) {
			// Double salaryValue = emp.getSalary();
			Double hourAdd = emp.getHourNoAdd();
			if (hourAdd != null) {
				emp.setPriceHourNoAdd(emp.getHourPrice() * hourAdd);
			}
		}
	}

	public void calcBonusSum(Employees emp) {
		if (emp != null) {
			Double salaryValue = emp.getMonthSalary();
			Double bonus = emp.getBonus();
			if (salaryValue != null) {
				if (bonus != null) {
					salaryValue = salaryValue + bonus;
					salaryValue = Math.round(salaryValue * 100) / 100.00d;
					// emp.setTotalSalary(salaryValue);
				}

			}
		}
	}

	public String printAll() {
		try {
			String reportName = "";
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
			Companies com = (Companies) departmentServiceImpl.findEntityById(Companies.class, companyId);

			parameters.put("comName", com.getName());
			parameters.put("dateFrom", fromD);
			parameters.put("dateTo", toD);
			if (con.getWorkType() == 1) // days
			{
				calHoursAndSalary();
				reportName = "/reports/companySalaries.jasper";
				parameters.put("days", days);
			} else // hours
			{
				reportName = "/reports/companySalaries_HOURS.jasper";
				Double dys = con.getHourNo() * days;
				parameters.put("days", dys.intValue());
				calHoursAndSalary();
			}
			Utils.printPdfReportFromListDataSource(reportName, parameters, employeesList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	private void calHoursAndSalary() {
		for (Employees emp : employeesList) {
			emp.setTotalHour(emp.getHourNoAdd() + (con.getHourNo() * days));
			Double hourVal = con.getConAmount() / con.getHourNo();
			hourVal = hourVal * 1.5;
			hourVal = Math.round(hourVal * 100) / 100.00d;
			emp.setHourPrice(hourVal);
			Double hourValPrice = emp.getHourNoAdd() * emp.getHourPrice();
			hourValPrice = Math.round(hourValPrice * 100) / 100.00d;
			emp.setPriceHourNoAdd(hourValPrice);
			emp.setMonthSalary(con.getConAmount() * days);
			emp.setTotalSalary(hourValPrice + emp.getMonthSalary());
		}
	}

	/**
	 * @param document
	 */
	public void postProcessXLS(Object document) {
		HSSFWorkbook wb = (HSSFWorkbook) document;
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow header = sheet.getRow(0);
		// sheet.removeColumnBreak(8);
		sheet.setColumnHidden(0, true);
//		sheet.setColumnHidden(5, true);

		// for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
		// HSSFCell cell = header.getCell(i);
		//
		// cell.setCellStyle(cellStyle);
		// }
	}

	/**
	 * export xls file from list
	 */
	public void exportXLS() {
		// Create a Workbook
		Workbook workbook = new HSSFWorkbook(); // new HSSFWorkbook() for
												// generating `.xls` file

		/*
		 * CreationHelper helps us create instances of various things like DataFormat,
		 * Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way
		 */
		CreationHelper createHelper = workbook.getCreationHelper();

		// Create a Sheet
		Sheet sheet = workbook.createSheet("ÇáÚÞæÏ");

		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setColor(IndexedColors.RED.getIndex());

		// Create a CellStyle with the font
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		// Create a Row
		Row headerRow = sheet.createRow(0);

		String[] columns = { "ÇáÑÞã", "ÑÞã ÇáÚÞÏ ", "ÊÇÑíÎ ÇáÈÏÇíÉ", "ÊÇÑíÎ ÇáäåÇíÉ", "ÇáãÓÊËãÑ", "ÍÇáÉ ÇáÝÇÊæÑÉ" };
		// Create cells
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);
		}

		// Create Cell Style for formatting Date
		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

		// Create Other rows and cells with employees data
		int rowNum = 1;
		int num = 1;
//		for (ContractDirect contObj : contractsDirectList) {
//			Row row = sheet.createRow(rowNum++);
//			row.createCell(0).setCellValue(num);
//			row.createCell(1).setCellValue(contObj.getContractNum());
//
//			if (!tableHigriMode) {
//				Cell date1 = row.createCell(2);
//				date1.setCellValue(contObj.getStartContDate());
//				date1.setCellStyle(dateCellStyle);
//				Cell date2 = row.createCell(3);
//				date2.setCellValue(contObj.getEndContDate());
//				date2.setCellStyle(dateCellStyle);
//			} else {
//				Cell date1 = row.createCell(2);
//				date1.setCellValue(contObj.getStartDate());
//				date1.setCellStyle(dateCellStyle);
//				Cell date2 = row.createCell(3);
//				date2.setCellValue(contObj.getEndDate());
//				date2.setCellStyle(dateCellStyle);
//			}
//
//			Investor inv = (Investor) dataAccessService.findEntityById(Investor.class, contObj.getInvestorId());
//			row.createCell(4).setCellValue(inv.getName());
//			row.createCell(5).setCellValue(contObj.getPayStatusName());
//			num++;
//		}

		// Resize all columns to fit the content size
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		// Write the output to a file

		try {
			String path = "D:/ÇáÚÞæÏ.xls";
			FileOutputStream fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {

			workbook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Closing the workbook

	}

	public UserService getUserServiceImpl() {
		return userServiceImpl;
	}

	public void setUserServiceImpl(UserService userServiceImpl) {
		this.userServiceImpl = userServiceImpl;
	}

	public DepartmentService getDepartmentServiceImpl() {
		return departmentServiceImpl;
	}

	public void setDepartmentServiceImpl(DepartmentService departmentServiceImpl) {
		this.departmentServiceImpl = departmentServiceImpl;
	}

	public List<Employees> getEmployeesList() {
		return employeesList;
	}

	public void setEmployeesList(List<Employees> employeesList) {
		this.employeesList = employeesList;
	}

	public Employees getEmployee() {
		return employee;
	}

	public void setEmployee(Employees employee) {
		this.employee = employee;
	}

	public boolean isDateBoolean() {
		return dateBoolean;
	}

	public void setDateBoolean(boolean dateBoolean) {
		this.dateBoolean = dateBoolean;
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

	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}

	public BigDecimal getTotalSalary() {
		return totalSalary;
	}

	public void setTotalSalary(BigDecimal totalSalary) {
		this.totalSalary = totalSalary;
	}

	public double getListTotalSum() {
		return listTotalSum;
	}

	public void setListTotalSum(double listTotalSum) {
		this.listTotalSum = listTotalSum;
	}

	public boolean isDateTableBoolean() {
		return dateTableBoolean;
	}

	public void setDateTableBoolean(boolean dateTableBoolean) {
		this.dateTableBoolean = dateTableBoolean;
	}

	public List<EmployeesMovements> getEmpMovList() {
		return empMovList;
	}

	public void setEmpMovList(List<EmployeesMovements> empMovList) {
		this.empMovList = empMovList;
	}

	public boolean isTableRend() {
		return tableRend;
	}

	public void setTableRend(boolean tableRend) {
		this.tableRend = tableRend;
	}

	public BigDecimal getTotalSalaryAfter() {
		return totalSalaryAfter;
	}

	public void setTotalSalaryAfter(BigDecimal totalSalaryAfter) {
		this.totalSalaryAfter = totalSalaryAfter;
	}

	public List<Employees> getEmployeesFilterList() {
		return employeesFilterList;
	}

	public void setEmployeesFilterList(List<Employees> employeesFilterList) {
		this.employeesFilterList = employeesFilterList;
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

	public List<String> getEnterpriseAdd() {
		return enterpriseAdd;
	}

	public void setEnterpriseAdd(List<String> enterpriseAdd) {
		this.enterpriseAdd = enterpriseAdd;
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

	public Contracts getCon() {
		return con;
	}

	public void setCon(Contracts con) {
		this.con = con;
	}

	public long getDays() {
		return days;
	}

	public void setDays(long days) {
		this.days = days;
	}

}

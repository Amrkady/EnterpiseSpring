package com.beans;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
		List ls = departmentServiceImpl.findAll(Employees.class);
		employeesList = ls;
		employeesList = employeesList.stream().filter(fdet -> "0".equalsIgnoreCase(fdet.getActive()))
				.collect(Collectors.toList());
		totalSalary = new BigDecimal(0);
		totalSalaryAfter = new BigDecimal(0);
		if (companyId != null) {
			employeesList = new ArrayList<Employees>();
			Contracts con = departmentServiceImpl.loadContractByCompanyId(companyId);
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

		if (enterpriseId != null) {
			employeesList = employeesList.stream().filter(fdet -> fdet.getEnterpriseId().equals(enterpriseId))
					.collect(Collectors.toList());
		}

		if (employeesList != null && employeesList.size() > 0) {
			listTotalSum = employeesList.stream().filter(fdet -> fdet.getSalary() != 0.0d)
					.mapToDouble(fdet -> fdet.getSalary()).sum();
			totalSalary = new BigDecimal(listTotalSum).setScale(3, RoundingMode.HALF_UP);
			// totalSalaryAfter = totalSalary;
			System.out.println("" + totalSalary);

		}
//		ls = departmentServiceImpl.findAll(Enterprise.class);
//		enterpriseList = ls;
	}

	public void calcFineValSum(Employees emp) {
		if (emp != null) {
			Double salaryValue = emp.getSalary();
			Double hourMinus = emp.getHourMinus();
			if (hourMinus != null) {
				salaryValue = salaryValue - hourMinus;
			}
			salaryValue = Math.round(salaryValue * 100) / 100.00d;
			emp.setTotalSalary(salaryValue);

		}
		double totalSum = employeesList.stream().filter(fdet -> fdet.getSalary() != 0.0d)
				.mapToDouble(fdet -> fdet.getTotalSalary()).sum();
		totalSalaryAfter = new BigDecimal(totalSum).setScale(3, RoundingMode.HALF_UP);

	}

	public void calcValSum(Employees emp) {
		if (emp != null) {
			Double salaryValue = emp.getSalary();
			Double hourAdd = emp.getHourNoAdd();
			if (salaryValue != null) {
				if (hourAdd != null) {
					salaryValue = salaryValue + hourAdd;
					salaryValue = Math.round(salaryValue * 100) / 100.00d;
					emp.setTotalSalary(salaryValue);
				}

			}
		}
		double totalSum = employeesList.stream().filter(fdet -> fdet.getTotalSalary() != 0.0d)
				.mapToDouble(fdet -> fdet.getTotalSalary()).sum();
		totalSalaryAfter = new BigDecimal(totalSum).setScale(3, RoundingMode.HALF_UP);

	}

	public void calcBonusSum(Employees emp) {
		if (emp != null) {
			Double salaryValue = emp.getSalary();
			Double bonus = emp.getBonus();
			if (salaryValue != null) {
				if (bonus != null) {
					salaryValue = salaryValue + bonus;
					salaryValue = Math.round(salaryValue * 100) / 100.00d;
					emp.setTotalSalary(salaryValue);
				}

			}
		}
		double totalSum = employeesList.stream().filter(fdet -> fdet.getTotalSalary() != 0.0d)
				.mapToDouble(fdet -> fdet.getTotalSalary()).sum();
		totalSalaryAfter = new BigDecimal(totalSum).setScale(3, RoundingMode.HALF_UP);

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

}

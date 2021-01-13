package com.beans;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.event.RowEditEvent;

import com.entities.Companies;
import com.entities.Contracts;
import com.entities.ContractsEmployees;
import com.entities.Employees;
import com.services.DepartmentService;

import common.util.MsgEntry;
import common.util.Utils;

@ManagedBean(name = "contractBean")
@ViewScoped
public class ContractBean {
	@ManagedProperty(value = "#{departmentServiceImpl}")
	private DepartmentService departmentServiceImpl;
	private List<Contracts> contractsList = new ArrayList<Contracts>();
	private List<Contracts> contractsFilterList;
	private List<Companies> companiesList = new ArrayList<Companies>();
	private Companies company;
	private Integer companyId;
	private Contracts conAdd = new Contracts();
	private List<String> empsAddIds = new ArrayList<>();
	private List<Employees> employeesList = new ArrayList<Employees>();
	private boolean dateBoolean;
	private boolean dateAddBoolean;
	private List<ContractsEmployees> empConList = new ArrayList<ContractsEmployees>();
	private List<ContractsEmployees> empConFilterList;
	private double listTotalSum;
	private BigDecimal listTotalSumDecimal;
	private double listTotalRest;
	private BigDecimal listTotalRestDecimal;
	private Date startEmpDate;
	private List<String> empsAdd = new ArrayList<>();
	private Contracts contractObject = new Contracts();

	@PostConstruct
	public void init() {
		List ls = departmentServiceImpl.findAll(Contracts.class);
		contractsList = ls;

		HttpServletRequest httprequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		HttpSession httpSession = httprequest.getSession(false);
		company = (Companies) httpSession.getAttribute("company");
		httpSession.removeAttribute("company");

		if (company != null) {
			contractsList = contractsList.stream().filter(fdet -> fdet.getCompanyId().equals(company.getId()))
					.collect(Collectors.toList());
			companyId = company.getId();
		}

		listTotalSumDecimal = new BigDecimal(0);
		listTotalRestDecimal = new BigDecimal(0);
		if (contractsList != null && contractsList.size() > 0) {
			listTotalSum = contractsList.stream().filter(fdet -> fdet.getConAmount() != 0.0d)
					.mapToDouble(fdet -> fdet.getConAmount()).sum();

			listTotalSumDecimal = new BigDecimal(listTotalSum).setScale(3, RoundingMode.HALF_UP);

			listTotalRest = contractsList.stream().filter(fdet -> fdet.getRestAmount() != 0.0d)
					.mapToDouble(fdet -> fdet.getRestAmount()).sum();

			listTotalRestDecimal = new BigDecimal(listTotalRest).setScale(3, RoundingMode.HALF_UP);

			System.out.println("" + listTotalSumDecimal);

		}
		ls = departmentServiceImpl.findAll(Companies.class);
		companiesList = ls;
		ls = departmentServiceImpl.findAll(Employees.class);
		employeesList = ls;
	}

	public String filter() {
		List ls = departmentServiceImpl.findAll(Contracts.class);
		contractsList = ls;

		listTotalSumDecimal = new BigDecimal(0);
		listTotalRestDecimal = new BigDecimal(0);
		if (companyId != null) {
			contractsList = contractsList.stream().filter(fdet -> fdet.getCompanyId().equals(companyId))
					.collect(Collectors.toList());

		}
		if (contractsList != null && contractsList.size() > 0) {
			listTotalSum = contractsList.stream().filter(fdet -> fdet.getConAmount() != 0.0d)
					.mapToDouble(fdet -> fdet.getConAmount()).sum();

			listTotalSumDecimal = new BigDecimal(listTotalSum).setScale(3, RoundingMode.HALF_UP);

			listTotalRest = contractsList.stream().filter(fdet -> fdet.getRestAmount() != 0.0d)
					.mapToDouble(fdet -> fdet.getRestAmount()).sum();

			listTotalRestDecimal = new BigDecimal(listTotalRest).setScale(3, RoundingMode.HALF_UP);

			System.out.println("" + listTotalSumDecimal);

		}
		return "";
	}

	public String addCompany() {
		try {
			if (conAdd != null) {
				if (dateAddBoolean) {
					conAdd.setStartmDate(Utils.convertHDateToGDate(conAdd.getStarthDate()));
					conAdd.setEndmDate(Utils.convertHDateToGDate(conAdd.getEndhDate()));
				} else {
					// SimpleDateFormat localDateFormat = new SimpleDateFormat("dd/MM/yyyy");
					conAdd.setStarthDate(Utils.grigDatesConvert(conAdd.getStartmDate()));
					conAdd.setEndhDate(Utils.grigDatesConvert(conAdd.getEndmDate()));

				}

				departmentServiceImpl.saveContract(conAdd, empsAddIds);
				MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.operation"));
				init();
				conAdd = new Contracts();
			}
		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			e.printStackTrace();
		}
		return "";
	}

//
	public String deleteCompany(Contracts com) {
		if (com != null) {
			try {
				departmentServiceImpl.deleteContract(com);
				MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.delete"));
				init();
			} catch (Exception e) {
				MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.delete"));
				e.printStackTrace();
			}
		}
		return "";
	}

	public String deleteEmp(ContractsEmployees emp) {
		if (emp != null) {
			try {
				departmentServiceImpl.deleteEmployeeFromContract(emp);
				MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.delete"));

			} catch (Exception e) {
				MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.delete"));
				e.printStackTrace();
			}
		}
		return "";
	}

	public String addEmpToContract() {
		try {
			contractObject.setStartmDate(startEmpDate);
			contractObject.setStarthDate(Utils.grigDatesConvert(startEmpDate));
			departmentServiceImpl.InsertEmpToContract(contractObject, empsAdd);
			MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.operation"));

		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			e.printStackTrace();
		}
		return "";
	}

//
	public String findEmpsByConId(Contracts com) {
		contractObject = new Contracts();
		if (com == null) {
			return "";
		}
		contractObject = com;
		empConList = new ArrayList<ContractsEmployees>();
		empConList = departmentServiceImpl.getEmpsByConId(com.getId());
		Utils.openDialog("showdlg");
		return "";
	}

//
	public void onRowEdit(RowEditEvent event) {
		try {
			conAdd = (Contracts) event.getObject();
			if (dateBoolean) {
				conAdd.setStartmDate(Utils.convertHDateToGDate(conAdd.getStarthDate()));
				conAdd.setEndmDate(Utils.convertHDateToGDate(conAdd.getEndhDate()));
			} else {
				// SimpleDateFormat localDateFormat = new SimpleDateFormat("dd/MM/yyyy");
				conAdd.setStarthDate(Utils.grigDatesConvert(conAdd.getStartmDate()));
				conAdd.setEndhDate(Utils.grigDatesConvert(conAdd.getEndmDate()));

			}
			departmentServiceImpl.update(conAdd);
			MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.update"));
			init();
			conAdd = new Contracts();
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

	public DepartmentService getDepartmentServiceImpl() {
		return departmentServiceImpl;
	}

	public void setDepartmentServiceImpl(DepartmentService departmentServiceImpl) {
		this.departmentServiceImpl = departmentServiceImpl;
	}

	public List<Contracts> getContractsList() {
		return contractsList;
	}

	public void setContractsList(List<Contracts> contractsList) {
		this.contractsList = contractsList;
	}

	public Contracts getConAdd() {
		return conAdd;
	}

	public void setConAdd(Contracts conAdd) {
		this.conAdd = conAdd;
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

	public List<Companies> getCompaniesList() {
		return companiesList;
	}

	public void setCompaniesList(List<Companies> companiesList) {
		this.companiesList = companiesList;
	}

	public List<String> getEmpsAddIds() {
		return empsAddIds;
	}

	public void setEmpsAddIds(List<String> empsAddIds) {
		this.empsAddIds = empsAddIds;
	}

	public List<Employees> getEmployeesList() {
		return employeesList;
	}

	public void setEmployeesList(List<Employees> employeesList) {
		this.employeesList = employeesList;
	}

	public List<ContractsEmployees> getEmpConList() {
		return empConList;
	}

	public void setEmpConList(List<ContractsEmployees> empConList) {
		this.empConList = empConList;
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

	public double getListTotalRest() {
		return listTotalRest;
	}

	public void setListTotalRest(double listTotalRest) {
		this.listTotalRest = listTotalRest;
	}

	public BigDecimal getListTotalRestDecimal() {
		return listTotalRestDecimal;
	}

	public void setListTotalRestDecimal(BigDecimal listTotalRestDecimal) {
		this.listTotalRestDecimal = listTotalRestDecimal;
	}

	public Companies getCompany() {
		return company;
	}

	public void setCompany(Companies company) {
		this.company = company;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public List<Contracts> getContractsFilterList() {
		return contractsFilterList;
	}

	public void setContractsFilterList(List<Contracts> contractsFilterList) {
		this.contractsFilterList = contractsFilterList;
	}

	public List<ContractsEmployees> getEmpConFilterList() {
		return empConFilterList;
	}

	public void setEmpConFilterList(List<ContractsEmployees> empConFilterList) {
		this.empConFilterList = empConFilterList;
	}

	public Date getStartEmpDate() {
		return startEmpDate;
	}

	public void setStartEmpDate(Date startEmpDate) {
		this.startEmpDate = startEmpDate;
	}

	public List<String> getEmpsAdd() {
		return empsAdd;
	}

	public void setEmpsAdd(List<String> empsAdd) {
		this.empsAdd = empsAdd;
	}

	public Contracts getContractObject() {
		return contractObject;
	}

	public void setContractObject(Contracts contractObject) {
		this.contractObject = contractObject;
	}

}

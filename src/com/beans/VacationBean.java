package com.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import com.entities.Employees;
import com.entities.Vacations;
import com.services.DepartmentService;

import common.util.MsgEntry;
import common.util.Utils;

@ManagedBean(name = "vacationBean")
@ViewScoped
public class VacationBean {
	@ManagedProperty(value = "#{departmentServiceImpl}")
	private DepartmentService departmentServiceImpl;
	private List<Vacations> vacationsList = new ArrayList<Vacations>();
	private Vacations vacAdd = new Vacations();
	private boolean dateBoolean;
	private List<Employees> employeesList = new ArrayList<Employees>();
	private boolean dateAddBoolean;
	private Integer empId;

	@PostConstruct
	public void init() {
		List ls = departmentServiceImpl.findAll(Vacations.class);
		vacationsList = ls;
		ls = departmentServiceImpl.findAll(Employees.class);
		employeesList = ls;
	}

	public String filter() {
		List ls = departmentServiceImpl.findAll(Vacations.class);
		vacationsList = ls;
		if (empId != null) {
			vacationsList = vacationsList.stream().filter(fdet -> fdet.getEmpId() != null && fdet.getEmpId() == empId)
					.collect(Collectors.toList());
		}
		return "";
	}

	public String addCompany() {
		try {
			if (vacAdd != null) {

				if (dateAddBoolean) {
					vacAdd.setVacmDate(Utils.convertHDateToGDate(vacAdd.getVachDate()));
					vacAdd.setVacEndmDate(Utils.convertHDateToGDate(vacAdd.getVacEndhDate()));
				} else {
					// SimpleDateFormat localDateFormat = new SimpleDateFormat("dd/MM/yyyy");
					vacAdd.setVachDate(Utils.grigDatesConvert(vacAdd.getVacmDate()));
					vacAdd.setVacEndhDate(Utils.grigDatesConvert(vacAdd.getVacEndmDate()));

				}

				departmentServiceImpl.saveVacation(vacAdd);
				MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.operation"));
				init();
				vacAdd = new Vacations();
			}
		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			e.printStackTrace();
		}
		return "";
	}

//
	public String deleteCompany(Vacations com) {
		if (com != null) {
			try {
				departmentServiceImpl.deleteVacation(com);
				MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.delete"));
				init();
			} catch (Exception e) {
				MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.delete"));
				e.printStackTrace();
			}
		}
		return "";
	}

//	public String infoCompany(Vacations com) {
//		if (com == null) {
//			return "";
//		}
//		HttpServletRequest httprequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
//				.getRequest();
//		HttpSession httpSession = httprequest.getSession(false);
//		httpSession.setAttribute("company", com);
//		return "contracts";
//	}

//
	public void onRowEdit(RowEditEvent event) {
		try {
			vacAdd = (Vacations) event.getObject();
			if (dateBoolean) {
				vacAdd.setVacmDate(Utils.convertHDateToGDate(vacAdd.getVachDate()));
				vacAdd.setVacEndmDate(Utils.convertHDateToGDate(vacAdd.getVacEndhDate()));
			} else {
				// SimpleDateFormat localDateFormat = new SimpleDateFormat("dd/MM/yyyy");
				vacAdd.setVachDate(Utils.grigDatesConvert(vacAdd.getVacmDate()));
				vacAdd.setVacEndhDate(Utils.grigDatesConvert(vacAdd.getVacEndmDate()));
			}

			departmentServiceImpl.saveVacation(vacAdd);
			MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.update"));
			init();
			vacAdd = new Vacations();
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

	public List<Vacations> getVacationsList() {
		return vacationsList;
	}

	public void setVacationsList(List<Vacations> vacationsList) {
		this.vacationsList = vacationsList;
	}

	public Vacations getVacAdd() {
		return vacAdd;
	}

	public void setVacAdd(Vacations vacAdd) {
		this.vacAdd = vacAdd;
	}

	public boolean isDateBoolean() {
		return dateBoolean;
	}

	public void setDateBoolean(boolean dateBoolean) {
		this.dateBoolean = dateBoolean;
	}

	public List<Employees> getEmployeesList() {
		return employeesList;
	}

	public void setEmployeesList(List<Employees> employeesList) {
		this.employeesList = employeesList;
	}

	public boolean isDateAddBoolean() {
		return dateAddBoolean;
	}

	public void setDateAddBoolean(boolean dateAddBoolean) {
		this.dateAddBoolean = dateAddBoolean;
	}

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

}

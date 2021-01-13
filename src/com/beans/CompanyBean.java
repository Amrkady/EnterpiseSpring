package com.beans;

import java.util.ArrayList;
import java.util.List;

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
import com.services.DepartmentService;

import common.util.MsgEntry;
import common.util.Utils;

@ManagedBean(name = "companyBean")
@ViewScoped
public class CompanyBean {
	@ManagedProperty(value = "#{departmentServiceImpl}")
	private DepartmentService departmentServiceImpl;
	private List<Companies> companiesList = new ArrayList<Companies>();
	private List<Companies> companiestFilterList;
	private Companies comAdd = new Companies();

	@PostConstruct
	public void init() {
		List ls = departmentServiceImpl.findAll(Companies.class);
		companiesList = ls;
	}

	public String addCompany() {
		try {
			if (comAdd != null) {
				departmentServiceImpl.save(comAdd);
				MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.operation"));
				init();
				comAdd = new Companies();
			}
		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			e.printStackTrace();
		}
		return "";
	}

//
	public String deleteCompany(Companies com) {
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

	public String infoCompany(Companies com) {
		if (com == null) {
			return "";
		}
		HttpServletRequest httprequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		HttpSession httpSession = httprequest.getSession(false);
		httpSession.setAttribute("company", com);
		return "contracts";
	}

//
	public void onRowEdit(RowEditEvent event) {
		try {
			comAdd = (Companies) event.getObject();
			departmentServiceImpl.update(comAdd);
			MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.update"));
			init();
			comAdd = new Companies();
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

	public List<Companies> getCompaniesList() {
		return companiesList;
	}

	public void setCompaniesList(List<Companies> companiesList) {
		this.companiesList = companiesList;
	}

	public Companies getComAdd() {
		return comAdd;
	}

	public void setComAdd(Companies comAdd) {
		this.comAdd = comAdd;
	}

	public List<Companies> getCompaniestFilterList() {
		return companiestFilterList;
	}

	public void setCompaniestFilterList(List<Companies> companiestFilterList) {
		this.companiestFilterList = companiestFilterList;
	}
}

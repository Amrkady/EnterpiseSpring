package com.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import com.entities.ExpensisTypes;
import com.services.DepartmentService;

import common.util.MsgEntry;
import common.util.Utils;

@ManagedBean(name = "expTypeBean")
@ViewScoped
public class ExpTypeBean {
	@ManagedProperty(value = "#{departmentServiceImpl}")
	private DepartmentService departmentServiceImpl;
	private List<ExpensisTypes> expensisTypesList = new ArrayList<ExpensisTypes>();
	private List<ExpensisTypes> expensisFilterList;
	private ExpensisTypes exAdd = new ExpensisTypes();
	@PostConstruct
	public void init() {
		expensisTypesList = departmentServiceImpl.loadExpTypes();
	}

	public String addGas() {
		try {
			if (exAdd != null) {
				exAdd.setGeneral(exAdd.isGeneralType() == true ? 1 : 0);
				departmentServiceImpl.save(exAdd);
				MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.operation"));
				expensisTypesList = departmentServiceImpl.loadExpTypes();
				exAdd = new ExpensisTypes();
			}
		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.operation"));
			e.printStackTrace();
		}
		return "";
	}

//
	public String deleteGas(ExpensisTypes gs) {
		if (gs != null) {
			try {
				departmentServiceImpl.delete(gs);
				MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.delete"));
				expensisTypesList = departmentServiceImpl.loadExpTypes();
			} catch (Exception e) {
				MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.delete"));
				e.printStackTrace();
			}
		}
		return "";
	}

//
	public void onRowEdit(RowEditEvent event) {
		try {
			ExpensisTypes gs = (ExpensisTypes) event.getObject();
			gs.setGeneral(gs.isGeneralType() ? 1 : 0);
			departmentServiceImpl.update(gs);
			MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.update"));
			expensisTypesList = departmentServiceImpl.loadExpTypes();
		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.update"));
			e.printStackTrace();
		}

	}

//
	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("·„ Ì „ «· ⁄œÌ·", "");
		FacesContext.getCurrentInstance().addMessage(null, msg);

	}

	public DepartmentService getDepartmentServiceImpl() {
		return departmentServiceImpl;
	}

	public void setDepartmentServiceImpl(DepartmentService departmentServiceImpl) {
		this.departmentServiceImpl = departmentServiceImpl;
	}

	public List<ExpensisTypes> getExpensisTypesList() {
		return expensisTypesList;
	}

	public void setExpensisTypesList(List<ExpensisTypes> expensisTypesList) {
		this.expensisTypesList = expensisTypesList;
	}

	public ExpensisTypes getExAdd() {
		return exAdd;
	}

	public void setExAdd(ExpensisTypes exAdd) {
		this.exAdd = exAdd;
	}

	public List<ExpensisTypes> getExpensisFilterList() {
		return expensisFilterList;
	}

	public void setExpensisFilterList(List<ExpensisTypes> expensisFilterList) {
		this.expensisFilterList = expensisFilterList;
	}


}

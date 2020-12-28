package com.services;

import java.util.Date;
import java.util.List;

import com.common.CommonDao;
import com.entities.Expensis;
import com.entities.ExpensisTypes;
import com.entities.SndSrfQbd;

public class AccountsServiceImpl implements AccountsService {

	CommonDao commonDao;

	public CommonDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(CommonDao commonDao) {
		this.commonDao = commonDao;
	}



	@Override
	public List<ExpensisTypes> loadAllExpensisTypes(Integer general) {
		List et = commonDao.findExpensisTypes(general);
		return et;
	}

	@Override
	public List<Expensis> loadAllExpensisList() {
		List et = commonDao.findAll(Expensis.class);
		return et;
	}


	@Override
	public List<Expensis> loadExpensisByDates(Date dateFrom, Date dateTo, Integer supType, Integer stId) {
		List ls = commonDao.findExpensisByDates(dateFrom, dateTo, supType, stId);
		return ls;
	}

	@Override
	public List<ExpensisTypes> loadAllExpensisTypesList() {
		List ls = commonDao.findAll(ExpensisTypes.class);
		return ls;
	}


	@Override
	public List<SndSrfQbd> loadSndByType(Integer type, Integer stationId) {
		List ls = commonDao.findSndByType(type, stationId);
		return ls;
	}

	@Override
	public List<SndSrfQbd> findGeneralSndByType(Integer type, Integer stationId) {
		List ls = commonDao.findGeneralSndByType(type, stationId);
		return ls;
	}


}

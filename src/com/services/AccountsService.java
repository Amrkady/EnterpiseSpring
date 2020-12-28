package com.services;

import java.util.Date;
import java.util.List;

import com.entities.Expensis;
import com.entities.ExpensisTypes;
import com.entities.SndSrfQbd;

public interface AccountsService {

	public List<ExpensisTypes> loadAllExpensisTypes(Integer general);

	public List<Expensis> loadExpensisByDates(Date dateFrom, Date dateTo, Integer supType, Integer stId);

	public List<Expensis> loadAllExpensisList();

	public List<ExpensisTypes> loadAllExpensisTypesList();

	public List<SndSrfQbd> loadSndByType(Integer type, Integer stationId);

	public List<SndSrfQbd> findGeneralSndByType(Integer type, Integer stationId);

}

package com.services;

import java.util.Date;
import java.util.List;

import com.common.CommonDao;
import com.entities.Attachment;
import com.entities.Contracts;
import com.entities.ContractsEmployees;
import com.entities.EmployeesMovements;
import com.entities.ExpensisTypes;
import com.entities.SndSrfQbd;
import com.entities.Taxs;
import com.entities.Vacations;

public class DepartmentServiceImpl implements DepartmentService {

	CommonDao commonDao;

	public CommonDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(CommonDao commonDao) {
		this.commonDao = commonDao;
	}

	@Override
	public Object findEntityById(Class entityClass, Integer EntityId) {
		return commonDao.findEntityById(entityClass, EntityId);
	}

	@Override
	public List<Object> findAll(Class object) {
		return commonDao.findAll(object);
	}

	@Override
	public void save(Object object) {
		commonDao.saveObject(object);

	}

	@Override
	public void update(Object object) {
		commonDao.updateObject(object);

	}

	@Override
	public void delete(Object object) {
		commonDao.deleteObject(object);

	}

	@Override
	public List<Attachment> loadAttachments(Integer stId) {
		List atts = commonDao.findAttachmentsByStationId(stId);
		return atts;
	}

	@Override
	public List<Taxs> loadTaxs() {
		List stations = commonDao.findAll(Taxs.class);
		return stations;
	}

	@Override
	public List<ExpensisTypes> loadExpTypes() {
		List stations = commonDao.findAll(ExpensisTypes.class);
		return stations;
	}

	@Override
	public List<SndSrfQbd> LoadAllSands(Date dateFrom, Date dateTo, Integer sndType) {
		return commonDao.LoadAllSands(dateFrom, dateTo, sndType);
	}

	@Override
	public void saveContract(Contracts conAdd, List<String> empsAddIds) {
		commonDao.saveContract(conAdd, empsAddIds);

	}

	@Override
	public List<ContractsEmployees> getEmpsByConId(Integer conId) {
		return commonDao.findEmpsByConId(conId);
	}

	@Override
	public void deleteContract(Contracts con) {
		commonDao.deleteContract(con);
	}

	@Override
	public List<EmployeesMovements> findEmpMovements(Integer empId) {
		return commonDao.loadEmpMovements(empId);
	}

	@Override
	public void saveVacation(Vacations vacAdd) {
		commonDao.saveVacation(vacAdd);

	}

	@Override
	public void deleteVacation(Vacations com) {
		commonDao.deleteVacation(com);

	}

	@Override
	public Contracts loadContractByCompanyId(Integer companyId) {
		return commonDao.loadContractByCompanyId(companyId);
	}

	@Override
	public List<ContractsEmployees> loadEmpByContractId(Integer companyId) {
		return commonDao.loadEmpByContractId(companyId);
	}

	@Override
	public void deleteEmployeeFromContract(ContractsEmployees emp) {
		commonDao.deleteEmployeeFromContract(emp);

	}

	@Override
	public void InsertEmpToContract(Contracts conAdd, List<String> empsAddIds) {
		commonDao.InsertEmpToContract(conAdd, empsAddIds);

	}

	@Override
	public List<Contracts> loadContractsByCompanyId(Integer companyId) {
		return commonDao.loadContractsByCompanyId(companyId);
	}

}

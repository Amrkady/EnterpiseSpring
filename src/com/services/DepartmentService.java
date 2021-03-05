package com.services;

import java.util.Date;
import java.util.List;

import com.entities.Attachment;
import com.entities.Contracts;
import com.entities.ContractsEmployees;
import com.entities.EmployeesMovements;
import com.entities.ExpensisTypes;
import com.entities.SndSrfQbd;
import com.entities.Taxs;
import com.entities.Vacations;

public interface DepartmentService {

	public void save(Object object);

	public void update(Object object);

	public List<Object> findAll(Class object);

	public void delete(Object object);

	public Object findEntityById(Class entityClass, Integer EntityId);

	public List<Attachment> loadAttachments(Integer stId);

	public List<Taxs> loadTaxs();

	public List<ExpensisTypes> loadExpTypes();

	public List<SndSrfQbd> LoadAllSands(Date dateFrom, Date dateTo, Integer sndType);

	public void saveContract(Contracts conAdd, List<String> empsAddIds);

	public List<ContractsEmployees> getEmpsByConId(Integer id);

	public void deleteContract(Contracts con);

	public List<EmployeesMovements> findEmpMovements(Integer id);

	public void saveVacation(Vacations vacAdd);

	public void deleteVacation(Vacations com);

	public Contracts loadContractByCompanyId(Integer companyId);

	public List<ContractsEmployees> loadEmpByContractId(Integer companyId);

	public void deleteEmployeeFromContract(ContractsEmployees emp);

	public void InsertEmpToContract(Contracts conAdd, List<String> empsAddIds);

	public List<Contracts> loadContractsByCompanyId(Integer companyId);
}

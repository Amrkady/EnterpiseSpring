package com.common;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.annotation.Transactional;

import com.entities.Attachment;
import com.entities.Contracts;
import com.entities.ContractsEmployees;
import com.entities.EmployeesMovements;
import com.entities.Expensis;
import com.entities.ExpensisTypes;
import com.entities.SndSrfQbd;
import com.entities.Users;
import com.entities.Vacations;

import common.util.Utils;

public class CommonDaoImpl extends HibernateTemplate implements CommonDao {
	private SessionFactory sessionFactory;

	@Override
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Override
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional
	public Integer saveObject(Object object) {
		Integer save = (Integer) sessionFactory.getCurrentSession().save(object);
		System.out.println("SAVE---> " + save);
		return save;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Object> findAll(Class typeClass) {
		try {
			return loadAll(typeClass);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Object findEntityById(Class entityClass, Integer EntityId) {
		return get(entityClass, EntityId);
	}

	@Override
	@Transactional
	public boolean deleteObject(Object object) {
		try {
			sessionFactory.getCurrentSession().delete(object);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	@Transactional
	public boolean updateObject(Object myObject) {
		try {
			sessionFactory.getCurrentSession().update(myObject);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Users loadUser(final String username, final String password) throws AuthenticationException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Users.class);
		criteria.add(Restrictions.eq("loginName", username));
		if (password != null) {

			criteria.add(Restrictions.eq("password", password));

		}
		Users result = (Users) criteria.uniqueResult();
		if (result == null) {
			throw new BadCredentialsException("bad credentials");
		}
		Hibernate.initialize(result.getRole());
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Attachment> findAttachmentsByStationId(Integer stationId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Attachment.class);
		criteria.add(Restrictions.eq("stationId", stationId));
		List<Attachment> List = criteria.list();
		return List;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Expensis> findExpensisByDates(Date dateFrom, Date dateTo, Integer supType, Integer stId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Expensis.class);
		if (stId != -1) {
			criteria.add(Restrictions.eq("stationId", stId));
		}

		if (dateFrom != null) {
			criteria.add(Restrictions.ge("monthDate", dateFrom));
		}
		if (dateTo != null) {
			criteria.add(Restrictions.le("monthDate", dateTo));
		}

		if (supType != null) {
			criteria.add(Restrictions.eq("expensisType", supType));
		}
		criteria.add(Restrictions.ne("expensisType", 9));
		List<Expensis> list = criteria.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ExpensisTypes> findExpensisTypes(Integer general) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ExpensisTypes.class);
		criteria.add(Restrictions.eq("general", general));
		List<ExpensisTypes> expensisList = criteria.list();
		return expensisList;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<SndSrfQbd> findSndByType(Integer type, Integer stationId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SndSrfQbd.class);
		if (stationId != -1) {
			criteria.add(Restrictions.eq("stationId", stationId));
		}
		criteria.add(Restrictions.eq("sndType", type));
		criteria.add(Restrictions.ne("expensisTypesId", -1));
		List<SndSrfQbd> list = criteria.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<SndSrfQbd> findGeneralSndByType(Integer type, Integer stationId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SndSrfQbd.class);
		if (stationId != -1) {
			criteria.add(Restrictions.eq("stationId", stationId));
		}
		criteria.add(Restrictions.eq("sndType", type));
		criteria.add(Restrictions.eq("expensisTypesId", -1));
		List<SndSrfQbd> list = criteria.list();
		return list;
	}

	@Transactional
	@Override
	public List<SndSrfQbd> LoadAllSands(Date dateFrom, Date dateTo, Integer sndType) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SndSrfQbd.class);
		if (dateFrom != null) {
			criteria.add(Restrictions.ge("sndDate", dateFrom));
		}
		if (dateTo != null) {
			criteria.add(Restrictions.le("sndDate", dateTo));
		}
		criteria.add(Restrictions.eq("sndType", sndType));
		List<SndSrfQbd> list = criteria.list();
		return list;
	}

	@Override
	@Transactional
	public void saveContract(Contracts conAdd, List<String> empsAddIds) {
		Integer conId = saveObject(conAdd);
		saveConEmp(conAdd, empsAddIds);

	}

	// @Transactional
	public void saveConEmp(Contracts conAdd, List<String> empsAddIds) {
		for (String id : empsAddIds) {
			ContractsEmployees conEmp = new ContractsEmployees();
			conEmp.setContractId(conAdd.getId());
			conEmp.setEmpId(Integer.parseInt(id));
			saveObject(conEmp);
			EmployeesMovements empMove = new EmployeesMovements();
			empMove.setStarthDate(conAdd.getStarthDate());
			empMove.setStartmDate(conAdd.getStartmDate());
			empMove.setEndhDate(conAdd.getEndhDate());
			empMove.setEndmDate(conAdd.getEndmDate());
			empMove.setMoveType("0"); // 0 contract 1 vacation
			empMove.setTypeId(conAdd.getCompanyId());
			empMove.setContractId(conAdd.getId());
			empMove.setEmpId(Integer.parseInt(id));
			saveObject(empMove);
		}
	}

	@Override
	@Transactional
	public List<ContractsEmployees> findEmpsByConId(Integer conId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ContractsEmployees.class);
		criteria.add(Restrictions.eq("contractId", conId));
		List<ContractsEmployees> list = criteria.list();
		return list;
	}

	@Override
	@Transactional
	public void deleteContract(Contracts con) {
		deleteObject(con);
		String hql = "delete FROM  ContractsEmployees  Where contractId = :conId";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("conId", con.getId());
		query.executeUpdate();

		String hql2 = "delete FROM  EmployeesMovements  Where moveType ='0' and contractId = :conId";
		Query query2 = sessionFactory.getCurrentSession().createQuery(hql2);
		query2.setParameter("conId", con.getId());
		query2.executeUpdate();
	}

	@Override
	@Transactional
	public List<EmployeesMovements> loadEmpMovements(Integer empId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(EmployeesMovements.class);
		criteria.add(Restrictions.eq("empId", empId));
		List<EmployeesMovements> list = criteria.list();
		return list;
	}

	@Override
	@Transactional
	public void saveVacation(Vacations vacAdd) {
		if (vacAdd != null) {
			EmployeesMovements empMove = new EmployeesMovements();
			empMove.setStarthDate(vacAdd.getVachDate());
			empMove.setStartmDate(vacAdd.getVacmDate());
			empMove.setEndhDate(vacAdd.getVacEndhDate());
			empMove.setEndmDate(vacAdd.getVacEndmDate());
			empMove.setMoveType("1"); // 0 contract 1 vacation
			empMove.setTypeId(vacAdd.getId());
			empMove.setEmpId(vacAdd.getEmpId());
			if (vacAdd.getId() == null) {
				saveObject(vacAdd);
				saveObject(empMove);
			} else {
				updateObject(vacAdd);
				String hql = "delete FROM  EmployeesMovements  Where moveType = '1' and typeId = :vocId";
				Query query = sessionFactory.getCurrentSession().createQuery(hql);
				query.setParameter("vocId", vacAdd.getId());
				query.executeUpdate();
				saveObject(empMove);
			}
		}

	}

	@Override
	@Transactional
	public void deleteVacation(Vacations com) {
		deleteObject(com);
		String hql = "delete FROM  EmployeesMovements  Where moveType = '1' and  typeId = :vacId";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("typeId", com.getId());
		query.executeUpdate();
	}

	@Override
	@Transactional
	public Contracts loadContractByCompanyId(Integer companyId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Contracts.class);
		criteria.add(Restrictions.eq("companyId", companyId));
		criteria.setProjection(Projections.max("id"));
		Integer conId = (Integer) criteria.uniqueResult();
		if (conId == null) {
			return null;
		}

		Contracts con = (Contracts) findEntityById(Contracts.class, conId);
		return con;
	}

	@Override
	@Transactional
	public List<ContractsEmployees> loadEmpByContractId(Integer contractId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ContractsEmployees.class);
		criteria.add(Restrictions.eq("contractId", contractId));
		List<ContractsEmployees> empList = criteria.list();
		return empList;
	}

	@Override
	@Transactional
	public void deleteEmployeeFromContract(ContractsEmployees emp) {
		delete(emp);
		updateEmployeeMovements(emp);
	}

	private void updateEmployeeMovements(ContractsEmployees emp) {
		String hql = "UPDATE EmployeesMovements set endmDate =:endDate, endhDate = :endhD WHERE "
				+ " contractId =:conId and empId = :empId";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("endDate", new Date());
		try {
			query.setParameter("endhD", Utils.grigDatesConvert(new Date()));
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		query.setParameter("conId", emp.getContractId());
		query.setParameter("empId", emp.getEmpId());
		query.executeUpdate();
	}

	@Override
	@Transactional
	public void InsertEmpToContract(Contracts conAdd, List<String> empsAddIds) {
		saveConEmp(conAdd, empsAddIds);
	}

}

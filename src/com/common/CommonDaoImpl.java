package com.common;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.annotation.Transactional;

import com.entities.Attachment;
import com.entities.Expensis;
import com.entities.ExpensisTypes;
import com.entities.SndSrfQbd;
import com.entities.Users;

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
	public boolean saveObject(Object object) {
		try {
			sessionFactory.getCurrentSession().save(object);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

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
		criteria.add(Restrictions.ge("sndDate", dateFrom));
		criteria.add(Restrictions.le("sndDate", dateTo));
		// criteria.add(Restrictions.ne("expensisTypesId", 5)); // tax
		// criteria.add(Restrictions.ne("expensisTypesId", 9)); // asoul
		criteria.add(Restrictions.eq("sndType", sndType));
		List<SndSrfQbd> list = criteria.list();
//		list.stream().mapToInt(snd -> snd.getExpensisTypesId()).filter(snd -> snd != 9);
		return list;
	}

}

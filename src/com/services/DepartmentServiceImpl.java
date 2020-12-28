package com.services;

import java.util.Date;
import java.util.List;

import com.common.CommonDao;
import com.entities.Attachment;
import com.entities.ExpensisTypes;
import com.entities.SndSrfQbd;
import com.entities.Taxs;

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

}

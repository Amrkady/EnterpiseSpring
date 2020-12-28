package com.services;

import java.util.Date;
import java.util.List;

import com.entities.Attachment;
import com.entities.ExpensisTypes;
import com.entities.SndSrfQbd;
import com.entities.Taxs;

public interface DepartmentService {

	public void save(Object object);

	public void update(Object object);

	public void delete(Object object);

	public Object findEntityById(Class entityClass, Integer EntityId);

	public List<Attachment> loadAttachments(Integer stId);

	public List<Taxs> loadTaxs();

	public List<ExpensisTypes> loadExpTypes();

	public List<SndSrfQbd> LoadAllSands(Date dateFrom, Date dateTo, Integer sndType);
}

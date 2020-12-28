
package com.common;

import java.util.Date;
import java.util.List;

import com.entities.Attachment;
import com.entities.Expensis;
import com.entities.ExpensisTypes;
import com.entities.SndSrfQbd;
import com.entities.Users;

public interface CommonDao {

	public boolean saveObject(Object object);

	List<Object> findAll(Class object);

	Object findEntityById(Class entityClass, Integer EntityId);

	boolean deleteObject(Object object);

	boolean updateObject(Object myObject);

	public Users loadUser(final String username, final String password);

	public List<Attachment> findAttachmentsByStationId(Integer stationId);

	public List<Expensis> findExpensisByDates(Date dateFrom, Date dateTo, Integer supType, Integer stId);

	public List<ExpensisTypes> findExpensisTypes(Integer general);

	public List<SndSrfQbd> findSndByType(Integer type, Integer stationId);

	public List<SndSrfQbd> LoadAllSands(Date dateFrom, Date dateTo, Integer sndType);

	public List<SndSrfQbd> findGeneralSndByType(Integer type, Integer stationId);

}

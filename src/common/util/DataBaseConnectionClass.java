/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common.util;

import java.sql.Connection;

import org.springframework.context.ApplicationContext;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.offie.license.SpringContext;

public class DataBaseConnectionClass {

	private static ApplicationContext applicationContext;
	private static com.mchange.v2.c3p0.ComboPooledDataSource dataSource;
	static {
		applicationContext = SpringContext.getApplicationContext();

	}

	public static Connection getConnection() {
		Connection conn = null;
		try {
			dataSource = (ComboPooledDataSource) applicationContext.getBean("DataSource");
			conn = dataSource.getConnection();

		} catch (Exception e) {
			System.err.println("CRITICAL :::: ERROR GETTING DATABASE CONNECTION" + e.getMessage());

		}
		return conn;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static void setApplicationContext(ApplicationContext applicationContext) {
		DataBaseConnectionClass.applicationContext = applicationContext;
	}
}

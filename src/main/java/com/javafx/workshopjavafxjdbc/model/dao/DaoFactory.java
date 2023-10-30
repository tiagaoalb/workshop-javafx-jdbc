package com.javafx.workshopjavafxjdbc.model.dao;

import com.javafx.workshopjavafxjdbc.db.DB;
import com.javafx.workshopjavafxjdbc.model.dao.impl.DepartmentDaoJDBC;
import com.javafx.workshopjavafxjdbc.model.dao.impl.SellerDaoJDBC;

public class DaoFactory {

	private DaoFactory() {
		throw new IllegalStateException("Utility class");
	}

	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC(DB.getConnection());
	}
	
	public static DepartmentDao createDepartmentDao() {
		return new DepartmentDaoJDBC(DB.getConnection());
	}
}

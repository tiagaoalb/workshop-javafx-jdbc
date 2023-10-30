package com.javafx.workshopjavafxjdbc.model.services;

import com.javafx.workshopjavafxjdbc.model.dao.DaoFactory;
import com.javafx.workshopjavafxjdbc.model.dao.DepartmentDao;
import com.javafx.workshopjavafxjdbc.model.entities.Department;

import java.util.List;

public class DepartmentService {

    private final DepartmentDao dao = DaoFactory.createDepartmentDao();

    public List<Department> findAll() {
        return dao.findAll();
    }
}

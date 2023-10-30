package com.javafx.workshopjavafxjdbc.model.services;

import com.javafx.workshopjavafxjdbc.model.entities.Department;

import java.util.ArrayList;
import java.util.List;

public class DepartmentService {

    public List<Department> findAll() {
        List<Department> list = new ArrayList<>();
        list.add(new Department(1, "Books"));
        list.add(new Department(2, "DVD's"));
        list.add(new Department(3, "Computers"));
        list.add(new Department(4, "Smartphones"));
        return list;
    }
}

package com.javafx.workshopjavafxjdbc.model.services;

import com.javafx.workshopjavafxjdbc.model.dao.DaoFactory;
import com.javafx.workshopjavafxjdbc.model.dao.SellerDao;
import com.javafx.workshopjavafxjdbc.model.entities.Seller;

import java.util.List;

public class SellerService {

    private final SellerDao dao = DaoFactory.createSellerDao();

    public List<Seller> findAll() {
        return dao.findAll();
    }

    public void saveOrUpdate(Seller obj) {
        if (obj.getId() == null) {
            dao.insert(obj);
        } else {
            dao.update(obj);
        }
    }

    public void remove(Seller obj) {
        dao.deleteById(obj.getId());
    }
}

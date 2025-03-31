package fr.univangers.service;

import fr.univangers.classes.*;
import fr.univangers.dao.AgentDAO;
import fr.univangers.dao.CalculDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class CalculService {
    final private CalculDAO dao;

    @Autowired
    public CalculService(CalculDAO dao){
        super();
        this.dao = dao;
    }

    public boolean setTBI() throws SQLException {
        return dao.setTBI();
    }

    public boolean setIndemn() throws SQLException {
        return dao.setIndemn();
    }

    public boolean setRafpp() throws SQLException {
        return dao.setRafpp();
    }

    public boolean setSeuil() throws SQLException {
        return dao.setSeuil();
    }

    public boolean calculBaseRetourRecalculeeEmp() throws SQLException {
        return dao.calculBaseRetourRecalculeeEmp();
    }

    public List<EmployeurCSV> getDataEmployeurCSV() throws SQLException {
        return dao.getDataEmployeurCSV();
    }




}

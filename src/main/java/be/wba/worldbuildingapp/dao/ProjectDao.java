package be.wba.worldbuildingapp.dao;

import java.util.List;

public interface ProjectDao {
    void save(String name);
    List<String> findAll();
}

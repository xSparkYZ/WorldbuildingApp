package be.wba.worldbuildingapp.dao;

import java.util.List;

public interface ProjectDao {
    List<String> findAllProjectNames();
    boolean save(String name);
    boolean deleteByName(String name);
    int findIdByName(String name);
}

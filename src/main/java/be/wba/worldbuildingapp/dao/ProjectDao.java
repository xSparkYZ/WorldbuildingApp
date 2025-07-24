package be.wba.worldbuildingapp.dao;

import java.util.List;

public interface ProjectDao {
    boolean save(String name);
    List<String> findAllProjectNames();
    boolean deleteByName(String name);
}

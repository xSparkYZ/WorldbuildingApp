package be.wba.worldbuildingapp.dao;

import java.util.List;

public interface ChapterDao {
    List<String> findByProjectId(int projectId);
    boolean save(int projectId, String chapterName);
}

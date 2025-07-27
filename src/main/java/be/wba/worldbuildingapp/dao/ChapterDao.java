package be.wba.worldbuildingapp.dao;

import be.wba.worldbuildingapp.domain.Chapter;

import java.util.List;

public interface ChapterDao {
    boolean save(int projectId, String name);

    List<Chapter> findByProjectId(int projectId);

    boolean updateContent(int chapterId, String content); // ← Add this line
}

package be.wba.worldbuildingapp.dao.impl;

import be.wba.worldbuildingapp.dao.ChapterDao;
import be.wba.worldbuildingapp.domain.Chapter;
import be.wba.worldbuildingapp.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChapterDaoImpl implements ChapterDao {

    @Override
    public List<Chapter> findByProjectId(int projectId) {
        List<Chapter> chapters = new ArrayList<>();
        String sql = "SELECT * FROM chapters WHERE project_id = ? ORDER BY chapter_order ASC";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, projectId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Chapter chapter = new Chapter(
                        rs.getInt("id"),
                        rs.getInt("project_id"),
                        rs.getString("name"),
                        rs.getInt("chapter_order"),
                        rs.getString("content")
                );
                chapters.add(chapter);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return chapters;
    }

    @Override
    public boolean updateContent(int chapterId, String content) {
        String sql = "UPDATE chapters SET content = ? WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, content);
            stmt.setInt(2, chapterId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean save(int projectId, String name) {
        String sql = "INSERT INTO chapters (project_id, name, chapter_order, content) VALUES (?, ?, ?, '')";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, projectId);
            stmt.setString(2, name);
            stmt.setInt(3, getNextOrder(conn, projectId));

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private int getNextOrder(Connection conn, int projectId) throws SQLException {
        String sql = "SELECT COALESCE(MAX(chapter_order), 0) + 1 FROM chapters WHERE project_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, projectId);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getInt(1) : 1;
        }
    }
}

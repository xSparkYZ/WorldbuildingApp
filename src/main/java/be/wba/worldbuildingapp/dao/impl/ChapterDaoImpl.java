package be.wba.worldbuildingapp.dao.impl;

import be.wba.worldbuildingapp.dao.ChapterDao;
import be.wba.worldbuildingapp.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChapterDaoImpl implements ChapterDao {

    @Override
    public List<String> findByProjectId(int projectId) {
        List<String> chapters = new ArrayList<>();
        String sql = "SELECT name FROM chapters WHERE project_id = ? ORDER BY chapter_order ASC";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, projectId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                chapters.add(rs.getString("name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return chapters;
    }

    @Override
    public boolean save(int projectId, String chapterName) {
        String sql = "INSERT INTO chapters (project_id, name, chapter_order, content) VALUES (?, ?, ?, '')";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, projectId);
            stmt.setString(2, chapterName);
            stmt.setInt(3, getNextOrder(conn, projectId));

            return stmt.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private int getNextOrder(Connection conn, int projectId) throws SQLException {
        String query = "SELECT COALESCE(MAX(chapter_order), 0) + 1 AS next_order FROM chapters WHERE project_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, projectId);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getInt("next_order") : 1;
        }
    }
}

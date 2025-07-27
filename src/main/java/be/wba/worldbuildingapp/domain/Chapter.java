package be.wba.worldbuildingapp.domain;

public class Chapter {
    private int id;
    private int projectId;
    private String name;
    private int order;
    private String content;

    public Chapter(int id, int projectId, String name, int order, String content) {
        this.id = id;
        this.projectId = projectId;
        this.name = name;
        this.order = order;
        this.content = content;
    }

    // Overload for listing only
    public Chapter(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getProjectId() {
        return projectId;
    }

    public String getName() {
        return name;
    }

    public int getOrder() {
        return order;
    }

    public String getContent() {
        return content;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setContent(String content) {
        this.content = content;
    }

    // This is critical for displaying in ListView
    @Override
    public String toString() {
        return name;
    }
}

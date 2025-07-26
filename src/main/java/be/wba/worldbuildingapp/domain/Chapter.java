package be.wba.worldbuildingapp.domain;

public class Chapter {
    private int id;
    private int projectId;
    private String title;
    private String content;
    private int position;

    public Chapter(int id, int projectId, String title, String content, int position) {
        this.id = id;
        this.projectId = projectId;
        this.title = title;
        this.content = content;
        this.position = position;
    }

    public Chapter(int projectId, String title, int position) {
        this(0, projectId, title, "", position);
    }

    // Getters and setters
    public int getId() { return id; }
    public int getProjectId() { return projectId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public int getPosition() { return position; }

    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
    public void setPosition(int position) { this.position = position; }
}

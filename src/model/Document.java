package model;

public class Document {
	String id;
	String title;
	String content;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return (content == null) ? "" : content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}

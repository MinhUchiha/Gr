package com.kyuubi.gr;

/**
 * Created by Administrator on 20/05/2017.
 */

public class Post {
    private String postid;
    private String classid;
    private String username;
    private String content;
    private String timestamp;

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


    public Post(String postid, String classid, String username, String content, String timestamp) {
        this.postid = postid;
        this.classid = classid;
        this.username = username;
        this.content = content;
        this.timestamp = timestamp;
    }

}

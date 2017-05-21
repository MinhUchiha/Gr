package com.kyuubi.gr;

/**
 * Created by Administrator on 21/05/2017.
 */

public class Comment {
    private String commentid;
    private int postid;
    private String username;
    private String content;
    private String timestamp;

    public String getCommentid() {
        return commentid;
    }

    public void setCommentid(String commentid) {
        this.commentid = commentid;
    }

    public int getPostid() {
        return postid;
    }

    public void setPostid(int postid) {
        this.postid = postid;
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

    public Comment(String commentid, int postid, String username, String content, String timestamp) {
        this.commentid = commentid;
        this.postid = postid;
        this.username = username;
        this.content = content;
        this.timestamp = timestamp;
    }
}

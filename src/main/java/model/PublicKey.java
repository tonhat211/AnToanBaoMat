package model;

import java.time.LocalDateTime;

public class PublicKey {
    public int id;
    public int userID;
    public String content;
    public LocalDateTime createAt;
    public LocalDateTime endAt;

    public PublicKey(int id, int userID, String content, LocalDateTime createAt, LocalDateTime endAt) {
        this.id = id;
        this.userID = userID;
        this.content = content;
        this.createAt = createAt;
        this.endAt = endAt;
    }

    public PublicKey(int id,int userID, String content) {
        this.id = id;
        this.userID = userID;
        this.content = content;
    }
}

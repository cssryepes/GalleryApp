
package com.kogi.galleryapp.domain.entities;

import java.util.List;

public class Feed {

    private String id;
    private String link;
    private String createdTime;
    private List<String> tags;
    private List<Image> images;
    private User user;
    private long likes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }

    @Override
    public String toString() {
        return "Feed{" +
                "id='" + id + '\'' +
                ", link='" + link + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", tags=" + tags +
                ", images=" + images +
                ", user=" + user +
                ", likes=" + likes +
                '}';
    }
}

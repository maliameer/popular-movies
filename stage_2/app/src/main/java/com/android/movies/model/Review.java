package com.android.movies.model;

public class Review {

    private String author;
    private String content;
    private String subContent;
    private String url;

    public Review(String author, String content, String url) {

        this.author = author;
        this.content = content;
        this.url = url;

        if (content == null || content.trim().isEmpty()) {
            content = "Review by " + this.author;
        } else {

            int subContentThreshold = 50;
            if ((content = content.trim()).length() > subContentThreshold) {
                this.subContent = content.substring(0, subContentThreshold) + " ...";
            } else {
                this.subContent = content;
            }

        }

    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSubContent() {
        return subContent;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Review Details:\n");
        stringBuilder.append("\tAuthor = " + author + "\n");
        stringBuilder.append("\tContent = " + content + "\n");
        stringBuilder.append("\tURL = " + url);

        return stringBuilder.toString();

    }

}
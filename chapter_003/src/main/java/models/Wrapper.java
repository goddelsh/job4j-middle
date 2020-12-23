package models;

import java.util.List;

public class Wrapper {
    private String status;
    private Actions action;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    private Integer userId;
    private List<Item> items;
    private List<Category> categories;

    public Wrapper(String status) {
        this.status = status;
    }

    public Wrapper(String status, List<Item> items) {
        this.status = status;
        this.items = items;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Actions getAction() {
        return action;
    }

    public void setAction(Actions action) {
        this.action = action;
    }

    public List<Item>  getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}

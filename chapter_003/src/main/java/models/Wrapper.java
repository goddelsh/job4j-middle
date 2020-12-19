package models;

import java.util.List;

public class Wrapper {
    private String status;
    private Actions action;
    private List<Item> items;

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
}

package services;

import models.Item;

import java.util.List;

public interface Store {

    void addTask(Item item);

    List<Item> getItems(boolean filtred);

    void markTask(Item item);
}

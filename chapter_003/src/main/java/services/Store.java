package services;

import models.Category;
import models.Item;
import models.User;

import java.util.List;

public interface Store {

    void addTask(Item item);

    List<Item> getItems(boolean filtred);

    void markTask(Item item);

    User getUser(User user);

    List<Item> getItemsByUser(Integer userId, boolean filtred);

    User createUser(User user);

    List<Category> getCategories();

}

package servlets;

import com.google.gson.Gson;
import models.Actions;
import models.Item;
import models.User;
import models.Wrapper;
import services.DBStore;
import services.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class TasksServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Wrapper parsedRequest = new Gson().fromJson(
                new InputStreamReader(req.getInputStream(),
                        StandardCharsets.UTF_8), Wrapper.class);

        Store store = DBStore.getInstance();
        String response = "";
        User user = (User) req.getSession().getAttribute("user");
        if (Actions.ADD.equals(parsedRequest.getAction())) {
            Item item = parsedRequest.getItems().get(0);
            item.setUser(user);
            store.addTask(item);
            response = new Gson().toJson(new Wrapper("OK"));
        } else if (Actions.GETALL.equals(parsedRequest.getAction())) {
            List<Item> items =  store.getItemsByUser(user.getId(), false);
            response = new Gson().toJson(new Wrapper("OK", items));
        } else if (Actions.GETUNDONE.equals(parsedRequest.getAction())) {
            List<Item> items =  store.getItemsByUser(user.getId(), true);
            response = new Gson().toJson(new Wrapper("OK", items));
        } else if (Actions.MARK.equals(parsedRequest.getAction())) {
            store.markTask(parsedRequest.getItems().get(0));
            response = new Gson().toJson(new Wrapper("OK"));
        } else {
            response = new Gson().toJson(new Wrapper("UNKNOWN"));
        }
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try (PrintWriter writer = new PrintWriter(resp.getOutputStream())) {
            writer.println(response);
        }
    }
}

package servlets;

import com.google.gson.Gson;
import models.User;
import models.Wrapper;
import services.AnnouncementService;
import services.AnnouncementServiceImpl;
import services.DBStoreService;
import services.StoreService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class AnnouncementServlet extends HttpServlet {

    private AnnouncementService announcementService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Wrapper parsedRequest = new Gson().fromJson(
                new InputStreamReader(req.getInputStream(),
                        StandardCharsets.UTF_8), Wrapper.class);

        User user = (User) req.getSession().getAttribute("user");
        Wrapper responseWrapper = new Wrapper();
        switch (parsedRequest.getAction()) {
            case GET_ANNOUNCEMENTS:
                responseWrapper.setAnnouncements(DBStoreService.getStoreService().getAnnouncements(user));
                responseWrapper.setStatus("OK");
                break;
            case GET_CARTYPES:
                responseWrapper.setCarTypes(DBStoreService.getStoreService().getCarTypes());
                responseWrapper.setStatus("OK");
                break;
            case GET_CARBODIES:
                responseWrapper.setCarBodies(DBStoreService.getStoreService().getCarBodies());
                responseWrapper.setStatus("OK");
                break;
            case GET_CARBRANDS:
                responseWrapper.setCarBrands(DBStoreService.getStoreService().getCarBrands());
                responseWrapper.setStatus("OK");
                break;
            case GET_CARMODELS:
                responseWrapper.setCarModels(DBStoreService.getStoreService().getCarModels());
                responseWrapper.setStatus("OK");
                break;
            case GET_CARPHOTOS:
                responseWrapper.setCarPhotos(DBStoreService.getStoreService().getCarPhotos(parsedRequest
                        .getAnnouncements().stream()
                        .findFirst().orElse(null)));
                responseWrapper.setStatus("OK");
                break;
            case GET_CARENGINES:
                responseWrapper.setCarEngines(DBStoreService.getStoreService().getCarEngines());
                responseWrapper.setStatus("OK");
                break;
            case GET_SELLERCONTACS:
                responseWrapper.setSellerContacts(DBStoreService.getStoreService().getSellerContact(user));
                responseWrapper.setStatus("OK");
                break;
            case ADD_ANNOUNCEMENTS:
                DBStoreService.getStoreService().addAnnouncement(parsedRequest
                        .getAnnouncements().stream()
                        .findFirst().orElse(null));
                responseWrapper.setStatus("OK");
                break;
            case EDIT_ANNOUNCEMENTS:
                DBStoreService.getStoreService().editAnnouncement(parsedRequest
                        .getAnnouncements().stream()
                        .findFirst().orElse(null));
                responseWrapper.setStatus("OK");
                break;
            case ADD_SELLERCONTACT:
                DBStoreService.getStoreService().addSellerContact(parsedRequest
                        .getSellerContacts().stream()
                        .findFirst().orElse(null));
                responseWrapper.setStatus("OK");
                break;
            case DELETE_SELLERCONTACT:
                DBStoreService.getStoreService().deleteSellerContact(parsedRequest
                        .getSellerContacts().stream()
                        .findFirst().orElse(null));
                responseWrapper.setStatus("OK");
                break;
            case ADD_CARPHOTO:
                DBStoreService.getStoreService().addCarPhoto(parsedRequest
                        .getCarPhotos().stream()
                        .findFirst().orElse(null));
                responseWrapper.setStatus("OK");
                break;
            case DELETE_CARPHOTO:
                DBStoreService.getStoreService().deleteCarPhoto(parsedRequest
                        .getCarPhotos().stream()
                        .findFirst().orElse(null));
                responseWrapper.setStatus("OK");
                break;
            default:
        }

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try (PrintWriter writer = new PrintWriter(resp.getOutputStream())) {
            writer.println(new Gson().toJson(responseWrapper));
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
        announcementService = new AnnouncementServiceImpl();
        DBStoreService.getStoreService().init();
    }
}

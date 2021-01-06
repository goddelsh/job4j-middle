package services;

import models.*;

import java.util.List;

public interface StoreService {

    void init();

    List<CarBody> getCarBodies();
    List<CarBrand> getCarBrands();
    List<CarEngine> getCarEngines();
    List<CarModel> getCarModels();
    List<CarType> getCarTypes();

    List<SellerContact> getSellerContact(User user);
    List<Announcement> getAnnouncements(User user);
    Announcement getFullAnnouncements(Announcement announcement);
    List<CarPhoto> getCarPhotos(Announcement announcement);

    void addAnnouncement(Announcement announcement);
    void editAnnouncement(Announcement announcement);

    void addCarPhoto(CarPhoto carPhoto);
    void deleteCarPhoto(CarPhoto carPhoto);

    void addSellerContact(SellerContact sellerContact);
    void deleteSellerContact(SellerContact sellerContact);

    User addUser(User user);
    User getUser(User user);
}

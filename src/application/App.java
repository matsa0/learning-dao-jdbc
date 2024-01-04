package application;

import java.util.Date;

import model_dao.DaoFactory;
import model_dao.SellerDao;
import model_entities.Department;
import model_entities.Seller;
public class App {
    public static void main(String[] args) throws Exception {
        SellerDao sellerDao = DaoFactory.createSellerDao();

        Seller sl1 = sellerDao.findById(3);

        System.out.println(sl1);
    }
}

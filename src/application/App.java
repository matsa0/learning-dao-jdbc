package application;

import java.util.Date;

import model_dao.DaoFactory;
import model_dao.SellerDao;
import model_entities.Department;
import model_entities.Seller;
public class App {
    public static void main(String[] args) throws Exception {
        Department dp1 = new Department(1, "Sales");
        System.out.println(dp1);

        System.out.println();

        Seller sl1 = new Seller(1, "Bob Brown", "bobrown@email.com", new Date(), 3000.00, dp1);
        System.out.println(sl1);

        SellerDao sellerDao = DaoFactory.createSellerDao();
    }
}

package application;

import java.util.List;

import model_dao.DaoFactory;
import model_dao.SellerDao;
import model_entities.Department;
import model_entities.Seller;
public class App {
    public static void main(String[] args) throws Exception {
        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("=== TEST 1: seller findById ===");
        Seller sl1 = sellerDao.findById(3);
        System.out.println(sl1);
        
        System.out.println("\n=== TEST 2: seller findByDepartment ===");
        List<Seller> sellerList = sellerDao.findByDepartment(new Department(2, null));
        for(Seller sl : sellerList) {
            System.out.println(sl + "\n");
        }
    }
}

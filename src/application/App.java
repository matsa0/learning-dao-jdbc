package application;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model_dao.DaoFactory;
import model_dao.SellerDao;
import model_entities.Department;
import model_entities.Seller;
public class App {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("=== TEST 1: seller findById ===");
        Seller sl1 = sellerDao.findById(3);
        System.out.println(sl1);
        
        System.out.println("\n\n=== TEST 2: seller findByDepartment ===");
        List<Seller> sellerList = sellerDao.findByDepartment(new Department(2, null));
        for(Seller sl : sellerList) {
            System.out.println(sl);
        }

        System.out.println("\n\n=== TEST 3: seller findAll ===");
        sellerList = sellerDao.findAll();
        for(Seller sl : sellerList) {
            System.out.println(sl);
        }
        
        System.out.println("\n\n=== TEST 4: seller insert ===");
        Seller sl2 = new Seller("Carl Jhonson", "carljhonson@email.com", new Date(), 5642.56, new Department(3));
        sellerDao.insert(sl2);
        System.out.println(sl2);
    
        System.out.println("\n\n=== TEST 5: seller update ===");
        sl1 = sellerDao.findById(8); //Carreguei os dados do vendedor de id 8 em sl1
        sl1.setName("Melo Lima");
        sl1.setEmail("melo@hotmail.com");
        sl1.setBirthDate(new Date());
        sl1.setBaseSalary(5461.41);
        sellerDao.update(sl1);
    
        System.out.println("\n\n TEST 6: seller delete");
        System.out.println("Enter id for delete: ");
        int id = sc.nextInt();
        sellerDao.deleteById(id);
        sc.close();
    
    }
}

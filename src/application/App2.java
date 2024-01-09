package application;

import java.util.ArrayList;
import java.util.List;

import model_dao.DaoFactory;
import model_dao.DepartmentDao;
import model_entities.Department;

public class App2 {
    public static void main(String[] args) throws Exception {
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
        Department dep1 = new Department("Security");
        /* Comentado mas funcionando
        System.out.println("=== TEST 1: department insert ===");
        departmentDao.insert(dep1);
        System.out.println(dep1);
        */
        System.out.println("=== TEST 2: department findById ===");
        dep1 = departmentDao.findById(3);
        System.out.println(dep1);

        System.out.println("=== TEST 3: department update ===");
        dep1 = departmentDao.findById(12);
        dep1.setName("Games");
        departmentDao.update(dep1);
        System.out.println(dep1);

        /* Comentado mas funcionando
        System.out.println("=== TEST 4: department delete ===");
        int id = 10;
        departmentDao.deleteById(id);
        */

        System.out.println("=== TEST 5: department findAll ===");
        List<Department> dept = new ArrayList<>();
        dept = departmentDao.findAll();
        for(Department dp : dept) {
            System.out.println(dp);
        }
    }
}

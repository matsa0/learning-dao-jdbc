package application;

import model_dao.DaoFactory;
import model_dao.DepartmentDao;
import model_entities.Department;

public class App2 {
    public static void main(String[] args) throws Exception {
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
        Department dep1 = new Department("Security");
        /*
        System.out.println("=== TEST 1: department insert ===");
        departmentDao.insert(dep1);
        System.out.println(dep1);
        */
        System.out.println("=== TEST 2: department findById ===");
        dep1 = departmentDao.findById(3);
        System.out.println(dep1);

        System.out.println("=== TEST 2: department update ===");
        dep1 = departmentDao.findById(12);
        dep1.setName("Games");
        departmentDao.update(dep1);
        System.out.println(dep1);
    }
}

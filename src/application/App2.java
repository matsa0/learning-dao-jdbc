package application;

import model_dao.DaoFactory;
import model_dao.DepartmentDao;
import model_entities.Department;

public class App2 {
    public static void main(String[] args) throws Exception {
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
        
        System.out.println("=== TEST 1: department insert ===");
        Department dep1 = new Department("Security");
        departmentDao.insert(dep1);
        System.out.println(dep1);
    }
}

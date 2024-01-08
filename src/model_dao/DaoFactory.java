package model_dao;

import db.Db;
import model_dao_impl.DepartmentDaoJDBC;
import model_dao_impl.SellerDaoJDBC;

public class DaoFactory {
    /*
    retorna uma instancia de SellerDaoJDBC internamente com o obejtivo de não expor implementação
    que foi realizada na classe SellerDaoJDBC que implementa a interface SellerDao
    */
    public static SellerDao createSellerDao() throws ClassNotFoundException {
        return new SellerDaoJDBC(Db.getConnection()); 

    }

    public static DepartmentDao createDepartmentDao() throws ClassNotFoundException {
        return new DepartmentDaoJDBC(Db.getConnection());
    }
}

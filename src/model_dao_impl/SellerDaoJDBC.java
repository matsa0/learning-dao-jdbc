package model_dao_impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.Db;
import db.DbException;
import model_dao.SellerDao;
import model_entities.Department;
import model_entities.Seller;

public class SellerDaoJDBC implements SellerDao{

    private Connection conn; //Objeto conn  a disposição para uso dentro da  classe

    public SellerDaoJDBC(Connection conn) { //dependência com a Conexão ao banco de dados
        this.conn = conn;
    }

    @Override
    public void insert(Seller obj) {
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }

    @Override
    public void update(Seller obj) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void deleteById(Integer id) {
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            st = conn.prepareStatement(
                "SELECT seller.*, department.Name as DepName " +
                "FROM seller INNER JOIN department " +
                "ON seller.DepartmentId = department.Id " +
                "WHERE seller.Id = ?"
            );

            st.setInt(1, id); //interrogação (1) e o valor que será passado
            rs = st.executeQuery(); //executa comando sql e armazena o resultado no ResultSet em formato de tabela  

            if(rs.next()) { //testa se existe algum resultado, se exite você navega por eles
                Department dp = instantiateDepartment(rs); //instanciando o objeto dp

                Seller sl1 = instantiateSeller(rs, dp); //instanciando o objeto sl1

            }
            return null;
        } 
        catch(SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            Db.closeStatment(st);
            Db.closeResultSet(rs);
        }
    }

    @Override
    public List<Seller> findAll() {
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException{ //vai capturar a exceção e lançar para a camada de cima
        Department dp = new Department();
        dp.setId(rs.getInt("DepartmentId"));
        dp.setName(rs.getString("DepName"));

        return dp;
    }

    private Seller instantiateSeller(ResultSet rs, Department dp) throws SQLException{
        Seller sl = new Seller();   
        sl.setId(rs.getInt("Id"));
        sl.setName(rs.getString("Name"));
        sl.setEmail(rs.getString("Email"));
        sl.setBirthDate(rs.getDate("BirthDate"));
        sl.setBaseSalary(rs.getDouble("BaseSalary"));
        sl.setDepartment(dp); //Acessando o objeto dp

        return sl;
    }
    
}

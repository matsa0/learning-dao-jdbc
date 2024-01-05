package model_dao_impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                return sl1;
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

    @Override
    public List<Seller> findByDepartment(Department dp) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                "SELECT seller.*, department.Name as DepName " +
                "FROM seller INNER JOIN department " +
                "ON seller.DepartmentId = department.Id " +
                "WHERE DepartmentId = ? " +
                "ORDER BY Name"
            );

            st.setInt(1, dp.getId()); //interrogação (1) e o valor que será passado
            rs = st.executeQuery(); //executa comando sql e armazena o resultado no ResultSet em formato de tabela

            List<Seller> sellerList =  new ArrayList<>(); //criando uma lista de vendedores
            Map<Integer, Department> map = new HashMap<>();

            while(rs.next()) { //enquanto existir um próximo resultado
                //Não posso instanciar um mesmo departamento várias vezes, então eu verifico se o departamento já existe no map
                Department dep = map.get(rs.getInt("DepartmentId")); //buscando o departamento no map pelo ID

                if(dep == null) {
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep); //adicionando o departamento no map
                }

                Seller sl = instantiateSeller(rs, dep);
                sellerList.add(sl); //adicionando o vendedor na lista
            }
            return sellerList;
        }
        catch(SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            Db.closeStatment(st);
            Db.closeResultSet(rs);
        }
    }

    //Métodos auxiliares
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

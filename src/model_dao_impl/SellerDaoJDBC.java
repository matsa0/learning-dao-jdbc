package model_dao_impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    public void insert(Seller sl) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                "INSERT INTO SELLER " +
                "(Name, Email, BirthDate, BaseSalary, DepartmentId) " +
                "VALUES (?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS /*Usando para inserções no banco de dados.
                Permite recuperar as chaves geradas automaticamente de uma coluna(auto_increment) após a execução de uma instrução SQL que 
                insere novos registros em uma tabela */
            );
            st.setString(1, sl.getName());
            st.setString(2, sl.getEmail());
            st.setDate(3, new Date(sl.getBirthDate().getTime()));
            st.setDouble(4, sl.getBaseSalary());
            st.setInt(5, sl.getDepartment().getId()); //pega o Id de dedpartment através do  etDepartment() de Seller

            /*executeUpdate(): É usado para executar instruções SQL que realizam ações de modificação nos dados no banco de dados, 
            como INSERT, UPDATE ou DELETE.*/
            int rowsAffected = st.executeUpdate(); 

            if(rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys(); // Obtém um ResultSet contendo as chaves geradas após a operação de inserção bem-sucedida.
                if(rs.next()) { //verifica se ainda possui chaves
                    int id = rs.getInt(1); //coluna das chaves
                    sl.setId(id); //define o id gerado pelo setGeneratedKeys() para o novo Seller
                }
                Db.closeResultSet(rs);
            }
            else {
                throw new DbException("ERRO! Nenhuma linha afetada");
            }
        }
        catch(SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            Db.closeStatment(st);
        }
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
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                "SELECT seller.*, department.Name as DepName " +
                "FROM seller INNER JOIN department " +
                "ON seller.DepartmentId = department.Id " +
                "ORDER BY Name"
            );

            rs = st.executeQuery();
            List<Seller> allSellerList = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while(rs.next()) {
                Department dep = map.get(rs.getInt("DepartmentId")); //buscando o departamento no map pelo ID

                if(dep == null){
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep); //adicionando o departamento no map
                }
                Seller sl = instantiateSeller(rs, dep);
                allSellerList.add(sl);
            }
            return allSellerList;
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

            st.setInt(1, dp.getId()); 
            rs = st.executeQuery(); 

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
                sellerList.add(sl); //adicionando o vendedor na lista de vendedores
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

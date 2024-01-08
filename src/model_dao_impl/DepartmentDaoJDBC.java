package model_dao_impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.mysql.cj.x.protobuf.MysqlxPrepare.Prepare;

import db.Db;
import db.DbException;
import model_dao.DepartmentDao;
import model_entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {

    private Connection conn = null;
    
    public DepartmentDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Department dp) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                "INSERT INTO department " +
                "(Name) " +
                "VALUES (?)", 
                Statement.RETURN_GENERATED_KEYS
            );

            st.setString(1, dp.getName());

            int rowsAffected = st.executeUpdate();

            if(rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if(rs.next()) {
                    int id = rs.getInt(1);
                    dp.setId(id);
                }
                Db.closeResultSet(rs);
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
    public void update(Department dp) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                "UPDATE department " +
                "SET Name = ? " +
                "WHERE Id = ?"
            );

            st.setString(1, dp.getName());
            st.setInt(2, dp.getId());
            
            st.executeUpdate();
        }
        catch(SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void deleteById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                "SELECT department.* " +
                "FROM department " +
                "WHERE Id = ?" 
            );
            st.setInt(1, id);
            rs = st.executeQuery();

            if(rs.next()) {
                Department dp = instantiateDepartment(rs);

                return dp;
            }
            return null;
        }
        catch(SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public List<Department> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }
    
    //Método auxilair
    private Department instantiateDepartment(ResultSet rs) throws SQLException{
        Department dp = new Department();
        dp.setId(rs.getInt("Id"));
        dp.setName(rs.getString("Name"));

        return dp;
    }
}


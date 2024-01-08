package model_dao_impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void deleteById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public Department findById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<Department> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }
    
}

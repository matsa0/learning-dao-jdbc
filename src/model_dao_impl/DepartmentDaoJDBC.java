package model_dao_impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                "DELETE FROM department " +
                "WHERE Id = ? "
            );

            st.setInt(1, id);

            int rowsAffected = st.executeUpdate();
            if(rowsAffected == 0) {
                throw new DbException("ERRO! Nenhuma linha alterada!");
            }

            System.out.println("DELETE completed! Rows Affected > " + rowsAffected);
        }
        catch(SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            Db.closeStatment(st);
        }
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
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                "SELECT department.* " +
                "FROM department"
            );

            rs = st.executeQuery();
            List<Department> allDepartments = new ArrayList<>();

            while(rs.next()) {
                Department dep = instantiateDepartment(rs);
                allDepartments.add(dep);
            }
            return allDepartments;
        }
        catch(SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            Db.closeStatment(st);
            Db.closeResultSet(rs);
        }
    }
    
    //Método auxilair
    private Department instantiateDepartment(ResultSet rs) throws SQLException{
        Department dp = new Department();
        dp.setId(rs.getInt("Id"));
        dp.setName(rs.getString("Name"));

        return dp;
    }
}


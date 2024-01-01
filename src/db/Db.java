package db;

import java.util.Properties;
import java.io.IOException;
import java.sql.*;
import java.io.FileInputStream;

public class Db {
    //conectar um banco de dados é instanciar um objeto Connection
    private static Connection conn = null;

    public static Connection getConnection() throws ClassNotFoundException {
        if(conn == null) { //se não houver conexão, realizar
            try {
                Properties props = loadProperties(); //carregar as propriedades do arquivo db.properties
                String url = props.getProperty("dburl"); //pegar a url do arquivo db.properties

                Class.forName("com.mysql.cj.jdbc.Driver");

                conn = DriverManager.getConnection(url, props); //instanciar a conexão com o banco de dados
            } 
            catch(SQLException e) {
                throw new DbException(e.getMessage());
            }
        }   
        return conn;
    }

    //método para fechar a conexão com o banco de dados
    public static void closeConnection() {
        if(conn != null) {
            try {
                conn.close();
            }
            catch(SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }


    //método para carregar as propriedades do arquivo db.properties
    private static Properties loadProperties() {
        //FIleInputStream lê dados de arquivos e nesse caso, do db.properties
        try(FileInputStream fs = new FileInputStream("db.properties")) {
            Properties props = new Properties();
            props.load(fs);
            return props;
        }
        catch(IOException e) {
            throw new DbException("Erro ao carregar as propriedades do arquivo db.properties: " + e.getMessage());
        }
    }

    //Statment são usados para executar instruções SQL estáticas e obter resultados produzidos por elas
    public static void closeStatment(Statement st) {
        if(st != null) {
            try {
                st.close();
            }
            catch(SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    //ResultSet é uma tabela de dados que representa o resultado de uma consulta SQL que é resultado de um statment. É possível iterar um ResultSet
    public static void closeResultSet(ResultSet rs) {
        if(rs != null) {
            try {
                rs.close();
            }
            catch(SQLException e) {
                throw new DbException(e.getMessage());
            }
         }
    }

}
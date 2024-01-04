package model_entities;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Department implements Serializable{

    private static final long serialVersionUID = 1L;
    private int id;
    private String name;

    public Department() {}

    public Department(int id, String name) {
        this.id = id;
        this.name = name;
    }


    //Getters and Setters
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    //toString
    @Override
    public String toString() {
        return "***Department***" + 
        "\nId > " + this.id + 
        "\nName > "  + this.name;
    }

    //Equals and HashCode
    @Override
    public boolean equals(Object obj) {
        //Verificar se o objeto é ele mesmo
        if(this == obj) {
            return true;
        }

        //Se o objeto passado for nulo ou não for uma instância da mesma classe, os objetos não podem ser iguais, então o método retorna false.
        if(obj == null || this.getClass() != obj.getClass())  {
            return false;
        }

        //Realizar o cast do objeto para a classe Department
        Department other = (Department) obj;

        return this.id == other.id; //Comparar os IDs
    }

    @Override
    public int hashCode() {
        //Este método está usando a classe utilitária Objects do Java para calcular o código hash baseado no campo id.
        return Objects.hash(id);
    }

}

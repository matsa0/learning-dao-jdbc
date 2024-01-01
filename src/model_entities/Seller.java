package model_entities;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


public class Seller implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String name;
    private String email;
    private Date birthDate;
    private double baseSalary;
    private Department department;

    public Seller() {}

    public Seller(int id, String name, String email, Date birthDate, double baseSalary, Department department) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.baseSalary = baseSalary;
        this.department = department;
    }   

    //Getters and Setters
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public double getBaseSalary() {
        return baseSalary;
    }
    public void setBaseSalary(double baseSalary) {
        this.baseSalary = baseSalary;
    }

    public Department getDepartment() {
        return department;
    }
    public void setDepartment(Department department) {
        this.department = department;
    }

    //toString
    @Override
    public String toString() {
        return "***Seller***" +
        "\nId > " + this.id +
        "\nName > " + this.name +
        "\nEmail > " + this.email +
        "\nBirthDate > " + this.birthDate +
        "\nBaseSalary > " + this.baseSalary;
    }

    //Equals and HashCode
    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }

        if(o == null || this.getClass() != o.getClass()) {
            return false;
        }

        Seller other = (Seller) o;

        return this.id == other.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

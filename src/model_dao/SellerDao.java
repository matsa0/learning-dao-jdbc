package model_dao;

import java.util.List;

import model_entities.Seller;
import model_entities.Department;

public interface SellerDao {
    public void insert(Seller obj); //inserir objeto no banco de dados
    public void update(Seller obj); //atualizar objeto no banco de dados
    public void deleteById(Integer id); //deletar objeto no banco de dados
    public Seller findById(Integer id); //encontrar objeto no banco de dados
    public List<Seller> findAll(); //encontrar todos os objetos no banco de dados
    public List<Seller> findByDepartment(Department dp); //encontrar todos os objetos no banco de dados
}

package model_dao;

import java.util.List;
import model_entities.Department;

public interface DepartmentDao {
    public void insert(Department obj); //inserir objeto no banco de dados
    public void update(Department obj); //atualizar objeto no banco de dados
    public void deleteById(Integer id); //deletar objeto no banco de dados
    public Department findById(Integer id); //encontrar objeto no banco de dados
    public List<Department> findAll(); //encontrar todos os objetos no banco de dados
}

/*
 * O padrão DAO é um padrão de projeto que abstrai e encapsula os mecanismos de acesso a dados escondendo os 
 * detalhes da execução da origem dos dados.
*/
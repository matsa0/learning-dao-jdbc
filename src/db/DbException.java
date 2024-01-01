package db;

//Exceção personalizada que extende de RuntimeException
//O Compilador não obriga a tratar essa exceção
public class DbException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DbException(String msg) {
        super(msg); //passar a mensagem para o construtor da superclasse
    }
}

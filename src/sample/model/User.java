package sample.model;

public class User {

    public String getIdAcc() {
        return idAcc;
    }

    public void setIdAcc(String idAcc) {
        this.idAcc = idAcc;
    }

    private String idAcc;
    private String login;
    private String password;
    private String status;
    private String role;


    public User() {
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(String login, String password, String status, String role) {
        this.login = login;
        this.password = password;
        this.status = status;
        this.role = role;
    }

    public User(String idAcc, String login, String password, String status, String role) {
        this.idAcc = idAcc;
        this.login = login;
        this.password = password;
        this.status = status;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getId() {
        return idAcc;
    }

    public void setId(String idAcc) {
        this.idAcc = idAcc;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }



    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}

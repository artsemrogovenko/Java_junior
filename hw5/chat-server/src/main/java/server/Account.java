package server;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "accounts")
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "логин")
    private String login;

    @Column(name = "пароль")
    private String passwd;

    public Account() {
    }

    public Account(String nick, String passwd) {
        this.login = nick;
        this.passwd = passwd;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPasswd() {
        return login;
    }

}

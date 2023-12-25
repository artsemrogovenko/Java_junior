package client;

import java.io.*;

public class Account implements Externalizable, Serializable {
    private String id, login, passwd;

    public Account(String login, String passwd) {
        this.login = login;
        this.passwd = passwd;
    }

    public Account() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getNick() {
        return login;
    }

    public String getPasswd() {
        return passwd;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(getId());
        out.writeUTF(getNick());
        out.writeUTF(getPasswd());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        Account newAcc = (Account) in.readObject();
        setId(newAcc.getId());
        setLogin(newAcc.getLogin());
    }
}

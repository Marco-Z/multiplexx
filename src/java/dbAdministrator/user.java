package dbAdministrator;

import java.io.Serializable;

public class user implements Serializable {
    private int id;
    private String email;
    private String nome;
    private String cognome;
    private String password;
    private float credito;
    private String tipo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    /**
     * @return the name
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param name the name to set
     */
    public void setNome(String name) {
        this.nome = name;
    }

    /**
     * @return the surname
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * @param surname the surname to set
     */
    public void setCognome(String surname) {
        this.cognome = surname;
    }

    /**
     * @return the credit
     */
    public float getCredito() {
        return credito;
    }

    /**
     * @param credito the credit to set
     */
    public void setCredit(float credito) {
        this.credito = credito;
    }

    /**
     * @return the type
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param type the type to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
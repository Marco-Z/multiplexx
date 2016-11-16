/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbAdministrator;

import java.io.Serializable;

/**
 * incorporara campi e funzioni per descivere un film
 * @author Lorenzo
 */
public class film implements Serializable{
    private String titolo;
    private String locandina;
    private String trailer;
    private String genere;
    private String regia;
    private String naz;
    private String tipo;
    private int anno=0;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getRegia() {
        return regia;
    }

    public void setRegia(String regia) {
        this.regia = regia;
    }

    public String getNaz() {
        return naz;
    }

    public void setNaz(String naz) {
        this.naz = naz;
    }

    public int getAnno() {
        return anno;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    /**
     * @return the titolo
     */
    public String getTitolo() {
        return titolo;
    }

    /**
     * @param titolo the titolo to set
     */
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    /**
     * @return the locandina
     */
    public String getLocandina() {
        return locandina;
    }

    /**
     * @param locandina the locandina to set
     */
    public void setLocandina(String locandina) {
        this.locandina = locandina;
    }

    /**
     * @return the trailer
     */
    public String getTrailer() {
        return trailer;
    }

    /**
     * @param trailer the trailer to set
     */
    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    /**
     * @return the genere
     */
    public String getGenere() {
        return genere;
    }

    /**
     * @param genere the genere to set
     */
    public void setGenere(String genere) {
        this.genere = genere;
    }
}

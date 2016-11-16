/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbAdministrator;

import java.sql.Timestamp;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.Vector;

/**
 *
 * @author Lorenzo
 */
public class spettacolo {
    private int id=0;
    private String titolo="";
    private String locandina="";
    private String trailer="";
    private int durata=0;
    private String genere="";
    private String descrizione="";
    private String regia="";
    private String naz="";
    private String tipo="";
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
    
    public ArrayList<Integer> ids=new ArrayList();
    public ArrayList<sala> sale=new ArrayList();
    public ArrayList<Timestamp> orari=new ArrayList();

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
     * @return the durata
     */
    public int getDurata() {
        return durata;
    }

    /**
     * @param durata the durata to set
     */
    public void setDurata(int durata) {
        this.durata = durata;
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

    /**
     * @return the descrizione
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * @param descrizione the descrizione to set
     */
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}

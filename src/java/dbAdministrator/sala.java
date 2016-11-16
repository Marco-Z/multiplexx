/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbAdministrator;

/**
 *
 * @author Lorenzo
 */
public class sala {
    private int larghezza=0;
    private int lunghezza=0;
    private String descrizione="";
    private String nome="";
    private char posti[][];
    
    public sala(int larghezza,int lunghezza,String descrizione,String nome){
        this.larghezza = larghezza;
        this.lunghezza = lunghezza;
        this.descrizione = descrizione;
        this.nome = nome;
        posti = new char[larghezza][lunghezza];
        for (int i=0;i<larghezza;i++)
            for (int j=0;j<lunghezza;j++) posti[i][j]='x';
    }
    public void setPosto(int x,int y,char val){
        posti[x][y]=val;
    }
    public char getPosto(int x,int y){
        return posti[x][y];
    }

    /**
     * @return the larghezza
     */
    public int getLarghezza() {
        return larghezza;
    }

    /**
     * @param larghezza the larghezza to set
     */
    public void setLarghezza(int larghezza) {
        this.larghezza = larghezza;
    }

    /**
     * @return the lunghezza
     */
    public int getLunghezza() {
        return lunghezza;
    }

    /**
     * @param lunghezza the lunghezza to set
     */
    public void setLunghezza(int lunghezza) {
        this.lunghezza = lunghezza;
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

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
}

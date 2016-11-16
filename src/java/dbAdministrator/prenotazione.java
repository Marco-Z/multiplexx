/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbAdministrator;

import java.sql.Timestamp;

/**
 *
 * @author Marco Zugliani
 */
public class prenotazione {
    private int prenotazione;
    private String film;
    private int id_utente;
    private Timestamp dataProiezione;
    private String sala;
    private String tipoBiglietto;
    private String tipoFilm;
    private int riga;
    private int colonna;
    private Timestamp dataPrenotazione;
    private double prezzo;

    public String getTipoFilm() {
        return tipoFilm;
    }

    public void setTipoFilm(String tipo_film) {
        this.tipoFilm = tipo_film;
    }

    public String getTipoBiglietto() {
        return tipoBiglietto;
    }

    public void setTipoBiglietto(String tipo) {
        this.tipoBiglietto = tipo;
    }

    public Timestamp getDataPrenotazione() {
        return dataPrenotazione;
    }

    public void setDataPrenotazione(Timestamp dataPrenotazione) {
        this.dataPrenotazione = dataPrenotazione;
    }

    public int getPrenotazione() {
        return prenotazione;
    }

    public void setPrenotazione(int prenotazione) {
        this.prenotazione = prenotazione;
    }

    public String getFilm() {
        return film;
    }

    public void setFilm(String film) {
        this.film = film;
    }

    public Timestamp getDataProiezione() {
        return dataProiezione;
    }

    public void setDataProiezione(Timestamp dataProiezione) {
        this.dataProiezione = dataProiezione;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public int getRiga() {
        return riga;
    }

    public void setRiga(int riga) {
        this.riga = riga;
    }

    public int getColonna() {
        return colonna;
    }

    public void setColonna(int colonna) {
        this.colonna = colonna;
    }
    
    public String getPosto() {
        String posto = "";
        char rowl[] = {'A','B','C','D','E','F','G','H','I','J','K','L','M',
                       'N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
        posto = "" + rowl[riga-1] + colonna;
        return posto;
    }

    /**
     * @return the id_utente
     */
    public int getId_utente() {
        return id_utente;
    }

    /**
     * @param id_utente the id_utente to set
     */
    public void setId_utente(int id_utente) {
        this.id_utente = id_utente;
    }

    /**
     * @return the prezzo
     */
    public double getPrezzo() {
        return prezzo;
    }

    /**
     * @param prezzo the prezzo to set
     */
    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }
}

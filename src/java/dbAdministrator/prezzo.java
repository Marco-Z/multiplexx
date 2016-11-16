/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbAdministrator;

/**
 *
 * @author Marco Zugliani
 */
public class prezzo {
    private String tipo_biglietto;
    private String tipo_film;
    private int prezzo;

    public String getTipo_biglietto() {
        return tipo_biglietto;
    }

    public void setTipo_biglietto(String tipo_biglietto) {
        this.tipo_biglietto = tipo_biglietto;
    }

    public String getTipo_film() {
        return tipo_film;
    }

    public void setTipo_film(String tipo_film) {
        this.tipo_film = tipo_film;
    }

    public int getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(int prezzo) {
        this.prezzo = prezzo;
    }
    
}

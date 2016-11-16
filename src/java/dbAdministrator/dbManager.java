/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbAdministrator;

import ServletGenerali.PdfTicket;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import static java.lang.Thread.sleep;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class dbManager implements Serializable {
    // transient == non viene serializzato
    private transient Connection con;
    public ArrayList<spettacolo> spettacoli;
    
    public dbManager(String dburl) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection(dburl,"postgres","gasonse");
        this.con = con;
        spettacoli = new ArrayList();
        aggiornamentoSpettacoli();
    }

    /**
     * spegne il database
     */
    public static void shutdown() {
        try {
            DriverManager.getConnection("jdbc:postgresql:;shutdown=true");
        } catch (SQLException ex) {
            Logger.getLogger(dbManager.class.getName()).info(ex.getMessage());
        }
    }
    
    /**
     * aggiorna la variabile spettacoli con tutti i film presenti nel catalogo
     */
    public void aggiornamentoSpettacoli() throws SQLException{
        spettacoli.clear();
        
        PreparedStatement stm = con.prepareStatement("SELECT * FROM film;");

        try {
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                spettacolo sp = new spettacolo();
                sp.setDescrizione(rs.getString("descrizione"));
                sp.setDurata(rs.getInt("durata"));
                sp.setGenere(rs.getString("genere"));
                sp.setRegia(rs.getString("regia"));
                sp.setNaz(rs.getString("nazionalita"));
                sp.setAnno(rs.getInt("anno"));
                sp.setLocandina(rs.getString("locandina"));
                sp.setTitolo(rs.getString("titolo"));
                sp.setTipo(rs.getString("tipo"));
                sp.setId(rs.getInt("id_film"));
                sp.setTrailer(rs.getString("link_trailer"));
                int id = rs.getInt("id_film");
                
                PreparedStatement stm2 = con.prepareStatement(""
                        + "SELECT * "
                        + "FROM spettacolo "
                        + "JOIN sala ON spettacolo.id_sala=sala.id_sala "
                        + "WHERE spettacolo.id_film=?");

                try {
                stm2.setInt(1, id);
                ResultSet rs2 = stm2.executeQuery();
                while(rs2.next()){
                    Timestamp t = rs2.getTimestamp("data_ora");
                    sp.orari.add(t);
                    System.out.println("eccomi");
                }
                spettacoli.add(sp);
                } catch (SQLException ex){
                    System.out.println("errore di inizializzazione"+ex.toString());
                } finally {
                    stm2.close();
            }
        }
        } catch (SQLException ex){
            System.out.println("errore di inizializzazione"+ex.toString());
        } finally {
            stm.close();
        }
    }
    
    /**
     * Autentica un utente in base a un nome utente e a una password
     * 
     * @param email il nome utente
     * @param password la password
     * @return null se l'utente non è autenticato, un oggetto User se l'utente esiste ed è autenticato
     * @throws java.sql.SQLException
     */
    public user authenticate(String email, String password) throws SQLException {
        user User = new user();
        
        PreparedStatement stm = con.prepareStatement(
                "SELECT * "
              + "FROM UTENTE as u "
              + "WHERE u.email = ? AND u.password = ?"
        );
        try {
            stm.setString(1, email);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();
            try {
                if (rs.next()) {
                    User.setId(rs.getInt("id_utente"));
                    User.setEmail(rs.getString("email"));
                    User.setNome(rs.getString("nome"));
                    User.setCognome(rs.getString("cognome"));   
                    User.setPassword(rs.getString("password"));
                    User.setCredit(rs.getInt("credito"));
                    User.setTipo(rs.getString("tipo"));
                } else {
                    User=null;
                }
            } finally {
                // ricordarsi SEMPRE di chiudere i ResultSet in un blocco finally 
                rs.close();
            }
        } finally { 
            // ricordarsi SEMPRE di chiudere i PreparedStatement in un blocco finally 
            stm.close();
        }
        return User;
    }  
    
    /**
     * salva nel database l'utente con i parametri indicati
     * @param email l'indirizzo e-mail dell'utente
     * @param nome il nome dell'utente
     * @param cognome il cognome dell'utente
     * @param password la password per l'account
     * @return null se non va a buon fine, l'utente se la registrazione riesce
     * @throws SQLException 
     */
    public user registerUser(String email, String nome, String cognome, String password) throws SQLException {
        user User = new user();
        PreparedStatement reg = con.prepareStatement(
                  "INSERT INTO UTENTE "
                + "(email,nome,cognome,password) "
                + "VALUES "
                + "(?,?,?,?)"
        );

        PreparedStatement stm = con.prepareStatement(
                          "SELECT id_utente "
                        + "FROM UTENTE "
                        + "WHERE email = ? "
        );
        
        reg.setString(1, email);
        reg.setString(2, nome);
        reg.setString(3, cognome);
        reg.setString(4, password);
        try{
            int a = reg.executeUpdate();
            if (a!=1){
                User = null;
            } else{
                stm.setString(1, email);
                ResultSet rs = stm.executeQuery();
                rs.next();
                int id = rs.getInt("id_utente");
                System.out.println(id);
                User.setId(id);
                User.setEmail(email);
                User.setNome(nome);
                User.setCognome(cognome);   
                User.setPassword(password);
                User.setCredit(0);
                User.setTipo("NORMALE");
            }
        }
        catch (SQLException ex){
            System.out.println("si è verificato un errore nella INSERT di un utente");
            return null;
        } finally {
            reg.close();
            stm.close();
        }
        return User;
    }
    
    /**
     * Ottiene la lista dei film dal DB
     * 
     * @return la lista dei film
     * @throws SQLException 
     */
    public List<film> getFilms() throws SQLException {
        List<film> products = new ArrayList<film>();
        
        PreparedStatement stm = con.prepareStatement(""
                + "SELECT * "
                + "FROM film");
        try {
            ResultSet rs = stm.executeQuery();
            try {
                
                while(rs.next()) {
                    film p = new film();
                    
                    p.setTitolo(rs.getString("titolo"));
                    p.setLocandina(rs.getString("locandina"));
                    p.setTrailer(rs.getString("link_trailer"));
                    p.setGenere(rs.getString("genere"));
                    p.setRegia(rs.getString("regia"));
                    p.setNaz(rs.getString("nazionalita"));
                    p.setTipo(rs.getString("tipo"));
                    p.setAnno(rs.getInt("anno"));
                    
                    products.add(p);
                }
                
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        
        return products;
    }
    
    /**
 * dato un id di un film cerca nel database l'id e restituisce le informazioni su quel film
 * @param id l'id del film
 * @return le informazioni riguardanti il film
 * @throws SQLException 
 */    
    public spettacolo getFilm(int id) throws SQLException {
        
        spettacolo movie = new spettacolo();
        
        PreparedStatement stm = con.prepareStatement(""
                + "SELECT * "
                + "FROM film "
                + "WHERE id_film=?" );
        stm.setInt(1, id);
        try {
            ResultSet rs = stm.executeQuery();
            rs.next();
            movie.setDescrizione(rs.getString("descrizione"));
            movie.setDurata(rs.getInt("durata"));
            movie.setGenere(rs.getString("genere"));
            movie.setRegia(rs.getString("regia"));
            movie.setNaz(rs.getString("nazionalita"));
            movie.setAnno(rs.getInt("anno"));
            movie.setLocandina(rs.getString("locandina"));
            movie.setTitolo(rs.getString("titolo"));
            movie.setTipo(rs.getString("tipo"));
            movie.setId(rs.getInt("id_film"));
            movie.setTrailer(rs.getString("link_trailer"));
            int idf = rs.getInt("id_film");
            PreparedStatement stm2 = con.prepareStatement(""
                    + "SELECT * "
                    + "FROM spettacolo "
                    + "JOIN sala ON spettacolo.id_sala=sala.id_sala "
                    + "WHERE spettacolo.id_film=?");
            stm2.setInt(1, idf);
            ResultSet rs2 = stm2.executeQuery();
            while(rs2.next()){
                Timestamp t = rs2.getTimestamp("data_ora");
                Integer id_spett = rs2.getInt("id_spettacolo");
                sala s = new sala(rs2.getInt("lunghezza")+1,rs2.getInt("larghezza")+1,rs2.getString("descrizione"),rs2.getString("nome_sala"));
                PreparedStatement stm3 = con.prepareStatement("SELECT * FROM posto WHERE posto.id_sala=?");
                stm3.setInt(1, rs2.getInt("id_sala"));
                ResultSet rs3 = stm3.executeQuery();
                while (rs3.next()){
                    s.setPosto(rs3.getInt("riga"), rs3.getInt("colonna"), 'o');
                    System.out.println("x="+rs3.getInt("riga")+" ,y="+rs3.getInt("colonna"));
                }
                movie.ids.add(id_spett);
                movie.orari.add(t);
                movie.sale.add(s);
                System.out.println("eccomi");
            }
        } catch (SQLException ex) {
            return movie; // ritorna uno spettacolo con i campi vuoti
        } finally {
            stm.close();
        }
        return movie;
    }
    
    /**
     * ricerca nel db per titolo del film
     * @param query la sottostringa del titolo da cercare
     * @return gli spettacoli che corrispondono al parametro in input
     */
    public ArrayList<spettacolo> cercaSpettacoli(String query) throws SQLException{
        
        ArrayList<spettacolo> ricerca = new ArrayList();
                
        PreparedStatement stm = con.prepareStatement("SELECT * FROM film WHERE LOWER(titolo) LIKE ?");  //confronto le stringhe con i caratteri minuscoli

        try {
            String q = "%" + query.toLowerCase() + "%";
            stm.setString(1, q);
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                spettacolo sp = new spettacolo();
                sp.setDescrizione(rs.getString("descrizione"));
                sp.setDurata(rs.getInt("durata"));
                sp.setGenere(rs.getString("genere"));
                sp.setRegia(rs.getString("regia"));
                sp.setNaz(rs.getString("nazionalita"));
                sp.setAnno(rs.getInt("anno"));
                sp.setLocandina(rs.getString("locandina"));
                sp.setTitolo(rs.getString("titolo"));
                sp.setTipo(rs.getString("tipo"));
                sp.setId(rs.getInt("id_film"));
                sp.setTrailer(rs.getString("link_trailer"));
                int id = rs.getInt("id_film");
                PreparedStatement stm2 = con.prepareStatement(""
                        + "SELECT * "
                        + "FROM spettacolo "
                        + "JOIN sala ON spettacolo.id_sala=sala.id_sala "
                        + "WHERE spettacolo.id_film=?");
                try {
                    stm2.setInt(1, id);
                    ResultSet rs2 = stm2.executeQuery();
                    while(rs2.next()){
                        Timestamp t = rs2.getTimestamp("data_ora");
                        sp.orari.add(t);
                        System.out.println("eccomi");
                    }
                } catch (SQLException ex) {
                    System.out.println("errore di inizializzazione"+ex.toString());
                } finally {
                    stm2.close();
                }
                ricerca.add(sp);
            }
        }
        catch (SQLException ex){
            System.out.println("errore di inizializzazione"+ex.toString());
        } finally {
            stm.close();
        }
        return ricerca;
    }
    
    /**
     * dato l'id di uno spettacolo costruisce una tabella html da inserire in una pagina web
     * @param spettacolo l'id dello spettacolo da cercare
     * @return codice html da mettare in una <table> che rappresenta la disposizione dei posti nella sala
     */
    public String griglia(int spettacolo) throws SQLException {
        
        String table = "";

        PreparedStatement stm = con.prepareStatement("SELECT lunghezza, larghezza " +
                                                     "FROM sala, spettacolo AS s " +
                                                     "WHERE sala.id_sala=s.id_sala AND " +
                                                     "      s.id_spettacolo=?");
        try {
            stm.setInt(1, spettacolo);
            ResultSet rs = stm.executeQuery();
            rs.next();
            
            int rows = rs.getInt("lunghezza");
            int columns = rs.getInt("larghezza");

            
            int [][] seats = new int[rows][columns];
            
            for(int i=0; i<rows; i++){
                for(int j=0; j<columns; j++){
                    seats[i][j] = 0;        //0 se il posto è non disponibile
                }
            }

            PreparedStatement posti = con.prepareStatement("SELECT p.riga, p.colonna " +
                                                          "FROM spettacolo AS s, posto AS p " +
                                                          "WHERE s.id_sala = p.id_sala AND " +
                                                          "	  s.id_spettacolo = ?");

            try {
                posti.setInt(1, spettacolo);
                ResultSet rs3 = posti.executeQuery();
                while(rs3.next()){
                    seats[rs3.getInt("riga")][rs3.getInt("colonna")] = 1;   //1 se il posto è disponibile
                }
            } catch (SQLException ex) {
                Logger.getLogger(dbManager.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                posti.close();
            }
            
            PreparedStatement stm2 = con.prepareStatement("SELECT id_riga, id_colonna " +
                                                         "FROM prenotazione " +
                                                         "WHERE id_spettacolo=?");
            
            
            try {
            stm2.setInt(1, spettacolo);
            ResultSet rs2 = stm2.executeQuery();
            while(rs2.next()){
                seats[rs2.getInt("id_riga")-1][rs2.getInt("id_colonna")-1] = 2; //2 se il posto è occupato
            }
                
            } catch (SQLException ex) {
                Logger.getLogger(dbManager.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                stm2.close();
            }
            char rowl[] = {'a','b','c','d','e','f','g','h','i','j','k','l','m',
                'n','o','p','q','r','s','t','u','v','w','x','y','z'};
            
            
            table = table.concat("<tr>\n");
            table = table.concat("<td> </td>\n");

            for(int i=0; i<columns; i++){
                table = table.concat("<td class='text-center'> <h1 class='col-id'>" + (i+1) +"</h1> </td>\n");
            }
            
            for(int i=0; i<rows; i++){
                table = table.concat("<tr>\n");
                table = table.concat("<td> <h1 class='middle row-id'>" + Character.toUpperCase(rowl[i]) +"</h1> </td>");
                for(int j=0; j<columns; j++){
                    table = table.concat("<td> <input type='checkbox' class='place' id='" + rowl[i] + (j+1) +"' name='"+rowl[i] + (j+1)+"' ");
                    if(seats[i][j]==1){   //se il posto è libero
                        table = table.concat("> <label for='" + rowl[i] + (j+1) +"' class='seat free'></label> </td>\n");
                    } else if(seats[i][j]==2){   //se il posto è occupato
                        table = table.concat(" disabled> <label for='" + rowl[i] + (j+1) +"' class='seat booked'></label> </td>\n");
                    } else { //se il posto non è disponibile
                        table = table.concat(" disabled> <label for='" + rowl[i] + (j+1) +"' class='seat nd'></label> </td>\n");
                    }
                }
                table = table.concat("</tr>\n");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(dbManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            stm.close();
        }
        
        return table;

    }
    
    /**
     * data una email restituisce la password associata nel database
     * @param email l'indirizzo e-mail di cui si vuole trovare la password
     * @return la password associata all'indirizzo email
     * @throws SQLException 
     */
    public String recuperaPassword(String email) throws SQLException{
        String password = new String();
        PreparedStatement stm = con.prepareStatement(
                          "SELECT password "
                        + "FROM UTENTE "
                        + "WHERE email=?"
                );
        stm.setString(1, email);
        try {
            ResultSet rs = stm.executeQuery();
            rs.next();
            password = rs.getString("password");
        } catch (SQLException ex){
            System.out.println("account inesistente"+ex.toString());
             return null;
        } finally {
            stm.close();
        }
        return password;
    }
    
    /**
     * dato l'id di uno spettacolo ritorna il tipo di film (2D/3D)
     * @param id l'id dello spettacolo da cercare
     * @return il tipo di film (2D/3D)
     * @throws SQLException 
     */
    public String getTipoFilm(int id) throws SQLException{
        String tipo;
        PreparedStatement stm = con.prepareStatement(""
                        + "SELECT tipo "
                        + "FROM film, spettacolo AS s "
                        + "WHERE film.id_film=s.id_film AND "
                        + "(s.id_spettacolo=?"
                );
        try {
        stm.setInt(1, id);
        ResultSet rs = stm.executeQuery();
        rs.next();
        tipo = rs.getString("tipo");
        } catch (SQLException ex){
            System.out.println("errore di ricerca"+ex.toString());
            return null;
        } finally {
            stm.close();
        }
        return tipo;
    }
    
    /**
     * dato un indirizzo e-mail di un utente restituisce il suo id nel database
     * @param email l'indirizzo e-mail da cercare
     * @return l'id dell'utente che corrisponde all'e-mail in input
     */
    public int getId(String email) throws SQLException{
        int id = -1;
        
            PreparedStatement stm = con.prepareStatement(
                    "SELECT id_utente "
                  + "FROM utente "
                  + "WHERE email=?" );
        
        try {
            stm.setString(1, email);
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                id = rs.getInt("id_utente");
            }             
            
        } catch (SQLException ex) {
            Logger.getLogger(dbManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            stm.close();
        }
        
        return id;
    }
    
  
    
    /**
     * dato l'id di un utente restituisce l'indirizzo e-mail associato
     * @param id l'id dell'utente da cercare
     * @return l'indirizzo email corrispondente all'id
     */
    public String getMail(int id) throws SQLException{
        String email = "";
        
            PreparedStatement stm = con.prepareStatement(
                    "SELECT email "
                  + "FROM utente "
                  + "WHERE id_utente=?" );
        
        try {
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                email = rs.getString("email");
            }             
            
        } catch (SQLException ex) {
            Logger.getLogger(dbManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            stm.close();
        }
        
        return email;
    }
    
    /**
     * dato l'id di un tuente restituisce tutte le prenotazione che ha effettuato
     * @param id_utente l'id dell'utente da cercare
     * @return le prenotazioni effettuate dall'utente
     */
    public ArrayList<prenotazione> getPrenotazioni(int id_utente) throws SQLException  {
        ArrayList<prenotazione> cron =new ArrayList();
        System.out.println(id_utente);
        
        PreparedStatement stm = con.prepareStatement(
                      "SELECT id_prenotazione,"
                    + "       pr.id_utente, "
                    + "       f.titolo, "
                    + "       f.tipo, "
                    + "       sp.data_ora, "
                    + "       s.nome_sala, "
                    + "       pr.id_riga,"
                    + "       pr.id_colonna,"
                    + "       pr.tipo_biglietto,"
                    + "       pr.data_ora_operazione "

                    + "FROM prenotazione AS pr, "
                    + "     spettacolo AS sp, "
                    + "     film AS f, "
                    + "     sala AS s "

                    + "WHERE pr.id_spettacolo=sp.id_spettacolo AND "
                    + "      sp.id_film=f.id_film AND "
                    + "      sp.id_sala=s.id_sala AND "
                    + "      pr.id_utente=? "

                    + "ORDER BY pr.data_ora_operazione");
            
        try {
            stm.setInt(1, id_utente);
            ResultSet rs = stm.executeQuery();
            while(rs.next()) {
                prenotazione p = new prenotazione();
                p.setPrenotazione(rs.getInt("id_prenotazione"));
                p.setFilm(rs.getString("titolo"));
                p.setDataProiezione(rs.getTimestamp("data_ora"));
                p.setSala(rs.getString("nome_sala"));
                p.setTipoBiglietto(rs.getString("tipo_biglietto"));
                p.setTipoFilm(rs.getString("tipo"));
                p.setRiga(rs.getInt("id_riga"));
                p.setColonna(rs.getInt("id_colonna"));
                p.setDataPrenotazione(rs.getTimestamp("data_ora_operazione"));
                cron.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(dbManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            stm.close();
        }
        return cron;
    }
    
    /**
     * dato l'id di uno spettacolo restituisce l'incasso totale per quella proiezione
     * @param spettacolo l'id dello spettacolo da cercare
     * @return l'incasso totale per quello spettacolo
     */
    public int getIncasso(int spettacolo) throws SQLException{
        int incasso = 0;

            PreparedStatement stm = con.prepareStatement(
                    "WITH pr AS ( " +
                    "	SELECT COUNT(p.tipo_biglietto) AS c, p.tipo_biglietto AS t " +
                    "	FROM prenotazione AS p " +
                    "	WHERE p.id_spettacolo=? " +
                    "	GROUP BY p.tipo_biglietto " +
                    "), t2 AS ( " +
                    "	SELECT pr.c * pz.prezzo AS par " +
                    "	FROM pr, " +
                    "       spettacolo AS s, " +
                    "       prezzi AS pz, " +
                    "       film AS f " +
                    "	WHERE s.id_spettacolo=? AND " +
                    "       pr.t = pz.tipo_biglietto AND " +
                    "       f.tipo = pz.tipo_film AND " +
                    "       s.id_film = f.id_film " +
                    ") " +
                    "SELECT SUM(t2.par) AS totale " +
                    "FROM t2");
            
        try {
            stm.setInt(1, spettacolo);
            stm.setInt(2, spettacolo);
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                incasso = rs.getInt("totale");
            }             
            
        } catch (SQLException ex) {
            Logger.getLogger(dbManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            stm.close();
        }
        
        return incasso;
    }
    
    /**
     * restituisce i prezzi corrispondenti ai vari tipi di biglietto e di film
     * @return i prezzi corrispondenti ai vari tipi di biglietto e di film
     */
    public ArrayList<prezzo> getPrezzi() throws SQLException {
        ArrayList<prezzo> pz = new ArrayList();
        
            PreparedStatement stm = con.prepareStatement("SELECT * "
                                                       + "FROM prezzi "
                                                       + "ORDER BY tipo_biglietto, tipo_film");

        try {
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                prezzo p = new prezzo();
                p.setTipo_biglietto(rs.getString("tipo_biglietto"));
                p.setTipo_film(rs.getString("tipo_film"));
                p.setPrezzo(rs.getInt("prezzo"));
                pz.add(p);
            }
            
            } catch (SQLException ex) {
            Logger.getLogger(dbManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            stm.close();
        }
        return pz;
    }
    
    /**
     * restituisce la lista degli incassi totali per ogni film
     * @return lista di film con l'incasso associato
     */
    public ArrayList<filmAdmin> incassiFilm () throws SQLException {
        ArrayList<filmAdmin> res = new ArrayList();

            PreparedStatement stm = con.prepareStatement(
                    "WITH pr AS ( " +
                    "	SELECT p.id_spettacolo, COUNT(p.tipo_biglietto) AS c, p.tipo_biglietto AS tb " +
                    "	FROM prenotazione AS p " +
                    "	GROUP BY p.id_spettacolo, p.tipo_biglietto " +
                    "	), t2 AS ( " +
                    "	SELECT DISTINCT f.titolo, pr.id_spettacolo, pr.c, pr.tb, f.tipo AS tf " +
                    "	FROM pr, " +
                    "        spettacolo AS s, " +
                    "        film AS f " +
                    "	WHERE pr.id_spettacolo = s.id_spettacolo AND " +
                    "         s.id_film = f.id_film " +
                    "	), t3 AS ( " +
                    "	SELECT t2.titolo, t2.id_spettacolo, SUM(t2.c * p.prezzo) AS par	" +
                    "	FROM t2, prezzi AS p " +
                    "	WHERE t2.tb = p.tipo_biglietto AND " +
                    "         t2.tf = p.tipo_film " +
                    "	GROUP BY t2.titolo, t2.id_spettacolo " +
                    "	) " +
                    "SELECT t3.titolo, SUM(t3.par) AS incasso " +
                    "FROM t3 " +
                    "GROUP BY t3.titolo " +
                    "ORDER BY incasso DESC " );
        
        try {
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                filmAdmin f = new filmAdmin();
                f.setTitolo(rs.getString("titolo"));
                f.setIncasso(rs.getInt("incasso"));
                res.add(f);
            }             
            
        } catch (SQLException ex) {
            Logger.getLogger(dbManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            stm.close();
        }
        
        return res;
    }
    
    /**
     * restituisce la lista dei clienti in ordine di numero di biglietti comprati
     * @return la lista dei clienti in ordine di numero di biglietti comprati
     */
    public ArrayList<cliente> clientiTop() throws SQLException {
        ArrayList<cliente> res = new ArrayList();

            PreparedStatement stm = con.prepareStatement(
                    "SELECT u.email, COUNT(p.id_prenotazione) AS n " +
                    "FROM utente AS u, prenotazione AS p " +
                    "WHERE u.id_utente = p.id_utente " +
                    "GROUP BY u.email " +
                    "ORDER BY n DESC" );
        
        try {
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                cliente c = new cliente();
                c.setEmail(rs.getString("email"));
                c.setnBiglietti(rs.getInt("n"));
                res.add(c);
            }             
            
        } catch (SQLException ex) {
            Logger.getLogger(dbManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            stm.close();
        }
        
        return res;
    }
    
    /**
     * trova il prezzo del biglietto associato alla prenotazione
     * @param id l'id della prenotazione da cercare
     * @return il prezzo del biglietto associato alla prenotazione
     */
    public int getPrezzo(int id) throws SQLException{
        int prezzo = 0;

            PreparedStatement stm = con.prepareStatement(
                    "SELECT pz.prezzo AS ac " +
                    "FROM prezzi AS pz, " +
                    "	 prenotazione AS p, " +
                    "	 film AS f, " +
                    "	 spettacolo AS s " +
                    "WHERE p.id_prenotazione = ? AND " +
                    "	  p.tipo_biglietto = pz.tipo_biglietto AND " +
                    "	  p.id_spettacolo = s.id_spettacolo AND " +
                    "	  s.id_film = f.id_film AND " +
                    "	  f.tipo = pz.tipo_film" );
            
        try {
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                prezzo = rs.getInt("ac");
            }             
            
        } catch (SQLException ex) {
            Logger.getLogger(dbManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            stm.close();
        }
        return prezzo;
    }
    
    /**
     * cerca se lo spettacolo è iniziato o meno
     * @param id l'id dello spettacolo da cercare
     * @return true se lo spettacolo è iniziato, false se lo spettacolo deve ancora cominciare
     * @throws SQLException 
     */
    public boolean iniziato(int id) throws SQLException{
        boolean res = false;
        Date date= new Date();
        Timestamp now = new Timestamp(date.getTime());
        Timestamp spettacolo = new Timestamp(0);

            PreparedStatement stm = con.prepareStatement(
                    "SELECT s.data_ora " +
                    "FROM spettacolo AS s, prenotazione AS p " +
                    "WHERE p.id_prenotazione = ? AND " +
                    "	  p.id_spettacolo = s.id_spettacolo" +
                    "	  " );
        
        try {
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                spettacolo = rs.getTimestamp("data_ora");
            }             
            
        } catch (SQLException ex) {
            Logger.getLogger(dbManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            stm.close();
        }
        if(spettacolo.compareTo(now)<=0){
            res = true;
        }
        System.out.println(spettacolo.toString());
        System.out.println(now.toString());
        System.out.println(spettacolo.compareTo(now));
        return res;
    }
    
    /**
     * cancella la prenotazione effettuata
     * @param id l'id della prenotazione da cercare
     */
    public void deleteBooking(int id) throws SQLException{
        
            PreparedStatement stm = con.prepareStatement(
                    "DELETE FROM prenotazione " +
                    "WHERE id_prenotazione = ?" );
        
        try {
            stm.setInt(1, id);
            stm.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(dbManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            stm.close();
        }
    }
    
    /**
     * aggiorna il credito dell'utente nel database
     * @param id_u l'id dell'utente che beneficia dell'prezzo
     * @param accredito l'importo dell'prezzo da aggiungere al credito esistente
     */
    public int accredita(int id_u, double accredito) throws SQLException{
        
        int res = 0;
        
        PreparedStatement stm = con.prepareStatement(
                "UPDATE utente " +
                "SET credito = credito + ? " +
                "WHERE id_utente = ?" );
        
        try {
            stm.setDouble(1, accredito);
            stm.setInt(2, id_u);
            stm.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(dbManager.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("errore update");
        } finally {
            stm.close();
        }
        
        
        PreparedStatement stm2 = con.prepareStatement(
                "SELECT credito " +
                "FROM utente " +
                "WHERE id_utente = ?" );
        
        try {
            stm2.setInt(1, id_u);
            ResultSet rs2 = stm2.executeQuery();
            if(rs2.next()){
                res = rs2.getInt("credito");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(dbManager.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("errore");
        } finally {
            stm2.close();
        }
        System.out.println(res);
        return res;
    }
    
    /**
     * Cancella la tabella spettacolo 
     * @throws SQLException 
     */
    public void resetFilm() throws SQLException {
        
        PreparedStatement drop = con.prepareStatement(
                 "DROP TABLE if exists spettacolo cascade"); //cancello la programmazione
        try {
            drop.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(dbManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            drop.close();
        }
        
        PreparedStatement crea = con.prepareStatement(
                 "CREATE TABLE SPETTACOLO (" +
                 "	id_spettacolo 		bigserial primary key not null," +
                 "	id_film 		int not null references FILM(id_film)," +
                 "	id_sala 		int not null references SALA(id_sala)," +
                 "	data_ora 		timestamp not null);"); //creo tabella
        try {
            crea.executeUpdate();
            
        } finally {
            crea.close();
        }
        aggiornamentoSpettacoli();
    }
      
    /**
     * Ripopola la tabella spettacolo pescando per ogni sala un film e progr
     * ammandolo ogni x minuti per 8 volte.
     * @param xminuti l'intervall di tempo tra la fine e l'inizio del nuovo spettacolo
     * @param ripetizioni il numero di volte per cui si ripete il film nella sala
     * @return la lista dei titoli inseriti in programmazione
     * @throws SQLException 
     */
    public ArrayList<String> insertFilm(int xminuti, int ripetizioni) throws SQLException {
        
        Random rand = new Random();
        System.out.println("Sto aggiornando gli spettacoli");
        System.out.println("I film messi in Programmazione hanno Id_:");
        ArrayList<String> result = new ArrayList<>();
        
        
        for(int sala=1 ;sala < 5; sala ++){ //per ogni sala
            
            int film = rand.nextInt(getFilms().size()-1) +1; //genero un numero da 1 all'id massimo del film
            System.out.println(film +"\n");
            PreparedStatement idfilms = con.prepareStatement(
                 "SELECT titolo FROM film WHERE id_film = ?");
                 idfilms.setInt(1, film);
            try {
                ResultSet rst = idfilms.executeQuery();
                while(rst.next()){
                    result.add(rst.getString("titolo"));   
                }
                
            } finally {
                idfilms.close();
            }
            
            double inizio = (rand.nextDouble()*xminuti); //genero un numero casulae 0-1 e lo moltiplico per x
            int iinizio = (int)Math.round(inizio); //lo converto in int
            Calendar cal = Calendar.getInstance(); //ottengo la data corrente
            
            for(int nvolte=0; nvolte < ripetizioni; nvolte ++){  //molte volte per ogni film [8]
                
                if(nvolte==0){ //allora è il primo inserimento
                    cal.add(Calendar.MINUTE, iinizio);//aggiugno il numero casuale
                }
                else{
                    cal.add(Calendar.MINUTE, xminuti);//altrimenti aggiungo solo x
                }
                
                PreparedStatement ins = con.prepareStatement(
                         "INSERT INTO spettacolo "  
                          + "(id_film, id_sala, data_ora) "
                          + "VALUES "
                          + "(?,?,?);"  );
                ins.setInt(1, film);
                ins.setInt(2, sala); 
                Timestamp tmstm = new Timestamp(cal.getTimeInMillis());//serve il timestamp
                ins.setTimestamp(3, tmstm);
        
                try {
//                    System.out.println(tmstm);
//                    System.out.println(cal.getTime());
//                    System.out.println(ins); //debug
                    ins.executeUpdate();
            
                } catch (SQLException e){
                    System.out.println("si è verificato un errore nel reset degli spettacoli:/n"+e.toString());
                } finally {
                    ins.close();
                }
                
            }
        }
        aggiornamentoSpettacoli();
        return result;
    } 

    /**
     * Prenota uno spettacolo a nome di un utente, controlla che i posti non siano già occupati
     * , che non sia già iniziato e che abbia soldi a suff.
     * 
     * @param user_id l'id dell utente che effettua l'operazione.
     * @param id_spet l'id dello spettacolo da prenotare.
     * @param posti un arraylist<String> contenente l'id dei posti del tipo [A-Z][0-9].
     * @param tipo un arraylist<String> contenente il tipo di posto.
     * @return ritorna la lista degli errori, "" in caso di successo.
     * @throws java.sql.SQLException
     * 
     */
    synchronized public String prenota
                    (int user_id, int id_spet, ArrayList<String> posti, ArrayList<String> tipo) 
                     throws SQLException {
                        System.out.println(user_id);
        String result ="";
        int id_sala =-1;
        int id_film =-1;
        String tipofilm ="";
        String  nomefilm ="";
        Date date =new Date();
        Timestamp oraspett ;
        Timestamp oracorrente = new Timestamp(date.getTime());
        double costointero = 0;
        double costoridotto = 0;
        double creditoutente =-1;
        double totaledapagare =0;
        
        
        ArrayList<Integer> fila = new ArrayList<>();
        ArrayList<Integer> colonna = new ArrayList<>();
        ArrayList<Integer> id_posto = new ArrayList<>();
        
        for (String posti1 : posti) { //formatto i posti in righe e colonna
            fila.add(posti1.charAt(0) - 'a' + 1);
            colonna.add(Integer.parseInt(posti1.substring(1)));
        }
        
        
                   
        creditoutente=getCredito(user_id); //ottengo il credito
//        System.out.println("credito: " + creditoutente);
        oraspett =new Timestamp(date.getTime());
        
        PreparedStatement cre = con.prepareStatement( //seleziono sala, film , tipofilm 
                         "Select * FROM spettacolo AS sp, film AS f, sala AS sa "  
                          + " WHERE sp.id_spettacolo = ?  and  sp.id_sala = sa.id_sala and f.id_film = sp.id_film "
                           );                
                
        try {

            cre.setInt(1,id_spet);
            ResultSet cref =cre.executeQuery();
            while(cref.next()){
                nomefilm=cref.getString("titolo");
                id_sala= cref.getInt("id_sala");
                id_film=cref.getInt("id_film");
                tipofilm=cref.getString("tipo");
                oraspett=cref.getTimestamp("data_ora");
            }  
            cref.close();

            ArrayList<prezzo> prezzi = getPrezzi();//prendo i prezzi
                for(int p =0; p < prezzi.size(); p++){
                    if (prezzi.get(p).getTipo_film().equalsIgnoreCase(tipofilm)){

                        if(prezzi.get(p).getTipo_biglietto().equalsIgnoreCase("ridotto")){
                            costoridotto=prezzi.get(p).getPrezzo();
                        }
                        else{
                            costointero=prezzi.get(p).getPrezzo();
                        }
                    }
                } 

            //faccio il preventivo
            for(int s =0; s < tipo.size();s++){ //calcolo il preventivo
                if(tipo.get(s).equalsIgnoreCase("intero")){
                    totaledapagare += costointero;
                }
                else{
                    totaledapagare+= costoridotto;
                }
             }
//              System.out.println("tipo film è : "+tipofilm);
//              System.out.println("il totale da pagare è : " + totaledapagare);
//              System.out.println("il costo ridotto è : " + costoridotto);
//              System.out.println("il credito utente è " + creditoutente);

        } catch (SQLException e){
                    System.out.println("si è verificato un errore nella prenotaz. fatta da "+ user_id +" per lo spett."+ String.valueOf(id_spet) +":/n"+e.toString());
                    return("Errore nel Database,Impossibile determinare i prezzi");
        } finally {
                    cre.close();
                }
        
        for (int p =0; p < fila.size();p++ ){
            id_posto.add(getPostoId(fila.get(p),colonna.get(p),id_sala));
        }        
                
        if (totaledapagare>creditoutente){
            return("Non hai abbastanza soldi");
        }
        if(oraspett.before(oracorrente)){
            return("Lo spettacolo è già iniziato");
        }
        
        PreparedStatement post = con.prepareStatement( //seleziono posti
                         "Select * from prenotazione "  
                          + "where id_spettacolo = ?  "
                           );              
                
        try {

            post.setInt(1,id_spet);
            ResultSet cref = post.executeQuery();
            while(cref.next()){
                for(int s=0; s < id_posto.size(); s++){
                    if(cref.getInt("id_posto") == id_posto.get(s) ){ //se combacia l'id 
                        return("I Posti scelti sono già stati prenotati"); //allora sono gìà occupati
                    }
                }
            }  
            cref.close();
                    
        } catch (SQLException e){
                    System.out.println("si è verificato un errore posti già prenotati "+ user_id +" per lo spett."+ String.valueOf(id_spet) +":/n"+e.toString());
                    return("Posti non prenotati");
        } finally {
                    post.close();
        }

        con.setAutoCommit(false);//faccio una transazione
        String insert = "INSERT INTO prenotazione (id_utente, id_spettacolo, id_posto, id_sala, data_ora_operazione, id_riga, id_colonna, tipo_biglietto) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?) ";
        PreparedStatement ins = con.prepareStatement( //seleziono posti manca il tipo biglietto
                     insert );
        ArrayList<prenotazione> pronotazioni = new ArrayList();
        try {
            for(int p =0 ;p< id_posto.size();p++){
                prenotazione pre = new prenotazione();
                pre.setId_utente(user_id);
                ins.setInt(1,user_id);
                pre.setFilm(nomefilm);
                ins.setInt(2,id_spet);
                pre.setRiga(fila.get(p));
                pre.setColonna(colonna.get(p));
                ins.setInt(3,id_posto.get(p));
                ins.setInt(4,id_sala);
                pre.setSala(Integer.toString(id_sala));
                ins.setTimestamp(5, oracorrente);
                pre.setDataProiezione(oraspett);
                ins.setInt(6,fila.get(p));
                ins.setInt(7,colonna.get(p));
                pre.setTipoFilm(tipofilm.toUpperCase());
                ins.setString(8, tipo.get(p).toUpperCase());
                pre.setTipoBiglietto(tipo.get(p).toUpperCase());
                if (tipo.get(p).equalsIgnoreCase("intero")){
                    pre.setPrezzo(costointero);
                }
                else{
                    pre.setPrezzo(costoridotto);
                }
                ins.executeUpdate();
                pronotazioni.add(pre);
            }
            
            //accredito
            ins=con.prepareStatement("update utente set credito = ? where id_utente = ?");
            ins.setDouble(1, creditoutente-totaledapagare);
            ins.setInt(2, user_id);
            ins.executeUpdate();
            con.commit();
            ins.close();
            
            //spedisco mail
            PdfTicket pdft = new PdfTicket();
                ByteArrayOutputStream pdf = pdft.generapdf(pronotazioni);
                ServletGenerali.SendMail.send(getMail(user_id), pdf);
            
        }
        catch (Exception e){
            con.rollback();//se succede qualche errore allora torno al checkpoint.
            con.setAutoCommit(false);
            System.out.println("si è verificato un errore nell'inserimento della prenotaz. fatta da "+ user_id +" per lo spett."+ String.valueOf(id_spet) +":/n"+e.toString());
            return("Errore nel DB");
        } finally {
            ins.close();
        }
                
        
        return result;
    }
    
    /**
     * restituisce il credito corrispondente all'utente cercato
     * @param user_id l'id dell user da cui ottenere il credito
     * @return il credito dell utente
     * @throws java.sql.SQLException
     */
    public double getCredito(int user_id) throws SQLException{
       
        double res =-1d;
        PreparedStatement stm = con.prepareStatement(
                    "SELECT credito FROM utente WHERE id_utente = ?" );
        try {
            stm.setInt(1, user_id);
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                res = rs.getDouble("credito");
            }
            rs.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(dbManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            stm.close();
        }
        return res;
    } 
    
    /**
    * Ricava l'id del posto di cui si fornisce id sala, fila e colonna
    * @param fila la fila.
    * @param colonna la colonna.
    * @param sala_id l'id della sala.
    * @return l'intero che rappresenta id_posto
    * @throws SQLException 
    */
    public int getPostoId(int fila, int colonna, int sala_id) throws SQLException{ 
    int result =-1;
    PreparedStatement ins = con.prepareStatement( //seleziono posti
                                 "SELECT * FROM posto WHERE id_sala = ? AND riga = ? AND colonna = ?"
                               );
        try {
                ins.setInt(1,sala_id);
                ins.setInt(2, fila);
                ins.setInt(3, colonna);

                ResultSet rst = ins.executeQuery();
                while(rst.next()){
                   result=rst.getInt("id_posto");
                }  
                rst.close();

    } catch (SQLException e){
            System.out.println("errore nella ricerca del posto");
    } finally {
            ins.close();
                }
    
    return result;
    }
}

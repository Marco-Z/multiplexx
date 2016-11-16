/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listener;
import dbAdministrator.dbManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**

 * Web application lifecycle listener.

 */

public class listener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        String dburl = sce.getServletContext().getInitParameter("dburl");
        try {
            dbManager manager = new dbManager(dburl);
            sce.getServletContext().setAttribute("dbmanager", manager);
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).severe(ex.toString());
            throw new RuntimeException(ex);
        }
    }


    public void contextDestroyed(ServletContextEvent sce) {
        // Il database Derby deve essere "spento" tentando di connettersi al database con shutdown=true        
        dbManager.shutdown();
    }
}
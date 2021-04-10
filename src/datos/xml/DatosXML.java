/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos.xml;

/**
 *
 * @author joang
 */
public class DatosXML {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Conexion c = new Conexion();
        Docente docente = new Docente("3", "Joan", "joan", "123456");
        c.leerXML(docente);

    }

}

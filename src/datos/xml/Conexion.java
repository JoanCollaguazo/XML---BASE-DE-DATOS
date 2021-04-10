/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos.xml;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ext.DatabaseClosedException;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author joang
 */
public class Conexion {

    private ObjectContainer oc;
    private Docente Docente;

    private void open() {
        //creamos la conexion y el archivo almacenara los datos
        this.oc = Db4o.openFile("database.yap");
    }

    Docente objet;

    @SuppressWarnings("empty-statement")
    public boolean Insertar(Docente objeto) {
        try {
            //BUSCAMOS SI EXISTE EL OBJETO CASO CONTRARIO INSERTAMOS EL OBJETO RECIBIDO
            this.open();
            oc.set(objeto);
            this.oc.close();
            objet = objeto;
            System.out.println("SE HA GUARDADO EL OBJETO CORRECTAMENTE");
            return true;
        } catch (DatabaseClosedException e) {
            System.out.println("bdoo.Controlador.InsertarPersona() : " + e);
            return false;
        }
    }

    public Docente[] insertarXML(Docente objeto) {

        //SI EL OBJETO SE ENCUENTRA YA GUARDADO COLOCAR UN MENSAJE 
        //SI EL OBJETO NO SE ENCUENTRA ENTONCES GUARDAR EL OBJETO
        leerXML(objeto);

        return null;
    }

    public Docente[] leerXML(Docente objeto) {
        Docente[] docentes = new Docente[10];
        try {
            Docente docentexml = null;
            File archivo = new File("datos.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document d = db.parse(archivo);
            d.getDocumentElement().normalize();            
            //CARGANDO LOS DOCENTES EN LOS NODOS
            NodeList listDocentes = d.getElementsByTagName("docente");//Con esto sacamos el número total que tenemos en base al nombre
            for (int i = 0; i < listDocentes.getLength(); i++) {
                Node nodo = listDocentes.item(i);

                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nodo;
                    String id = element.getAttribute("id");
                    String nombre = element.getElementsByTagName("nombre").item(0).getTextContent();
                    String username = element.getElementsByTagName("username").item(0).getTextContent();
                    String password = element.getElementsByTagName("password").item(0).getTextContent();

                    docentexml = new Docente(id, nombre, username, password);
                    docentes[i] = docentexml;

                }
                int a = Integer.parseInt(objeto.getId());
                int b = Integer.parseInt(docentexml.getId());
                if (a == b) {
                    System.out.println("EL DOCENTE ESTA INGRESADO");
                    return null;
                }
            }
            Insertar(objeto);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return docentes;

    }
}

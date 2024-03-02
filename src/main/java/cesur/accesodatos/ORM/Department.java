package cesur.accesodatos.ORM;

import java.util.Objects;

import jakarta.persistence.*;

/**
 * Department class represents a department entity in the system.
 * It encapsulates information about a department, such as its unique identifier,
 * name, and location.
 */
@Entity
@Table(name = "departamento", schema = "public", catalog = "Empresa")
public class Department {
    // Class variables

    /**
     * The ID of the department.
     */
    @Id
    @Column(name = "depno")
    private int depno;

    /**
     * The name of the department.
     */
    @Basic
    @Column(name = "nombre")
    private String nombre;

    /**
     * The location of the department.
     */
    @Basic
    @Column(name = "ubicacion")
    private String ubicacion;


    // Constructors
    /**
     *
     * @param depno  Identification of each Department
     * @param nombre   Name which each Department is going to have
     * @param ubicacion Location which each Department is going to have
     */
    public Department(int depno, String nombre, String ubicacion) {
        this.depno = depno;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
    }

    /**
     *  Default constructor to initialize the Department without parameters.
     */
    public Department() {

    }

    /**
     *
     * @return Return Name of the Department
     */
    public String getName() {
        return nombre;
    }

    /**
     *
     * @param nombre Set Name of the Department
     */

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     *
     * @return  Return Location of the Department
     */
    public String getLocation() {
        return ubicacion;
    }

    /**
     *
     * @param ubicacion Set Location of the Department
     */
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    /**
     *
     * @return Return identification of the Department
     */
    public int getDepno() {
        return depno;
    }

    /**
     *
     * @param depno Set Depno of the Department
     */
    public void setDepno(int depno) {
        this.depno = depno;
    }
    // TO STRING //
    /**
     *
     * @return It's going to return us a string representation the specified object
     */

    @Override
    public String toString() {
        return "Department{" +
                "depno=" + depno +
                ", nombre='" + nombre + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                '}';
    }



}

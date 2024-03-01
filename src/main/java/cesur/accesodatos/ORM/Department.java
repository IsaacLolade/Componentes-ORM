package cesur.accesodatos.ORM;

import java.util.Objects;

import jakarta.persistence.*;

@Entity
@Table(name = "departamento", schema = "public", catalog = "Empresa")
public class Department {
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "depno")
    private int depno;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "ubicacion")
    private String ubicacion;



    public int getDepno() {
        return depno;
    }

    public void setDepno(int depno) {
        this.depno = depno;
    }

    public Department(int depno, String nombre, String ubicacion) {
        this.depno = depno;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
    }

    public Department() {

    }

    @Override
    public String toString() {
        return "Department{" +
                "depno=" + depno +
                ", nombre='" + nombre + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                '}';
    }

    public String getName() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLocation() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return depno == that.depno && Objects.equals(nombre, that.nombre) && Objects.equals(ubicacion, that.ubicacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(depno, nombre, ubicacion);
    }
}

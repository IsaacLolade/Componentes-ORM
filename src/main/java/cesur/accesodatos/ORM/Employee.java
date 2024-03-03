package cesur.accesodatos.ORM;

import java.util.Objects;

import jakarta.persistence.*;
/**
 * Represents an employee in the system.
 * This class encapsulates information about an employee, including their unique identifier,
 * name, job position, and department number.
 *
 * @author Marc Albert Seguí Olmos
 */

@Entity
@Table(name = "empleado", schema = "public", catalog = "company")
public class Employee {
   // @GeneratedValue(strategy = GenerationType.IDENTITY)

    // Class variables
    /**
     * The unique identifier for the Employee.
     */
    @Id
    @Column(name = "empno")
    private int empno;


    /**
     * The name of the Employee.
     */
    @Basic
    @Column(name = "nombre")
    private String name;

    /**
     * The job position of the Employee.
     */
    @Basic
    @Column(name = "puesto")
    private String position;

    /**
     * The department number where the Employee works.
     */
    @Basic
    @Column(name = "depno")
    private Integer depno;
    @ManyToOne
    @JoinColumn(name = "depno", referencedColumnName = "depno", insertable = false, updatable = false)
    private Department departmentByDepno;


    // Constructors
    /**
     * Constructs a new Employee with the specified identifier, name, job position, and department number.
     * @param empno The unique identifier for the Employee.
     * @param name The name of the Employee.
     * @param position The job position of the Employee.
     * @param depno The department number where the Employee works.
     */

    public Employee(int empno, String name, String position, Integer depno) {
        this.empno = empno;
        this.name = name;
        this.position = position;
        this.depno = depno;
    }

    /**
     * Default constructor for creating an Employee instance without setting properties in advance.
     */
    public Employee() {

    }

    /**
     * Retrieves the unique identifier of the Employee.
     * @return The identifier of the Employee.
     */
    public int getEmpno() {
        return empno;
    }

    /**
     * Sets the unique identifier of the Employee.
     * @param empno The new identifier for the Employee.
     */
    public void setEmpno(int empno) {
        this.empno = empno;
    }

    /**
     * Retrieves the name of the Employee.
     * @return The name of the Employee.
     */
    public String getName() {
        return name;
    }


    /**
     * Sets the name of the Employee.
     * @param name The new name for the Employee.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the job position of the Employee.
     * @return The job position of the Employee.
     */
    public String getPosition() {
        return position;
    }

    /**
     * Sets the job position of the Employee.
     * @param position The new job position for the Employee.
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * Retrieves the department number where the Employee works.
     * @return The department number.
     */
    public Integer getDepno() {
        return depno;
    }

    /**
     * Sets the department number where the Employee works.
     * @param depno The new department number.
     */
    public void setDepno(Integer depno) {
        this.depno = depno;
    }

    // TO STRING
    /**
     * Returns a string representation of the Employee object,
     * including the unique identifier, name, position, and department number.
     * @return A string representation of the Employee.
     */

    @Override
    public String toString() {
        return "Employee{" +
                "empno=" + empno +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", depno=" + depno +
                '}';
    }

}

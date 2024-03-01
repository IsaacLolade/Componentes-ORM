package cesur.accesodatos.ORM;

import java.util.Objects;

import jakarta.persistence.*;

@Entity
@Table(name = "empleado", schema = "public", catalog = "company")
public class Employee {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "empno")
    private int empno;
    @Basic
    @Column(name = "nombre")
    private String name;
    @Basic
    @Column(name = "puesto")
    private String position;
    @Basic
    @Column(name = "depno")
    private Integer depno;
    @ManyToOne
    @JoinColumn(name = "depno", referencedColumnName = "depno", insertable = false, updatable = false)
    private Department departmentByDepno;



    public Employee(int empno, String name, String position, Integer depno) {
        this.empno = empno;
        this.name = name;
        this.position = position;
        this.depno = depno;
    }

    public Employee(int empno, String name, String position, Integer depno , Department department) {
        this.empno = empno;
        this.name = name;
        this.position = position;
        this.depno = depno;
        this.departmentByDepno = department;
    }

    public Employee() {

    }

    @Override
    public String toString() {
        return "Employee{" +
                "empno=" + empno +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", depno=" + depno +
                '}';
    }

    public int getEmpno() {
        return empno;
    }

    public void setEmpno(int empno) {
        this.empno = empno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getDepno() {
        return depno;
    }

    public void setDepno(Integer depno) {
        this.depno = depno;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee that = (Employee) o;
        return empno == that.empno && Objects.equals(name, that.name) && Objects.equals(position, that.position) && Objects.equals(depno, that.depno);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empno, name, position, depno);
    }

    public Department getDepartmentByDepno() {
        return departmentByDepno;
    }

    public void setDepartmentByDepno(Department departmentByDepno) {
        this.departmentByDepno = departmentByDepno;
    }
}

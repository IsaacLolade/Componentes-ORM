package cesur.accesodatos.ORM;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.spi.ServiceException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ORMDAO implements ConnectionInterface, IDAO, Menu {

    // Terminal outputs and colors
    static final String BLACK_FONT = "\u001B[30m";
    static final String GREEN_FONT = "\u001B[32m";
    static final String WHITE_BG = "\u001B[47m";
    static final String RESET = "\u001B[0m";
    static final String USER_INPUT = String.format("%s%s>%s ", BLACK_FONT, WHITE_BG, RESET);

    private final Pattern ipPattern = Pattern.compile("(localhost)|(\\b(?:\\d{1,3}\\.){3}\\d{1,3}\\b)"); // Regular expression to identify if the user either localhost or ip address the right way

    private final InputStreamReader isr = new InputStreamReader(System.in); // Input reader for what the user introduces at the console

    private SessionFactory sessionFactory;

    private boolean connectionFlag = false; // to determine if i've established connection with the database

    private boolean executionFlag = true;


    @Override
    public List<Employee> findAllEmployees() {
        if (!this.connectionFlag) {
            System.out.println("ERROR, there's no connection to the Database. Please try using method connectDB() before trying any other thing");
        } else {

            try (Session session = sessionFactory.openSession()) {
                return session.createQuery("FROM Employee ", Employee.class).setReadOnly(true).getResultList();
            }catch (HibernateException e){
                System.out.println("ERROR: HibernateException error reported" + e.getMessage());
            }catch (Exception e){
                System.out.println("ERROR: Exception error reported "+ e.getMessage());
            }
        }
        return null;
    }

    @Override
    public Employee findEmployeeById(Object id) {
        if (!this.connectionFlag) {
            System.out.println("ERROR, there's no connection to the Database. Please try using method connectDB() before trying any other thing");
        } else {

            try(Session s = sessionFactory.openSession()) {
                return s.find(Employee.class,id); // se devuelve el empleado referenciado por el id

            }catch (HibernateException e){
                System.out.println("ERROR: HibernateException error reported" + e.getMessage());
            }catch (Exception e){
                System.out.println("ERROR: Exception error reported "+ e.getMessage());
            }
        }
        return null;
    }

    @Override
    public void addEmployee(Employee employee) {
        if (!this.connectionFlag) {
            System.out.println("ERROR, there's no connection to the Database. Please try using method connectDB() before trying any other thing");
        } else {
            try (Session session = sessionFactory.openSession()) {
                Transaction t = session.beginTransaction(); // iniacimos la transaccion
                try {
                    Employee trrabajador = new Employee();     // creamos el estudiante
                    trrabajador.setName(employee.getName());
                    trrabajador.setDepno(employee.getDepno());
                    trrabajador.setPosition(employee.getPosition());
                    trrabajador.setEmpno(employee.getEmpno());
                    session.merge(trrabajador);     // hacemos empledao persistente
                    t.commit(); // subimos los cambios
                } catch (HibernateException e) {
                    System.out.println("ERROR: HiibernateException  error reported " + e.getMessage());
                    t.rollback();
                }
            }

        }

    }

    @Override
    public Employee updateEmployee(Object id) {
        if (!this.connectionFlag) {
            System.out.println("ERROR, there's no connection to the Database. Please try using method connectDB() before trying any other thing");
        } else {
            BufferedReader br = new BufferedReader(isr);
            try(Session s = sessionFactory.openSession()) {

                System.out.println("SET the name from the employee, please");
                String name = br.readLine();
                System.out.println("SET the position from the employee, please");
                String position = br.readLine();
                System.out.println("SET the Department number from the employee, please");
                Integer depno = Integer.parseInt(br.readLine());

                Transaction  t = s.beginTransaction();
                try{
                    s.createQuery("UPDATE Employee SET name =  :name, depno = :department, position = :play WHERE empno = : id ")
                            .setParameter("id",id)
                            .setParameter("name",name)
                            .setParameter("department",depno)
                            .setParameter("play", position)
                            .executeUpdate();
                    t.commit();

                    return s.find(Employee.class,id);
                }catch (HibernateException e){
                    System.out.println("ERROR: HibernateException error reported" + e.getMessage());
                }catch (Exception e){
                    System.out.println("ERROR: Exception error reported "+ e.getMessage());
                }

            } catch (IOException e) {
                System.out.println("ERROR: IOException error reported " + e.getMessage());

            } catch (NumberFormatException e) {
                System.out.println("ERROR: NumberFormatException error reported " + e.getMessage());
            }

        }
        return null;
    }

    @Override
    public Employee deleteEmployee(Object id) {
        if (!this.connectionFlag){
            System.out.println("ERROR, there's no connection to the Database. Please try using method connectDB() before trying any other thing");
        }else{
            try(Session s = sessionFactory.openSession()) {
                Employee employee = s.get(Employee.class,id);
                Transaction  t = s.beginTransaction();
                try{
                    s.createQuery("DELETE FROM Employee WHERE empno = : id ")
                            .setParameter("id",id)
                            .executeUpdate();
                    t.commit();

                    return employee; // to check if it still exists
                }catch (HibernateException e){
                    System.out.println("ERROR: HibernateException error reported" + e.getMessage());
                }catch (Exception e){
                    System.out.println("ERROR: Exception error reported "+ e.getMessage());
                }
            }
        }
        return null;
    }

    @Override
    public List<Department> findAllDepartments() {
        if (!this.connectionFlag) {
            System.out.println("ERROR, there's no connection to the Database. Please try using method connectDB() before trying any other thing");
        } else {

            try (Session session = sessionFactory.openSession()) {
                return session.createQuery("FROM Department ", Department.class).setReadOnly(true).getResultList();
            }catch (HibernateException e){
                System.out.println("ERROR: HibernateException error reported" + e.getMessage());
            }catch (Exception e){
                System.out.println("ERROR: Exception error reported "+ e.getMessage());
            }
        }
        return null;
    }

    @Override
    public Department findDepartmentById(Object id) {
        if (!this.connectionFlag) {
            System.out.println("ERROR, there's no connection to the Database. Please try using method connectDB() before trying any other thing");
        } else {

            try(Session s = sessionFactory.openSession()) {
                return s.find(Department.class,id); // se devuelve el empleado referenciado por el id

            }catch (HibernateException e){
                System.out.println("ERROR: HibernateException error reported" + e.getMessage());
            }catch (Exception e){
                System.out.println("ERROR: Exception error reported "+ e.getMessage());
            }
        }
        return null;
    }

    @Override
    public void addDepartment(Department department) {
        if (!this.connectionFlag){
            System.out.println("ERROR, there's no connection to the Database. Please try using method connectDB() before trying any other thing");
        }else {
            try (Session session = sessionFactory.openSession()) {
                Transaction t = session.beginTransaction(); // iniacimos la transaccion
                try {
                    Department departmento = new Department();     // creamos el estudiante

                    departmento.setNombre(department.getName());
                    departmento.setUbicacion(department.getLocation());
                    departmento.setDepno(departmento.getDepno());

                    session.merge(departmento);     // hacemos empledao persistente
                    t.commit();                 // subimos los cambios
                } catch (TransactionException e) {
                    System.out.println("ERROR: TransactionException  error reported " + e.getMessage());
                    t.rollback();
                }
            }

        }

    }

    @Override
    public Department updateDepartment(Object id) {
        if (!this.connectionFlag) {
            System.out.println("ERROR, there's no connection to the Database. Please try using method connectDB() before trying any other thing");
        } else {
            BufferedReader br = new BufferedReader(isr);

            try(Session s = sessionFactory.openSession()) {

                System.out.println("SET the name from the department, please");
                String name = br.readLine();
                System.out.println("SET the location from the department, please");
                String location = br.readLine();
                System.out.println("SET the Department number, please");
                Integer depno = Integer.parseInt(br.readLine());

                Transaction  t = s.beginTransaction();
                try{
                    s.createQuery("UPDATE Department SET  nombre =  :name, ubicacion = :location WHERE depno = : id ")
                            .setParameter("id",depno)
                            .setParameter("name",name)
                            .setParameter("location", location)
                            .executeUpdate();
                    t.commit();

                    return s.find(Department.class,id);
                }catch (HibernateException e){
                    System.out.println("ERROR: HibernateException error reported" + e.getMessage());
                }catch (Exception e){
                    System.out.println("ERROR: Exception error reported "+ e.getMessage());
                }

            } catch (IOException e) {
                System.out.println("ERROR: IOException error reported " + e.getMessage());

            } catch (NumberFormatException e) {
                System.out.println("ERROR: NumberFormatException error reported " + e.getMessage());
            }

        }
        return null;
    }



    @Override
    public Department deleteDepartment(Object id) {
        if (!this.connectionFlag){
            System.out.println("ERROR, there's no connection to the Database. Please try using method connectDB() before trying any other thing");
        }else{
            try(Session s = sessionFactory.openSession()) {
                Department department = s.get(Department.class,id);

                Transaction  t = s.beginTransaction();
                try{
                    s.createQuery("DELETE FROM Department WHERE depno = : id ")
                            .setParameter("id",id)
                            .executeUpdate();
                    t.commit();

                    return department; // to check if it still exists
                }catch (HibernateException e){
                    System.out.println("ERROR: HibernateException error reported" + e.getMessage());
                }catch (Exception e){
                    System.out.println("ERROR: Exception error reported "+ e.getMessage());
                }
            }
        }
        return null;
    }

    @Override
    public List<Employee> findEmployeesByDept(Object id) {
        if (!this.connectionFlag) {
            System.out.println("ERROR, there's no connection to the Database. Please try using method connectDB() before trying any other thing");
        } else {

            try (Session s = sessionFactory.openSession()) {

                Transaction t = s.beginTransaction();
                try {
                    Integer numero = Integer.parseInt(id.toString());
                  List<Employee> employeeList=  s.createQuery(" FROM Employee WHERE departmentByDepno = :id ", Employee.class)
                            .setParameter("id", numero)
                            .setReadOnly(true)
                            .getResultList();
                    t.commit();
                    return employeeList;
                } catch (NumberFormatException e) {
                    System.out.println("ERROR: NumberException error reported "+ e.getMessage());
                }
            }

        }
        return null;
    }

    @Override
    public boolean connectDB() {
        Configuration cfg = new Configuration();
        cfg.configure();
        BufferedReader reader = new BufferedReader(this.isr);

            try {

                // StringBuilder for String connection
                StringBuilder connectionBuilder = new StringBuilder();
                connectionBuilder.append("jdbc:postgresql://"); // Append DBM

                System.out.println("Insert the Database server IP:"); // DB Server IP
                System.out.print(USER_INPUT);
                String serverIp = reader.readLine();
                if(!ipPattern.matcher(serverIp).matches()) {
                    System.err.println("ERROR: The provided IP address is not valid");
                    return false;
                }
                connectionBuilder.append(serverIp).append(":");

                System.out.println("Insert the Database server PORT:"); // DB Server PORT
                System.out.print(USER_INPUT);
                String serverPort = reader.readLine();
                connectionBuilder.append(serverPort).append("/");
                System.out.println("Insert the Database NAME (case sensitive!):"); // DB Name
                System.out.print(USER_INPUT);
                String dbName = reader.readLine();
                connectionBuilder.append(dbName);
                cfg.setProperty("hibernate.connection.url", connectionBuilder.toString());
                System.out.println("LOGIN: indicate the username and password"); // DB Server IP
                System.out.print("username> ");
                String username = reader.readLine();
                cfg.setProperty("hibernate.connection.username", username);
                System.out.print("password> ");
                String passwd = reader.readLine();
                cfg.setProperty("hibernate.connection.password", passwd);
                cfg.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
                cfg.setProperty("hibernate.hbm2ddl.auto","update");
                cfg.setProperty("hibernate.show_sql","true");
                cfg.addAnnotatedClass(cesur.accesodatos.ORM.Department.class);
                cfg.addAnnotatedClass(cesur.accesodatos.ORM.Employee.class);

                this.sessionFactory = cfg.buildSessionFactory();

                if (this.sessionFactory != null){
                    this.connectionFlag = true;
                    return true;
                }else{
                    System.out.println("ERROR trying to get connected to database, some of your entries may not be good");
                    return false;
                }

            } catch (IOException e) {
                System.err.println("ERROR: IOException error reported: " + e.getMessage());
            }catch (ServiceException e){
                System.out.println("ERROR: ServiceException error reported: "+ e.getMessage());
            }
        return false;
    }

    @Override
    public void closeConnection() {
        if (this.connectionFlag) {
            this.sessionFactory.close();
            System.out.printf("%s- Database connection closed -%s", GREEN_FONT, RESET);
        }
    }

    @Override
    public void executeMenu() {
        BufferedReader reader = new BufferedReader(this.isr); // At this point the Stream is still opened -> At finally block I'll close it
        try {
            while (this.executionFlag) {
                System.out.printf("%s%s- WELCOME TO THE COMPANY -%s\n", "\u001B[46m", BLACK_FONT, RESET);
                System.out.println("Select an option:" +
                        "\n\t1) List all Employees" +
                        "\n\t2) Find Employee by its ID" +
                        "\n\t3) Add new Employee" +
                        "\n\t4) Update Employee" +
                        "\n\t5) Delete Employee" +
                        "\n\t6) List all Departments" +
                        "\n\t7) Find Department by its ID" +
                        "\n\t8) Add new Department" +
                        "\n\t9) Update Department" +
                        "\n\t10) Delete Department" +
                        "\n\t11) Find Employees by Department" +
                        "\n\t0) Exit program");
                System.out.print(USER_INPUT);
                String optStr = reader.readLine();
                if (optStr.isEmpty()) {
                    System.err.println("ERROR: Please indicate the option number");
                    continue;
                } else if (!optStr.matches("\\d{1,2}")) {
                    System.err.println("ERROR: Please provide a valid input for option! The input must be an Integer value");
                    continue;
                }
                int opt = Integer.parseInt(optStr);
                switch (opt) {
                    case 1 -> executeFindAllEmployees();
                    case 2 -> executeFindEmployeeByID();
                    case 3 -> executeAddEmployee();
                    case 4 -> executeUpdateEmployee();
                    case 5 -> executeDeleteEmployee();
                    case 6 -> executeFindAllDepartments();
                    case 7 -> executeFindDepartmentByID();
                    case 8 -> executeAddDepartment();
                    case 9 -> executeUpdateDepartment();
                    case 10 -> executeDeleteDepartment();
                    case 11 -> executeFindEmployeesByDept();
                    case 0 -> this.executionFlag = false;
                    default -> System.err.println("Please provide a valid option");
                }
            }
        } catch (IOException ioe) {
            System.err.println("ERROR: IOException error reported: " + ioe.getMessage());
        } finally {
            try {
                reader.close();
            } catch (IOException ioe) {
                System.err.println("ERROR: IOException error on reader close reported: " + ioe.getMessage());
            }
            closeConnection();
        }
        System.out.printf("%s%s- SEE YOU SOON -%s\n", "\u001B[46m", BLACK_FONT, RESET); // Program execution end
    }


    @Override
    public void executeFindAllEmployees() {

        if (this.connectionFlag) {
            String row = "+" + "-".repeat(7) + "+" + "-".repeat(16) + "+" + "-".repeat(16) + "+" + "-".repeat(7) + "+";
            List<Employee> employees = this.findAllEmployees();
            if (employees != null) {
                System.out.println(row);
                System.out.printf("| %-5s | %-14s | %-14s | %-5s |\n", "EMPNO", "NOMBRE", "PUESTO", "DEPNO");
                System.out.println(row);
                for (Employee e : employees) {
                    System.out.printf("| %-5s | %-14s | %-14s | %-5s |\n", e.getEmpno(), e.getName(), e.getPosition(), e.getDepno());
                }
                System.out.println(row);
            } else {
                System.out.println("There are currently no Employees stored");
            }
        } else {
            System.err.println("ERROR: You must first try to connect to the database with the method .connectDB()");
        }
    }

    @Override
    public void executeFindEmployeeByID() {
        if (this.connectionFlag) {
            BufferedReader reader = new BufferedReader(this.isr); // To read user input
            try {
                System.out.println("Insert Employee's ID:");
                System.out.print(USER_INPUT);
                String input = reader.readLine();
                if(!input.matches("\\d+")) { // Check if the output is not numeric
                    System.err.println("ERROR: Please provide a valid Employee ID. Employee's ID are Integer values");
                    return;
                }
                Employee returnEmp = this.findEmployeeById(Integer.parseInt(input));
                if (returnEmp != null) {
                    System.out.println("Employee's information:");
                    System.out.println(returnEmp.toString());
                } else { // There is no Employee with the indicated ID
                    System.out.println("There is no Employee with EMPNO " + input);
                }
            } catch (IOException ioe) {
                System.err.println("ERROR: IOException error reported: " + ioe.getMessage());
            }
        } else {
            System.err.println("ERROR: You must first try to connect to the database with the method .connectDB()");
        }
    }

    @Override
    public void executeAddEmployee() {
        if (this.connectionFlag) {
            BufferedReader reader = new BufferedReader(this.isr); // To read user input
            try {
                System.out.println("Insert new Employee's ID:");
                System.out.print(USER_INPUT);
                String id = reader.readLine();
                if(!id.matches("\\d+")) { // Check if the output is not numeric
                    System.err.println("ERROR: Please provide a valid Employee ID. Employee's ID are Integer values");
                    return;
                } else if (findEmployeeById(Integer.parseInt(id)) != null) { // There is already an Employee with that ID
                    System.err.println("ERROR: There is already an Employee with the same ID");
                    return;
                }
                System.out.println("Insert new Employee's NAME:");
                System.out.print(USER_INPUT);
                String name = reader.readLine();
                if(name.isEmpty()) {
                    System.err.println("ERROR: You can't leave the information empty");
                    return;
                }
                System.out.println("Insert new Employee's ROLE:");
                System.out.print(USER_INPUT);
                String role = reader.readLine();
                if(role.isEmpty()) {
                    System.err.println("ERROR: You can't leave the information empty");
                    return;
                }
                System.out.println("Insert new Employee's DEPNO:");
                System.out.print(USER_INPUT);
                String depno = reader.readLine();
                if(!depno.matches("\\d+")) { // Check if the output is not numeric
                    System.err.println("ERROR: Please provide a valid Department ID. Departments' ID are Integer values");
                    return;
                } else if (findDepartmentById(Integer.parseInt(depno)) == null) { // There is no Department with introduced DEPNO
                    System.err.println("ERROR: There is no Department with DEPNO " + depno);
                    return;
                }
                // Everything is good to execute the method
                Employee newEmployee = new Employee(Integer.parseInt(id), name, role, Integer.parseInt(depno)); // Create Employee object
                this.addEmployee(newEmployee);
                System.out.printf("%sNew Employee added successfully!%s\n", GREEN_FONT, RESET);
            } catch (IOException ioe) {
                System.err.println("ERROR: IOException error reported: " + ioe.getMessage());
            }
        } else {
            System.err.println("ERROR: You must first try to connect to the database with the method .connectDB()");
        }
    }

    @Override
    public void executeUpdateEmployee() {
        if (this.connectionFlag) {
            BufferedReader reader = new BufferedReader(this.isr); // To read user input
            try {
                System.out.println("Insert Employee's ID:");
                System.out.print(USER_INPUT);
                String input = reader.readLine();
                if(!input.matches("\\d+")) { // Check if the output is not numeric
                    System.err.println("ERROR: Please provide a valid Employee ID. Employee's ID are Integer values");
                    return;
                }
                Employee returnEmp = this.findEmployeeById(Integer.parseInt(input));
                if (returnEmp == null) { // Check if there is an Employee with the indicated ID
                    System.out.println("There is no Employee with EMPNO " + input);
                    return;
                }
                // Execute IDAO method
                Employee updated = updateEmployee(Integer.parseInt(input));
                System.out.println(updated.toString());
            } catch (IOException ioe) {
                System.err.println("ERROR: IOException error reported: " + ioe.getMessage());
            }
        } else {
            System.err.println("ERROR: You must first try to connect to the database with the method .connectDB()");
        }
    }

    @Override
    public void executeDeleteEmployee() {
        if (this.connectionFlag) {
            BufferedReader reader = new BufferedReader(this.isr); // To read user input
            try {
                System.out.println("Insert Employee's ID:");
                System.out.print(USER_INPUT);
                String input = reader.readLine();
                if(!input.matches("\\d+")) { // Check if the output is not numeric
                    System.err.println("ERROR: Please provide a valid Employee ID. Employee's ID are Integer values");
                    return;
                }
                Employee returnEmp = this.findEmployeeById(Integer.parseInt(input));
                if (returnEmp == null) { // Check if there is an Employee with the indicated ID
                    System.out.println("There is no Employee with EMPNO " + input);
                    return;
                }
                // Execute IDAO method
                Employee deleted = deleteEmployee(Integer.parseInt(input));
                System.out.println(deleted.toString());
            } catch (IOException ioe) {
                System.err.println("ERROR: IOException error reported: " + ioe.getMessage());
            }
        } else {
            System.err.println("ERROR: You must first try to connect to the database with the method .connectDB()");
        }
    }

    @Override
    public void executeFindAllDepartments() {
        if (this.connectionFlag) {
            String row = "+" + "-".repeat(7) + "+" + "-".repeat(20) + "+" + "-".repeat(16) + "+";
            List<Department> departments = this.findAllDepartments();
            if(departments != null) {
                System.out.println(row);
                System.out.printf("| %-5s | %-18s | %-14s |\n", "DEPNO", "NOMBRE", "UBICACION");
                System.out.println(row);
                for (Department d : departments) {
                    System.out.printf("| %-5s | %-18s | %-14s |\n", d.getDepno(), d.getName(), d.getLocation());
                }
                System.out.println(row);
            } else {
                System.out.println("There are currently no Department stored");
            }
        } else {
            System.err.println("ERROR: You must first try to connect to the database with the method .connectDB()");
        }
    }

    @Override
    public void executeFindDepartmentByID() {
        if (this.connectionFlag) {
            BufferedReader reader = new BufferedReader(this.isr); // To read user input
            try {
                System.out.println("Insert Department's ID:");
                System.out.print(USER_INPUT);
                String input = reader.readLine();
                if(!input.matches("\\d+")) { // Check if the output is not numeric
                    System.err.println("ERROR: Please provide a valid Department ID. Department's ID are Integer values");
                    return;
                }
                Department returnDept = this.findDepartmentById(Integer.parseInt(input));
                if (returnDept != null) {
                    System.out.println("Department's information:");
                    System.out.println(returnDept.toString());
                } else { // There is no Employee with the indicated ID
                    System.out.println("There is no Department with DEPNO " + input);
                }
            } catch (IOException ioe) {
                System.err.println("ERROR: IOException error reported: " + ioe.getMessage());
            }
        } else {
            System.err.println("ERROR: You must first try to connect to the database with the method .connectDB()");
        }
    }

    @Override
    public void executeAddDepartment() {
        if (this.connectionFlag) {
            BufferedReader reader = new BufferedReader(this.isr); // To read user input
            try {
                System.out.println("Insert new Department's ID:");
                System.out.print(USER_INPUT);
                String depno = reader.readLine();
                if(!depno.matches("\\d+")) { // Check if the output is not numeric
                    System.err.println("ERROR: Please provide a valid Department ID. Department's ID are Integer values");
                    return;
                } else if (findDepartmentById(Integer.parseInt(depno)) != null) { // There is already an Employee with that ID
                    System.err.println("ERROR: There is already an Department with the same ID");
                    return;
                }
                System.out.println("Insert new Department's NAME:");
                System.out.print(USER_INPUT);
                String name = reader.readLine();
                if(name.isEmpty()) {
                    System.err.println("ERROR: You can't leave the information empty");
                    return;
                }
                System.out.println("Insert new Department's LOCATION:");
                System.out.print(USER_INPUT);
                String location = reader.readLine();
                if(location.isEmpty()) {
                    System.err.println("ERROR: You can't leave the information empty");
                    return;
                }
                // Everything is good to execute the method
                Department newDepartment = new Department(Integer.parseInt(depno), name, location); // Create Employee object
                this.addDepartment(newDepartment);
                System.out.printf("%sNew Department added successfully!%s\n", GREEN_FONT, RESET);
            } catch (IOException ioe) {
                System.err.println("ERROR: IOException error reported: " + ioe.getMessage());
            }
        } else {
            System.err.println("ERROR: You must first try to connect to the database with the method .connectDB()");
        }
    }

    @Override
    public void executeUpdateDepartment() {
        if (this.connectionFlag) {
            BufferedReader reader = new BufferedReader(this.isr); // To read user input
            try {
                System.out.println("Insert Department's ID:");
                System.out.print(USER_INPUT);
                String input = reader.readLine();
                if(!input.matches("\\d+")) { // Check if the output is not numeric
                    System.err.println("ERROR: Please provide a valid Department ID. Department's ID are Integer values");
                    return;
                }
                Department returnDept = this.findDepartmentById(Integer.parseInt(input));
                if (returnDept == null) { // Check if there is an Employee with the indicated ID
                    System.out.println("There is no Department with DEPNO " + input);
                    return;
                }
                // Execute IDAO method
                Department updated = updateDepartment(Integer.parseInt(input));
                System.out.println(updated.toString());
            } catch (IOException ioe) {
                System.err.println("ERROR: IOException error reported: " + ioe.getMessage());
            }
        } else {
            System.err.println("ERROR: You must first try to connect to the database with the method .connectDB()");
        }
    }

    @Override
    public void executeDeleteDepartment() {
        if (this.connectionFlag) {
            BufferedReader reader = new BufferedReader(this.isr); // To read user input
            try {
                System.out.println("Insert Department's ID:");
                System.out.print(USER_INPUT);
                String input = reader.readLine();
                if(!input.matches("\\d+")) { // Check if the output is not numeric
                    System.err.println("ERROR: Please provide a valid Department ID. Department's ID are Integer values");
                    return;
                }
                Department returnDept = this.findDepartmentById(Integer.parseInt(input));
                if (returnDept == null) { // Check if there is an Employee with the indicated ID
                    System.out.println("There is no Department with DEPNO " + input);
                    return;
                }
                // Execute IDAO method
                Department deleted = deleteDepartment(Integer.parseInt(input));
                System.out.println(deleted.toString());
            } catch (IOException ioe) {
                System.err.println("ERROR: IOException error reported: " + ioe.getMessage());
            }
        } else {
            System.err.println("ERROR: You must first try to connect to the database with the method .connectDB()");
        }
    }

    @Override
    public void executeFindEmployeesByDept() {
        if (this.connectionFlag) {
            BufferedReader reader = new BufferedReader(this.isr); // To read user input
            try {
                System.out.println("Insert Department's ID:");
                System.out.print(USER_INPUT);
                String input = reader.readLine();
                if(!input.matches("\\d+")) { // Check if the output is not numeric
                    System.err.println("ERROR: Please provide a valid Department ID. Department's ID are Integer values");
                    return;
                }
                Department returnDept = this.findDepartmentById(Integer.parseInt(input));
                if (returnDept == null) { // Check if there is an Employee with the indicated ID
                    System.out.println("There is no Department with DEPNO " + input);
                    return;
                }
                // Execute IDAO method
                ArrayList<Employee> departmentEmployees = (ArrayList<Employee>) findEmployeesByDept(Integer.parseInt(input));
                String row = "+" + "-".repeat(7) + "+" + "-".repeat(16) + "+" + "-".repeat(16) + "+";
                if(departmentEmployees == null || departmentEmployees.isEmpty()) { // No Employees in Department case
                    System.out.println("There are currently no Employees in the Department");
                } else {
                    System.out.println(row);
                    System.out.printf("| %-5s | %-14s | %-14s |\n", "EMPNO", "NOMBRE", "PUESTO");
                    System.out.println(row);
                    for (Employee e : departmentEmployees) {
                        System.out.printf("| %-5s | %-14s | %-14s |\n", e.getEmpno(), e.getName(), e.getPosition());
                    }
                    System.out.println(row);
                }
            } catch (IOException ioe) {
                System.err.println("ERROR: IOException error reported: " + ioe.getMessage());
            }
        } else {
            System.err.println("ERROR: You must first try to connect to the database with the method .connectDB()");
        }

    }
}

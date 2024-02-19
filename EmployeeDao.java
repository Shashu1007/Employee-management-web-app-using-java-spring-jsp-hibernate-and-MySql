/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package suktha.dao;

import java.io.IOException;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import suktha.model.Employee;
import suktha.model.Employee.EmployeeStatus;
import suktha.util.ConnectionFactory;
import suktha.util.HibernateUtil;

/**
 *
 * @author Shashank
 */

    
public class EmployeeDao {
    
   @SuppressWarnings("unchecked")
public static List<Employee> getEmployees(int start, int recordsPerPage) {
    Transaction transaction = null;
    List<Employee> employees = null;
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
        transaction = session.beginTransaction();
        
       
        Query<Employee> query = session.createQuery("FROM Employee", Employee.class);
        query.setFirstResult(start);
        query.setMaxResults(recordsPerPage);
        employees = query.getResultList();
        
        transaction.commit();
    } catch (HibernateException e) {
        if (transaction != null) {
            transaction.rollback();
        }
        e.printStackTrace();
    } finally {
        if (session != null) {
            session.close();
        }
    }
    return employees;
}

  public static List<Employee> searchEmployees(String searchKeyword) throws SQLException, ParseException {
    List<Employee> employees = new ArrayList<>();
   
    String sql = "SELECT * FROM Employee " +
                 "WHERE employee_id LIKE ? OR " +
                 "first_name LIKE ? OR " +
                 "last_name LIKE ? OR " +
                 "e_mail LIKE ? OR " +
                 "Phone_no LIKE ?";
    
    try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        String keyword = "%" + searchKeyword + "%";
        stmt.setString(1, keyword); // employee_id
        stmt.setString(2, keyword); // first_name
        stmt.setString(3, keyword); // last_name
        stmt.setString(4, keyword); // email
        stmt.setString(5, keyword); // phone_no
        
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Employee employee = new Employee();
                
                employee.setEmployee_id(rs.getString("employee_id"));
                employee.setFirstName(rs.getString("first_name"));
                employee.setLastName(rs.getString("last_name"));
                employee.setEmail(rs.getString("e_mail"));
                String dobString = rs.getString("dob");
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date dob = dateFormat.parse(dobString);
                employee.setDob(dob);
                employee.setLocation(rs.getString("location"));
                employee.setPhoneNo(rs.getString("Phone_no"));
                employee.setGender(rs.getString("gender"));
                employee.setManager(rs.getString("manager"));
                employee.setProject(rs.getString("project"));
                employee.setJob(rs.getString("job"));
                employee.setSalary(rs.getString("salary"));
                String empStatusString = rs.getString("empStatus");
                EmployeeStatus empStatus = EmployeeStatus.valueOf(empStatusString);
                employee.setEmpStatus(empStatus);

                employees.add(employee);
            }
        }
    } catch (SQLException | ParseException e) {
        e.printStackTrace();
    }
    
    return employees;
}



     public static void addEmployee(Employee employee) {
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            session.save(employee);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
  public static Employee getEmployeeById(int id) {
    Transaction transaction = null;
    Employee employee = null;
    Session session = HibernateUtil.getSessionFactory().openSession();
    try  {
        transaction = session.beginTransaction();
        employee = (Employee) session.createQuery("from Employee e where e.id = :id")
                                    .setParameter("id", id)
                                    .uniqueResult();
        transaction.commit();
    } catch (HibernateException e) {
        if (transaction != null) {
            transaction.rollback();
        }
        e.printStackTrace();
    } finally {
            if (session != null) {
                session.close();
            }
        }
    return employee;
}


    public  static void updateEmployee(Employee employee) {
		Transaction transaction =null;
                Session session= HibernateUtil.getSessionFactory().openSession();
		try {
			
			transaction = session.beginTransaction();
			session.update(employee);
			transaction.commit();
                        

			
		}
		catch(Exception e) {
			if(transaction!=null) {
				transaction.rollback();
			}  e.printStackTrace();
                } finally {
                if (session != null) {
                session.close();
            }
        }
}
	
@SuppressWarnings("unchecked")
public static List<Employee> getExcelEmployees() {
    Transaction transaction = null;
    List<Employee> employees = null;
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
        transaction = session.beginTransaction();
        
       
        employees = session.createQuery(
                "select e.employee_id, e.firstName, e.lastName, e.email, e.dob, e.location, " +
                        "e.phoneNo, e.gender, e.job, e.salary, e.manager, e.project, e.empStatus " +
                        "from Employee e")
                .getResultList();
        
        transaction.commit();
    } catch (HibernateException e) {
        if (transaction != null) {
            transaction.rollback();
        }
        e.printStackTrace();
    }
    return employees;
}

    @SuppressWarnings("unchecked")
public static List<Employee> getEmployees() {
    int start = 0; // Default starting index
    int recordsPerPage = 5; // Default records per page
    return getEmployees(start, recordsPerPage);
}
    @SuppressWarnings({"unchecked", "CallToPrintStackTrace"})
    private void listEmployees(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    int page = 1;
    int recordsPerPage = 5;

    if (request.getParameter("page") != null) {
        page = Integer.parseInt(request.getParameter("page"));
    }

    int start = (page - 1) * recordsPerPage;

    List<Employee> listEmployees = EmployeeDao.getEmployees(start, recordsPerPage);
    long totalCount = EmployeeDao.getEmployeeCount(); // Assuming you have a method to get total count
    int totalPages = (int) Math.ceil((double) totalCount / recordsPerPage);

    request.setAttribute("listEmployees", listEmployees);
    request.setAttribute("totalPages", totalPages);
    request.setAttribute("currentPage", page);

    RequestDispatcher dispatcher = request.getRequestDispatcher("employee-list.jsp");
    dispatcher.forward(request, response);
}


    public static void deleteEmployee(int id) {
    Transaction transaction = null;
    @SuppressWarnings("UnusedAssignment")
    Employee employee = null;
    Session session = HibernateUtil.getSessionFactory().openSession();
    try  {
    transaction = session.beginTransaction();
    employee = session.get(Employee.class, id);
    if (employee != null) {
        session.delete(employee);
        transaction.commit();
        
    } else {
        System.out.println("No employee found with the provided ID.");
    }
} catch (Exception ex) {
    if (transaction != null) {
        transaction.rollback();
    }
    ex.printStackTrace();
   }finally {
    if(session!=null){
    session.close();}
    }
 }
   public static long getEmployeeCount() {
       Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            
            return (Long) session.createQuery("select count(*) from Employee").uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }finally{
        if(session!=null)
            session.close();
        }
    }
public static List<Employee> FilterEmployees(String searchKeyword) throws SQLException, ParseException {
    List<Employee> employees = new ArrayList<>();
   
    String sql = "SELECT * FROM Employee " +
                 "WHERE  LIKE location? AND " +
                 "gender LIKE ? AND " +
                 "manager LIKE ? AND " +
                 "project LIKE ? AND " +
                 "job LIKE ?";
    
    try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        String keyword = "%" + searchKeyword + "%";
        stmt.setString(1, keyword); // employee_id
        stmt.setString(2, keyword); // first_name
        stmt.setString(3, keyword); // last_name
        stmt.setString(4, keyword); // email
        stmt.setString(5, keyword); // phone_no
        
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Employee employee = new Employee();
                
                employee.setEmployee_id(rs.getString("employee_id"));
                employee.setFirstName(rs.getString("first_name"));
                employee.setLastName(rs.getString("last_name"));
                employee.setEmail(rs.getString("e_mail"));
                String dobString = rs.getString("dob");
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date dob = dateFormat.parse(dobString);
                employee.setDob(dob);
                employee.setLocation(rs.getString("location"));
                employee.setPhoneNo(rs.getString("Phone_no"));
                employee.setGender(rs.getString("gender"));
                employee.setManager(rs.getString("manager"));
                employee.setProject(rs.getString("project"));
                employee.setJob(rs.getString("job"));
                employee.setSalary(rs.getString("salary"));
                String empStatusString = rs.getString("empStatus");
                EmployeeStatus empStatus = EmployeeStatus.valueOf(empStatusString);
                employee.setEmpStatus(empStatus);

                employees.add(employee);
            }
        }
    } catch (SQLException | ParseException e) {
        e.printStackTrace();
    }
    
    return employees;
}
public static long getEmployeeCounts() {
    Transaction transaction = null;
    long count = 0;
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
        transaction = session.beginTransaction();

        // Execute query to count total number of employees
        count = (long) session.createQuery("SELECT COUNT(*) FROM Employee").uniqueResult();

        transaction.commit();
    } catch (HibernateException e) {
        if (transaction != null) {
            transaction.rollback();
        }
        e.printStackTrace();
    }
    return count;
}
// DAO method to filter employees based on criteria
public static List<Employee> filterEmployees(String[] locations, String[] genders, String[] jobRoles, int minSalary, int maxSalary, String[] managers, String[] projects) {
    List<Employee> filteredResults = null;
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = builder.createQuery(Employee.class);
        Root<Employee> root = criteriaQuery.from(Employee.class);
        List<Predicate> predicates = new ArrayList<>();

        // Add criteria as predicates
        if (locations != null && locations.length > 0) {
            predicates.add(root.get("location").in((Object[]) locations));
        }
        if (genders != null && genders.length > 0) {
            predicates.add(root.get("gender").in((Object[]) genders));
        }
        if (jobRoles != null && jobRoles.length > 0) {
            predicates.add(root.get("job").in((Object[]) jobRoles));
        }
        predicates.add(builder.between(root.get("salary"), minSalary, maxSalary));
        if (managers != null && managers.length > 0) {
            predicates.add(root.get("manager").in((Object[]) managers));
        }
        if (projects != null && projects.length > 0) {
            predicates.add(root.get("project").in((Object[]) projects));
        }
        
        // Construct the final query
        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        // Execute the query
        filteredResults = session.createQuery(criteriaQuery).getResultList();
    } catch (HibernateException e) {
        e.printStackTrace();
    }
    return filteredResults;
}

 public static List<String> getAllDistinctLocations() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT DISTINCT location FROM Employee", String.class).getResultList();
        }
    }

    public static List<String> getAllDistinctGenders() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT DISTINCT gender FROM Employee", String.class).getResultList();
        }
    }

    public static List<String> getAllDistinctManagers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT DISTINCT manager FROM Employee", String.class).getResultList();
        }
    }

    public static List<String> getAllDistinctProjects() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT DISTINCT project FROM Employee", String.class).getResultList();
        }
    }

    public static List<String> getAllDistinctJobs() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT DISTINCT job FROM Employee", String.class).getResultList();
        }
    }

}
    


package suktha.impl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.hibernate.HibernateException;
import suktha.dao.EmployeeDao;
import suktha.model.Employee;
import suktha.model.Employee.EmployeeStatus;

@WebServlet("/")
public class EmployeeServlet extends HttpServlet {
    private EmployeeDao employeedao;
    private final int RECORDS_PER_PAGE = 10;
    private static final long serialVersionUID = 1L;    

    @Override
    public void init() {
        employeedao = new EmployeeDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException ,HibernateException {
       
        String action = request.getServletPath();

        try {
            switch (action) {
                case "/new":
                    showNewForm(request, response);
                    break;
                
                case "/insert":
                    insertEmployee(request, response);
                    break;
                case "/delete":
                    deleteEmployee(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateEmployee(request, response);
                    break;
                case "/search":
                    searchEmployee(request, response);
                    break;
                
                case "/filter":
                         
                List<String> locations = EmployeeDao.getAllDistinctLocations();
                List<String> genders = EmployeeDao.getAllDistinctGenders();
                List<String> managers = EmployeeDao.getAllDistinctManagers();
                List<String> projects = EmployeeDao.getAllDistinctProjects();
                List<String> jobs = EmployeeDao.getAllDistinctJobs();

                // Set attributes for use in JSP
                request.setAttribute("locations", locations);
                request.setAttribute("genders", genders);
                request.setAttribute("managers", managers);
                request.setAttribute("projects", projects);
                request.setAttribute("jobs", jobs);

                // Forward to filter form JSP
                RequestDispatcher dispatcher = request.getRequestDispatcher("filter-form.jsp");
                dispatcher.forward(request, response);
                break;

                case "/list":
                    listEmployee(request, response);
                    break;
                default:
                    listEmployee(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        } catch (ParseException ex) {
            Logger.getLogger(EmployeeServlet.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred.");
        }
    }
    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException ,HibernateException {
    doGet(request, response);
}

    private void listEmployee(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
    long count = EmployeeDao.getEmployeeCounts();
    int page = 1;
    int recordsPerPage = 05; 
    
    if (request.getParameter("page") != null) {
        page = Integer.parseInt(request.getParameter("page"));
    }
    
    int start = (page - 1) * recordsPerPage;
    
   List<Employee> listEmployees = EmployeeDao.getEmployees(start, recordsPerPage);
long totalCount = EmployeeDao.getEmployeeCount(); // Use long instead of int
int totalPages = (int) Math.ceil((double) totalCount / recordsPerPage);

request.setAttribute("listEmployees", listEmployees);
request.setAttribute("totalPages", totalPages);
request.setAttribute("currentPage", page);
request.setAttribute("count", totalCount); // Set the totalCount attribute

RequestDispatcher dispatcher = request.getRequestDispatcher("employee-list.jsp");
dispatcher.forward(request, response);
}

private void searchEmployee(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, ParseException {
    String searchKeyword = request.getParameter("searchKeyword");
    try {
        List<Employee> searchResult = EmployeeDao.searchEmployees(searchKeyword);
        request.setAttribute("listEmployees", searchResult);
        RequestDispatcher dispatcher = request.getRequestDispatcher("employee-list.jsp");
        dispatcher.forward(request, response);
    } catch (SQLException e) {
        // Handle database error
        e.printStackTrace();
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error occurred while searching employees.");
    }
}
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException ,HibernateException{
        RequestDispatcher dispatcher = request.getRequestDispatcher("employee-form.jsp");
        dispatcher.forward(request, response);
    }
private void showEditForm(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    String idParam = request.getParameter("id");
    if (idParam != null && !idParam.isEmpty()) {
        try {
            int id = Integer.parseInt(idParam);
            Employee existingEmployee = EmployeeDao.getEmployeeById(id);
            if (existingEmployee != null) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("employee-form.jsp");
                request.setAttribute("employee", existingEmployee);
                dispatcher.forward(request, response);
            } else {
                response.getWriter().println("No employee found with the provided ID.");
            }
        } catch (NumberFormatException e) {
            
            response.getWriter().println("Invalid employee ID format.");
            e.printStackTrace();
        }
    } else {
        
        response.getWriter().println("Employee ID parameter is missing or empty.");
    }
}


    private void insertEmployee(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ParseException, ServletException {
            Employee employee = new Employee();
            String employeeId = employee.getEmployee_id();
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");
            String dobStr=request.getParameter("dob");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Part filePart = request.getPart("profilePictureFile");
    InputStream inputStream = filePart.getInputStream();
    byte[] profilePicture = inputStream.readAllBytes();

            @SuppressWarnings("UnusedAssignment")
            Date dob = null;
            dob = sdf.parse(dobStr);
            String location = request.getParameter("location");
            String phoneNo = request.getParameter("phoneNo"); 
            String genderStr=request.getParameter("gender");
            @SuppressWarnings("UnusedAssignment")
            String gender= null;
            boolean isMale = false; 
            if (genderStr != null && genderStr.equals("male")) {
            isMale = true;
            }
            if(isMale){
                gender="Male";
            }
            else{
                gender="Female";
            }
            String manager = request.getParameter("manager");
            String project = request.getParameter("project");
            String job = request.getParameter("job");
            String salary = request.getParameter("salary");
            String empStatusStr = request.getParameter("empStatus");
            EmployeeStatus empStatus = EmployeeStatus.valueOf(empStatusStr);
            Date createdBy = new Date(); 
        
        Employee newEmployee = new Employee(employeeId ,firstName, lastName, email, dob, location, phoneNo, gender, manager, project, job, salary, empStatus ,createdBy );
        EmployeeDao.addEmployee(newEmployee);
        response.sendRedirect("list");
}

        
    
private void deleteEmployee(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            String idParam = request.getParameter("id");
            if (idParam != null && !idParam.isEmpty()) {
                int id = Integer.parseInt(idParam);
                EmployeeDao.deleteEmployee(id); 
                response.sendRedirect("list"); 
            } else {
                response.getWriter().println("Employee ID parameter is missing or empty.");
            }
        } catch (NumberFormatException e) {
            response.getWriter().println("Invalid employee ID format.");
        }
}

private void filterEmployees(HttpServletRequest request, HttpServletResponse response)
        throws SQLException, IOException, ParseException, ServletException {
    String[] locations = request.getParameterValues("location");
    String[] genders = request.getParameterValues("gender");
    String[] jobRoles = request.getParameterValues("jobRole");
    int minSalary = 0;
    int maxSalary = Integer.MAX_VALUE;
    try {
        minSalary = Integer.parseInt(request.getParameter("minSalary"));
        maxSalary = Integer.parseInt(request.getParameter("maxSalary"));
    } catch (NumberFormatException e) {
        // Handle parsing errors, maybe set default values or display an error message
        e.printStackTrace();
    }
    String[] managers = request.getParameterValues("manager");
    String[] projects = request.getParameterValues("project");
    
    
    List<Employee> filteredResults = EmployeeDao.filterEmployees(locations, genders, jobRoles, minSalary, maxSalary, managers, projects);
   
    request.setAttribute("listEmployees", filteredResults);
    RequestDispatcher dispatcher = request.getRequestDispatcher("employee-list.jsp");
    dispatcher.forward(request, response);
}
   


private void updateEmployee(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ParseException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");
            String dobStr=request.getParameter("dob");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            @SuppressWarnings("UnusedAssignment")
            Date dob = null;
            dob = sdf.parse(dobStr);
            String location = request.getParameter("location");
            String phoneNo = request.getParameter("phoneNo"); 
            String genderStr=request.getParameter("gender");
            @SuppressWarnings("UnusedAssignment")
            String gender= null;
            boolean isMale = false; 
            if (genderStr != null && genderStr.equals("male")) {
            isMale = true;
            }
            if(isMale){
                gender="Male";
            }
            else{
                gender="Female";
            }
            String manager = request.getParameter("manager");
            String project = request.getParameter("project");
            String job = request.getParameter("job");
            String salary= request.getParameter("salary");
            String empStatusStr = request.getParameter("empStatus");
            EmployeeStatus empStatus = EmployeeStatus.valueOf(empStatusStr);
            

            Employee existingEmployee = EmployeeDao.getEmployeeById(id);
            if (existingEmployee != null) {
                existingEmployee.setFirstName(firstName);
                existingEmployee.setLastName(lastName);
                existingEmployee.setEmail(email);
                existingEmployee.setDob(dob);
                existingEmployee.setLocation(location);
                existingEmployee.setPhoneNo(phoneNo);  
                existingEmployee.setGender(gender);
                existingEmployee.setManager(manager);
                existingEmployee.setProject(project);
                existingEmployee.setJob(job);
                existingEmployee.setSalary(salary);
                existingEmployee.setEmpStatus(empStatus);
            

                EmployeeDao.updateEmployee(existingEmployee);
                response.sendRedirect("list");
            } else {
                response.getWriter().println("No employee found with the provided ID.");
            }
        } catch (NumberFormatException e) {
            response.getWriter().println("Invalid employee ID format.");
    }
}
}
    


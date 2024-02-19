/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package suktha.model;

/**
 *
 * @author Shashank
 */
import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Random;
import org.hibernate.annotations.Type;
//import org.springframework.web.multipart.MultipartFile;


@Entity
@Table(name = "employee")
public class Employee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "employee_id", unique = true, nullable = false)
    private String employee_id;
    
     @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] profilePicture;
     
//     @Transient
//    private MultipartFile profilePictureFile;

  
    @Column(name = "first_name", nullable = false, length = 225)
    private String firstName;

    @Column(name = "last_name", length = 225)
    private String lastName;

    @Column(name = "e_mail", nullable = false, length = 225)
    private String email;

    @Column(name = "dob")
    @Temporal(TemporalType.DATE)
    private Date dob;

    @Column(name = "location", nullable = false, length = 500)
    private String location;

    @Column(name = "Phone_no", nullable = false, length = 10)
    private String phoneNo;

    @Column(name = "gender")
    private String gender;
    
    

    @Column(name = "manager", length = 225)
    private String manager;

    @Column(name = "project", length = 225)
    private String project;

    @Column(name = "job", length = 200)
    private String job;

    @Column(name = "salary")
    private String salary;

    @Enumerated(EnumType.STRING)
    private EmployeeStatus empStatus;

    @Column(name = "created_by" ,updatable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdBy;

    @Column(name = "updated_by")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedBy;

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

//    public MultipartFile getProfilePictureFile() {
//        return profilePictureFile;
//    }
//
//    public void setProfilePictureFile(MultipartFile profilePictureFile) {
//        this.profilePictureFile = profilePictureFile;
//    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

   public EmployeeStatus getEmpStatus() {
        return empStatus;
    }

    public void setEmpStatus(EmployeeStatus empStatus) {
        this.empStatus = empStatus;
    }
   

    public Date getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Date createdBy) {
        this.createdBy = createdBy;
    }

    public Date getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Date updatedBy) {
        this.updatedBy = updatedBy;
    }

    
    

    public Employee() {
        
        this.employee_id = generateEmployee_id();
        this.createdBy = new Date();

    }

    public Employee( String employee_id,String firstName, String lastName, String email, Date dob, String location,
                    String phoneNo, String gender, String manager, String project, String job, String salary,
                   EmployeeStatus empStatus , Date createdBy) {
        
        super();
        this.employee_id = generateEmployee_id();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dob = dob;
        this.location = location;
        this.phoneNo = phoneNo;
        this.gender = gender;
        this.manager = manager;
        this.project = project;
        this.job = job;
        this.salary = salary;
        this.empStatus = empStatus;
        this.createdBy = createdBy;
        
    }

//    public Employee(int id, String employee_id, byte[] profilePicture, MultipartFile profilePictureFile, String firstName, String lastName, String email, Date dob, String location, String phoneNo, String gender, String manager, String project, String job, String salary, EmployeeStatus empStatus, Date createdBy, Date updatedBy) {
//        super();
//        this.id = id;
//        this.employee_id = employee_id;
//        this.profilePicture = profilePicture;
//        this.profilePictureFile = profilePictureFile;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.email = email;
//        this.dob = dob;
//        this.location = location;
//        this.phoneNo = phoneNo;
//        this.gender = gender;
//        this.manager = manager;
//        this.project = project;
//        this.job = job;
//        this.salary = salary;
//        this.empStatus = empStatus;
//        this.createdBy = createdBy;
//        this.updatedBy = updatedBy;
//    }
     private String generateEmployee_id() {
        Random random = new Random();
        int randomNumber = random.nextInt(1111); 
        String paddedNumber = String.format("%04d", randomNumber); 
        return "SUKT" + paddedNumber;
    }
     
     @PreUpdate
    protected void onUpdate() {
        this.updatedBy = new Date(); 
    }

  
 
    public enum EmployeeStatus {
    ACTIVE,
    DEPARTED,
    RETIRED,
    AWOL;

}
}



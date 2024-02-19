
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.sql.*" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="javax.naming.Context" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.naming.NamingException" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Employee Profile Management</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="/css/styles.css">

<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
    <script>
        function resetForm() {
        document.getElementById("employeeForm").reset();
    }

    function saveOrUpdateEmployee() {
        document.getElementById("employeeForm").submit();
    }</script>
<style>
     .filter {
        margin-left: 70%;
        margin-top: 2%
    }

    #sorting,
    #res {
        font-size: 12px;
        margin-top: 2%
    }
.container-xl {
    max-width: 1600px;
    margin-right: auto; 
    margin-left: auto;
   
}
body {
	color: #566787;
	background: #f5f5f5;
	font-family: 'Varela Round', sans-serif;
	font-size: 13px;
}
.table-responsive {
    margin:5px;
    
}
.table-wrapper {
	background: #fff;
	padding: 20px 25px;
	border-radius: 3px;
	min-width: 1000px;
	box-shadow: 0 1px 1px rgba(0,0,0,.05);
        width: inherit;
}
.table-title {        
	padding-bottom: 15px;
	background: #435d7d;
	color: #fff;
	padding: 16px 30px;
	min-width: 100%;
	margin: -20px -25px 10px;
	border-radius: 3px 3px 0 0;
}
.table-title h2 {
	margin: 5px 0 0;
	font-size: 24px;
}
.table-title .btn-group {
	float: right;
}
.table-title .btn {
	color: #fff;
	float: right;
	font-size: 13px;
	border: none;
	min-width: 50px;
	border-radius: 2px;
	border: none;
	outline: none !important;
	margin-left: 10px;
}
.table-title .btn i {
	float: left;
	font-size: 21px;
	margin-right: 5px;
}
.table-title .btn span {
	float: left;
	margin-top: 2px;
}
table.table tr th, table.table tr td {
	border-color: #e9e9e9;
	padding: 12px 15px;
	vertical-align: middle;
}
table.table tr th:first-child {
	width: 60px;
}
table.table tr th:last-child {
	width: 100px;
}
table.table-striped tbody tr:nth-of-type(odd) {
	background-color: #fcfcfc;
        width: inherit;
}
table.table-striped.table-hover tbody tr:hover {
    width: inherit;
	background: #f5f5f5;
}
table.table th i {
	font-size: 13px;
	margin: 0 5px;
	cursor: pointer;
}	
table.table td:last-child i {
	opacity: 0.9;
	font-size: 22px;
	margin: 0 5px;
}
table.table td a {
	font-weight: bold;
	color: #566787;
	display: inline-block;
	text-decoration: none;
	outline: none !important;
}
table.table td a:hover {
	color: #2196F3;
}
table.table td a.edit {
	color: #FFC107;
}
table.table td a.delete {
	color: #F44336;
}
table.table td i {
	font-size: 19px;
}
table.table .avatar {
	border-radius: 50%;
	vertical-align: middle;
	margin-right: 10px;
}
.pagination {
	float: right;
	margin: 0 0 5px;
        text-decoration: #000;
}.pagination li a {
    border: none;
    font-size: 13px;
    min-width: 20px;
    min-height: 20px;
    margin: 0 2px;
    line-height: 20px;
    border-radius: 2px !important;
    text-align: center;
    padding: 0 6px;
    color: darkcyan;
    text-decoration: none;
}
.pagination li a:hover {
	color: #666;
}	
.pagination li.active a, .pagination li a:hover {
    background-color: #000;
    color: #fff;
}

.pagination li.active a:hover {        
	background: #0397d6;
        color: #fff
        
}
.pagination li.disabled i {
    color: #666;
}
.pagination li i {
    background: #0397d6;
    font-size: 13px;
    padding-top: 6px;
    color: #000;
}
.hint-text {
	float: left;
	margin-top: 10px;
	font-size: 13px;
}    

.custom-checkbox {
	position: relative;
}
.custom-checkbox input[type="checkbox"] {    
	opacity: 0;
	position: absolute;
	margin: 5px 0 0 3px;
	z-index: 9;
}
.custom-checkbox label:before{
	width: 18px;
	height: 18px;
}
.custom-checkbox label:before {
	content: '';
	margin-right: 10px;
	display: inline-block;
	vertical-align: text-top;
	background: white;
	border: 1px solid #bbb;
	border-radius: 2px;
	box-sizing: border-box;
	z-index: 2;
}
.custom-checkbox input[type="checkbox"]:checked + label:after {
	content: '';
	position: absolute;
	left: 6px;
	top: 3px;
	width: 6px;
	height: 11px;
	border: solid #000;
	border-width: 0 3px 3px 0;
	transform: rotateZ(45deg);
	z-index: 3;
	transform: rotateZ(45deg);
}
.custom-checkbox input[type="checkbox"]:checked + label:before {
	border-color: #03A9F4;
	background: #03A9F4;
}
.custom-checkbox input[type="checkbox"]:checked + label:after {
	border-color: #fff;
}
.custom-checkbox input[type="checkbox"]:disabled + label:before {
	color: #b8b8b8;
	cursor: auto;
	box-shadow: none;
	background: #ddd;
}

.modal .modal-dialog {
	max-width: 700px;
}
.modal , .modal .modal-body, .modal .modal-footer {
	padding: 05px 05px;     
}
.modal-header{
        background-color: #435d7d;
        color:#dddddd;     
}
.close{
    color: #fff;
}

.modal .modal-content {
	border-radius: 3px;
	font-size: 14px;
}
.modal .modal-footer {
	background: #ecf0f1;
	border-radius: 0 0 3px 3px;
}
.modal .modal-title {
	display: inline-block;
        
}
.modal .form-control {
	border-radius: 2px;
	box-shadow: none;
	border-color: #dddddd;
}
.modal textarea.form-control {
	resize: vertical;
}
.modal .btn {
	border-radius: 2px;
	min-width: 100px;
}	
.modal form label {
	font-weight: normal;
        .table-scrollable {
    display: block;
    max-height: 300px;
    overflow-y: auto;
    width: 100%;
  }
  
}	
.slider {
  -webkit-appearance: none;
  width: 100%;
  height: 15px;
  border-radius: 5px;  
  background: #d3d3d3;
  outline: none;
  opacity: 0.7;
  -webkit-transition: .2s;
  transition: opacity .2s;
}

.slider::-webkit-slider-thumb {
  -webkit-appearance: none;
  appearance: none;
  width: 25px;
  height: 25px;
  border-radius: 50%; 
  background: #04AA6D;
  cursor: pointer;
}

.slider::-moz-range-thumb {
  width: 25px;
  height: 25px;
  border-radius: 50%;
  background: #04AA6D;
  cursor: pointer;
}
</style>
<script>
$(document).ready(function(){
	
	$('[data-toggle="tooltip"]').tooltip();
	
	
	var checkbox = $('table tbody input[type="checkbox"]');
	$("#selectAll").click(function(){
		if(this.checked){
			checkbox.each(function(){
				this.checked = true;                        
			});
		} else{
			checkbox.each(function(){
				this.checked = false;                        
			});
		} 
	});
	checkbox.click(function(){
		if(!this.checked){
			$("#selectAll").prop("checked", false);
		}
	});
});

$(document).ready(function(){
    $('.delete').on('click', function(){
        var id = $(this).data('id');
        $('#deleteId').val(id);
    });
});
$(document).ready(function(){
    $('.update').on('click', function(){
        var id = $(this).data('id');
        $('#updateId').val(id);
    });
});
//$(document).ready(function(){
//    $('.pagin')
//}
$(document).ready(function(){
    $('.edit').on('click', function(){
        var id = $(this).data('id');
        $('#updateId').val(id);
    });
});
</script>
</head>
<body>
<div class="container-xl">
	<div class="table-responsive">
		<div class="table-wrapper">
			<div class="table-title">
				<div class="row">
					<div class="col-sm-6">
						<h2>Manage <b>Employees</b></h2>
					</div>
					<div class="col-sm-6">
                                            
  
                                            <div align="right">
                                                
                                                
                                                <form action="export" method="get">
                                                    <button type="submit" class="btn btn-success">Export To Excel</button>
                                                </form>
                                            </div>
						<a href="#filterModal" class="btn btn-success" data-toggle="modal"><i class="material-icons">&#xE147;</i> <span>filter</span></a>
						<a href="#new" class="btn btn-success" data-toggle="modal"><i class="material-icons">&#xE147;</i> <span>Add</span></a>
						<a href="#delete" class="btn btn-danger" data-toggle="modal"><i class="material-icons">&#xE15C;</i> <span>Delete</span></a>	
                                                
					<form action="search" method="get" class="form-inline">
                <div class="form-group">
                    <input type="text" name="searchKeyword" data-toggle="tooltip" title=" Search by Name,ID,email,phone_no "  class="form-control" placeholder="Search by Name,ID,email,phone_no">
                </div>
                
                <button type="submit" class="btn btn-primary">Search</button>
            </form>
                                                
                                        </div>
				</div>
			</div>
			<table class="table table-striped  table-hover " >
				<thead>
					<tr>
						<th>
							<span class="custom-checkbox">
								<input type="checkbox" id="selectAll">
								<label for="selectAll"></label>
							</span>
						</th>
                                                    <th>Pic</th>
                                                    <th>#ID</th>
                                                    <th>First Name</th>
                                                    <th>Last Name</th>
                                                    <th>Email</th>
                                                    <th>DoB</th>
                                                    <th>Location</th>
                                                    <th>Phone</th>
                                                    <th>Gender</th>
                                                    <th>Manager</th>
                                                    <th>Project</th>
                                                    <th>Job</th>
                                                    <th>Salary</th>
                                                    <th>Status</th>
                                                 
                                                    <th>Actions</th>
					</tr>
				</thead>
				<tbody >
					 <c:forEach var="employee" items="${listEmployees}">
    <tr>
        <td>
            <span class="custom-checkbox">
                <input type="checkbox" id="checkbox1" name="options[]" value="1">
                <label for="checkbox1"></label>
            </span>
        </td>
         <td>
            <img src="data:image/jpeg;base64,${employee.profilePicture}">
        </td>
        <td>${employee.employee_id}</td>
        <td>${employee.firstName}</td>
        <td>${employee.lastName}</td>
        <td>${employee.email}</td>
        <td>${employee.dob}</td>
        <td>${employee.location}</td>
        <td>${employee.phoneNo}</td>
        <td>${employee.gender}</td>
        <td>${employee.manager}</td>
        <td>${employee.project}</td>
        <td>${employee.job}</td>
        <td>${employee.salary}</td>
        <td>${employee.empStatus}</td>
        <td>
            
            <a href="#update" class="edit" data-toggle="modal" data-id="${employee.id}"><i class="material-icons" data-toggle="tooltip" title="Edit">&#xE254;</i></a>

            <a href="#delete" class="delete" data-toggle="modal" data-id="${employee.id}"><i class="material-icons" data-toggle="tooltip" title="Delete">&#xE872;</i></a>
        </td>
    </tr>
</c:forEach>
			</tbody>
			</table>
                    
                        <div class="clearfix">
    <div class="hint-text">
        Showing <b>${listEmployees.size()}</b> out of <b>${totalPages*5}</b> entries
    </div>
    <ul class="pagination">
        <c:if test="${currentPage > 1}">
            <li class="page-item disabled"><a href="list?page=${currentPage - 1}">&laquo; Prev</a></li>
        </c:if>
        <c:forEach var="pageNumber" begin="1" end="${totalPages}">
            <li class="page-item active"> <a href="list?page=${pageNumber}">${pageNumber}</a></li>
        </c:forEach>
        <c:if test="${currentPage < totalPages}">
            <li class="page-item">  <a href="list?page=${currentPage + 1}">Next &raquo;</a></li>
        </c:if>
    </ul>
</div>
                </div>
	</div>        


<div id="new" class="modal fade">
    <div class="modal-dialog modal-lg"> 
        <div class="modal-content">
            <form action="insert" method="post">
                <div class="modal-header">						
                    <h4 class="modal-title">Add Employee</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-6">
                            <!-- Left column -->
                            
                            <div class="form-group">
                                 <input type="file" name="profilePictureFile">
                            </div>
                                 
                            <div class="form-group">
                                <label>First Name *</label>
                                <input type="text" name="firstName" class="form-control" required>
                            </div>
                            <div class="form-group">
                                <label>Last Name *</label>
                                <input type="text" name="lastName" class="form-control">
                            </div>
                            <div class="form-group">
                                <label>E-mail *</label>
                                <input type="email" name="email" class="form-control" pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}" required>
                            </div>
                            <div class="form-group">
                                <label>Date Of Birth *</label>
                                <input type="date" name="dob" max="2006-02-12" min="1964-02-12" class="form-control" required>
                            </div>
                            <div class="form-group">
                                <label>Location *</label>
                                <textarea class="form-control" name="location" required></textarea>
                            </div>
                             <div class="form-group">
                                <label>Phone No. *</label>
                                <input type="tel" pattern="[5-9]{1}[0-9]{9}" name="phoneNo" class="form-control" required>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <!-- Right column -->
                            
                            <div class="form-group">
                                <label>Gender *</label>
                                <br>
                                <input type="radio" id="male" name="gender" value="male">
                                <label for="male">Male</label>
                                &nbsp;&nbsp;
                                <input type="radio" id="female" name="gender" value="female">
                                <label for="female">Female</label>
                            </div>
                            <div class="form-group">
                                <label>Manager</label>
                                <input type="text" name="manager" class="form-control">
                            </div>
                            <div class="form-group">
                                <label>Project</label>
                                <input type="text" name="project" class="form-control">
                            </div>
                            <div class="form-group">
                                <label>Job *</label>
                                <input type="text" class="form-control" name="job" required>
                            </div>
                            <div class="form-group">
                                <label>Salary *</label>
                                <input type="text" class="form-control" name="salary" required>
                            </div>
                            <div class="form-group">
                                <label for="empStatus">Employee Status:</label><br>
                                <select id="empStatus" name="empStatus" class="form-control">
                                    <option value="ACTIVE">Active</option>
                                    <option value="DEPARTED">Departed</option>
                                    <option value="RETIRED">Retired</option>
                                    <option value="AWOL">AWOL</option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="reset" class="btn btn-danger" data-dismiss="modal">Clear</button>
                    <button type="submit" class="btn btn-success">${employee != null ? 'Update' : 'Save'}</button>
                </div>
            </form>
        </div>
    </div>
</div>

    <div id="update" class="modal fade">
    <div class="modal-dialog modal-lg"> <!-- Added modal-lg class for large modal -->
        <div class="modal-content">
            <form id="updateForm" action="update" method="post">
                <input type="hidden" id="updateId" name="id">
                <div class="modal-header">						
                    <h4 class="modal-title">Edit Employee</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-6">
                            <!-- Left column -->
                              <div class="form-group">
                                 <input type="file" name="profilePictureFile">
                            </div>
                            <div class="form-group">
                                <label>First Name *</label>
                                <input type="text" name="firstName" class="form-control" required>
                            </div>
                            <div class="form-group">
                                <label>Last Name *</label>
                                <input type="text" name="lastName" class="form-control">
                            </div>
                            <div class="form-group">
                                <label>E-mail *</label>
                                <input type="email" name="email" class="form-control" pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}" required>
                            </div>
                            <div class="form-group">
                                <label>Date Of Birth *</label>
                                <input type="date" name="dob" max="2006-02-12" min="1964-02-12" class="form-control" required>
                            </div>
                            <div class="form-group">
                                <label>Location *</label>
                                <textarea class="form-control" name="location" required></textarea>
                            </div>
                            <div class="form-group">
                                <label>Phone No. *</label>
                                <input type="tel" pattern="[5-9]{1}[0-9]{9}" name="phoneNo" class="form-control" required>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <!-- Right column -->
                            <div class="form-group">
                                <label>Gender *</label>
                                <br>
                                <input type="radio" id="male" name="gender" value="Male">
                                <label for="male">Male</label>
                                &nbsp;&nbsp;
                                <input type="radio" id="female" name="gender" value="Female">
                                <label for="female">Female</label>
                            </div>
                            <div class="form-group">
                                <label>Manager</label>
                                <input type="text" name="manager" class="form-control">
                            </div>
                            <div class="form-group">
                                <label>Project</label>
                                <input type="text" name="project" class="form-control">
                            </div>
                            <div class="form-group">
                                <label>Job *</label>
                                <input type="text" class="form-control" name="job" required>
                            </div>
                            <div class="form-group">
                                <label>Salary *</label>
                                <input type="text" class="form-control" name="salary" required>
                            </div>
                            <div class="form-group">
                                <label for="empStatus">Employee Status:</label><br>
                                <select id="empStatus" name="empStatus" class="form-control">
                                    <option value="ACTIVE">Active</option>
                                    <option value="DEPARTED">Departed</option>
                                    <option value="RETIRED">Retired</option>
                                    <option value="AWOL">AWOL</option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger" data-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-success" name="update">${employee != null ? 'Update' : 'Save'}</button>
                </div>
            </form>
        </div>
    </div>
</div>
<!--
-->


<!-- Filter Modal -->
<div class="modal fade" id="filterModal" tabindex="-1" role="dialog" aria-labelledby="filterModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
        <form action="filter" method="get">
      <div class="modal-header">
        <h5 class="modal-title" id="filterModalLabel">Employee Filter</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>   
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-6">
            
         <div class="form-group">
        <label for="location">Location:</label>
        <select id="location" name="location" class="form-control" multiple>
            <c:forEach var="location" items="${locations}">
                <option value="${location}">${location}</option>
            </c:forEach>
        </select>
    </div>
          
          <div class="form-group">
            <label for="gender">Gender:</label>
            <select id="gender" name="gender" class="form-control" multiple>
                 <c:forEach var="gender" items="${genders}">
              <option value="${gender}">${gender}</option>
              </c:forEach>
            </select>
          </div>
            <div class="form-group">
            <label for="manager">Manager</label>
            <select id="location" name="location" class="form-control" multiple>
              <c:forEach var="manager" items="${managers}">
                <option value="${manager}">${manager}</option>
              </c:forEach>
            </select>
          </div>
                        </div>
                         <div class="col-md-6">
            <div class="form-group">
            <label for="project">Project</label>
            <select id="project" name="project" class="form-control" multiple>
              <c:forEach var="project" items="${projects}">
                <option value="${project}">${project}</option>
              </c:forEach>
            </select>
          </div>
              
            <div class="form-group">
            <label for="job">Dob</label>
            <select id="job" name="job" class="form-control" multiple>
              <c:forEach var="job" items="${jobRoles}">
                <option value="${job}">${job}</option>
              </c:forEach>
            </select>
          </div>
             <div class="form-group">
            <label for="salary">Salary</label>
            <input type="range" name="minSalary" min="10000" max="500000" step="1000" value="0">
            <input type="range" name="maxSalary" min="0" max="100000" step="1000" value="100000">

          </div>
                         </div>
                    </div>
          </div>
            <div class="modal-footer">
          <button type="submit"  class="btn btn-primary">Apply Filters</button>
            </div>
        </form>
    </div>
  </div>
</div>

<div id="delete" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <form id="deleteForm" action="delete" method="post">
                <input type="hidden" id="deleteId" name="id">
                <div class="modal-header">						
                    <h4 class="modal-title">Delete Employee</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                </div>
                <div class="modal-body">					
                    <p>Are you sure you want to delete this record?</p>
                    <p class="text-warning"><small>This action cannot be undone.</small></p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-danger">Delete</button>
                </div>
            </form>
        </div>
    </div>
</div>
</div>
</body>
</html>
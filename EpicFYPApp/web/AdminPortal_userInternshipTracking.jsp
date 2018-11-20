<%@page import="Model.Entity.InternshipStudentStatus"%>
<%@page import="Model.Dao.InternshipStudentStatusDAO"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="Model.Dao.InternshipDAO"%>
<%@page import="Model.Entity.Internship"%>
<%@page import="Model.Dao.UserDAO"%>
<%@page import="Model.Entity.User"%>
<%@page import="Model.Entity.InternshipStudent"%>
<%@page import="Model.Dao.InternshipStudentDAO"%>
<%@page import="java.time.LocalDate"%>
<%@page import="Model.Dao.CompanyDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Model.Entity.Company"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Epic Journey - Admin View Tracking User internships</title>
        <meta http-equiv="content-type" content="text/html; charset=utf-8" />
        <meta name="description" content="Imparting life skills through overseas exposure via internships and study missions. Countries of focus: Cambodia, Laos, Myanmar, Vietnam, India, Indonesia, Thailand, Japan and China." />
        <meta name="keywords" content="overseas, study missions, internships, training, life skills, career exposure" />
        <!--[if lte IE 8]><script src="css/ie/html5shiv.js"></script><![endif]-->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/animate.css@3.5.2/animate.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

        <script src="js/jquery.min.js"></script>
        <script src="js/skel.min.js"></script>
        <script src="js/skel-layers.min.js"></script>
        <script src="js/init.js"></script>
        <noscript>
        <link rel="stylesheet" href="css/skel.css" />
        <link rel="stylesheet" href="css/style.css" />
        <link rel="stylesheet" href="css/style-xlarge.css" />
        </noscript>
        <!--[if lte IE 8]><link rel="stylesheet" href="css/ie/v8.css" /><![endif]-->
    </head>
    <body>

        <!-- Header -->
        <jsp:include page="header.jsp" />
        
        <jsp:include page="AdminPortalPermission.jsp" />

        <section id="main" class="wrapper">
            <div class="container">
                <h2 class="align-center">User Internship Applications</h2>

                <!--tabs                -->
                <div class="tab align-center">
                    <button class="tablinks" onclick="openUser(event, 'confirmed')">Confirmed</button>
                    <button class="tablinks" onclick="openUser(event, 'pendingAdmin')" id="defaultOpen">Pending Admin Action</button>
                    <button class="tablinks" onclick="openUser(event, 'pendingUser')">Pending User Action</button>
                    <button class="tablinks" onclick="openUser(event, 'rejected')">Rejected</button>
                    <button class="tablinks" onclick="openUser(event, 'cancelled')">Cancelled</button>
                </div>

                <!--tabs' content 1.confirmed 2.pendingAdmin 3.pendingUser 4.Rejected 5.Cancelled-->
                <div id="confirmed" class="tabcontent">
                    <span onclick="this.parentElement.style.display = 'none'" class="toprightClose">&times</span>
                    <div class="row">
                        <div class="12u 12u(xsmall)">

                            <%
                                ArrayList<InternshipStudent> confirmedInternship = InternshipStudentDAO.getAllConfirmedInternshipStudents();
                                int countConfirmed = 0;
                                SimpleDateFormat fromDB = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                SimpleDateFormat myFormat = new SimpleDateFormat("dd MMMM yyyy , HH:mm a");
                                if (confirmedInternship.isEmpty()) {
                            %>
                            <p class="align-center">You have not confirmed any internship :(</p>
                            <%
                            } else {
                            %>
                            <table class="alt align-center" style="font-size:14px;">
                                <thead>
                                    <tr>
                                        <th class="align-center">#</th>
                                        <th class="align-center">Partner - Internship Name</th>
                                        <th class="align-center">Name</th>
                                        <th class="align-center">Last Updated</th>
                                        <th class="align-center">More Info</th>
                                        <th class="align-center">Cancel</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                        for (int i = 0; i < confirmedInternship.size(); i++) {
                                            InternshipStudent ci = confirmedInternship.get(i);
                                            int internshipID = ci.getInternshipID();
                                            Internship internship = InternshipDAO.getInternshipByID(internshipID);
                                            Company company= CompanyDAO.getCompanyByID(internshipID);
                                            User user = UserDAO.getUser(ci.getInternshipUserEmail());
                                            countConfirmed += 1;
                                            String lastUpdatedString = ci.getInternshipStudentLastUpdate();
                                            String reformattedStr = myFormat.format(fromDB.parse(lastUpdatedString));

                                    %>
                                    <tr>
                                        <td><%out.print(countConfirmed);%></td>
                                        <td><b><%out.print(company.getCompanyName());%></b> - <%out.print(internship.getInternshipName());%></td>
                                        <td><%out.print(user.getUserFirstName());%> <%out.print(user.getUserLastName());%></td>
                                        <td><%out.print(reformattedStr);%></td>
                                        <td><button type="button" class="button" data-toggle="modal" data-target="#myModalConfirmed<%out.print(i);%>">View</button></td>
                                        <td><button type="button" class="button">Cancel</button></td>
                                    </tr>

                                    <%
                                        }
                                    %>
                                </tbody>
                            </table>
                            <%}%>
                        </div>

                    </div>
                </div>

                <div id="pendingAdmin" class="tabcontent">
                    <span onclick="this.parentElement.style.display = 'none'" class="toprightClose">&times</span>
                    <div class="row">
                        <div class="12u 12u(xsmall)">
                            <%
                                ArrayList<InternshipStudent> pendingAdmin = InternshipStudentDAO.getAllPendingAdminInternshipStudents();
                                int countPendingAdmin = 0;
                                if (pendingAdmin.isEmpty()) {
                            %>
                            <p class="align-center">There are no pending admin tasks :D</p>
                            <%
                            } else {
                            %>
                            <table class="alt align-center" style="font-size:14px;">
                                <thead>
                                    <tr>
                                        <th class="align-center">#</th>
                                        <th class="align-center">Name</th>
                                        <th class="align-center">User's Field of Study</th>
                                        <th class="align-center">Status</th>
                                        <th class="align-center">Follow-up</th>
                                        <th class="align-center">Last Updated</th>
                                        <th class="align-center">More Info</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                        for (int i = 0; i < pendingAdmin.size(); i++) {
                                            InternshipStudent ci = pendingAdmin.get(i);
                                            User user = UserDAO.getUser(ci.getInternshipUserEmail());

                                            countPendingAdmin += 1;

                                            String lastUpdatedString = ci.getInternshipStudentLastUpdate();
                                            String reformattedStr = myFormat.format(fromDB.parse(lastUpdatedString));
                                            
                                            
                                            
                                            String[] statusFollowupList = ci.getInternshipStudentStatus().split("\\s*,\\s*");
                                            String lastStatusInt = statusFollowupList[statusFollowupList.length - 1];
                                            InternshipStudentStatus lastStatusWord = InternshipStudentStatusDAO.getinternshipStudentStatusByID(lastStatusInt);
                                            String[] statusFollowup = lastStatusWord.getInternshipStudentStatusName().split("\\s*-\\s*");
                                    %>
                                    <tr>
                                        <td><%out.print(countPendingAdmin);%></td>
                                        <td><%out.print(user.getUserFirstName());%> <%out.print(user.getUserLastName());%></td>
                                        <td><%out.print(user.getUserFieldOfStudy());%></td>
                                        <td><%out.print(statusFollowup[0]);%></td>
                                        <td><%out.print(statusFollowup[1]);%></td>
                                        <td><%out.print(reformattedStr);%></td>
                                        <td><button type="button" class="button" data-toggle="modal" data-target="#myModalPendingAdmin<%out.print(i);%>">View</button></td>

                                    </tr>
                                    <%
                                        }
                                    %>
                                </tbody>
                            </table>
                            <%}%>
                        </div>
                    </div>
                </div>

                <div id="pendingUser" class="tabcontent">
                    <span onclick="this.parentElement.style.display = 'none'" class="toprightClose">&times</span>
                    <div class="row">
                        <div class="12u 12u(xsmall)">
                            <%
                                ArrayList<InternshipStudent> pendingUser = InternshipStudentDAO.getAllPendingUserInternshipStudents();
                                int countPendingUser = 0;
                                if (pendingUser.isEmpty()) {
                            %>
                            <p class="align-center">There are no pending user tasks :D</p>
                            <%
                            } else {
                            %>
                            <table class="alt align-center" style="font-size:14px;">
                                <thead>
                                    <tr>
                                        <th class="align-center">#</th>
                                        <th class="align-center">Name</th>
                                        <th class="align-center">User's Field of Study</th>
                                        <th class="align-center">Status</th>
                                        <th class="align-center">Follow-up</th>
                                        <th class="align-center">Last Updated</th>
                                        <th class="align-center">More Info</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                        for (int i = 0; i < pendingUser.size(); i++) {
                                            InternshipStudent ci = pendingUser.get(i);
                                            User user = UserDAO.getUser(ci.getInternshipUserEmail());

                                            countPendingUser += 1;

                                            String lastUpdatedString = ci.getInternshipStudentLastUpdate();
                                            String reformattedStr = myFormat.format(fromDB.parse(lastUpdatedString));

                                            String[] statusFollowupList = ci.getInternshipStudentStatus().split("\\s*,\\s*");
                                            String lastStatus = statusFollowupList[statusFollowupList.length - 1];
                                            String[] statusFollowup = lastStatus.split("\\s*-\\s*");
                                    %>
                                    <tr>
                                        <td><%out.print(countPendingUser);%></td>
                                        <td><%out.print(user.getUserFirstName());%> <%out.print(user.getUserLastName());%></td>
                                        <td><%out.print(user.getUserFieldOfStudy());%></td>
                                        <td><%out.print(statusFollowup[0]);%></td>
                                        <td><%out.print(statusFollowup[1]);%></td>
                                        <td><%out.print(reformattedStr);%></td>
                                        <td><button type="button" class="button" data-toggle="modal" data-target="#myModalPendingUser<%out.print(i);%>">View</button></td>

                                    </tr>
                                    <%
                                        }
                                    %>
                                </tbody>
                            </table>
                            <%
                                }
                            %>
                        </div>
                    </div>
                </div>                

                <div id="rejected" class="tabcontent">
                    <span onclick="this.parentElement.style.display = 'none'" class="toprightClose">&times</span>
                    <div class="row">
                        <div class="12u 12u(xsmall)">
                            <%
                                ArrayList<InternshipStudent> rejectedInternship = InternshipStudentDAO.getAllRejectedInternshipStudents();
                                int countRejected = 0;
                                if (rejectedInternship.isEmpty()) {
                            %>
                            <p class="align-center">We have not had any rejected applications ;D</p>
                            <%
                            } else {
                            %>

                            <table class="alt align-center" style="font-size:14px;">
                                <thead>
                                    <tr>
                                        <th class="align-center">#</th>
                                        <th class="align-center">Name</th>
                                        <th class="align-center">Status</th>
                                        <th class="align-center">Last Updated</th>
                                        <th class="align-center">More Info</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                        for (int i = 0; i < rejectedInternship.size(); i++) {
                                            InternshipStudent ci = rejectedInternship.get(i);
                                            User user = UserDAO.getUser(ci.getInternshipUserEmail());

                                            countRejected += 1;

                                            String lastUpdatedString = ci.getInternshipStudentLastUpdate();
                                            String reformattedStr = myFormat.format(fromDB.parse(lastUpdatedString));

                                            String[] statusFollowupList = ci.getInternshipStudentStatus().split("\\s*,\\s*");
                                            String lastStatus = statusFollowupList[statusFollowupList.length - 1];
                                    %>
                                    <tr>
                                        <td><%out.print(countRejected);%></td>
                                        <td><%out.print(user.getUserFirstName());%> <%out.print(user.getUserLastName());%></td>
                                        <td><%out.print(lastStatus);%></td>
                                        <td><%out.print(reformattedStr);%></td>
                                        <td><button type="button" class="button" data-toggle="modal" data-target="#myModalRejected<%out.print(i);%>">View</button></td>

                                    </tr>
                                    <%
                                        }
                                    %>
                                </tbody>
                            </table>
                            <%
                                }
                            %>
                        </div>
                    </div>
                </div>


                <div id="cancelled" class="tabcontent">
                    <span onclick="this.parentElement.style.display = 'none'" class="toprightClose">&times</span>
                    <div class="row">
                        <div class="12u 12u(xsmall)">
                            <%
                                ArrayList<InternshipStudent> cancelledInternship = InternshipStudentDAO.getAllCancelledInternshipStudents();
                                int countCancelled = 0;
                                if (cancelledInternship.isEmpty()) {
                            %>
                            <p class="align-center">No internship has been cancelled :D</p>
                            <%
                            } else {
                            %>
                            <table class="alt align-center" style="font-size:14px;">
                                <thead>
                                    <tr>
                                        <th class="align-center">#</th>
                                        <th class="align-center">Name</th>
                                        <th class="align-center">Partner - Internship Name</th>
                                        <th class="align-center">Supervisor Name</th>
                                        <th class="align-center">Supervisor Email</th>
                                        <th class="align-center">Last Updated</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                        for (int i = 0; i < cancelledInternship.size(); i++) {
                                            InternshipStudent ci = cancelledInternship.get(i);
                                            User user = UserDAO.getUser(ci.getInternshipUserEmail());
                                            int internshipID = ci.getInternshipID();
                                            Internship internship = InternshipDAO.getInternshipByID(internshipID);
                                            Company company = CompanyDAO.getCompanyByID(internshipID);

                                            countCancelled += 1;

                                            String lastUpdatedString = ci.getInternshipStudentLastUpdate();
                                            String reformattedStr = myFormat.format(fromDB.parse(lastUpdatedString));

                                    %>
                                    <tr>
                                        <td><%out.print(countCancelled);%></td>
                                        <td><%out.print(user.getUserFirstName());%> <%out.print(user.getUserLastName());%></td>
                                        <td><b><%out.print(company.getCompanyName());%></b> - <%out.print(internship.getInternshipName());%></td>   
                                        <td><%out.print(internship.getInternshipSupervisor());%></td>
                                        <td><%out.print(internship.getInternshipSupervisorEmail());%></td>
                                        <td><%out.print(reformattedStr);%></td>
                                    </tr>
                                    <%
                                        }
                                    %>
                                </tbody>
                            </table>
                            <%
                                }
                            %>
                        </div>
                    </div>
                </div>                

                <!--modal box content: 1.confirmed 2.pendingAdmin 3.pendingUser 4.Rejected 5.Cancelled-->
                <%
                DecimalFormat df2 = new DecimalFormat("#.00");
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
                if (!confirmedInternship.isEmpty()) {
                    for (int i = 0; i < confirmedInternship.size(); i++) {
                        InternshipStudent ci = confirmedInternship.get(i);
                        int internshipID = ci.getInternshipID();
                        Internship internship = InternshipDAO.getInternshipByID(internshipID);
                        Company company = CompanyDAO.getCompanyByID(internshipID);
                        User user = UserDAO.getUser(ci.getInternshipUserEmail());
                        
                        String userName = user.getUserFirstName() + " " + user.getUserLastName();
                
                %>
                <div class="modal fade" id="myModalConfirmed<%out.print(i);%>" role="dialog">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                                <h4 class="modal-title align-center"><b><%out.print(userName);%></b> has accepted <b><%out.print(company.getCompanyName());%> - <%out.print(internship.getInternshipName());%></b></h4>
                            </div>
                            <div class="modal-body">
                                <div class="row">
                                    <div class="6u 12u">
                                             <table>
                                            <tbody>
                                                
                                                <tr>
                                                    <td class="align-right"><b>Company Name</b></td>
                                                    <td><%out.print(company.getCompanyName()); %></td>
                                                </tr>
                                                <tr>
                                                    <td class="align-right"><b>Country</b></td>
                                                    <td><%out.print(company.getCompanyCountry()); %>, <%out.print(company.getCompanyState()); %></td>
                                                </tr>
                                                
                                                <tr>
                                                    <td class="align-right"><b>Internship Position</b></td>
                                                    <td><%out.print(internship.getInternshipName()); %></td>
                                                </tr>
                                                <tr>
                                                    <td class="align-right"><b>Time Period</b></td>
                                                    <td><%out.print(dateFormat.format(internship.getInternshipStart())); %> <b>to</b> <%out.print(dateFormat.format(internship.getInternshipEnd())); %></td>
                                                </tr>
                                                <tr>
                                                    <td class="align-right"><b>Salary</b></td>
                                                    <td>$ <%out.print(df2.format(internship.getInternshipPay())); %></td>
                                                </tr>
                                                <tr>
                                                    <td class="align-right"><b>Supervisor Name</b></td>
                                                    <td><%out.print(internship.getInternshipSupervisor()); %></td>
                                                </tr>
                                                <tr>
                                                    <td class="align-right"><b>Supervisor Email</b></td>
                                                    <td><%out.print(internship.getInternshipSupervisorEmail()); %></td>
                                                </tr>
                                                <tr>
                                                    <td class="align-right"><b>Field of Study</b></td>
                                                    <td><%out.print(internship.getInternshipFieldOfStudy()); %></td>
                                                </tr>
                                                <tr>
                                                    <td class="align-right"><b>Description</b></td>
                                                    <td><%out.print(internship.getInternshipDescription()); %></td>
                                                </tr>
                                                
                                            </tbody>

                                        </table>                                          
                                    </div>
                                    <div class="6u 12u">
                                        <table>
                                            <tbody>
                                                <tr>
                                                    <td class="align-right"><b>Name </b></td>
                                                    <td><% out.print(userName); %></td>
                                                </tr>
                                                <tr>
                                                    <td class="align-right"><b>Email </b></td>
                                                    <td><% out.print(user.getUserEmail()); %></td>
                                                </tr>
                                                <tr>
                                                    <td class="align-right"><b>Phone </b></td>
                                                    <td><% out.print(user.getUserPhone()); %></td>
                                                </tr>
                                                <tr>
                                                    <td class="align-right"><b>Year of birth </b></td>
                                                    <td><% out.print(user.getUserDOB()); %></td>
                                                </tr>
                                                <tr>
                                                    <td class="align-right"><b>Citizenship </b></td>
                                                    <td><% out.print(user.getUserCitizenship()); %></td>
                                                </tr>
                                                <tr>
                                                    <td class="align-right"><b>Highest Qualification</b></td>
                                                    <td><% out.print(user.getUserHighestEducation()); %></td>
                                                </tr>
                                                <tr>
                                                    <td class="align-right"><b>Occupation </b></td>
                                                    <td><% out.print(user.getUserOccupation()); %></td>
                                                </tr>
                                                <tr>
                                                    <td class="align-right"><b>Organisation/School </b></td>
                                                    <td><% out.print(user.getUserSchool()); %></td>
                                                </tr>
                                                <tr>
                                                    <td class="align-right"><b>Field of Study </b></td>
                                                    <td><% out.print(user.getUserFieldOfStudy()); %></td>
                                                </tr>
                                                <tr>
                                                    <td class="align-right"><b>Interests </b></td>
                                                    <td><% out.print(user.getUserInterest()); %></td>
                                                </tr>
                                                
                                            </tbody>

                                        </table> 

                                    </div>                             
                                </div>
                                                
                                <div class="row">
                                    <h3 class="align-center"><b>Status History</b></h3>
                                    <table class="alt align-center">
                                            <thead>
                                                <tr>
                                                    <th class="align-center">#</th>
                                                    <th class="align-center">Application Status</th>
                                                    <th class="align-center">Date & Time</th>
                                                </tr>
                                             </thead> 
                                             <tbody>
                                    <%
                                            String[] statusFollowupList = ci.getInternshipStudentStatus().split("\\s*,\\s*");
                                            String[] timeStampList = ci.getInternshipStudentDatetime().split("\\s*,\\s*");
                                           int countStatus = 0;
                                           int length = statusFollowupList.length - 1;
                                            for(int x = length; x>=0; x--){
                                                String status = statusFollowupList[x];
                                                String dateReformat = myFormat.format(fromDB.parse(timeStampList[x]));
                                                
                                                countStatus += 1;
                                            
                                    %>
                                    <tr>
                                        <td><%out.print(countStatus);%></td>
                                        <td><%out.print(status);%></td>
                                        <td><%out.print(dateReformat);%></td>
                                    </tr>
                                    
                                    <%
                                            }
                                    %>
                                    </tbody>
                                    </table>
                                </div>

                            </div>
                            <div class="modal-footer">
                                <button type="button" class="button" data-dismiss="modal">Close</button>
                            </div>
                        </div>
                    </div>
                </div>

                <%
                    }}
                %>

                <%
                if (!pendingAdmin.isEmpty()) {
                    for (int i = 0; i < pendingAdmin.size(); i++) {
                        InternshipStudent ci = pendingAdmin.get(i);
                        User user = UserDAO.getUser(ci.getInternshipUserEmail());
                        
                        String userName = user.getUserFirstName() + " " + user.getUserLastName();
                
                %>
                <div class="modal fade" id="myModalPendingAdmin<%out.print(i);%>" role="dialog">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                                <h4 class="modal-title align-center">Information of <b><%out.print(userName);%></b></h4>
                            </div>
                            <div class="modal-body">
                                <div class="row">
                                    <div class="12u 12u">
                                        <table>
                                            <tbody>
                                                <tr>
                                                    <td class="align-right"><b>Name </b></td>
                                                    <td><% out.print(userName); %></td>
                                                </tr>
                                                <tr>
                                                    <td class="align-right"><b>Email </b></td>
                                                    <td><% out.print(user.getUserEmail()); %></td>
                                                </tr>
                                                <tr>
                                                    <td class="align-right"><b>Phone </b></td>
                                                    <td><% out.print(user.getUserPhone()); %></td>
                                                </tr>
                                                <tr>
                                                    <td class="align-right"><b>Year of birth </b></td>
                                                    <td><% out.print(user.getUserDOB()); %></td>
                                                </tr>
                                                <tr>
                                                    <td class="align-right"><b>Citizenship </b></td>
                                                    <td><% out.print(user.getUserCitizenship()); %></td>
                                                </tr>
                                                <tr>
                                                    <td class="align-right"><b>Highest Qualification</b></td>
                                                    <td><% out.print(user.getUserHighestEducation()); %></td>
                                                </tr>
                                                <tr>
                                                    <td class="align-right"><b>Occupation </b></td>
                                                    <td><% out.print(user.getUserOccupation()); %></td>
                                                </tr>
                                                <tr>
                                                    <td class="align-right"><b>Organisation/School </b></td>
                                                    <td><% out.print(user.getUserSchool()); %></td>
                                                </tr>
                                                <tr>
                                                    <td class="align-right"><b>Field of Study </b></td>
                                                    <td><% out.print(user.getUserFieldOfStudy()); %></td>
                                                </tr>
                                                <tr>
                                                    <td class="align-right"><b>Interests </b></td>
                                                    <td><% out.print(user.getUserInterest()); %></td>
                                                </tr>
                                                
                                            </tbody>

                                        </table> 

                                    </div>                             
                                </div>
                                                
                                <div class="row">
                                    <h3 class="align-center"><b>Status History</b></h3>
                                    <table class="alt align-center">
                                            <thead>
                                                <tr>
                                                    <th class="align-center">#</th>
                                                    <th class="align-center">Application Status</th>
                                                    <th class="align-center">Date & Time</th>
                                                </tr>
                                             </thead> 
                                             <tbody>
                                    <%
                                            String[] statusFollowupList = ci.getInternshipStudentStatus().split("\\s*,\\s*");
                                            String[] timeStampList = ci.getInternshipStudentDatetime().split("\\s*,\\s*");
                                           int countStatus = 0;
                                           int length = statusFollowupList.length - 1;
                                            for(int x = length; x>=0; x--){
                                                String status = statusFollowupList[x];
                                                String dateReformat = myFormat.format(fromDB.parse(timeStampList[x]));
                                                
                                                countStatus += 1;
                                            
                                    %>
                                    <tr>
                                        <td><%out.print(countStatus);%></td>
                                        <td><%out.print(status);%></td>
                                        <td><%out.print(dateReformat);%></td>
                                    </tr>
                                    
                                    <%
                                            }
                                    %>
                                    </tbody>
                                    </table>
                                </div>

                            </div>
                            <div class="modal-footer">
                                <button type="button" class="button" data-dismiss="modal">Close</button>
                            </div>
                        </div>
                    </div>
                </div>

                <%
                    }}
                %>

                <%
                if (!pendingUser.isEmpty()) {
                    for (int i = 0; i < pendingUser.size(); i++) {
                        InternshipStudent ci = pendingUser.get(i);
                        User user = UserDAO.getUser(ci.getInternshipUserEmail());
                        
                        String userName = user.getUserFirstName() + " " + user.getUserLastName();
                
                %>
                <div class="modal fade" id="myModalPendingUser<%out.print(i);%>" role="dialog">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                                <h4 class="modal-title align-center">Information of <b><%out.print(userName);%></b></h4>
                            </div>
                            <div class="modal-body">
                                <div class="row">
                                    
                                    <div class="12u 12u">
                                        <table>
                                            <tbody>
                                                <tr>
                                                    <td class="align-right"><b>Name </b></td>
                                                    <td><% out.print(userName); %></td>
                                                </tr>
                                                <tr>
                                                    <td class="align-right"><b>Email </b></td>
                                                    <td><% out.print(user.getUserEmail()); %></td>
                                                </tr>
                                                <tr>
                                                    <td class="align-right"><b>Phone </b></td>
                                                    <td><% out.print(user.getUserPhone()); %></td>
                                                </tr>
                                                <tr>
                                                    <td class="align-right"><b>Year of birth </b></td>
                                                    <td><% out.print(user.getUserDOB()); %></td>
                                                </tr>
                                                <tr>
                                                    <td class="align-right"><b>Citizenship </b></td>
                                                    <td><% out.print(user.getUserCitizenship()); %></td>
                                                </tr>
                                                <tr>
                                                    <td class="align-right"><b>Highest Qualification</b></td>
                                                    <td><% out.print(user.getUserHighestEducation()); %></td>
                                                </tr>
                                                <tr>
                                                    <td class="align-right"><b>Occupation </b></td>
                                                    <td><% out.print(user.getUserOccupation()); %></td>
                                                </tr>
                                                <tr>
                                                    <td class="align-right"><b>Organisation/School </b></td>
                                                    <td><% out.print(user.getUserSchool()); %></td>
                                                </tr>
                                                <tr>
                                                    <td class="align-right"><b>Field of Study </b></td>
                                                    <td><% out.print(user.getUserFieldOfStudy()); %></td>
                                                </tr>
                                                <tr>
                                                    <td class="align-right"><b>Interests </b></td>
                                                    <td><% out.print(user.getUserInterest()); %></td>
                                                </tr>
                                                
                                            </tbody>

                                        </table> 

                                    </div>                             
                                </div>
                                                
                                <div class="row">
                                    <h3 class="align-center"><b>Status History</b></h3>
                                    <table class="alt align-center">
                                            <thead>
                                                <tr>
                                                    <th class="align-center">#</th>
                                                    <th class="align-center">Application Status</th>
                                                    <th class="align-center">Date & Time</th>
                                                </tr>
                                             </thead> 
                                             <tbody>
                                    <%
                                            String[] statusFollowupList = ci.getInternshipStudentStatus().split("\\s*,\\s*");
                                            String[] timeStampList = ci.getInternshipStudentDatetime().split("\\s*,\\s*");
                                           int countStatus = 0;
                                           int length = statusFollowupList.length - 1;
                                            for(int x = length; x>=0; x--){
                                                String status = statusFollowupList[x];
                                                String dateReformat = myFormat.format(fromDB.parse(timeStampList[x]));
                                                
                                                countStatus += 1;
                                            
                                    %>
                                    <tr>
                                        <td><%out.print(countStatus);%></td>
                                        <td><%out.print(status);%></td>
                                        <td><%out.print(dateReformat);%></td>
                                    </tr>
                                    
                                    <%
                                            }
                                    %>
                                    </tbody>
                                    </table>
                                </div>

                            </div>
                            <div class="modal-footer">
                                <button type="button" class="button" data-dismiss="modal">Close</button>
                            </div>
                        </div>
                    </div>
                </div>

                <%
                    }}
                %>        
                
                <%
                if (!rejectedInternship.isEmpty()) {
                    for (int i = 0; i < rejectedInternship.size(); i++) {
                        InternshipStudent ci = rejectedInternship.get(i);
                        User user = UserDAO.getUser(ci.getInternshipUserEmail());
                        
                        String userName = user.getUserFirstName() + " " + user.getUserLastName();
                
                %>
                <div class="modal fade" id="myModalRejected<%out.print(i);%>" role="dialog">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                                <h4 class="modal-title align-center">Information of <b><%out.print(userName);%></b></h4>
                            </div>
                            <div class="modal-body">
                                <div class="row">\
                                    <div class="12u 12u">
                                        <table>
                                            <tbody>
                                                <tr>
                                                    <td class="align-right"><b>Name </b></td>
                                                    <td><% out.print(userName); %></td>
                                                </tr>
                                                <tr>
                                                    <td class="align-right"><b>Email </b></td>
                                                    <td><% out.print(user.getUserEmail()); %></td>
                                                </tr>
                                                <tr>
                                                    <td class="align-right"><b>Phone </b></td>
                                                    <td><% out.print(user.getUserPhone()); %></td>
                                                </tr>
                                                <tr>
                                                    <td class="align-right"><b>Year of birth </b></td>
                                                    <td><% out.print(user.getUserDOB()); %></td>
                                                </tr>
                                                <tr>
                                                    <td class="align-right"><b>Citizenship </b></td>
                                                    <td><% out.print(user.getUserCitizenship()); %></td>
                                                </tr>
                                                <tr>
                                                    <td class="align-right"><b>Highest Qualification</b></td>
                                                    <td><% out.print(user.getUserHighestEducation()); %></td>
                                                </tr>
                                                <tr>
                                                    <td class="align-right"><b>Occupation </b></td>
                                                    <td><% out.print(user.getUserOccupation()); %></td>
                                                </tr>
                                                <tr>
                                                    <td class="align-right"><b>Organisation/School </b></td>
                                                    <td><% out.print(user.getUserSchool()); %></td>
                                                </tr>
                                                <tr>
                                                    <td class="align-right"><b>Field of Study </b></td>
                                                    <td><% out.print(user.getUserFieldOfStudy()); %></td>
                                                </tr>
                                                <tr>
                                                    <td class="align-right"><b>Interests </b></td>
                                                    <td><% out.print(user.getUserInterest()); %></td>
                                                </tr>
                                                
                                            </tbody>

                                        </table> 

                                    </div>                             
                                </div>
                                                
                                <div class="row">
                                    <h3 class="align-center"><b>Status History</b></h3>
                                    <table class="alt align-center">
                                            <thead>
                                                <tr>
                                                    <th class="align-center">#</th>
                                                    <th class="align-center">Application Status</th>
                                                    <th class="align-center">Date & Time</th>
                                                </tr>
                                             </thead> 
                                             <tbody>
                                    <%
                                            String[] statusFollowupList = ci.getInternshipStudentStatus().split("\\s*,\\s*");
                                            String[] timeStampList = ci.getInternshipStudentDatetime().split("\\s*,\\s*");
                                           int countStatus = 0;
                                           int length = statusFollowupList.length - 1;
                                            for(int x = length; x>=0; x--){
                                                String status = statusFollowupList[x];
                                                String dateReformat = myFormat.format(fromDB.parse(timeStampList[x]));
                                                
                                                countStatus += 1;
                                            
                                    %>
                                    <tr>
                                        <td><%out.print(countStatus);%></td>
                                        <td><%out.print(status);%></td>
                                        <td><%out.print(dateReformat);%></td>
                                    </tr>
                                    
                                    <%
                                            }
                                    %>
                                    </tbody>
                                    </table>
                                </div>

                            </div>
                            <div class="modal-footer">
                                <button type="button" class="button" data-dismiss="modal">Close</button>
                            </div>
                        </div>
                    </div>
                </div>

                <%
                    }}
                %>

                
            </div>
        </section>
    </body>
    <script src="js/tabs.js"></script>
</html>
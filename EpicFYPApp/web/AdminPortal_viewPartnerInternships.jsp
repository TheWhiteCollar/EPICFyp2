<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="Model.Entity.Internship"%>
<%@page import="Model.Dao.InternshipDAO"%>
<%@page import="java.time.LocalDate"%>
<%@page import="Model.Dao.CompanyDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Model.Entity.Company"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Epic Journey - Admin View Partner internship applications</title>
        <meta http-equiv="content-type" content="text/html; charset=utf-8" />
        <meta name="description" content="Imparting life skills through overseas exposure via internships and study missions. Countries of focus: Cambodia, Laos, Myanmar, Vietnam, India, Indonesia, Thailand, Japan and China." />
        <meta name="keywords" content="overseas, study missions, internships, training, life skills, career exposure" />
        
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/animate.css@3.5.2/animate.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

        <script src="js/jquery.min.js"></script>
        <script src="js/bootstrap-notify.min.js"></script>
        <script src="js/skel.min.js"></script>
        <script src="js/skel-layers.min.js"></script>
        <script src="js/init.js"></script>
        
        <noscript>
        <link rel="stylesheet" href="css/skel.css" />
        <link rel="stylesheet" href="css/style.css" />
        <link rel="stylesheet" href="css/style-xlarge.css" />
        </noscript>
        
        <script>
            $(function () {
                $.get('/EpicFYPApp/getAllInternshipsServlet', function (internshipJson) {
                    var internships = JSON.parse(internshipJson);
                    var internshipHTML = '<div class="table-wrapper"><table>';
                    $.each(internships, function (index, internship) {
                        internshipHTML += '<thead><tr><th>Internship ID : ' + internship.internshipID + '</th><th colspan="3">' + internship.internshipName + "</th></tr></thead>";
                        internshipHTML += "<tr><td><form class=\"updateInternshipApproval\">";
                        internshipHTML += "<input style=\"display: none\" type=\"text\" name=\"internshipID\" value=\"" + internship.internshipID + "\"/>";
                        //internshipHTML += "<button class = \"button\" type=\"submit\" id=\"asd" + index + "\">Yes</button></form></td>";
                    });
                    internshipHTML += '</table></div>';
                    $("#internships").append(internshipHTML);
                });
            });   
            function reloadTable() {
                $.get('/EpicFYPApp/getAllInternshipsServlet', function (internshipJson) {
                        //parse string into JSON object
                    var internships = JSON.parse(internshipJson);
                        
                    $("#internships").empty();
                    var internshipHTML = '<div class="table-wrapper"><table>';
                        
                    $.each(internships, function (index, internship) {
                        internshipHTML += '<thead><tr><th>Internship ID : ' + internship.internshipID + '</th><th colspan="3">' + internship.internshipName + "</th></tr></thead>";
                        internshipHTML += "<tr><td><form class=\"updateInternshipApproval\">";
                        internshipHTML += "<input style=\"display: none\" type=\"text\" name=\"internshipID\" value=\"" + internship.internshipID + "\"/>";
                        //internshipHTML += "<button class = \"button\" type=\"submit\" id=\"asd" + index + "\">Yes</button></form></td>";
                    });
                    internshipHTML += '</table></div>';
                    $("#internships").append(internshipHTML);
                });
            }
        </script>
    </head>
    <body>

        <!-- Header -->
        <jsp:include page="header.jsp" />

        <section id="main" class="wrapper">
            <div class="container">
                <h2 class="align-center">Partner Internship Applications</h2>
                
                <!--tabs-->
                <div class="tab align-center">
                    <button class="tablinks" onclick="openUser(event, 'approved')">Approved</button>
                    <button class="tablinks" onclick="openUser(event, 'pending')" id="defaultOpen">Pending</button>
                    <button class="tablinks" onclick="openUser(event, 'rejected')">Rejected</button>
                </div>
                
                <!--tabs' content 1.approved 2.pending 3.rejected-->
                <div id="approved" class="tabcontent">
                    <span onclick="this.parentElement.style.display = 'none'" class="toprightClose">&times</span>
                    <div class="row">
                    <div class="12u 12u(xsmall)">
                        <%
                        ArrayList<Internship> approvedInternships = InternshipDAO.getAllApprovedInternships();
                                int counta = 0;
                                if (approvedInternships.isEmpty()) {
                        %>
                        <p class="align-center">There are no partner internship postings approved yet :/</p>
                        <%
                                }else{
                        %>
                        <table class="alt align-center" style="font-size:14px;">
                            <thead>
                                <tr>
                                    <th class="align-center">#</th>
                                    <th class="align-center">Partner Company</th>
                                    <th class="align-center">Job Title</th>
                                    <th class="align-center">Field of Study</th>
                                    <th class="align-center">Country</th>
                                    <th class="align-center">Vacancy</th>
                                    <th class="align-center">More Information</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                
                                    for (int i = 0; i < approvedInternships.size(); i++) {
                                        Internship internship = approvedInternships.get(i);
                                        Company company = CompanyDAO.getCompanyByID(internship.getInternshipPartnerID());
                                        counta += 1;
                                %>
                                <tr>
                                    <td class = "align-center"><%out.print(counta);%></td>
                                    <td><%out.print(company.getCompanyName());%></td>
                                    <td><%out.print(internship.getInternshipName());%></td>
                                    <td><%out.print(internship.getInternshipFieldOfStudy());%></td>
                                    <td><%out.print(company.getCompanyCountry());%></td>
                                    <td><%out.print(internship.getInternshipVacancy());%></td>
                                    <td><button type="button" class="button" data-toggle="modal" data-target="#myModalApproved<%out.print(i);%>">View</button></td>
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
                
                <div id="pending" class="tabcontent">
                    <span onclick="this.parentElement.style.display='none'" class="toprightClose">&times</span>
                    <div class="row">
                        <div class="12u 12u(xsmall)">
                            <%
                            ArrayList<Internship> pendingInternships = InternshipDAO.getAllPendingInternships();
                                int countp = 0;
                                if (pendingInternships.isEmpty()) {
                            %>
                            <p class="align-center">There are no pending applications to deal with :D</p>
                            <%
                                }else{
                            %>
                            <table class="alt align-center" style="font-size:14px;">
                            <thead>
                                <tr>
                                    <th class="align-center">#</th>
                                    <th class="align-center">Partner Company</th>
                                    <th class="align-center">Job Title</th>
                                    <th class="align-center">Contact's Name</th>
                                    <th class="align-center">Contact's Email</th>
                                    <th class="align-center">More Information</th>
                                   
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                
                                    for (int i = 0; i < pendingInternships.size(); i++) {
                                        Internship internship = pendingInternships.get(i);
                                        Company company= CompanyDAO.getCompanyByID(internship.getInternshipPartnerID());
                                        countp += 1;
                                %>
                                <tr>
                                    <td class = "align-center"><%out.print(countp);%></td>
                                    <td><%out.print(company.getCompanyName());%></td>
                                    <td><%out.print(internship.getInternshipName());%></td>
                                    <td><%out.print(internship.getInternshipSupervisor());%></td>
                                    <td><%out.print(internship.getInternshipSupervisorEmail());%></td>
                                    <td><button type="button" class="button" data-toggle="modal" data-target="#myModalPending<%out.print(i);%>">View</button></td>
                                    
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
                    <span onclick="this.parentElement.style.display='none'" class="toprightClose">&times</span>
                    <div class="row">
                        <div class="12u 12u(xsmall)">
                            <%
                            ArrayList<Internship> rejectedInternships = InternshipDAO.getAllRejectedInternships();
                                int countr = 0;
                                if (rejectedInternships.isEmpty()) {
                            %>
                            <p class="align-center">You have no rejected applications :P</p>
                            <%
                                }else{
                            %>
                            
                            <table class="alt align-center" style="font-size:14px;">
                            <thead>
                                <tr>
                                    <th class="align-center">#</th>
                                    <th class="align-center">Partner Company</th>
                                    <th class="align-center">Job Title</th>
                                    <th class="align-center">Contact's Name</th>
                                    <th class="align-center">Contact's Email</th>
                                    <th class="align-center">More Information</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                
                                    for (int i = 0; i < rejectedInternships.size(); i++) {
                                        Internship internship = rejectedInternships.get(i);
                                        Company company = CompanyDAO.getCompanyByID(internship.getInternshipPartnerID());
                                        countr += 1;
                                %>
                                <tr>
                                    <td class = "align-center"><%out.print(countr);%></td>
                                    <td><%out.print(company.getCompanyName());%></td>
                                    <td><%out.print(internship.getInternshipName());%></td>
                                    <td><%out.print(internship.getInternshipSupervisor());%></td>
                                    <td><%out.print(internship.getInternshipSupervisorEmail());%></td>
                                    <td><button type="button" class="button" data-toggle="modal" data-target="#myModalRejected<%out.print(i);%>">View</button></td>
                                
                                </tr>
                                <%
                                }}
                                %>
                            </tbody>
                        </table>
                        </div>
                    </div>
                </div>

               
                <!--modal box content: 1.approved 2.pending 3.rejected-->
                <%
                SimpleDateFormat fromDB = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat myFormat = new SimpleDateFormat("dd MMMM yyyy , HH:mm a");
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
                DecimalFormat df2 = new DecimalFormat("#.00");
                if (!approvedInternships.isEmpty()) {
                    for (int i = 0; i < approvedInternships.size(); i++) {
                        Internship internship = approvedInternships.get(i);
                        Company company = CompanyDAO.getCompanyByID(internship.getInternshipPartnerID());
                        
                        String dateTimes = internship.getInternshipDatetime();
                        String[] dateTimeList = dateTimes.split("\\s*,\\s*");
                        String date1 = dateTimeList[1];
                        String date0 = dateTimeList[0];

                        
                        String reformattedStr1 = myFormat.format(fromDB.parse(date1));
                        String reformattedStr0 = myFormat.format(fromDB.parse(date0));
                                      
                %>
                <div class="modal fade" id="myModalApproved<%out.print(i);%>" role="dialog">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                                <h4 class="modal-title align-center"><b><%out.print(company.getCompanyName()); %></b> internship application for <b><%out.print(internship.getInternshipName());%></b></h4>
                            </div>
                            <div class="modal-body">
                                <div class="row">
                                    <div class="4u 12u">
                                        <div align="center"><img src="https://www.freelogodesign.org/Content/img/logo-ex-7.png" alt ="partner-logo" height="80%" width="80%"></div>
                                        <table class="align-center">
                                            <tbody>
                                                <tr>
                                                    <td><b><%out.print(company.getCompanyName()); %></b></td>
                                                </tr>
                                                <tr>
                                                    <td><%out.print(company.getCompanyCountry()); %>, <%out.print(company.getCompanyState()); %></td>
                                                </tr>

                                            </tbody>
                                        </table>                                          
                                    </div>
                                    <div class="8u 12u">
                                        <table>
                                            <tbody>
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
                                                    <td class="align-right"><b>Vacancy</b></td>
                                                    <td><%out.print(internship.getInternshipVacancy()); %></td>
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
                                </div>
                                <div class="row">
                                    <div class="12u 12u(small)">
                                        <h2 class="align-center">Application Status History</h2>
                                        <table class="alt align-center">
                                            <thead>
                                                <tr>
                                                    <th class="align-center">#</th>
                                                    <th class="align-center">Application Status</th>
                                                    <th class="align-center">Date & Time</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td>1</td>
                                                    <td>Approved</td>
                                                    <td><%out.print(reformattedStr1); %></td>
                                                </tr>
                                                <tr>
                                                    <td>2</td>
                                                    <td>Pending</td>
                                                    <td><%out.print(reformattedStr0); %></td>
                                                </tr>
                                                
                                            </tbody>   
                                        </table>
                                    </div>
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
                if (!pendingInternships.isEmpty()) {
                    for (int i = 0; i < pendingInternships.size(); i++) {
                        Internship internship = pendingInternships.get(i);
                        Company company = CompanyDAO.getCompanyByID(internship.getInternshipPartnerID());
                        
                        
                                      
                %>
                <div class="modal fade" id="myModalPending<%out.print(i);%>" role="dialog">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                                <h4 class="modal-title align-center"><b><%out.print(company.getCompanyName()); %></b> internship application for <b><%out.print(internship.getInternshipName());%></b></h4>
                            </div>
                            <div class="modal-body">
                                <div class="row">
                                    <div class="4u 12u">
                                        <div align="center"><img src="https://www.freelogodesign.org/Content/img/logo-ex-7.png" alt ="partner-logo" height="80%" width="80%"></div>
                                        <table class="align-center">
                                            <tbody>
                                                <tr>
                                                    <td><b><%out.print(company.getCompanyName()); %></b></td>
                                                </tr>
                                                <tr>
                                                    <td><%out.print(company.getCompanyCountry()); %>, <%out.print(company.getCompanyState()); %></td>
                                                </tr>

                                            </tbody>
                                        </table>                                          
                                    </div>
                                    <div class="8u 12u">
                                        <table>
                                            <tbody>
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
                                                    <td class="align-right"><b>Vacancy</b></td>
                                                    <td><%out.print(internship.getInternshipVacancy()); %></td>
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
                                </div>
                                <div class="row">
                                    <div class="12u 12u(small)">
                                        <h2 class="align-center">Application Status History</h2>
                                        <table class="alt align-center">
                                            <thead>
                                                <tr>
                                                    <th class="align-center">#</th>
                                                    <th class="align-center">Application Status</th>
                                                    <th class="align-center">Date & Time</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td>1</td>
                                                    <td>Pending</td> 
                                                    <td><%out.print(myFormat.format(fromDB.parse(internship.getInternshipDatetime()))); %></td>
                                                </tr>
                                            </tbody>   
                                        </table>
                                    </div>
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
                if (!rejectedInternships.isEmpty()) {
                    for (int i = 0; i < rejectedInternships.size(); i++) {
                        Internship internship = rejectedInternships.get(i);
                        Company company = CompanyDAO.getCompanyByID(internship.getInternshipPartnerID());
                        
                        String dateTimes = internship.getInternshipDatetime();
                        String[] dateTimeList = dateTimes.split("\\s*,\\s*");
                        String date1 = dateTimeList[1];
                        String date0 = dateTimeList[0];

                        String reformattedStr1 = myFormat.format(fromDB.parse(date1));
                        String reformattedStr0 = myFormat.format(fromDB.parse(date0));
                                      
                %>
                <div class="modal fade" id="myModalRejected<%out.print(i);%>" role="dialog">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                                <h4 class="modal-title align-center"><b><%out.print(company.getCompanyName()); %></b> internship application for <b><%out.print(internship.getInternshipName());%></b></h4>
                            </div>
                            <div class="modal-body">
                                <div class="row">
                                    <div class="4u 12u">
                                        <div align="center"><img src="https://www.freelogodesign.org/Content/img/logo-ex-7.png" alt ="partner-logo" height="80%" width="80%"></div>
                                        <table class="align-center">
                                            <tbody>
                                                <tr>
                                                    <td><b><%out.print(company.getCompanyName()); %></b></td>
                                                </tr>
                                                <tr>
                                                    <td><%out.print(company.getCompanyCountry()); %>, <%out.print(company.getCompanyState()); %></td>
                                                </tr>

                                            </tbody>
                                        </table>                                          
                                    </div>
                                    <div class="8u 12u">
                                        <table>
                                            <tbody>
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
                                                    <td class="align-right"><b>Vacancy</b></td>
                                                    <td><%out.print(internship.getInternshipVacancy()); %></td>
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
                                </div>
                                <div class="row">
                                    <div class="12u 12u(small)">
                                        <h2 class="align-center">Application Status History</h2>
                                        <table class="alt align-center">
                                            <thead>
                                                <tr>
                                                    <th class="align-center">#</th>
                                                    <th class="align-center">Application Status</th>
                                                    <th class="align-center">Date & Time</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td>1</td>
                                                    <td>Rejected</td>
                                                    <td><%out.print(reformattedStr1); %></td>
                                                </tr>
                                                <tr>
                                                    <td>2</td>
                                                    <td>Pending</td>
                                                    <td><%out.print(reformattedStr0); %></td>
                                                </tr>
                                                
                                            </tbody>   
                                        </table>
                                    </div>
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
<%@page import="java.time.LocalDate"%>
<%@page import="Model.Dao.PartnerDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Model.Entity.Partner"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Epic Journey - Admin View Partner Profile</title>
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

<!--         Main 
        <section id="main" class="wrapper">
            <div class="container">

                <header class="major">
                    <h2>Partners' Profiles</h2>
                </header>

                 To add filter button 

                <div class ="row uniform 50%">
                    <div class ="6u 12u(xsmall)">
                        <input id="searchBar" type="text" name="searchbar" placeholder="Search for partner:"/>
                    </div>
                    <div class ="2u 12u(xsmall)">
                        <input type="submit">
                    </div>

                    <div class ="4u 12u(xsmall)">
                        <% //LocalDate todayDate = java.time.LocalDate.now(); %>
                        <form method="get" action="#">
                            <button type="submit" class="button">Download!</button>
                        </form>
                        <a href="/Users/xiuwenhime/NetBeansProjects/JavaApplication5/NewExcelFile.xls" download="<% //out.print(todayDate); %>_all_student_profile" class="button full_width">Download all student profiles</a>
                    </div>


                </div>
            </div>
        </section>-->
        <section class="wrapper">
            <div class="container" >
                
                <h2 class="align-center">Partners' Profiles</h2>
                <table class="alt">
                    <thead>
                        <tr>
                            <th class="align-center">#</th>
                            <th>Partner Name</th>
                            <th>Country</th>
                            <th class="align-center">State</th>
                            <th class="align-center">More information</th>

                        </tr>
                    </thead>
                    <tbody>
                        <%
                            ArrayList<Partner> allPartners = PartnerDAO.getAllPartners();
                            int count = 0;
                            if (!allPartners.isEmpty()) {
                                for (int i = 0; i < allPartners.size(); i++) {
                                    Partner p = allPartners.get(i);
                                    count += 1;
                        %>
                        <tr>
                            <td class="align-center"><%out.print(count);%></td>
                            <td><% out.print(p.getPartnerName()); %></td>                      
                            <td><% out.print(p.getPartnerCountry()); %></td>
                            <td class="align-center"><% out.print(p.getPartnerState()); %></td>
                            <td class="align-center"><button type="button" class="button" data-toggle="modal" data-target="#myModal<%out.print(i);%>">View</button></td>
                        </tr>
                        <%
                                }
                            }
                        %>
                    </tbody>    
                </table>

                <%
                    if (!allPartners.isEmpty()) {
                        for (int i = 0; i < allPartners.size(); i++) {
                            Partner p = allPartners.get(i);

                %>
                <div class="modal fade" id="myModal<%out.print(i);%>" role="dialog">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                                <h4 class="modal-title align-center"><b><%out.print(p.getPartnerName()); %></b></h4>
                            </div>
                            <div class="modal-body">
                                <div class="row">
                                    <div class="4u 12u">
                                        <div align="center"><img src="https://www.freelogodesign.org/Content/img/logo-ex-7.png" alt ="partner-logo" height="80%" width="80%"></div>
                                        <table class="align-center">
                                            <tbody>
                                                <tr>
                                                    <td><b><%out.print(p.getPartnerName()); %></b></td>
                                                </tr>

                                            </tbody>
                                        </table>                                          
                                    </div>
                                    <div class="8u 12u">
                                        <table>
                                            <tbody>
                                                <tr>
                                                    <td class="align-right"><b>Country </b></td>
                                                    <td><%out.print(p.getPartnerCountry()); %></td>
                                                </tr>
                                                <tr>
                                                    <td class="align-right"><b>State </b></td>
                                                    <td><%out.print(p.getPartnerState()); %></td>
                                                </tr>
                                                <tr>
                                                    <td class="align-right"><b>Description </b></td>
                                                    <td><% out.print(p.getPartnerDescription()); %></td>
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
                        }
                    }
                %>

            </div>
        </section>
    </body>
</html>
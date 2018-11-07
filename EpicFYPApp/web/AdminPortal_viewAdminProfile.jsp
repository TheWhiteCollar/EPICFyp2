<%@page import="Model.Entity.Admin"%>
<%@page import="Model.Dao.AdminDAO"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Epic Journey - Admin View Admin Profile</title>
        <meta http-equiv="content-type" content="text/html; charset=utf-8" />
        <meta name="description" content="Imparting life skills through overseas exposure via internships and study missions. Countries of focus: Cambodia, Laos, Myanmar, Vietnam, India, Indonesia, Thailand, Japan and China." />
        <meta name="keywords" content="overseas, study missions, internships, training, life skills, career exposure" />
        <!--[if lte IE 8]><script src="css/ie/html5shiv.js"></script><![endif]-->
        
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
            $(function() {
                //call get all admins using ajax get method. receives adminJson string
                $.get('/EpicFYPApp/getAllAdminsServlet', function (adminJson) {
                    //parse string into JSON object
                    var admins = JSON.parse(adminJson);
                    console.log(admins);
                    var adminHTML = '<div class="table-wrapper"><table>';
                    //loop through each admin and print out as rows in a table
                    $.each(admins, function (index, admin) {
                          adminHTML += '<thead><tr><th>Admin Name : ' + admin.adminName + '</th><th colspan="3">'
                                +  'Admin Level : '+ admin.adminLevel + "</th></tr></thead>";
                        adminHTML += "<tr><td><form class=\"deleteAdmin\">";
                        adminHTML += "<input style=\"display: none\" type=\"text\" name=\"adminName\" value=\"" + admin.adminName + "\"/>";
                        adminHTML += "<button class = \"button\" type=\"submit\" id=\"asd" + index + "\">Delete Admin</button></form></td>";                    
                    });
                    
                    adminHTML += '</table></div>';
                    
                    $("#admins").append(adminHTML);

                    //wait for delete admin form to be submited
                    $(".deleteAdmin").submit(function (event) {
                        //store the adminname from the form
                        var adminName = "" + $(this).children("input").val();
                        var deleteData = {
                            'adminName': adminName
                        };
                        console.log(deleteData);
                        //send an ajax post request to the delete admin servlet with delete data
                        $.post('/EpicFYPApp/deleteAdmin', deleteData, function (response) {
                            if (response === "success") {
                                $.notify({
                                    // options
                                    message: 'Successfully deleted admin'
                                }, {
                                    // settings
                                    type: 'success'
                                });
                            } else {
                                $.notify({
                                    // options
                                    message: 'Fail to delete admin'
                                }, {
                                    // settings
                                    type: 'danger'
                                });
                            }
                            reloadTable();
                        })
                        //prevents form from being submitted
                        event.preventDefault();
                        // validate and process form here
                    });
                });
            });
            
            // wait for add trip submit event 
            function addingAdmin() {
                let adminName = $('input[name="adminName"]').val();
                let adminPassword = $('input[name="adminPassword"]').val();
                let adminLevel = $('input[name="adminLevel"]').val();

                let adminData = {
                    "adminName": adminName,
                    "adminPassword": adminPassword,
                    "adminLevel": adminLevel
                }

                console.log("this is admin data");
                console.log(adminData);
                //send ajax post request to addAdmin servlet with adminData
                $.post('/EpicFYPApp/addAdmin', adminData, function (response) {
                    $('button[data-dismiss="modal"]').click();
                    reloadTable();
                    if (response === "success") {
                        $.notify({
                            // options
                            message: 'Successfully inserted admin'
                        }, {
                            // settings
                            type: 'success'
                        });
                    } else {
                        $.notify({
                            // options
                            message: 'Fail to insert admin'
                        }, {
                            // settings
                            type: 'danger'
                        });
                    }
                });
                event.preventDefault();
            }
            
            function reloadTable() {
                $.get('/EpicFYPApp/getAllAdminsServlet', function (adminJson) {
                    //parse string into JSON object
                    var admins = JSON.parse(adminJson);
                    console.log(admins);
                    var adminHTML = '<div class="table-wrapper"><table>';
                    //loop through each admin and print out as rows in a table
                    $.each(admins, function (index, admin) {
                          adminHTML += '<thead><tr><th>Admin Name : ' + admin.adminName + '</th><th colspan="3">'
                                +  'Admin Level : '+ admin.adminLevel + "</th></tr></thead>";
                        adminHTML += "<tr><td><form class=\"deleteAdmin\">";
                        adminHTML += "<input style=\"display: none\" type=\"text\" name=\"adminName\" value=\"" + admin.adminName + "\"/>";
                        adminHTML += "<button class = \"button\" type=\"submit\" id=\"asd" + index + "\">Delete Admin</button></form></td>";                    
                    });
                    
                    adminHTML += '</table></div>';
                    
                    $("#admins").empty();
                    $("#admins").append(adminHTML);

                    //wait for delete admin form to be submited
                    $(".deleteAdmin").submit(function (event) {
                        //store the adminname from the form
                        var adminName = "" + $(this).children("input").val();
                        var deleteData = {
                            'adminName': adminName
                        };
                        console.log("adminName: " + adminName);
                        //send an ajax post request to the delete admin servlet with delete data
                        $.post('/EpicFYPApp/deleteAdmin', deleteData, function (response) {
                            if (response === "success") {
                                $.notify({
                                    // options
                                    message: 'Successfully deleted admin'
                                }, {
                                    // settings
                                    type: 'success'
                                });
                            } else {
                                $.notify({
                                    // options
                                    message: 'Fail to delete admin'
                                }, {
                                    // settings
                                    type: 'danger'
                                });
                            }
                            reloadTable();
                        })
                        event.preventDefault();
                    });
                });
            }
        </script>
    </head>
    <body>

        <!-- Header -->
        <jsp:include page="header.jsp" />

        <section id="main" class="wrapper">
            
            <div class="container" >
                <h2 class="align-center">Admins' Profiles</h2>
                <div class="container align-center">
                    <div class="tab">
                        <button class="tablinks" onclick="openUser(event, 'addAdmin')">Add New Admin</button>
                    </div>
                </div>
                </br>
                
                <div id="addAdmin" class="tabcontent">
                    <span onclick="this.parentElement.style.display = 'none'" class="toprightClose">&times</span>              
                    <div class = "row 50% uniform">
                        <div class = "5u 12u(xsmall) table-wrapper" id="allAdmins"></div>
                        <div class="11u 10u(xsmall)">
                            <table>
                                <tbody>
                                    <tr>
                                        <td colspan="2"><h4 class="align-center">Admin Name <input type="text" name="adminName" placeholder="E.g. Emily" /></h4></td>
                                        <td colspan="2"><h4 class="align-center">Admin Password <input type="password" name="adminPassword"/></h4></td>
                                        <td colspan="2"><h4 class="align-center">Admin Level <input type="text" name="adminLevel" placeholder="E.g. superuser" /></h4></td>
                                        <td><button onclick="addingAdmin()">Add Admin</button></td>
                                    </tr>
                                </tbody>
                            </table>                
                        </div>
                    </div>
                </div>
                
                <div class ="container">
                <br>
                <h2>All Listed Admins</h2>
                <div id="admins" class ="container"></div>
                </div>
                
            </div>
        </section>
        <script src="js/custom-file-input.js"></script>
        <script src="js/tabs.js"></script>
    </body>
</html>
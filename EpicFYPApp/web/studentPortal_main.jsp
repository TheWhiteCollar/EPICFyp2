<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Student Portal Main Page</title>
        <meta http-equiv="content-type" content="text/html; charset=utf-8" />
        <meta name="description" content="Imparting life skills through overseas exposure via internships and study missions. Countries of focus: Cambodia, Laos, Myanmar, Vietnam, India, Indonesia, Thailand, Japan and China." />
        <meta name="keywords" content="overseas, study missions, internships, training, life skills, career exposure" />
        <!--[if lte IE 8]><script src="css/ie/html5shiv.js"></script><![endif]-->
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


        <section class="wrapper">
            <div class ="container">              
                <header class="major align-center">
                    <h2>Application Status</h2>
                </header>
            </div>
            <div class="row 50% uniform">
                <div class="6u">
                    <%
                    //get the status of a user
                    //get how many different trips they signed up for
                    //then populate the trip status by order
                    //if the status equals to Pending Deposit have action to point them
                    //if status equals to Pending Full Payment then point to ___
                    


                    %>
                    
                    <table class="alt align-center" style="font-size:14px;">
                        <thead>
                            <tr>
                                <th>Status</th>
                                <th>Timestamp</th>
                                <th>Action</th>
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
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <%
                                }
                            %>
                            
                        </tbody>
                    </table>
                </div>
                <div class="6u">
                    
                </div>
                
            </div>
            
            
        </section>
        

        


    </body>
</html>

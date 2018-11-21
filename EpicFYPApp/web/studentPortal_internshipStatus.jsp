<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Model.Dao.TripStudentDAO"%>
<%@page import="Model.Entity.User"%>
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
            
            <div class="row 50% uniform">
                <div class="6u 12u">
                    <%
                    //get the status of a user (below) if 0 then don't have .isEmpty();
                    //get how many different trips they signed up for
                    //then populate the trip status by order
                    //if the status equals to Pending Deposit have action to point them
                    //if status equals to Pending Remaining Amount then point to ___
                    User User = (User) session.getAttribute("User");
                    String email = User.getUserEmail();
                    
                    ArrayList<Integer> tripList= TripStudentDAO.getTripIDsByUser(email);   

                    %>
                    
                    <table class="alt align-center" style="font-size:14px;">
                        <thead>
                            <tr>
                                <th>Trip ID</th>
                                <th>Status</th>
                                <th>Timestamp</th>
                                <th>Action</th>
                            </tr>                           
                        </thead>
                        <tbody>
                            <%    
                                //loop based off tripList count
                                for(int i = 0; i < tripList.size() ;i++){
                                    int tripID = tripList.get(i);
                                    ArrayList<String> tripStatusList = TripStudentDAO.getTripStatusByTripID(email, tripID);
                                    int statusCount = tripStatusList.size();
                                    String status = tripStatusList.get(statusCount-2);
                                    String statusDate = tripStatusList.get(statusCount-1);
                                    
                                    //format the statusDate
                                    SimpleDateFormat fromDB = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    SimpleDateFormat myFormat = new SimpleDateFormat("dd MMMM yyyy , HH:mm a"); 
                                    String reformattedDate = myFormat.format(fromDB.parse(statusDate));
                            %>
                            
                            <tr>
                                <td class="align-center"><%out.print(tripID);%></td>
                                <td class="align-center"><%out.print(status);%></td>
                                <td class="align-center"><%out.print(reformattedDate);%></td>
                                <%
                                    if (status.equals("Pending Deposit")){
                                %>
                                <td class="align-center"><a href="payment.jsp?tripId=<%out.print(tripID);%>&type=deposit" class="button">Pay</a></td>
                                <%        
                                    } else if (status.equals("Pending Remaining Amount")){ 
                                %> 
                                <td class="align-center"><a href="payment.jsp?tripId=<%out.print(tripID);%>&type=remainder" class="button">Pay</a></td>
                                <%
                                    }
                                %>
                                
                            </tr>
                            <%
                                }
                            %>
                            
                        </tbody>
                    </table>
                </div>
                <div class="6u 12u">
                    
                </div>
                
            </div>
                
            </div>
                            
            
            
        </section>
        

        


    </body>
</html>
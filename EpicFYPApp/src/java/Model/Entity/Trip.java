/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Entity;

import java.sql.Date;
import java.util.ArrayList;

/* Database sequence
    #1: tripID (int 11)
    #2: tripTitle (varchar 100)
    #3: tripPrice (double)
    #4: tripItinerary (varchar 100)
    #5: tripDescription (varchar 1000)
    #6: tripCountry (varchar 100)
    #7: tripState (varchar 100)
    #8: tripStart (date)
    #9: tripEnd (date)
    #10: tripDuration (int 11)
    #11: tripActivation (int 3)
    #12: tripInterest (varchar 500)
    #13: tripTotalSignup (int 11)
    #14: tripPromo (varchar 100)
    #15: tripPromoPercentage (double)
 */

public class Trip {

    private int tripID;
    private String tripTitle;
    private double tripPrice;
    private String tripItinerary;
    private String tripDescription;
    private String tripCountry;
    private String tripState;
    private Date tripStart;
    private Date tripEnd;
    private int tripDuration;
    private int tripActivation;
    private String tripInterest;
    private int tripTotalSignup;
    private String tripPromo;
    private double tripPromoPercentage;
    
    /*to count signup and counter*/
    private ArrayList<String> signedUpEmails;
    private boolean activated;
    

    public Trip(int tripID, String tripTitle, double tripPrice, String tripItinerary, String tripDescription, String tripCountry, String tripState, Date tripStart, Date tripEnd, int tripDuration, int tripActivation, String tripInterest, int tripTotalSignup, String tripPromo, double tripPromoPercentage, ArrayList<String> signedUpEmails) {
        this.tripID = tripID;
        this.tripTitle = tripTitle;
        this.tripPrice = tripPrice;
        this.tripItinerary = tripItinerary;
        this.tripDescription = tripDescription;
        this.tripCountry = tripCountry;
        this.tripState = tripState;
        this.tripStart = tripStart;
        this.tripEnd = tripEnd;
        this.tripDuration = tripDuration;
        this.tripActivation = tripActivation;
        this.tripInterest = tripInterest;
        this.tripTotalSignup = tripTotalSignup;
        this.tripPromo = tripPromo;
        this.tripPromoPercentage = tripPromoPercentage;
        
        this.signedUpEmails = signedUpEmails;
        if(signedUpEmails.size()< tripActivation ){
            this.activated = false;
        } else {
            this.activated = true;
        }
    }
    
    // have 1 more trip constructor cause database dont have the column with Emails of signees
    public Trip(int tripID, String tripTitle, double tripPrice, String tripCountry, Date tripStart, Date tripEnd, int tripDuration) {
        this.tripID = tripID;
        this.tripTitle = tripTitle;
        this.tripPrice = tripPrice;
        this.tripCountry = tripCountry;
        this.tripStart = tripStart;
        this.tripEnd = tripEnd;
        this.tripDuration = tripDuration;        
    }
    
    public int getTripID() {
        return tripID;
    }

    public void setTripID(int tripID) {
        this.tripID = tripID;
    }

    public String getTripTitle() {
        return tripTitle;
    }

    public void setTripTitle(String tripTitle) {
        this.tripTitle = tripTitle;
    }

    public double getTripPrice() {
        return tripPrice;
    }

    public void setTripPrice(double tripPrice) {
        this.tripPrice = tripPrice;
    }

    public String getTripItinerary() {
        return tripItinerary;
    }

    public void setTripItinerary(String tripItinerary) {
        this.tripItinerary = tripItinerary;
    }

    public String getTripDescription() {
        return tripDescription;
    }

    public void setTripDescription(String tripDescription) {
        this.tripDescription = tripDescription;
    }

    public String getTripCountry() {
        return tripCountry;
    }

    public void setTripCountry(String tripCountry) {
        this.tripCountry = tripCountry;
    }

    public String getTripState() {
        return tripState;
    }

    public void setTripState(String tripState) {
        this.tripState = tripState;
    }

    public Date getTripStart() {
        return tripStart;
    }

    public void setTripStart(Date tripStart) {
        this.tripStart = tripStart;
    }

    public Date getTripEnd() {
        return tripEnd;
    }

    public void setTripEnd(Date tripEnd) {
        this.tripEnd = tripEnd;
    }

    public int getTripDuration() {
        return tripDuration;
    }

    public void setTripDuration(int tripDuration) {
        this.tripDuration = tripDuration;
    }
    
    public int getTripActivation() {
        return tripActivation;
    }

    public void setTripActivation(int tripActivation) {
        this.tripActivation = tripActivation;
    }

    public String getTripInterest() {
        return tripInterest;
    }

    public void setTripInterest(String tripInterest) {
        this.tripInterest = tripInterest;
    }

    public int getTripTotalSignup() {
        return tripTotalSignup;
    }

    public void setTripTotalSignup(int tripTotalSignup) {
        this.tripTotalSignup = tripTotalSignup;
    }

    public String getTripPromo() {
        return tripPromo;
    }

    public void setTripPromo(String tripPromo) {
        this.tripPromo = tripPromo;
    }

    public double getTripPromoPercentage() {
        return tripPromoPercentage;
    }

    public void setTripPromoPercentage(double tripPromoPercentage) {
        this.tripPromoPercentage = tripPromoPercentage;
    }
    
    public int getNumberOfStudents() {
        return signedUpEmails.size();
    }
    
    public boolean getActivated() {
        return activated;
    }
    
    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public ArrayList<String> getSignedUpEmails() {
        return signedUpEmails;
    }

    public void setSignedUpEmails(ArrayList<String> signedUpEmails) {
        this.signedUpEmails = signedUpEmails;
    } 
}

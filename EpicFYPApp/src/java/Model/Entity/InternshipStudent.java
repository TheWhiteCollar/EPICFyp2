/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Entity;

/* Database sequence
    #1: internshipID (int 11)
    #2: internshipUserEmail (varchar 50)
    #3: internshipStudentStatus (varchar 25)
    #4: internshipStudentContinent (varchar 15)
    #5: internshipStudentDatetime (varchar 160)
    #6: internshipStudentDatetimeApplied (datetime YYYY-MM-DD HH:MM:SS)
    #7: internshipStudentLastUpdate (datetime YYYY-MM-DD HH:MM:SS)
 */

public class InternshipStudent {

    private int internshipID;
    private String internshipUserEmail;
    private String internshipStudentStatus;
    private String internshipStudentContinent;
    private String internshipStudentDatetime;
    private String internshipStudentDatetimeApplied;
    private String internshipStudentLastUpdate;

    public InternshipStudent(int internshipID, String internshipUserEmail, String internshipStudentStatus, String internshipStudentContinent, String internshipStudentDatetime, String internshipStudentDatetimeApplied, String internshipStudentLastUpdate) {
        this.internshipID = internshipID;
        this.internshipUserEmail = internshipUserEmail;
        this.internshipStudentStatus = internshipStudentStatus;
        this.internshipStudentContinent = internshipStudentContinent;
        this.internshipStudentDatetime = internshipStudentDatetime;
        this.internshipStudentDatetimeApplied = internshipStudentDatetimeApplied;
        this.internshipStudentLastUpdate = internshipStudentLastUpdate;
    }

    public int getInternshipID() {
        return internshipID;
    }

    public void setInternshipID(int internshipID) {
        this.internshipID = internshipID;
    }

    public String getInternshipUserEmail() {
        return internshipUserEmail;
    }

    public void setInternshipUserEmail(String internshipUserEmail) {
        this.internshipUserEmail = internshipUserEmail;
    }

    public String getInternshipStudentStatus() {
        return internshipStudentStatus;
    }

    public void setInternshipStudentStatus(String internshipStudentStatus) {
        this.internshipStudentStatus = internshipStudentStatus;
    }

    public String getInternshipStudentContinent() {
        return internshipStudentContinent;
    }

    public void setInternshipStudentContinent(String internshipStudentContinent) {
        this.internshipStudentContinent = internshipStudentContinent;
    }

    public String getInternshipStudentDatetime() {
        return internshipStudentDatetime;
    }

    public void setInternshipStudentDatetime(String internshipStudentDatetime) {
        this.internshipStudentDatetime = internshipStudentDatetime;
    }

    public String getInternshipStudentDatetimeApplied() {
        return internshipStudentDatetimeApplied;
    }

    public void setInternshipStudentDatetimeApplied(String internshipStudentDatetimeApplied) {
        this.internshipStudentDatetimeApplied = internshipStudentDatetimeApplied;
    }

    public String getInternshipStudentLastUpdate() {
        return internshipStudentLastUpdate;
    }

    public void setInternshipStudentLastUpdate(String internshipStudentLastUpdate) {
        this.internshipStudentLastUpdate = internshipStudentLastUpdate;
    }
   
    
    
}

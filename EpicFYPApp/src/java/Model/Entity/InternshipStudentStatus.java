/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Entity;

/*
    #1: internshipStudentStatusID (int 2)
    #2: internshipStudentStatusName (varchar 50)
    #3: internshipStudentAction (int 1) 0=rejected, 1=admin action, 2=user action, 3=internship confirmed, 4=cancelled
    #4: internshipStudentCycle (varchar 10) done, processing

 */
public class InternshipStudentStatus {
    private int internshipStudentStatusID;
    private String internshipStudentStatusName;
    private int internshipStudentAction;
    private String internshipStudentCycle;

    public InternshipStudentStatus(int internshipStudentStatusID, String internshipStudentStatusName, int internshipStudentAction, String internshipStudentCycle) {
        this.internshipStudentStatusID = internshipStudentStatusID;
        this.internshipStudentStatusName = internshipStudentStatusName;
        this.internshipStudentAction = internshipStudentAction;
        this.internshipStudentCycle = internshipStudentCycle;
    }

    public int getInternshipStudentStatusID() {
        return internshipStudentStatusID;
    }

    public void setInternshipStudentStatusID(int internshipStudentStatusID) {
        this.internshipStudentStatusID = internshipStudentStatusID;
    }

    public String getInternshipStudentStatusName() {
        return internshipStudentStatusName;
    }

    public void setInternshipStudentStatusName(String internshipStudentStatusName) {
        this.internshipStudentStatusName = internshipStudentStatusName;
    }

    public int getInternshipStudentAction() {
        return internshipStudentAction;
    }

    public void setInternshipStudentAction(int internshipStudentAction) {
        this.internshipStudentAction = internshipStudentAction;
    }

    public String getInternshipStudentCycle() {
        return internshipStudentCycle;
    }

    public void setInternshipStudentCycle(String internshipStudentCycle) {
        this.internshipStudentCycle = internshipStudentCycle;
    }
    
    
}

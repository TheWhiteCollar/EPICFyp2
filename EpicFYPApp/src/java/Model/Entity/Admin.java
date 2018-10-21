/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Entity;

/* Database sequence
    #1: adminName (varchar 15)
    #2: adminPassword (varchar 30)
    #3: adminLevel (varchar 30)
 */

public class Admin {

    private String adminName;
    private String adminPassword;
    private String adminLevel;

    public Admin(String adminName, String adminPassword, String adminLevel) {
        this.adminName = adminName;
        this.adminPassword = adminPassword;
        this.adminLevel = adminLevel;
    }
    
    public Admin(String adminName, String adminLevel) {
        this.adminName = adminName;
        this.adminLevel = adminLevel;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(String adminLevel) {
        this.adminLevel = adminLevel;
    }

    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Attempt.Util;
import Session.EmployeeFacade;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author hugoc
 */

@Named
@RequestScoped
public class EmployeeDAO {
    
    Logger logger = Logger.getLogger(DAO.EmployeeDAO.class.getName());
    
    @EJB
    private EmployeeFacade ef;
    
    private int employeeId;
    private String employeeName;
    private String email;
    private String password;

    
    
    public String Regist() {    //method to regist employees
        boolean isRegistrable = ef.IsRegistrable(email);    //verifies if this email is registrable
        if (isRegistrable) {   //if so
            ef.InsertEmployeeInDB(employeeId, employeeName, email, password);   //creates an employee with it
            // get Http Session and store email
            HttpSession session = Util.getSession();
            session.setAttribute("email", email);
            return "Login";  //returns Login.xhtml page
        } else if(isRegistrable == false){    //if not (not registrable)
            FacesContext.getCurrentInstance().addMessage(   // displays these messages
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "That email already exists",
                    "Try Again!"));
            
            //message = "Invalid Login. Please Try Again!";
            return "Regist"; //returns Regist.xhtml page
        }
        else {  //asks the user to verify if every field is filled
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Please verify if every field is filled!",
                    "Try Again!"));
 
            return "Regist"; //returns Regist.xhtml page
        }
    }
    
    public String Login() { //method to login employees
        boolean isLoggable = ef.IsLoggable(email, password);    //verifies if is loggable
        if (isLoggable) {   //if so
            if(email.equalsIgnoreCase("admin")) {   //if employee is admin
                String emailCase = email.toLowerCase(); //this converts the email to lower case so even if the employee types in upper case it will be redirected to the same page
                // get Http Session and store email
                HttpSession session = Util.getSession();
                session.setAttribute("email", emailCase);
                return "AdminProductsPage";  //returns AdminProductsPage.xhtml page
            } else {    //se não entra na de user normal
                // get Http Session and store email
                HttpSession session = Util.getSession();
                session.setAttribute("email", email);
                setEmployeeName(employeeName);

                return "UserProductsPage";  //returns UserProductsPage.xhtml page
            }
            
        } else {    // if not
 
            FacesContext.getCurrentInstance().addMessage(   // displays these messages
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Invalid Login!",
                    "Please Try Again!"));
 
            return "Login"; //returns Login.xhtml page
        }
    }
 
    public String Logout() {
        HttpSession session = Util.getSession();
        session.invalidate();     //invalidates the current session
        return "Login";   //returns Login.xhtml page
    }
    
    
    //getters and setters
    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}

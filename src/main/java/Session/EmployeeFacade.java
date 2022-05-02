/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Session;

import Entity.Employee;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author hugoc
 */
@Stateless
public class EmployeeFacade extends AbstractFacade<Employee> {

    Logger logger = Logger.getLogger(Session.EmployeeFacade.class.getName());
    
    @PersistenceContext(unitName = "com.mycompany_ProjetoFinalAttempt3_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmployeeFacade() {
        super(Employee.class);
    }
    
    public void InsertEmployeeInDB(int id, String name, String email, String password) {    //inserts the employee in DB
        try {
            String emailCase = email.toLowerCase(); //all employees are created in lower case
            Employee e = new Employee(id, name, emailCase, password);
            em.persist(e);  
        } catch(Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public boolean IsRegistrable(String email) {    //verifies if is registrable
        String emailCase = email.toLowerCase(); //all employees are created in lower case so we have to find them like this
        try {
            String result = em.createQuery("select e.email from Employee e where e.email = '" + emailCase +"'", String.class).getSingleResult();    //looks for this email in DB
            if(result.equals(null)) {    //if it doesn't exist (is registrable)
                return true;
            }
            else {  //it isn't
                return false;
            }
        } catch(NoResultException e) {  //so it won't throw exception
            return true;
        }
    }  
    
    public boolean IsLoggable(String email, String password) { //verifies if is loggable
        String emailCase = email.toLowerCase(); //all employees are created in lower case 
        try {
            String result = em.createQuery("select e.email from Employee e where e.email = '" + emailCase +"' and e.password = '" + password + "'", String.class).getSingleResult();    //verifies if this combination exists in DB
            if(result.equals(null)) {    //if not (doesn't exist)
                return false;   //isn't loggable
            }
            else {  // if so (exists)
                return true;    //is loggable
            }
        } catch(NoResultException e) {  //so it won't throw exception
            return false;
        }
    }
    
    public Employee GetEmployeeByEmail(String email) {      //returns the employee with this email
        String emailCase = email.toLowerCase(); //all employees are created in lower case so we have to find them like this
        Employee result = em.createQuery("select e from Employee e where e.email = '" + emailCase +"'", Employee.class).getSingleResult();
        return result;
    }
    
    public int SelectEmployeeIdByEmail(String email) {  //returns the employee with this email
        String emailCase = email.toLowerCase(); //all employees are created in lower case so we have to find them like this
        Integer result = em.createQuery("select e.employeeid from Employee e where e.email = '" + emailCase +"'", Integer.class).getSingleResult();
        return result;
    }
    
}

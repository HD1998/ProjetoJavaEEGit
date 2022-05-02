/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Session;

import Entity.Employee;
import Entity.Fatura;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.eclipse.persistence.exceptions.QueryException;

/**
 *
 * @author hugoc
 */
@Stateless
public class FaturaFacade extends AbstractFacade<Fatura> {

    @PersistenceContext(unitName = "com.mycompany_ProjetoFinalAttempt3_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FaturaFacade() {
        super(Fatura.class);
    }
    
    
    public List<Fatura> SelectFaturasByEmployeeId(int employeeId) { //returns all "faturas" from this employee
        try {
            List<Fatura> result = em.createQuery("SELECT f FROM Fatura f WHERE f.employee.employeeid = " + employeeId +"", Fatura.class).getResultList();
            return result;
        } catch(QueryException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<Integer> SelectFaturaIdByEmployeeId(int employeeId) {   //returns every "faturaId" from this employee
        List<Integer> result = em.createQuery("select f.faturaid from Fatura f where f.employee.employeeid = " + employeeId +"", Integer.class).getResultList();    //verifica se existe este email com esta pass na DB
        return result;
    }
    
    public void InsertFaturaInDB(int faturaId, Employee employee, Date date, BigDecimal total) {    //inserts "fatura" in DB
        Fatura f = new Fatura(faturaId, employee, date, total);
        em.persist(f);  
    }
    
    public Fatura SelectFaturaByFaturaId(int faturaId) {    //returns "fatura" with this faturaID
        
        try {
            Fatura result = em.createQuery("Select f From Fatura f where f.faturaid = " + faturaId + "", Fatura.class).getSingleResult();
            if(result.equals(null)) {    //se result for null quer dizer que aquela categoria não existe na DB, logo retorna true (é creatable)
                return null;
            }
            else {  // se aquela categoria existir na DB, retorna false(não é creatable)
                return result;
            }
        } catch(NoResultException e) {  //caso seja null, para não lançar exceção, apanha-se e retorna true (é creatable)
            return null;
        }
    }
    
    public void UpdateFatura(Fatura f) {    //updates "fatura"
        em.merge(f);
    }
}

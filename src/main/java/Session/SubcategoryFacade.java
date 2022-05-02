/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Session;

import Entity.Category;
import Entity.Subcategory;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.eclipse.persistence.exceptions.QueryException;

/**
 *
 * @author hugoc
 */
@Stateless
public class SubcategoryFacade extends AbstractFacade<Subcategory> {

    Logger logger = Logger.getLogger(Session.SubcategoryFacade.class.getName());
    
    @PersistenceContext(unitName = "com.mycompany_ProjetoFinalAttempt3_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SubcategoryFacade() {
        super(Subcategory.class);
    }
    
    public boolean IsCreatable(String subCategoryName) {    //verifies if the subCategory is creatable
        String subCategoryCase = subCategoryName.toLowerCase(); 
        try {
            String result = em.createQuery("select sc.subcategoryname from Subcategory sc where sc.subcategoryname = '" + subCategoryCase +"'", String.class).getSingleResult();    
            if(result.equals(null)) {    //if there isn't any with this name in db
                return true;    //is creatable
            }
            else {  // else
                return false;   //isn't
            }
        } catch(NoResultException e) {  
            return true;
        }
    }
    
    public void InsertSubCategoryInDB(int subCategoryId, String subCategoryName, Category categoryId) {    //inserts the subCategory in DB 
        String subCategoryCase = subCategoryName.toLowerCase();   
        Subcategory sc = new Subcategory(subCategoryId, subCategoryCase, categoryId);
        em.persist(sc);
    }
    
    public Subcategory GetSubCategoryBySubCategoryName(String subCategoryName) {    //returns the subCategory with this subCategoryName
        try {
            Subcategory result = em.createQuery("select sc from Subcategory sc where sc.subcategoryname = '" + subCategoryName +"'", Subcategory.class).getSingleResult();
            if(result.equals(null)) {    
                return null;
            }
            else {  
                return result;
            }
        } catch(NoResultException e) {  
            return null;
        }
    }  
    
    public List<Subcategory> SelectSubCategoriesFromDB(int categoryID) {    //returns a list of subCategories with this categoryId
        try {
            List<Subcategory> result = em.createQuery("Select s From Subcategory s where s.category.categoryid = " + categoryID +"", Subcategory.class).getResultList();
            return result;
        } catch(QueryException q) {
            q.printStackTrace();
        }
        return null;
    }
    
    public List<Integer> SelectSubCategoriesIdFromDB(String categoryName) {    //returns a list of subCategories with this categoryId
        try {
            List<Integer> result = em.createQuery("Select sc.subcategoryid From Subcategory sc where sc.category.categoryname = '" + categoryName +"'", Integer.class).getResultList();
            return result;
        } catch(QueryException q) {
            q.printStackTrace();
        }
        return null;
    }
    
    public List<Integer> SelectSubCategoriesId() {   //returns a list of subCategories from these subCategories
        List<Integer> result = em.createQuery("Select sc.subcategoryid From Subcategory sc", Integer.class).getResultList();
        return result;
    }
    
    public String SelectSubCategoryNameBySubCategoryId(int subCategoryId) { //returns the subcategoryname with this subCategoryId
        try {
            String result = em.createQuery("select sc.subcategoryname from Subcategory sc where sc.subcategoryid = " + subCategoryId +"", String.class).getSingleResult();
            if(result.equals(null)) {    
                return "";
            }
            else {  
                return result;
            }
        } catch(NoResultException e) { 
            return "";
        }
    }
    
    public void UpdateSubCategoryInDB(int subCategoryId, String subCategoryName, Category c) {    //updates subcategory in DB
        try {
            String scNameCase = subCategoryName.toLowerCase();
            Subcategory sc = new Subcategory(subCategoryId, scNameCase, c);
            em.merge(sc);  
        } catch(ConstraintViolationException e) {
            for (ConstraintViolation actual : e.getConstraintViolations()) {
                System.out.println(actual.toString());
            }
        } catch(Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public String SelectCategoryNameBySubCategoryName(int subCategoryId) {
        try {
            String result = em.createQuery("select sc.category.categoryname from Subcategory sc where sc.subcategoryid = '" + subCategoryId +"'", String.class).getSingleResult();
            if(result.equals(null)) {    
                return "";
            }
            else {  
                return result;
            }
        } catch(NoResultException e) { 
            return "";
        }
    }
    
    public void DeleteSubCategoryByIDFromDB(int subCategoryId) {    //deletes subcategory from db
        Subcategory sc = em.find(Subcategory.class, subCategoryId);
        em.remove(sc);
    }
}

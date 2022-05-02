/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Session;

import Entity.Category;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author hugoc
 */
@Stateless
public class CategoryFacade extends AbstractFacade<Category> {

    Logger logger = Logger.getLogger(Session.CategoryFacade.class.getName());
    
    @PersistenceContext(unitName = "com.mycompany_ProjetoFinalAttempt3_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CategoryFacade() {
        super(Category.class);
    }
    
    public Category GetCategoryByCategoryName(String categoryName) {    //returns a category with this categoryName
        try {
            Category result = em.createQuery("select c from Category c where c.categoryname = '" + categoryName +"'", Category.class).getSingleResult();    
            if(result.equals(null)) {    //if there is no category with that name
                return null;    
            }
            else {  //if there is a category with that name
                return result;
            }
        } catch(NoResultException e) {  //so it won't throw exception
            return null;
        }
    }
    
    public int SelectCategoryIdByCategoryName(String categoryName) {    //returns the categoryId from this categoryName
        Integer result = em.createQuery("select c.categoryid from Category c where c.categoryname = '" + categoryName +"'", Integer.class).getSingleResult();
        return result;
    }
    
    public boolean IsCreatable(String categoryName) {    //checks whether this category already exists in DB or not
        String categoryCase = categoryName.toLowerCase(); //all categories are created in lower case so we have to find them like this
        try {
            String result = em.createQuery("select c.categoryname from Category c where c.categoryname = '" + categoryCase +"'", String.class).getSingleResult();    //verifica se existe este email na DB
            if(result.equals(null)) {    //if there are no results its because it is creatable
                return true;
            }
            else {  // it isnt
                return false;
            }
        } catch(NoResultException e) {  //so it won't throw exception
            return true;
        }
    }
    
    public void InsertCategoryInDB(int categoryId, String categoryName) {    //inserts the category in DB
        String categoryCase = categoryName.toLowerCase();   //all categories are created in lower case
        Category c = new Category(categoryId, categoryCase);
        em.persist(c);
    }
    
    public List<Integer> SelectCategoriesId() {   //returns a list of categoriesId from these categories
        List<Integer> result = em.createQuery("Select c.categoryid From Category c", Integer.class).getResultList();
        return result;
    }
    
    public String SelectCategoryNameByCategoryId(int categoryId) { //returns the categoryName with this categoryId
        try {
            String result = em.createQuery("select c.categoryname from Category c where c.categoryid = " + categoryId +"", String.class).getSingleResult();
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
    
    public void UpdateCategoryInDB(int categoryId, String categoryName) {    //updates category in DB
        try {
            String cNameCase = categoryName.toLowerCase();
            Category c = new Category(categoryId, cNameCase);
            em.merge(c);  
        } catch(ConstraintViolationException e) {
            for (ConstraintViolation actual : e.getConstraintViolations()) {
                System.out.println(actual.toString());
            }
        } catch(Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void DeleteCategoryByIDFromDB(int categoryId) {    //deletes category from db
        Category c = em.find(Category.class, categoryId);
        em.remove(c);
    }
}

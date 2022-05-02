/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Session;

import Entity.Category;
import Entity.Employee;
import Entity.Product;
import Entity.Subcategory;
import java.math.BigDecimal;
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
public class ProductFacade extends AbstractFacade<Product> {

    Logger logger = Logger.getLogger(Session.ProductFacade.class.getName());
    
    @PersistenceContext(unitName = "com.mycompany_ProjetoFinalAttempt3_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductFacade() {
        super(Product.class);
    }
    
    
    public boolean IsCreatable(String productName) {    //verifies it it is creatable
        String productCase = productName.toLowerCase(); 
        try {
            String result = em.createQuery("select p.productname from Product p where p.productname = '" + productCase +"'", String.class).getSingleResult();    
            if(result.equals(null)) {    //if result is null
                return true;    //product doesn't exist in db(is creatable)
            }
            else {  // isnt creatable
                return false;
            }
        } catch(NoResultException e) {  //so it wont throw exception
            return true;
        }
    }
    
    public void InsertProductInDB(int productId, String productName, Category categoryId, Subcategory subCategoryId, BigDecimal price, int stock, Employee employeeId) {    //inserts product in DB
        try {
            String pNameCase = productName.toLowerCase();
            Product p = new Product(productId, pNameCase, categoryId, subCategoryId, price, stock, employeeId);
            em.persist(p);  
        } catch(ConstraintViolationException e) {
            for (ConstraintViolation actual : e.getConstraintViolations()) {
                System.out.println(actual.toString());
            }
        } catch(Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public List<Product> SelectProductsByEmployeeId(int employeeId) {   //returns a list of products from this employeeId
        try {
            List<Product> result = em.createQuery("SELECT p FROM Product p WHERE p.employee.employeeid = " + employeeId +"", Product.class).getResultList();
            return result;
        } catch(QueryException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<Integer> SelectProductIdByEmployeeId(int employeeId) {  //returns a list of productIds with this employeeId
        List<Integer> result = em.createQuery("select p.productid from Product p where p.employee.employeeid = " + employeeId +"", Integer.class).getResultList();    //verifica se existe este email com esta pass na DB
        return result;
    }
    
    public String SelectProductNameByProductId(int productId) { //returns the productName with this productId
        try {
            String result = em.createQuery("select p.productname from Product p where p.productid = " + productId +"", String.class).getSingleResult();
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
    
    public void UpdateProductInDB(int productId, String productName, Category categoryId, Subcategory subCategoryId, BigDecimal price, int stock, Employee employeeId) {    //updates prdocut in DB
        try {
            String pNameCase = productName.toLowerCase();
            Product p = new Product(productId, pNameCase, categoryId, subCategoryId, price, stock, employeeId);
            em.merge(p);  
        } catch(ConstraintViolationException e) {
            for (ConstraintViolation actual : e.getConstraintViolations()) {
                System.out.println(actual.toString());
            }
        } catch(Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
     
    public void DeleteProductByIDFromDB(int productId) {    //deletes product from db
        Product p = em.find(Product.class, productId);
        em.remove(p);
    }
    
    public List<Product> SelectProductsByCategoryIdFromDB(int categoryID) { //returns a list of products with this categoryID
        try {
            List<Product> result = em.createQuery("Select p From Product p where p.category.categoryid = " + categoryID +"", Product.class).getResultList();
            return result;
        } catch(QueryException q) {
            q.printStackTrace();
        }
        return null;
    }
    
    public Product SelectProductByProductName(String productName) { //returns the product with this name
        try {
            Product result = em.createQuery("Select p From Product p where p.productname = '" + productName + "'", Product.class).getSingleResult();
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
    
    public List<Integer> SelectProductsIdForAdmin() {   //returns a list of productIds from these products
        List<Integer> result = em.createQuery("Select p.productid From Product p", Integer.class).getResultList();
        return result;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Attempt.Util;
import Entity.Category;
import Entity.Employee;
import Entity.Product;
import Entity.Subcategory;
import Session.CategoryFacade;
import Session.EmployeeFacade;
import Session.ProductFacade;
import Session.SubcategoryFacade;
import java.math.BigDecimal;
import java.util.List;
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
public class ProductDAO {
    Logger logger = Logger.getLogger(DAO.ProductDAO.class.getName());
    
    @EJB
    private ProductFacade pf;
    @EJB
    private EmployeeFacade ef;
    @EJB
    private CategoryFacade cf;
    @EJB
    private SubcategoryFacade scf;

    private int productid;
    private String productName;
    private double price;
    private int stock;
    private String categoryName;
    private String subCategoryName;
    private int employeeId;

    
    public String CreateNewProduct() {  //creates new products
        HttpSession session = Util.getSession();
        session.getAttribute("email");
        String email = (String) session.getAttribute("email");
        Employee e = ef.GetEmployeeByEmail(email);
        Category c = cf.GetCategoryByCategoryName(categoryName);
        Subcategory sc = scf.GetSubCategoryBySubCategoryName(subCategoryName);
        boolean isCreatable = pf.IsCreatable(productName);    //verifies if product is creatable
        if (isCreatable && e!=null && c!=null && sc!=null && price != 0.0 && stock != 0) {   //if so and if every field is filled
            pf.InsertProductInDB(productid, productName, c, sc, BigDecimal.valueOf(price), stock, e);   //creates the product
            FacesContext.getCurrentInstance().addMessage(   //display messages
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Product added successfully!",
                    ""));
            if(email.equalsIgnoreCase("admin")) {
                return "AdminCreateProduct";    //returns AdminCreateProduct.xhtml page
            } else{
                return "UserCreateProduct";  //returns UserCreateProduct.xhtml page
            }
        } else if(isCreatable == false){    // if it isnt creatable
            FacesContext.getCurrentInstance().addMessage(   //display messages
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "That product already exists",
                    "Try a new one!"));
            
            if(email.equalsIgnoreCase("admin")) {
                return "AdminCreateProduct";    //returns AdminCreateProduct.xhtml page
            } else{
                return "UserCreateProduct";  //returns UserCreateProduct.xhtml page
            }
        }
        else {  //verifies if every field is filled
            FacesContext.getCurrentInstance().addMessage(   //display messages
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Please verify if every field is filled!",
                    "Try Again!"));
 
            //message = "Please verify if every field is filled. Please Try Again!";
            if(email.equalsIgnoreCase("admin")) {
                return "AdminCreateProduct";    //returns AdminCreateProduct.xhtml page
            } else{
                return "UserCreateProduct";  //returns UserCreateProduct.xhtml page
            }
        }
    }
    
    
    public List<Product> MyProducts() { //returns a list of products from this employee
        HttpSession session = Util.getSession();
        session.getAttribute("email");
        String email = (String) session.getAttribute("email");
        Employee e = ef.GetEmployeeByEmail(email);
        List<Product> myProducts = pf.SelectProductsByEmployeeId(e.getEmployeeid());
        return myProducts;
    }
    
    public List<Integer> SelectProductId() {    //retuns a list of productIds from this employee
        HttpSession session = Util.getSession();
        session.getAttribute("email");
        String email = (String) session.getAttribute("email");
        int employeeId = ef.SelectEmployeeIdByEmail(email);
        List<Integer> productId = pf.SelectProductIdByEmployeeId(employeeId);
        return productId;
    }
    
    public List<Integer> SelectProductsIdForAdmin() {  //retuns a list of productIds from every employee 
        List<Integer> productsId = pf.SelectProductsIdForAdmin();
        return productsId;
    }
    
    public String UpdateProduct() { //updates the product
        String pNameInDB = pf.SelectProductNameByProductId(productid);  //selects the productName from this productID
        boolean isCreatable = pf.IsCreatable(productName);    //verifies if it is creatable(if this productName isnt already in the DB)
        HttpSession session = Util.getSession();
        session.getAttribute("email");
        String email = (String) session.getAttribute("email");
        Employee e = ef.GetEmployeeByEmail(email);  //gets the employee by email
        Category c = cf.GetCategoryByCategoryName(categoryName);    //gets the category by categoryName
        Subcategory sc = scf.GetSubCategoryBySubCategoryName(subCategoryName);  //gets the employee by subCategoryName
	if(productid != 0 && pNameInDB != "" && (pNameInDB.equalsIgnoreCase(productName) || isCreatable)) { //if productId is selected, productName is filled and either the filled name is the same as the one already in the DB or a different one that doesn't exist yet...
            if(c != null && sc != null && price != 0.0 && stock != 0) { //if every other field is filled
                pf.UpdateProductInDB(productid, productName, c, sc, BigDecimal.valueOf(price), stock, e);   //updates the product
                FacesContext.getCurrentInstance().addMessage(   //display messages
                                null,   //"upMessages"
                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Product Updated successfully",
                                ""));
                if(email.equalsIgnoreCase("admin")) {
                    return "AdminUpdateProduct";    //returns AdminUpdateProduct.xhtml page
                } else{
                    return "UserUpdateProduct";  //returns UserUpdateProduct.xhtml page
                }
            } else {    //verifies if every field is filled
                FacesContext.getCurrentInstance().addMessage(   //display messages
                    null,   //"upMessages"
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Couldn't Update the Product",
                    "Please verify if every field is filled"));
                if(email.equalsIgnoreCase("admin")) {
                    return "AdminUpdateProduct";    //returns AdminUpdateProduct.xhtml page
                } else{
                    return "UserUpdateProduct";  //returns UserUpdateProduct.xhtml page
                }
            }
        } else if(pNameInDB != "" && (pNameInDB.equalsIgnoreCase(productName) || !isCreatable)) {   //if productName is filled and, either the filled name is the same as the one already in the DB (if the productId corresponds to this name, this if won't be executed) or a different one that already exists...
            FacesContext.getCurrentInstance().addMessage(   //display messages
                "upMessages",
                new FacesMessage(FacesMessage.SEVERITY_WARN,
                "Couldn't Update the Product",
                "There is a product with that name already"));
            if(email.equalsIgnoreCase("admin")) {
                return "AdminUpdateProduct";    //returns AdminUpdateProduct.xhtml page
            } else{
                return "UserUpdateProduct";  //returns UserUpdateProduct.xhtml page
            }
	} else {    //verifies if every field is filled
                FacesContext.getCurrentInstance().addMessage(   //display messages
                    "upMessages",
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Couldn't Update the Product",
                    "Please verify if every field is filled"));
            if(email.equalsIgnoreCase("admin")) {
                return "AdminUpdateProduct";    //returns AdminUpdateProduct.xhtml page
            } else{
                return "UserUpdateProduct";  //returns UserUpdateProduct.xhtml page
            }
	}
    }
    
    public String DeleteProudct() { //deletes the product
        HttpSession session = Util.getSession();
        session.getAttribute("email");
        String email = (String) session.getAttribute("email");
        if(productid != 0) {    //if the user selects a product
            pf.DeleteProductByIDFromDB(productid);  //deletes it
            FacesContext.getCurrentInstance().addMessage(   //display messages
                null,
                new FacesMessage(FacesMessage.SEVERITY_WARN,
                "Product deleted!",
                ""));
            if(email.equalsIgnoreCase("admin")) {
                return "AdminDeleteProduct";    //returns AdminDeleteProduct.xhtml page
            } else{
                return "UserDeleteProduct";  //returns UserDeleteProduct.xhtml page
            }
        } else {    //doesn't
            FacesContext.getCurrentInstance().addMessage(   //display messages
                null,
                new FacesMessage(FacesMessage.SEVERITY_WARN,
                "Please insert a product to delete",
                ""));
            if(email.equalsIgnoreCase("admin")) {
                return "AdminDeleteProduct";    //returns AdminDeleteProduct.xhtml page
            } else{
                return "UserDeleteProduct";  //returns UserDeleteProduct.xhtml page
            }
        }
        
    }
        
    public List<Product> getAllProductsName(){ //returns a list of products with this category
        if(categoryName == null){   //if category isnt selected
              return pf.findAll();  //returns all products
        }else{  //if a category is selected
            int categoryId =  cf.SelectCategoryIdByCategoryName(categoryName);
            return pf.SelectProductsByCategoryIdFromDB(categoryId);  //returns all products from that category
        }
     }
    
    public List<Category> getAllCategories(){   //returns every category in DB
        return cf.findAll();
    }
    
    public List<Subcategory> getAllSubcategories(){ //returns every subcategory in DB
        if(categoryName == null){   //if category isnt selected
              return scf.findAll(); //returns all subcategories
        }else{  //if a category is selected
            int categoryId =  cf.SelectCategoryIdByCategoryName(categoryName);
            return scf.SelectSubCategoriesFromDB(categoryId);   //returns all subcategories from that category
        } 
    }
    
    public List<Product> getAllProducts(){  //returns all products in DB
        return pf.findAll();    //finds all products
    }
    
    //getters and setters
    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
    
    public int getProductId() {
        return productid;
    }

    public void setProductId(int productid) {
        this.productid = productid;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productname) {
        this.productName = productname;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    public String getSubCategoryName() {
        return subCategoryName;
    }
    
    public void setSubCategoryName(String subCategoryName) {    
        this.subCategoryName = subCategoryName;
    }
    
}

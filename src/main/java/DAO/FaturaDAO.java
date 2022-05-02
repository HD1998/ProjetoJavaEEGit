/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Attempt.Util;
import Entity.Category;
import Entity.Employee;
import Entity.Fatura;
import Entity.Product;
import Session.CategoryFacade;
import Session.EmployeeFacade;
import Session.FaturaFacade;
import Session.ProductFacade;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
public class FaturaDAO {
    
    Logger logger = Logger.getLogger(DAO.FaturaDAO.class.getName());
    
    @EJB
    FaturaFacade ff;
    @EJB
    EmployeeFacade ef;
    @EJB
    ProductFacade pf;
    @EJB
    CategoryFacade cf;
    
    private int employeeId;
    private Fatura fatura;
    private Set<Product> products = new HashSet<>();
    private Product product;
    private int faturaId;
    private String productName;
    private BigDecimal amountOfProducts;
    private String categoryName;
    
    public List<Fatura> MyFaturas() {   //returs every "Fatura" from this employee
        HttpSession session = Util.getSession();
        session.getAttribute("email");
        String email = (String) session.getAttribute("email");
        Employee e = ef.GetEmployeeByEmail(email);
        List<Fatura> myFaturas = ff.SelectFaturasByEmployeeId(e.getEmployeeid());
        return myFaturas;
    }
    
    public List<Integer> SelectFaturaId() { //returns every "FaturaId" from this employee
        HttpSession session = Util.getSession();
        session.getAttribute("email");
        String email = (String) session.getAttribute("email");
        int employeeId = ef.SelectEmployeeIdByEmail(email);
        List<Integer> faturaId = ff.SelectFaturaIdByEmployeeId(employeeId);
        return faturaId;
    }

    public String CreateFatura() {  //Creates new "Fatura"
        HttpSession session = Util.getSession();
        session.getAttribute("email");
        String email = (String) session.getAttribute("email");
        Employee e = ef.GetEmployeeByEmail(email);
        ff.InsertFaturaInDB(0, e, Date.valueOf(LocalDate.now()), BigDecimal.ZERO);
        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_WARN,
                "Fatura created successfully!",
                ""));
        if(email.equalsIgnoreCase("admin")) {
            return "AdminFaturasPage";  //returns AdminFaturasPage.xhtml page
        } else{
            return "UserFaturasPage";  //returns UserFaturasPage.xhtml page
        }
    }
    
    public String AddProductToFatura() {    //Adds products to "fatura"
        HttpSession session = Util.getSession();
        session.getAttribute("email");
        String email = (String) session.getAttribute("email");
        try {
            if(faturaId != 0 && categoryName != null && productName != null && !amountOfProducts.equals(BigDecimal.ZERO) && amountOfProducts != null) { //if every field is filled
                fatura = ff.SelectFaturaByFaturaId(faturaId);   //Selects the "fatura" from DB
                product = pf.SelectProductByProductName(productName);   //Selects the Product by ProductName (choose by the user)
                products = fatura.getProducts();    //retrieves the current products from this "fatura"
                products.add(product);  //adds the new product to the product list
                fatura.setProducts(products);   //sets the new list of products to the "fatura" products list
                BigDecimal total = product.getPrice().multiply(amountOfProducts).add(fatura.getTotal());    //multiplies the amountOfProduct inserted in "fatura" by its price and adds the old "fatura" total to the new total
                fatura.setTotal(total); //sets the new total
                ff.UpdateFatura(fatura);    //updates it in DB
                FacesContext.getCurrentInstance().addMessage(   //displays messages
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "Product added successfully!",
                        ""));
                if(email.equalsIgnoreCase("admin")) {
                    return "AdminFaturasPage";  //returns AdminFaturasPage.xhtml page
                } else{
                    return "UserFaturasPage";  //returns UserFaturasPage.xhtml page
                }
            } else {    //if any field isn't filled
                FacesContext.getCurrentInstance().addMessage(   //displays messages
                        null,   //mudar este null
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "Product not added",
                        "Please verify if every field is filled!"));
                if(email.equalsIgnoreCase("admin")) {
                    return "AdminFaturasPage";   //returns AdminFaturasPage.xhtml page
                } else{
                    return "UserFaturasPage";   //returns UserFaturasPage.xhtml page
                }
            }
        } catch(NullPointerException ex) {
            FacesContext.getCurrentInstance().addMessage(   //displays messages
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Product not added",
                    "Please verify if every field is filled!"));
            if(email.equalsIgnoreCase("admin")) {
                return "AdminFaturasPage";  //returns AdminFaturasPage.xhtml page
            } else{
                return "UserFaturasPage";  //returns UserFaturasPage.xhtml page
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
    
    
    public List<Category> getAllCategories(){   //gets all Categories from db
        return cf.findAll();
    }
    
    //getters and setters
    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public Fatura getFatura() {
        return fatura;
    }

    public void setFatura(Fatura fatura) {
        this.fatura = fatura;
    }

    public int getFaturaId() {
        return faturaId;
    }

    public void setFaturaId(int faturaId) {
        this.faturaId = faturaId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    public BigDecimal getAmountOfProducts() {
        return amountOfProducts;
    }

    public void setAmountOfProducts(BigDecimal amountOfProducts) {
        this.amountOfProducts = amountOfProducts;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    
    
}

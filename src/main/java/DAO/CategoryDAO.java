/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.Category;
import Session.CategoryFacade;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author hugoc
 */
@Named
@RequestScoped
public class CategoryDAO {
    
    Logger logger = Logger.getLogger(DAO.CategoryDAO.class.getName());
    
    @EJB
    private CategoryFacade cf;
    
    private int categoryId;
    private String categoryName; 
    
    
    public String CreateNewCategory() {
        boolean isCreatable = cf.IsCreatable(categoryName);    //verifies if this category is creatable
        if (isCreatable) {   //if so
            cf.InsertCategoryInDB(categoryId, categoryName);   //creates it
            return "AdminCreateCategory";  //returns the Categories.xhtml page
        } else if(isCreatable == false){    // if not (isnt creatable)
            FacesContext.getCurrentInstance().addMessage(   //displays these messages
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "That category already exists",
                    "Try a new one!"));

            return "AdminCreateCategory"; //returns the AdminCategoriesPage.xhtml page
        }
        else {  //asks the user to verify if every field is filled
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Please verify if every field is filled!",
                    "Try Again!"));
 
            return "AdminCreateCategory"; //returns the AdminCategoriesPage.xhtml page
        }
    }
    
    public String UpdateCategory() { //updates the category
        String cNameInDB = cf.SelectCategoryNameByCategoryId(categoryId);  //selects the categoryName from this categoryId
        boolean isCreatable = cf.IsCreatable(categoryName);    //verifies if it is creatable(if this categoryName isnt already in the DB)
	if(categoryId != 0 && cNameInDB != "" && (cNameInDB.equalsIgnoreCase(categoryName) || isCreatable)) { //if categoryId is selected, categoryName is filled and either the filled name is the same as the one already in the DB or a different one that doesn't exist yet...    
            cf.UpdateCategoryInDB(categoryId, categoryName);   //updates the category
            FacesContext.getCurrentInstance().addMessage(   //display messages
                    null,   //"upMessages"
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Category Updated successfully",
                    ""));
                return "AdminUpdateCategory";    //returns AdminUpdateCategory.xhtml page
        } else if(cNameInDB != "" && (cNameInDB.equalsIgnoreCase(categoryName) || !isCreatable)) {   //if categoryName is filled and, either the filled name is the same as the one already in the DB (if the categoryId corresponds to this name, this if won't be executed) or a different one that already exists...
            FacesContext.getCurrentInstance().addMessage(   //display messages
                "upMessages",
                new FacesMessage(FacesMessage.SEVERITY_WARN,
                "Couldn't Update the Category",
                "There is a category with that name already"));
            return "AdminUpdateCategory";    //returns AdminUpdateCategory.xhtml page
	} else {    //verifies if every field is filled
                FacesContext.getCurrentInstance().addMessage(   //display messages
                    "upMessages",
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Couldn't Update the Category",
                    "Please verify if every field is filled"));
            return "AdminUpdateCategory";    //returns AdminUpdateCategory.xhtml page
	}
    }
    
    public String DeleteCategory() { //deletes the category
        if(categoryId != 0) {    //if the user selects a category
            cf.DeleteCategoryByIDFromDB(categoryId);  //deletes it
            FacesContext.getCurrentInstance().addMessage(   //display messages
                null,
                new FacesMessage(FacesMessage.SEVERITY_WARN,
                "Category deleted!",
                ""));
            return "AdminDeleteCategory";    //returns AdminDeleteCategory.xhtml page
        } else {    //doesn't
            FacesContext.getCurrentInstance().addMessage(   //display messages
                null,
                new FacesMessage(FacesMessage.SEVERITY_WARN,
                "Please insert a category to delete",
                ""));
            return "AdminDeleteCategory";    //returns AdminDeleteCategory.xhtml page
        }
        
    }
    
    
    public List<Integer> SelectCategoriesId() {  //retuns a list of categoriesId from every employee 
        List<Integer> categoriesId = cf.SelectCategoriesId();
        return categoriesId;
    }
    
    public List<Category> getAllCategories(){   //gets all Categories from db
        return cf.findAll();
    }
    
    //getters and setters
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
}

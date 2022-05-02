/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.Category;
import Entity.Subcategory;
import Session.CategoryFacade;
import Session.SubcategoryFacade;
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
public class SubCategoryDAO {
    Logger logger = Logger.getLogger(DAO.CategoryDAO.class.getName());
    
    @EJB
    SubcategoryFacade scf;
    @EJB
    CategoryFacade cf;
    
    private int subCategoryId;
    private String subCategoryName;
    private int categoryId;
    private String categoryName;
    
    public String CreateNewSubCategory() {  //creates new subcategory
        Category c = cf.GetCategoryByCategoryName(categoryName);
        boolean isCreatable = scf.IsCreatable(subCategoryName);    //verifies if it is creatable
        if (isCreatable && c != null) {   //if so
            scf.InsertSubCategoryInDB(subCategoryId, subCategoryName, c);   //creates it
            return "AdminCreateSubCategory";  //returns AdminCreateSubCategory.xhtml page
        } else if(isCreatable == false){    // if not
            FacesContext.getCurrentInstance().addMessage(   //displays messages
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "That subcategory already exists",
                    "Try a new one!"));
            
            return "AdminCreateSubCategory"; //returns AdminCreateSubCategory.xhtml page
        }
        else {  //verifies if every field is filled
            FacesContext.getCurrentInstance().addMessage(   //displays messages
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Please verify if every field is filled!",
                    "Try Again!"));
 
            return "AdminCreateSubCategory"; //returns AdminCreateSubCategory.xhtml page
        }
    }
    
    public String UpdateSubCategory() { //updates the subcategory
        String cNameInDB = scf.SelectCategoryNameBySubCategoryName(subCategoryId);
        String scNameInDB = scf.SelectSubCategoryNameBySubCategoryId(subCategoryId);  //selects the subCategoryName from this subCategoryId
        boolean isCreatable = scf.IsCreatable(subCategoryName);    //verifies if it is creatable(if this subCategoryName isnt already in the DB)
        Category c = cf.GetCategoryByCategoryName(cNameInDB);    //gets the category by categoryName
        if(subCategoryId != 0 && scNameInDB != "" && (scNameInDB.equalsIgnoreCase(subCategoryName) || isCreatable)) { //if subCategoryId is selected, subCategoryName is filled and either the filled name is the same as the one already in the DB or a different one that doesn't exist yet...    
            if(c != null) { //if every other field is filled
                scf.UpdateSubCategoryInDB(subCategoryId, subCategoryName, c);   //updates the category
                FacesContext.getCurrentInstance().addMessage(   //display messages
                        null,   //"upMessages"
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "SubCategory Updated successfully",
                        ""));
                return "AdminUpdateSubCategory";    //returns AdminUpdateSubCategory.xhtml page
            } else {    //verifies if every field is filled
                FacesContext.getCurrentInstance().addMessage(   //display messages
                    null,   //"upMessages"
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Couldn't Update the SubCategory",
                    "Please verify if every field is filled"));
                return "AdminUpdateSubCategory";    //returns AdminUpdateSubCategory.xhtml page
            }
        } else if(scNameInDB != "" && (scNameInDB.equalsIgnoreCase(subCategoryName) || !isCreatable)) {   //if subCategoryName is filled and, either the filled name is the same as the one already in the DB (if the subCategoryId corresponds to this name, this if won't be executed) or a different one that already exists...
            FacesContext.getCurrentInstance().addMessage(   //display messages
                "upMessages",
                new FacesMessage(FacesMessage.SEVERITY_WARN,
                "Couldn't Update the SubCategory",
                "There is a subcategory with that name already"));
            return "AdminUpdateSubCategory";    //returns AdminUpdateSubCategory.xhtml page
	} else {    //verifies if every field is filled
                FacesContext.getCurrentInstance().addMessage(   //display messages
                    "upMessages",
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Couldn't Update the SubCategory",
                    "Please verify if every field is filled"));
            return "AdminUpdateSubCategory";    //returns AdminUpdateSubCategory.xhtml page
	}
    }
    
    public String DeleteSubCategory() { //deletes the subcategory
        if(subCategoryId != 0) {    //if the user selects a subcategory
            scf.DeleteSubCategoryByIDFromDB(subCategoryId);  //deletes it
            FacesContext.getCurrentInstance().addMessage(   //display messages
                null,
                new FacesMessage(FacesMessage.SEVERITY_WARN,
                "SubCategory deleted!",
                ""));
            return "AdminDeleteSubCategory";    //returns AdminDeleteSubCategory.xhtml page
        } else {    //doesn't
            FacesContext.getCurrentInstance().addMessage(   //display messages
                null,
                new FacesMessage(FacesMessage.SEVERITY_WARN,
                "Please insert a subcategory to delete",
                ""));
            return "AdminDeleteSubCategory";    //returns AdminDeleteSubCategory.xhtml page
        }
        
    }
    
    public List<Integer> SelectSubCategoriesId() {  //retuns a list of subcategoriesId from every employee 
        List<Integer> subCategoriesId = scf.SelectSubCategoriesId();
        return subCategoriesId;
    }
    
    public List<Subcategory> getAllSubcategories(){ //gets all subCategories from db
        return scf.findAll();
    }
    
    public List<Category> getAllCategories(){   //gets all Categories from db
        return cf.findAll();
    }
    
    //getters and setters
    public int getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(int subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

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

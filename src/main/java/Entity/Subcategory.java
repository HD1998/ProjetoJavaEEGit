/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.SEQUENCE;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author hugoc
 */
@Entity
@Table(name = "SUBCATEGORY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Subcategory.findAll", query = "SELECT s FROM Subcategory s")
    , @NamedQuery(name = "Subcategory.findBySubcategoryid", query = "SELECT s FROM Subcategory s WHERE s.subcategoryid = :subcategoryid")
    , @NamedQuery(name = "Subcategory.findBySubcategoryname", query = "SELECT s FROM Subcategory s WHERE s.subcategoryname = :subcategoryname")})
public class Subcategory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @SequenceGenerator(name="scidGen",sequenceName="SCID_SEQ",allocationSize=1)
    @GeneratedValue(strategy=SEQUENCE,generator="scidGen")
    @Column(name = "SUBCATEGORYID")
    private Integer subcategoryid;
    @Size(max = 50)
    @Column(name = "SUBCATEGORYNAME")
    private String subcategoryname;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subcategory")
    private Collection<Product> productCollection;
    @JoinColumn(name = "CATEGORYID", referencedColumnName = "CATEGORYID")
    @ManyToOne(optional = false)
    private Category category;

    public Subcategory() {
    }

    public Subcategory(Integer subcategoryid) {
        this.subcategoryid = subcategoryid;
    }

    public Subcategory(int subCategoryId, String subCategoryName, Category category) {
        this.subcategoryid = subCategoryId;
        this.subcategoryname = subCategoryName;
        this.category = category;
    }

    public Integer getSubcategoryid() {
        return subcategoryid;
    }

    public void setSubcategoryid(Integer subcategoryid) {
        this.subcategoryid = subcategoryid;
    }

    public String getSubcategoryname() {
        return subcategoryname;
    }

    public void setSubcategoryname(String subcategoryname) {
        this.subcategoryname = subcategoryname;
    }

    @XmlTransient
    public Collection<Product> getProductCollection() {
        return productCollection;
    }

    public void setProductCollection(Collection<Product> productCollection) {
        this.productCollection = productCollection;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (subcategoryid != null ? subcategoryid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Subcategory)) {
            return false;
        }
        Subcategory other = (Subcategory) object;
        if ((this.subcategoryid == null && other.subcategoryid != null) || (this.subcategoryid != null && !this.subcategoryid.equals(other.subcategoryid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return subcategoryname;
    }

    
    
}

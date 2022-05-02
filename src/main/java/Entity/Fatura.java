/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.SEQUENCE;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hugoc
 */
@Entity
@Table(name = "FATURA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fatura.findAll", query = "SELECT f FROM Fatura f")
    , @NamedQuery(name = "Fatura.findByFaturaid", query = "SELECT f FROM Fatura f WHERE f.faturaid = :faturaid")
    , @NamedQuery(name = "Fatura.findByEmissiondate", query = "SELECT f FROM Fatura f WHERE f.emissiondate = :emissiondate")
    , @NamedQuery(name = "Fatura.findByTotal", query = "SELECT f FROM Fatura f WHERE f.total = :total")})
public class Fatura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @SequenceGenerator(name="fidGen",sequenceName="FID_SEQ",allocationSize=1)
    @GeneratedValue(strategy=SEQUENCE,generator="fidGen")
    @Column(name = "FATURAID")
    private Integer faturaid;
    @Column(name = "EMISSIONDATE")
    @Temporal(TemporalType.DATE)
    private Date emissiondate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "TOTAL")
    private BigDecimal total;
    @JoinColumn(name = "EMPLOYEEID", referencedColumnName = "EMPLOYEEID")
    @ManyToOne(optional = false)
    private Employee employee;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL) 
    @JoinTable(name = "PRODUCTFATURA",
                joinColumns = {@JoinColumn(name = "FATURAID", referencedColumnName = "FATURAID")},
                inverseJoinColumns = {@JoinColumn(name = "PRODUCTID", referencedColumnName = "PRODUCTID")})
    private Set<Product> products = new HashSet<Product>(); //doesn't allow duplicates
    

    public Fatura() {
    }
    
    public Fatura(int faturaId, Employee employee, Date date, BigDecimal total) {
        this.faturaid = faturaId;
        this.employee = employee;
        this.emissiondate = date;
        this.total = total;
    }

    public Fatura(Integer faturaid) {
        this.faturaid = faturaid;
    }

    public Integer getFaturaid() {
        return faturaid;
    }

    public void setFaturaid(Integer faturaid) {
        this.faturaid = faturaid;
    }

    public Date getEmissiondate() {
        return emissiondate;
    }

    public void setEmissiondate(Date emissiondate) {
        this.emissiondate = emissiondate;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (faturaid != null ? faturaid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fatura)) {
            return false;
        }
        Fatura other = (Fatura) object;
        if ((this.faturaid == null && other.faturaid != null) || (this.faturaid != null && !this.faturaid.equals(other.faturaid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Fatura{" + "faturaid=" + faturaid + ", emissiondate=" + emissiondate + ", total=" + total + ", employeeid=" + employee + '}';
    }

    
    
}

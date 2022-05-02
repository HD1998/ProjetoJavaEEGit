package Entity;

import Entity.Category;
import Entity.Employee;
import Entity.Fatura;
import Entity.Subcategory;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-09-16T15:15:47")
@StaticMetamodel(Product.class)
public class Product_ { 

    public static volatile SetAttribute<Product, Fatura> faturas;
    public static volatile SingularAttribute<Product, Integer> productid;
    public static volatile SingularAttribute<Product, BigDecimal> price;
    public static volatile SingularAttribute<Product, String> productname;
    public static volatile SingularAttribute<Product, Integer> stock;
    public static volatile SingularAttribute<Product, Category> category;
    public static volatile SingularAttribute<Product, Employee> employee;
    public static volatile SingularAttribute<Product, Subcategory> subcategory;

}
package Entity;

import Entity.Fatura;
import Entity.Product;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-09-16T15:15:47")
@StaticMetamodel(Employee.class)
public class Employee_ { 

    public static volatile SingularAttribute<Employee, String> password;
    public static volatile CollectionAttribute<Employee, Fatura> faturaCollection;
    public static volatile CollectionAttribute<Employee, Product> productCollection;
    public static volatile SingularAttribute<Employee, Integer> employeeid;
    public static volatile SingularAttribute<Employee, String> employeename;
    public static volatile SingularAttribute<Employee, String> email;

}
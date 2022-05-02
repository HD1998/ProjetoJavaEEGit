package Entity;

import Entity.Employee;
import Entity.Product;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-09-16T15:15:47")
@StaticMetamodel(Fatura.class)
public class Fatura_ { 

    public static volatile SingularAttribute<Fatura, BigDecimal> total;
    public static volatile SingularAttribute<Fatura, Date> emissiondate;
    public static volatile SingularAttribute<Fatura, Integer> faturaid;
    public static volatile SingularAttribute<Fatura, Employee> employee;
    public static volatile SetAttribute<Fatura, Product> products;

}
package Entity;

import Entity.Category;
import Entity.Product;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-09-16T15:15:47")
@StaticMetamodel(Subcategory.class)
public class Subcategory_ { 

    public static volatile SingularAttribute<Subcategory, String> subcategoryname;
    public static volatile CollectionAttribute<Subcategory, Product> productCollection;
    public static volatile SingularAttribute<Subcategory, Integer> subcategoryid;
    public static volatile SingularAttribute<Subcategory, Category> category;

}
package pojoClassesForAPIs;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class SearchProductResponsePojo {

    //variables
    private int responseCode;
    private List<Product> products;

    @Getter
    @Setter
    @ToString
    public static class Product {
        private int id;
        private String name;
        private String price;
        private String brand;
        private Category category;
    }

    @Getter
    @Setter
    @ToString
    public static class Category {
        private UserType usertype;
        private String category;
    }

    @Getter
    @Setter
    @ToString
    public static class UserType {
        private String usertype;
    }
}

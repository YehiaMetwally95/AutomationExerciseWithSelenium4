package pojoClassesForAPIs;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter
@ToString
@Jacksonized
@Builder
public class SearchProductRequestPojo {

    //variables
    private String search_product;
}

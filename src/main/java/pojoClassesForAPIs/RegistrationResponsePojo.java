package pojoClassesForAPIs;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RegistrationResponsePojo {

    //variables
    private int responseCode;
    private String message;
}

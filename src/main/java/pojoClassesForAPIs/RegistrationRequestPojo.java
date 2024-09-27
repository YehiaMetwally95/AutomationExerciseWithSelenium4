package pojoClassesForAPIs;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class RegistrationRequestPojo {

    //variables
    @JsonProperty("Name") private String name;
    @JsonProperty("Email") private String email;
    @JsonProperty("Password") private String password;
    @JsonProperty("Title") private String title;
    @JsonProperty("DayOfBirth") private String birth_date;
    @JsonProperty("MonthOfBirth") private String birth_month;
    @JsonProperty("YearOfBirth") private String birth_year;
    @JsonProperty("FirstName") private String firstname;
    @JsonProperty("LastName") private String lastname;
    @JsonProperty("Company") private String company;
    @JsonProperty("Address1") private String address1;
    @JsonProperty("Address2") private String address2;
    @JsonProperty("Country") private String country;
    @JsonProperty("ZipCode") private String zipcode;
    @JsonProperty("State") private String state;
    @JsonProperty("City") private String city;
    @JsonProperty("MobileNumber") private String mobile_number;
}

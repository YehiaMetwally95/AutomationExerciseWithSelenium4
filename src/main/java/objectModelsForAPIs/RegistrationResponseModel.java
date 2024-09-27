package objectModelsForAPIs;

import io.qameta.allure.Step;
import org.testng.Assert;
import pojoClassesForAPIs.RegistrationRequestPojo;
import pojoClassesForAPIs.RegistrationResponsePojo;

public class RegistrationResponseModel {

    //ObjectsFromPojoClasses
    RegistrationRequestPojo requestObject;
    RegistrationResponsePojo responseObject;

    //Constructor to pass the response from Request Model to Response Model
    public RegistrationResponseModel(RegistrationRequestPojo requestObject, RegistrationResponsePojo responseObject) {
        this.requestObject = requestObject;
        this.responseObject = responseObject;
    }

    //Validation Methods
    @Step("validateMassageFromResponse")
    public RegistrationResponseModel validateMassageFromResponse(String expectedMessage) {
        Assert.assertEquals(responseObject.getMessage(), expectedMessage);
        return this;
    }

    @Step("validateCodeFromResponse")
    public RegistrationResponseModel validateCodeFromResponse(int responseCody) {
        Assert.assertEquals(responseObject.getResponseCode(), responseCody);
        return this;
    }

    //Getter Methods
    public RegistrationRequestPojo getRequestPojoObject() {
        return requestObject;
    }

    public RegistrationResponsePojo getResponsePojoObject() {
        return responseObject;

    }
}

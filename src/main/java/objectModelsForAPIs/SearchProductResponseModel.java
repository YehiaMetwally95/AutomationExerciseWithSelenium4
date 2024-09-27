package objectModelsForAPIs;

import io.qameta.allure.Step;
import org.testng.Assert;
import pojoClassesForAPIs.RegistrationRequestPojo;
import pojoClassesForAPIs.RegistrationResponsePojo;
import pojoClassesForAPIs.SearchProductRequestPojo;
import pojoClassesForAPIs.SearchProductResponsePojo;
import utils.CustomSoftAssert;

public class SearchProductResponseModel {
    //ObjectsFromPojoClasses
    SearchProductRequestPojo requestObject;
    SearchProductResponsePojo responseObject;

    //Constructor to pass the response from Request Model to Response Model
    public SearchProductResponseModel(SearchProductRequestPojo requestObject, SearchProductResponsePojo responseObject) {
        this.requestObject = requestObject;
        this.responseObject = responseObject;
    }

    //Validation Methods
    @Step("Validate Response Code from Response")
    public SearchProductResponseModel validateResponseCodeFromResponse(int responseCode) {
        Assert.assertEquals(responseObject.getResponseCode(),responseCode);
        return this;
    }

    @Step("Validate Product Id from Response")
    public SearchProductResponseModel validateProductIdFromResponse(String id) {
        CustomSoftAssert.softAssert.assertEquals(String.valueOf(responseObject.getProducts().getFirst().getId()),id);
        return this;
    }

    @Step("Validate Product Name from Response")
    public SearchProductResponseModel validateProductNameFromResponse(String name) {
        CustomSoftAssert.softAssert.assertEquals(responseObject.getProducts().getFirst().getName(),name);
        return this;
    }

    @Step("Validate Product Price from Response")
    public SearchProductResponseModel validateProductPriceFromResponse(String price) {
        CustomSoftAssert.softAssert.assertEquals(responseObject.getProducts().getFirst().getPrice(),price);
        return this;
    }

    @Step("Validate Product Brand from Response")
    public SearchProductResponseModel validateProductBrandFromResponse(String brand) {
        CustomSoftAssert.softAssert.assertEquals(responseObject.getProducts().getFirst().getBrand(),brand);
        return this;
    }

    @Step("Validate Product Category from Response")
    public SearchProductResponseModel validateProductCategoryFromResponse(String category) {
        CustomSoftAssert.softAssert.assertEquals(responseObject.getProducts().getFirst()
                .getCategory().getUsertype().getUsertype(),category);
        return this;
    }

    @Step("Validate Product Subcategory from Response")
    public SearchProductResponseModel validateProductSubcategoryFromResponse(String subCategory) {
        CustomSoftAssert.softAssert.assertEquals(responseObject.getProducts().getFirst()
                .getCategory().getCategory(),subCategory);
        return this;
    }

    @Step("Validate Product Total Price")
    public SearchProductResponseModel validateProductTotalPrice(String quantity,String totalPrice) {
        int productPrice = Integer.parseInt(responseObject.getProducts().getFirst().getPrice().split("Rs. ",2)[1]);
        int productQuantity = Integer.parseInt(quantity);
        int productTotalPrice = Integer.parseInt(totalPrice);
        CustomSoftAssert.softAssert.assertEquals(productPrice*productQuantity , productTotalPrice);
        return this;
    }

    //Getter Methods
    public SearchProductRequestPojo getRequestPojoObject() {
        return requestObject;
    }

    public SearchProductResponsePojo getResponsePojoObject() {
        return responseObject;
    }
}

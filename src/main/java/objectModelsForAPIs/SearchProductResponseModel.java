package objectModelsForAPIs;

import io.qameta.allure.Step;
import org.testng.Assert;
import pojoClassesForAPIs.SearchProductRequestPojo;
import pojoClassesForAPIs.SearchProductResponsePojo;
import yehiaEngine.assertions.CustomAssert;
import yehiaEngine.assertions.CustomSoftAssert;

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
        CustomAssert.assertEquals(responseObject.getResponseCode(),responseCode);
        return this;
    }

    @Step("Validate Product Details from Response")
    public SearchProductResponseModel validateProductDetailsFromResponse(String id,String name,String price,
                                                                         String brand,String category,
                                                                         String subcategory,String quantity,
                                                                         String totalPrice) {
        validateProductIdFromResponse(id)
                .validateProductNameFromResponse(name)
                .validateProductPriceFromResponse(price)
                .validateProductBrandFromResponse(brand)
                .validateProductCategoryFromResponse(category)
                .validateProductSubcategoryFromResponse(subcategory)
                .validateProductTotalPrice(quantity,totalPrice);
        return this;
    }

    @Step("Validate Product Details from Response")
    public SearchProductResponseModel validateProductDetailsFromResponse(String id,String name,String price,
                                                                         String brand,String category,
                                                                         String subcategory) {
        validateProductIdFromResponse(id)
                .validateProductNameFromResponse(name)
                .validateProductPriceFromResponse(price)
                .validateProductBrandFromResponse(brand)
                .validateProductCategoryFromResponse(category)
                .validateProductSubcategoryFromResponse(subcategory);
        return this;
    }

    @Step("Validate Product Id from Response")
    private SearchProductResponseModel validateProductIdFromResponse(String id) {
        CustomSoftAssert.assertEquals(String.valueOf(responseObject.getProducts().get(0).getId()),id);
        return this;
    }

    @Step("Validate Product Name from Response")
    private SearchProductResponseModel validateProductNameFromResponse(String name) {
        CustomSoftAssert.assertEquals(responseObject.getProducts().get(0).getName(),name);
        return this;
    }

    @Step("Validate Product Price from Response")
    private SearchProductResponseModel validateProductPriceFromResponse(String price) {
        CustomSoftAssert.assertEquals(responseObject.getProducts().get(0).getPrice(),price);
        return this;
    }

    @Step("Validate Product Brand from Response")
    private SearchProductResponseModel validateProductBrandFromResponse(String brand) {
        CustomSoftAssert.assertEquals(responseObject.getProducts().get(0).getBrand(),brand);
        return this;
    }

    @Step("Validate Product Category from Response")
    private SearchProductResponseModel validateProductCategoryFromResponse(String category) {
        CustomSoftAssert.assertEquals(responseObject.getProducts().get(0)
                .getCategory().getUsertype().getUsertype(),category);
        return this;
    }

    @Step("Validate Product Subcategory from Response")
    private SearchProductResponseModel validateProductSubcategoryFromResponse(String subCategory) {
        CustomSoftAssert.assertEquals(responseObject.getProducts().get(0)
                .getCategory().getCategory(),subCategory);
        return this;
    }

    @Step("Validate Product Total Price")
    private SearchProductResponseModel validateProductTotalPrice(String quantity,String totalPrice) {
        int productPrice = Integer.parseInt(responseObject.getProducts().get(0).getPrice().split("Rs. ",2)[1]);
        int productQuantity = Integer.parseInt(quantity);
        int productTotalPrice = Integer.parseInt(totalPrice);
        CustomSoftAssert.assertEquals(productPrice*productQuantity , productTotalPrice);
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

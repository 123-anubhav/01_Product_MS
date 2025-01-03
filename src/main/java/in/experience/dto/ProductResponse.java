package in.experience.dto;

import in.experience.model.Product;

public class ProductResponse {
    private Object headers;
    private Product body;  // Assuming body contains the Product data
    private int statusCodeValue;
    private String statusCode;

    // Getters and Setters
    public Product getBody() {
        return body;
    }

    public void setBody(Product body) {
        this.body = body;
    }

    // Other getters and setters for headers, statusCodeValue, etc.
}

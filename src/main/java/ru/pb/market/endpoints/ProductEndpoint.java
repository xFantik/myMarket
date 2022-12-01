package ru.pb.market.endpoints;


import lombok.RequiredArgsConstructor;
import ru.pb.market.services.ProductService;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.pb.market.soap.products.GetAllProductsRequest;
import ru.pb.market.soap.products.GetAllProductsResponse;
import ru.pb.market.soap.products.GetProductByNameRequest;
import ru.pb.market.soap.products.GetProductByNameResponse;


@Endpoint
@RequiredArgsConstructor
public class ProductEndpoint {
    private static final String NAMESPACE_URI = "http://www.pb.ru/market/products";
    private final ProductService productService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductByNameRequest")
    @ResponsePayload
    public GetProductByNameResponse getProductById(@RequestPayload GetProductByNameRequest request) {
        GetProductByNameResponse response = new GetProductByNameResponse();
        response.setProduct(productService.getProductByTitle(request.getName()));
        return response;
    }

    /*
        Пример запроса: POST http://localhost:8080/ws
        Header -> Content-Type: text/xml

        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:f="http://www.pb.ru/market/products">
            <soapenv:Header/>
            <soapenv:Body>
                <f:getAllProductsRequest/>
            </soapenv:Body>
        </soapenv:Envelope>
     */

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllProductsRequest")
    @ResponsePayload
    public GetAllProductsResponse getAllProducts(@RequestPayload GetAllProductsRequest request) {
        GetAllProductsResponse response = new GetAllProductsResponse();
        productService.getAllProductsSoap().forEach(response.getProducts()::add);
        return response;
    }
}

package ru.neoflex.controllers;
import io.restassured.http.ContentType;
import ru.neoflex.model.RequestPutPrice;
import ru.neoflex.model.RequestSaveTestimony;

import static io.restassured.RestAssured.given;

public class RequestTestController {

    //Метод для отправки Post запроса RequestSaveTestimony на микросервис.
    public static int getRequestCode(String uRL, RequestSaveTestimony requestSaveTestimony) {


            return given().
                    contentType(ContentType.JSON).
                    body(requestSaveTestimony).
                    when().
                    post(uRL).
                    then().
                    extract().
                    response().
                    getStatusCode();

    }

    //Метод для отправки Post запроса RequestPutPrice на микросервис.
    public static int getRequestCode(String uRL, RequestPutPrice requestPutPrice) {


        return given().
                contentType(ContentType.JSON).
                body(requestPutPrice).
                when().
                post(uRL).
                then().
                extract().
                response().
                getStatusCode();

    }

    //Метод для отправки GET запроса на микросервис
    public static int getRequestCode(String uRL) {

            return given().
                    when().
                    get(uRL).
                    then().
                    extract().
                    response().
                    getStatusCode();

    }
}
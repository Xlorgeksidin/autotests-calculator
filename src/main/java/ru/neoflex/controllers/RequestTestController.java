package ru.neoflex.controllers;

import io.restassured.http.ContentType;
import ru.neoflex.model.RequestPutPrice;
import ru.neoflex.model.RequestSaveTestimony;
import ru.neoflex.model.ResponsePutPrice;
import ru.neoflex.model.ResponseSaveTestimony;

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
    public static ResponseSaveTestimony getResponseBodySave(String uRL, RequestSaveTestimony requestSaveTestimony) {
        return given().
                contentType(ContentType.JSON).
                body(requestSaveTestimony).
                when().
                post(uRL).
                then().
                extract().
                response().
                as(ResponseSaveTestimony.class);
    }

    public static ResponsePutPrice getResponseBodyPutPrice(String uRL, RequestPutPrice requestPutPrice) {
        return given().
                contentType(ContentType.JSON).
                body(requestPutPrice).
                when().
                post(uRL).
                then().
                extract().
                response().
                as(ResponsePutPrice.class);
    }

    public static ResponseSaveTestimony getResponseBodyGet(String uRL) {
        return given().
                when().
                get(uRL).
                then().
                extract().
                response().
                as(ResponseSaveTestimony.class);
    }

}
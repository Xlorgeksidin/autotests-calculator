package ru.vtb.neoflex.autotests;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import ru.neoflex.controllers.RequestTestController;
import ru.neoflex.model.Price;
import ru.neoflex.model.RequestPutPrice;

public class ChangePriceTest {
    @Test
    public void checkCodeSuccessTest(){
        String changePriceTestimonyURI = "http://localhost:8080/services/testimony/changePrice";
        RequestPutPrice requestPutPrice = new RequestPutPrice();
        Price price = new Price();

        price.setPriceColdWater(1050);
        price.setPriceHotWater(123);
        price.setPriceGas(124);
        price.setPriceElectricity(12412412);
        requestPutPrice.setPrice(price);

        int actualStatusCode = RequestTestController.getRequestCode(changePriceTestimonyURI, requestPutPrice);

        System.out.print("changePrice return code: " + actualStatusCode);

        Assertions.assertEquals(200, actualStatusCode);

    }
}

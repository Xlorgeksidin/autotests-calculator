package ru.vtb.neoflex.autotests;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import ru.neoflex.controllers.RequestTestController;
import ru.neoflex.model.Price;
import ru.neoflex.model.RequestPutPrice;
import ru.neoflex.model.ResponsePutPrice;

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
    @Test
    public void checkFaultCodeSuccessTest(){
        String changePriceTestimonyURI = "http://localhost:8080/services/testimony/changePrice";
        RequestPutPrice requestPutPrice = new RequestPutPrice();
        Price price = new Price();

        price.setPriceColdWater(1050);
        price.setPriceHotWater(123);
        price.setPriceGas(124);
        price.setPriceElectricity(12412412);
        requestPutPrice.setPrice(price);

        ResponsePutPrice responsePutPrice = RequestTestController.getResponseBodyPutPrice(changePriceTestimonyURI, requestPutPrice);
        String resultCode = responsePutPrice.getResultCode();
        String resultText = responsePutPrice.getResultText();
        //Тут проявлю инициативу и выведу полностью тело ответа, коли у него так красиво описан toString :)
        System.out.println(responsePutPrice);
        Assertions.assertEquals("0", resultCode);
        Assertions.assertEquals("success", resultText);

    }
}

package ru.vtb.neoflex.autotests;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import ru.neoflex.controllers.RequestTestController;
import ru.neoflex.dao.MySqlConnector;
import ru.neoflex.model.Price;
import ru.neoflex.model.RequestPutPrice;
import ru.neoflex.model.ResponsePutPrice;

import java.sql.ResultSet;

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
    @Test
    public void checkSaveDataInDbPriceGuide(){
        String changePriceTestimonyURI = "http://localhost:8080/services/testimony/changePrice";
        RequestPutPrice requestPutPrice = new RequestPutPrice();
        Price price = new Price();

        price.setPriceColdWater(54);
        price.setPriceHotWater(102);
        price.setPriceGas(120);
        price.setPriceElectricity(2);
        requestPutPrice.setPrice(price);

        //Так как тут проверяем только правильность сохранения данных в таблицу, то
        //просто дергаем запрос, чтобы он отправил данные, а ссылку на объект присваивать не будем
        RequestTestController.getResponseBodyPutPrice(changePriceTestimonyURI, requestPutPrice);

        try {
            ResultSet expectedResult = MySqlConnector.selectAllFromPriceGuide();
            while (expectedResult.next()){
                double coldWater = expectedResult.getDouble("priceColdWater");
                double hotWater = expectedResult.getDouble("priceHotWater");
                double gas = expectedResult.getDouble("priceGas");
                double electricity = expectedResult.getDouble("priceElectricity");

                Assertions.assertEquals(coldWater, requestPutPrice.getPrice().getPriceColdWater());
                Assertions.assertEquals(hotWater, requestPutPrice.getPrice().getPriceHotWater());
                Assertions.assertEquals(gas, requestPutPrice.getPrice().getPriceGas());
                Assertions.assertEquals(electricity, requestPutPrice.getPrice().getPriceElectricity());
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}

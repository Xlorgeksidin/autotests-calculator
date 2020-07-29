package ru.vtb.neoflex.autotests;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.neoflex.controllers.RequestTestController;
import ru.neoflex.dao.MySqlConnector;
import ru.neoflex.model.Price;
import ru.neoflex.model.RequestPutPrice;
import ru.neoflex.model.ResponsePutPrice;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.Iterator;

import static ru.vtb.neoflex.autotests.TestBase.validRequestChange;
/*В настройках Gradle такая особенность, которая блочит запуск тестов из Gradle.
Следующий текст относится к настроечному файлу build.gradle(т.к. комменты на русском языке в нем не отображаются корректно)
Мы не можем запустить параметризованные тесты без блока test.
При этом, для запуска параметризованных тестов нужны следующие расширения:
testImplementation 'org.junit.jupiter:junit-jupiter-api:5.7.0-M1'
testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine:5.7.0-M1'
а
в блоке test должна использоваться платформа useJUnitPlatform()
Однако, при таких настройках будут запущены только параметризованные тесты, а остальные будут пропущены.
Если, в блоке test указать useJUnit(), то будут запущены только НЕпараметризованные тесты.

UPD:
Эврика, решение нашлось!
Для запуска всех тестов из сборщика нужно в dependencies указать:
    testImplementation 'org.junit.jupiter:junit-jupiter-api:5.7.0-M1'
    testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine:5.7.0-M1'
    testCompileOnly 'junit:junit:4.12'
    testRuntimeOnly 'org.junit.vintage:junit-vintage-engine:5.3.1'
а в
    test указать useJUnitPlatform()
 */

public class ChangePriceTest {
    String changePriceTestimonyURI = "http://localhost:8080/services/testimony/changePrice";
    public static Iterator<Object[]> changePrice() throws IOException{
        String request = "src/test/resources/ChangePriceTest.json";
        return validRequestChange(request);
    }

    @MethodSource("changePrice")
    @ParameterizedTest
    public void checkCodeSuccessTest(RequestPutPrice requestPutPrice){
        int codeResponse = RequestTestController.getRequestCode(changePriceTestimonyURI, requestPutPrice);
        Assertions.assertEquals(200,codeResponse);
    }



    @MethodSource("changePrice")
    @ParameterizedTest
    public void checkFaultCodeSuccessTest(RequestPutPrice requestPutPrice){

        ResponsePutPrice responsePutPrice = RequestTestController.getResponseBodyPutPrice(changePriceTestimonyURI, requestPutPrice);
        String resultCode = responsePutPrice.getResultCode();
        String resultText = responsePutPrice.getResultText();
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

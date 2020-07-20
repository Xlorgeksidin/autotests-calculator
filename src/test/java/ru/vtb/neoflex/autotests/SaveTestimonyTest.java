package ru.vtb.neoflex.autotests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.neoflex.controllers.RequestTestController;
import ru.neoflex.dao.MySqlConnector;
import ru.neoflex.model.CurrentTestimony;
import ru.neoflex.model.RequestSaveTestimony;
import ru.neoflex.model.ResponseSaveTestimony;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.Iterator;

import static ru.vtb.neoflex.autotests.TestBase.validRequestSave;

public class SaveTestimonyTest {
    String saveTestimonyURI = "http://localhost:8080/services/testimony/save";

    public static Iterator<Object[]> dataRead() throws IOException {
        String requestFile = "src/test/resources/SaveTestimonyTest.json";
        return validRequestSave(requestFile);
    }

    @MethodSource("dataRead")
    @ParameterizedTest
    public void checkCodeSuccessTest(RequestSaveTestimony requestSaveTestimony) {
        int actualStatusCode = RequestTestController.getRequestCode(saveTestimonyURI, requestSaveTestimony);
        Assertions.assertEquals(200, actualStatusCode);
    }



    @Test
    public void checkFaultCodeSuccessTest(){
        String saveTestimonyURI = "http://localhost:8080/services/testimony/save";
        RequestSaveTestimony requestSaveTestimony = new RequestSaveTestimony();
        CurrentTestimony currentTestimony = new CurrentTestimony();

        requestSaveTestimony.setDate("02-2020");
        currentTestimony.setColdWater(20);
        currentTestimony.setHotWater(30);
        currentTestimony.setGas(40);
        currentTestimony.setElectricity(50);
        requestSaveTestimony.setCurrentTestimony(currentTestimony);


        ResponseSaveTestimony responseSaveTestimony = RequestTestController.getResponseBodySave(saveTestimonyURI, requestSaveTestimony);
        String resultCode = responseSaveTestimony.getFaultcode().getResultCode();
        String resultText = responseSaveTestimony.getFaultcode().getResultText();
        System.out.println(resultCode);
        System.out.println(resultText);
        Assertions.assertEquals("0", resultCode);
        Assertions.assertEquals("success", resultText);


        try {
            ResultSet expectedResult = MySqlConnector.selectAllFromBilling(requestSaveTestimony.getDate());
            while (expectedResult.next()) {
                String date = expectedResult.getString("currentmonth");
                double coldWater = expectedResult.getDouble("coldWater");
                double hotWater = expectedResult.getDouble("hotWater");
                double gas = expectedResult.getDouble("gas");
                double electricity  = expectedResult.getDouble("electricity");

                Assertions.assertEquals(date, requestSaveTestimony.getDate());
                Assertions.assertEquals(coldWater, requestSaveTestimony.getCurrentTestimony().getColdWater());
                Assertions.assertEquals(hotWater, requestSaveTestimony.getCurrentTestimony().getHotWater());
                Assertions.assertEquals(gas, requestSaveTestimony.getCurrentTestimony().getGas());
                Assertions.assertEquals(electricity, requestSaveTestimony.getCurrentTestimony().getElectricity());

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


}

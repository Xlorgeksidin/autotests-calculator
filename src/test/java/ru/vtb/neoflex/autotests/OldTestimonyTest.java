package ru.vtb.neoflex.autotests;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import ru.neoflex.controllers.RequestTestController;
import ru.neoflex.dao.MySqlConnector;
import ru.neoflex.model.ResponseSaveTestimony;

import java.sql.ResultSet;

public class OldTestimonyTest {
    @Test
    public void checkCodeSuccessTest(){
        String oldTestimonyURI = "http://localhost:8080/services/testimony/get/old/testimony/02-2020";

        int actualStatusCode = RequestTestController.getRequestCode(oldTestimonyURI);

        System.out.print("oldTestimony return code: " + actualStatusCode);

        Assertions.assertEquals(200, actualStatusCode);
    }

    @Test
    public void checkResponseSuccessTest(){
        String oldTestimonyURI = "http://localhost:8080/services/testimony/get/old/testimony/02-2020";

        ResponseSaveTestimony responseGetTestimony = RequestTestController.getResponseBodyGet(oldTestimonyURI);
        String resultCode = responseGetTestimony.getFaultcode().getResultCode();
        String resultText = responseGetTestimony.getFaultcode().getResultText();
        String date = responseGetTestimony.getDate();
        double electricity = responseGetTestimony.getCost().getElectricity();

        System.out.println(responseGetTestimony);

        Assertions.assertEquals("0", resultCode);
        Assertions.assertEquals("success", resultText);
        Assertions.assertEquals("02-2020", date);
        Assertions.assertEquals(20.0, electricity);

    }
    @Test
    public void checkConformityResponseAndDataInDB(){
        String oldTestimonyURI = "http://localhost:8080/services/testimony/get/old/testimony/02-2020";

        ResponseSaveTestimony responseGetTestimony = RequestTestController.getResponseBodyGet(oldTestimonyURI);

        Assertions.assertEquals("0", responseGetTestimony.getFaultcode().getResultCode());
        Assertions.assertEquals("success", responseGetTestimony.getFaultcode().getResultText());

        try{
            ResultSet resultSet = MySqlConnector.selectAllFromTestimonyHistory("02-2020");
            while(resultSet.next()){
                Assertions.assertEquals(resultSet.getString("currentmonth"), responseGetTestimony.getDate());
                Assertions.assertEquals(resultSet.getString("previous_month"), responseGetTestimony.getPreviousDate());
                Assertions.assertEquals(resultSet.getDouble("coldWater"), responseGetTestimony.getConsumed().getColdWater());
                Assertions.assertEquals(resultSet.getDouble("hotWater"), responseGetTestimony.getConsumed().getHotWater());
                Assertions.assertEquals(resultSet.getDouble("gas"), responseGetTestimony.getConsumed().getGas());
                Assertions.assertEquals(resultSet.getDouble("electricity"), responseGetTestimony.getConsumed().getElectricity());
                Assertions.assertEquals(resultSet.getDouble("cost_coldWater"), responseGetTestimony.getCost().getColdWater());
                Assertions.assertEquals(resultSet.getDouble("cost_hotWater"), responseGetTestimony.getCost().getHotWater());
                Assertions.assertEquals(resultSet.getDouble("cost_gas"), responseGetTestimony.getCost().getGas());
                Assertions.assertEquals(resultSet.getDouble("cost_electricity"), responseGetTestimony.getCost().getElectricity());
                Assertions.assertEquals(resultSet.getDouble("total_cost"), responseGetTestimony.getTotalCost());
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}

package ru.vtb.neoflex.autotests;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import ru.neoflex.controllers.RequestTestController;
import ru.neoflex.model.ResponseSaveTestimony;

public class OldTestimonyTest {
    @Test
    public void checkCodeSuccessTest(){
        String oldTestimonyURI = "http://localhost:8080/services/testimony/get/old/testimony/02-2020";

        int actualStatusCode = RequestTestController.getRequestCode(oldTestimonyURI);

        System.out.print("oldTestimony return code: " + actualStatusCode);

        Assertions.assertEquals(200, actualStatusCode);
    }

    @Test
    public void checkFaultCodeSuccessTest(){
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
        Assertions.assertEquals(1.2412412E8, electricity);

    }
}

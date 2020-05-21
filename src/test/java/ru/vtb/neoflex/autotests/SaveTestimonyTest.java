package ru.vtb.neoflex.autotests;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import ru.neoflex.controllers.RequestTestController;
import ru.neoflex.model.CurrentTestimony;
import ru.neoflex.model.RequestSaveTestimony;

public class SaveTestimonyTest {
    @Test
    public void checkCodeSuccessTest(){
        String saveTestimonyURI = "http://localhost:8080/services/testimony/save";
        RequestSaveTestimony requestSaveTestimony = new RequestSaveTestimony();
        CurrentTestimony currentTestimony = new CurrentTestimony();

        requestSaveTestimony.setDate("02-2020");
        currentTestimony.setColdWater(20);
        currentTestimony.setHotWater(30);
        currentTestimony.setGas(40);
        currentTestimony.setElectricity(50);
        requestSaveTestimony.setCurrentTestimony(currentTestimony);

        int actualStatusCode = RequestTestController.getRequestCode(saveTestimonyURI, requestSaveTestimony);

        System.out.print("saveTestimony return code: " + actualStatusCode);

        Assertions.assertEquals(200, actualStatusCode);


    }
}

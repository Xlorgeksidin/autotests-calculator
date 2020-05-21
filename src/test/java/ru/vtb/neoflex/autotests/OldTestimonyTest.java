package ru.vtb.neoflex.autotests;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import ru.neoflex.controllers.RequestTestController;

public class OldTestimonyTest {
    @Test
    public void checkCodeSuccessTest(){
        String oldTestimonyURI = "http://localhost:8080/services/testimony/get/old/testimony/02-2020";

        int actualStatusCode = RequestTestController.getRequestCode(oldTestimonyURI);

        System.out.print("oldTestimony return code: " + actualStatusCode);

        Assertions.assertEquals(200, actualStatusCode);
    }
}

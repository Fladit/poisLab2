package com.javacodegeeks.drools;

import org.drools.core.ClassObjectFilter;
import org.junit.Assert;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.util.Collection;
import java.util.HashMap;

import static org.junit.Assert.*;

public class AutomobileInsuranceTest {
    @Test
    public void checkInfo() throws Exception {
        try {
            HashMap<String, Double> checkPrice = new HashMap<>();
            checkPrice.put("Ivan", 40000.0);
            checkPrice.put("Denis", 30000.0);
            checkPrice.put("Eugene", 20000.0);
            checkPrice.put("Arsen", 10000.0);
            // load up the knowledge base
            KieServices ks = KieServices.Factory.get();
            KieContainer kContainer = ks.getKieClasspathContainer();
            KieSession kSession = kContainer.newKieSession("ksession-rules");

            AutomobileInsurance droolsIntroduction = new AutomobileInsurance("Eugene", "Andreevich", "Zatsarin",
                    21, 1, 77, 5);
            kSession.insert(droolsIntroduction);
            kSession.insert(new AutomobileInsurance("Denis", "Petrovich", "Vlasov", 45, 25, 430, 2));
            kSession.insert(new AutomobileInsurance("Ivan", "Alexeevich", "Sinicin", 32, 5 , 256, 7));
            kSession.fireAllRules();

            kSession.setGlobal("topicLevel", "Beginner");
            kSession.insert(new AutomobileInsurance("Arsen", "Aramovich", "Arushanyan", 40, 15, 146, 4));
            kSession.fireAllRules();

            Collection<AutomobileInsurance> myfacts = (Collection<AutomobileInsurance>) kSession.getObjects( new ClassObjectFilter(AutomobileInsurance.class) );
            final boolean[] answer = {true};
            myfacts.forEach(element -> {
                if (!element.getDrivingInsurancePrice().equals(checkPrice.get(element.getFirstName()))) {
                    answer[0] = false;
                }
            });
            Assert.assertEquals(answer[0], true);

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
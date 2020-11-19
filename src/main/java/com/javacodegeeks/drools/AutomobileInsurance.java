package com.javacodegeeks.drools;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * This is a sample class to launch a rule.
 */
public class AutomobileInsurance {
    private String firstName;
    private String secondName;
    private String lastName;
    private Integer clientAge;
    private Integer ageDrivingExperience;
    private Integer enginePower;
    private Integer carAge;
    private Double drivingInsurancePrice = 10000.0;
    private Double coefficient = 1.0;

    public AutomobileInsurance(String firstName, String secondName, String lastName, Integer clientAge, Integer ageDrivingExperience, Integer enginePower, Integer carAge) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.clientAge = clientAge;
        this.ageDrivingExperience = ageDrivingExperience;
        this.enginePower = enginePower;
        this.carAge = carAge;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getClientAge() {
        return clientAge;
    }

    public Integer getAgeDrivingExperience() {
        return ageDrivingExperience;
    }

    public Integer getEnginePower() {
        return enginePower;
    }

    public Integer getCarAge() {
        return carAge;
    }

    public Double getDrivingInsurancePrice() {
        return drivingInsurancePrice;
    }

    public Double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Double coefficient) {
        this.coefficient = coefficient;
    }

    public void setDrivingInsurancePrice(double drivingInsurancePrice) {
        this.drivingInsurancePrice = drivingInsurancePrice;
    }

    public static final void main(String[] args) {
        try {
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
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public String getInsuranseInfo() {
        return (firstName + " " + secondName + " " + lastName + " может получить страховку за " + drivingInsurancePrice + " рублей!");
    }

}
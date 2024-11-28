package com.sg.bankBuddy.bankBuddy_core.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

class ArchitectureTest {

    private final JavaClasses importedClasses = new ClassFileImporter().importPackages("com.sg.bankBuddy.bankBuddy_core");

    @Test
    void domainShouldBeIndependent() {
        ArchRule rule = classes()
                .that().resideInAPackage("..domain..")
                .should().onlyDependOnClassesThat()
                .resideInAnyPackage(
                        "java..",
                        "..domain.."
                );

        rule.check(importedClasses);
    }

    @Test
    void noCyclicDependenciesBetweenPackages() {
        ArchRule rule = slices()
                .matching("com.sg.bankBuddy.bankBuddy_core.(*)..")
                .should().beFreeOfCycles();

        rule.check(importedClasses);
    }

}

package io.github.devsong.base.test;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import io.github.devsong.base.entity.GlobalConstant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;

@AnalyzeClasses(packages = GlobalConstant.SYSTEM_PACKAGE_PREFIX, importOptions = {ImportOption.OnlyIncludeTests.class})
public class ArchUnit extends BaseTest {

    @ArchTest
    protected final ArchRule test_class_should_be_package_private =
            classes().that()
                    .haveSimpleNameEndingWith("Test")
                    .should()
                    .bePackagePrivate();

    @ArchTest
    protected final ArchRule test_method_should_be_package_private =
            methods()
                    .that()
                    .haveNameNotStartingWith("validate_")
                    .and()
                    .areDeclaredInClassesThat()
                    .haveSimpleNameEndingWith("Test")
                    .and()
                    .areAnnotatedWith(Test.class)
                    .or()
                    .areAnnotatedWith(ParameterizedTest.class)
                    .should()
                    .bePackagePrivate();

}

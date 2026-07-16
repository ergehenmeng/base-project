package com.eghm.architecture;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "com.eghm", importOptions = ImportOption.DoNotIncludeTests.class)
class ArchitectureRulesTest {

    @ArchTest
    static final ArchRule packages_use_declared_roots = classes()
            .that().resideInAPackage("com.eghm..")
            .should().resideInAnyPackage(
                    "com.eghm.foundation..", "com.eghm.platform..", "com.eghm.member..",
                    "com.eghm.business..", "com.eghm.integration..", "com.eghm.app.manage..",
                    "com.eghm.app.webapp..", "com.eghm.i18n..", "com.eghm.apiversion..");

    @ArchTest
    static final ArchRule foundation_does_not_depend_on_business = noClasses()
            .that().resideInAPackage("com.eghm.foundation..")
            .should().dependOnClassesThat().resideInAnyPackage(
                    "com.eghm.platform..", "com.eghm.member..", "com.eghm.business..",
                    "com.eghm.integration..", "com.eghm.app..");

    @ArchTest
    static final ArchRule business_modules_do_not_depend_on_servers = noClasses()
            .that().resideInAnyPackage(
                    "com.eghm.platform..", "com.eghm.member..", "com.eghm.business..",
                    "com.eghm.integration..")
            .should().dependOnClassesThat().resideInAPackage("com.eghm.app..");

    @ArchTest
    static final ArchRule mybatis_mappers_reside_in_mapper_packages = classes()
            .that().areAssignableTo(BaseMapper.class)
            .should().resideInAPackage("..mapper..");

    @ArchTest
    static final ArchRule service_implementations_use_impl_packages = classes()
            .that().resideInAPackage("..service..")
            .and().haveSimpleNameEndingWith("Impl")
            .should().resideInAPackage("..service.impl..");
}

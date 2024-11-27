package nh.springgraphql.graphqlservice.config.graphql;

import graphql.analysis.FieldComplexityCalculator;
import graphql.analysis.FieldComplexityEnvironment;
import graphql.analysis.MaxQueryComplexityInstrumentation;
import graphql.analysis.MaxQueryDepthInstrumentation;
import graphql.scalars.ExtendedScalars;
import nh.springgraphql.graphqlservice.config.graphql.tracing.SimplifiedTracingInstrumentation;
import nh.springgraphql.graphqlservice.graphql.NodeId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class GraphQlConfig {
    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder.scalar(ExtendedScalars.DateTime);
    }

    @Bean
    NodeId.NodeIdConverter nodeIdConverter() {
        return new NodeId.NodeIdConverter();
    }

    @Bean
    SimplifiedTracingInstrumentation tracingInstrumentation() {
        return new SimplifiedTracingInstrumentation();
    }

    @Bean
    public RuntimeWiringConfigurer directiveConfigurer() {
        return wiringBuilder -> wiringBuilder.directive("auth", new AuthorizationDirectiveWiring());
    }

    // Wenn diese Bean aktiviert wird, darf eine Query nur eine maximale Komplexität
    // vom 10 haben
    //  -> In der Default-Konfiguration hat jedes Feld eine "Komplexität" von 1
    //     so dass man mit der Konfiguration max. zehn Felder abfragen kann
    //  Als 2. Parameter kann man einen "Calculator" angeben, der die Komplexität
    //     eines Feldes individuell berechnet.
    //     Damit kann man z.B. Felder, die einne Remote-Aufruf auslösen "komplexer"
    //     machen als andere Felder
    //     Meine examplarische Implemtierung "DirectiveBasedCalculator" dafür verwendet
    //     eine Schema Direktive, mit der man im Schema festlegen kann, wie "komplex"
    //     ein Feld ist.
    //     (s. Schema-Datei)

    //    @Bean
//    MaxQueryComplexityInstrumentation maxQueryComplexityInstrumentation() {
//        final Logger log = LoggerFactory.getLogger( GraphQlConfig.class );
//        return new MaxQueryComplexityInstrumentation(10, new DirectiveBasedCalculator());
//    }

    // Mit dieser Bean kann man eine maximale Tiefe/maximale Anzahl
    // von Ebenen angeben, die ein Client abfragen kann
    //  damit kann man z.B. endlose Rekursionen verhindern
    //   (Story -> Comment -> Story -> Comment)
//    @Bean
//    MaxQueryDepthInstrumentation maxQueryDepthInstrumentation() {
//        return new MaxQueryDepthInstrumentation(3);
//    }



}
package nh.springgraphql.graphqlservice.config.graphql;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.idl.SchemaDirectiveWiring;
import graphql.schema.idl.SchemaDirectiveWiringEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;

// mit RuntimeWiringConfigurer in GraphqlConfig hinzufügen
public class AuthorizationDirectiveWiring implements SchemaDirectiveWiring {

    private static final Logger log = LoggerFactory.getLogger(AuthorizationDirectiveWiring.class);

    @Override
    public GraphQLFieldDefinition onField(SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> environment) {
        // "role"-Attribute, das im Schema per 'auth'-Direktive angegeben ist,
        // auslesen
        String role = environment.getAppliedDirective().getArgument("role").getValue();
        // "Original" DataFetcher (müsste auch mit DGS funktionieren)
        var existingDf = environment.getFieldDataFetcher();
        log.info("Wiring Role {} {}", role, existingDf);

        // "Wrapper DataFetcher", der vor dem Aufruf des "Original" DataFetchers
        // die Rolle des aktuellen Benutzers überprüft
        DataFetcher<?> df = new DataFetcher() {
            @Override
            public Object get(DataFetchingEnvironment environment) throws Exception {
                var auth = SecurityContextHolder.getContext().getAuthentication();
                if (auth == null) {
                    // Keine Authenication im Objekt gesetzt => Zugriff nicht erlaubt
                    //   (Kein Benutzer => keine Rolle => kein Zugriff)
                    return null;
                }

                var hasRole = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(role));

                if (!hasRole) {
                    log.info("Required role '{}' not present in SecurityContext - do not invoke DataFetcher", role);
                    return null;
                }

                // Ausführung / Ermitteln der Daten an den ursprünglichen DataFetcher
                // delegieren
                return existingDf.get(environment);
            }
        };

        return environment.setFieldDataFetcher(df);
    }
}

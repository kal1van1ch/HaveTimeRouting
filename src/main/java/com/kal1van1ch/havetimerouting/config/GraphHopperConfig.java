package com.kal1van1ch.havetimerouting.config;

import com.graphhopper.GraphHopper;
import com.graphhopper.config.Profile;
import com.graphhopper.util.CustomModel;
import com.graphhopper.util.TurnCostsConfig;
import com.kal1van1ch.havetimerouting.exception.GraphImportOrLoadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.graphhopper.json.Statement.If;
import static com.graphhopper.json.Statement.Op.LIMIT;
import static com.graphhopper.json.Statement.Op.MULTIPLY;

@Configuration
public class GraphHopperConfig {

    @Bean(destroyMethod = "close")
    public GraphHopper graphHopper(
            @Value("${routing.osm-file}") String osmFile,
            @Value("${routing.graph-location}") String graphLocation
    ) {
        GraphHopper hopper = new GraphHopper();

        hopper.setOSMFile(osmFile);
        hopper.setGraphHopperLocation(graphLocation);

        hopper.setEncodedValuesString(
                """
                foot_access, foot_average_speed,
                car_access, car_average_speed
                """
        );

        Profile footProfile = createShortestRouteByProfile(
                "foot",
                "foot_access",
                "foot_average_speed"
        );

        Profile carProfile = createShortestRouteByProfile(
                "car",
                "car_access",
                "car_average_speed"
        );
        carProfile.setTurnCostsConfig(
                TurnCostsConfig.car()
                        .setUTurnCosts(
                                TurnCostsConfig.INFINITE_U_TURN_COSTS
                        )
        );

        hopper.setProfiles(footProfile, carProfile);

        try {
            hopper.importOrLoad();
            return hopper;
        } catch (RuntimeException exception) {
            hopper.close();
            throw new GraphImportOrLoadException("Error with import or load the graph", exception);
        }
    }

    private Profile createShortestRouteByProfile(
            String name,
            String accessProperty,
            String speedProperty
    ) {
        CustomModel model = new CustomModel();

        model.setDistanceInfluence(0.0);

        model.addToPriority(
                If(
                        "!" + accessProperty + " || " + speedProperty + " <= 0",
                        MULTIPLY,
                        "0"
                )
        );

        model.addToSpeed(
                If("true", LIMIT, speedProperty)
        );

        return new Profile(name)
                .setCustomModel(model)
                .putHint("cm_version", "2");
    }
}
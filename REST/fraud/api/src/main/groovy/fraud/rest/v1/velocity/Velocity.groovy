package fraud.rest.v1.velocity

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

/**
 * Created by Tiger on 15.10.14.
 */
@ToString
@EqualsAndHashCode
class Velocity {
    Map<String, String> primaryMetrics
    Map<String, Map<Aggregation, Double>> aggregations
}

package com.graphhopper.jsprit.core.algorithm.ruin;

import com.graphhopper.jsprit.core.problem.Location;
import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import com.graphhopper.jsprit.core.problem.job.Break;
import com.graphhopper.jsprit.core.problem.job.Job;
import com.graphhopper.jsprit.core.problem.solution.route.VehicleRoute;
import com.graphhopper.jsprit.core.problem.solution.route.activity.BreakActivity;
import com.graphhopper.jsprit.core.problem.solution.route.activity.TourActivity;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleImpl;
import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by schroeder on 04/08/15.
 */
public class RuinBreakTest {

    @Test
    public void itShouldRuinBreaks() {
        Break aBreak = Break.Builder.newInstance("break").build();
        VehicleImpl v = VehicleImpl.Builder.newInstance("v").setStartLocation(Location.newInstance("loc"))
            .setBreak(aBreak).build();
        VehicleRoutingProblem vrp = VehicleRoutingProblem.Builder.newInstance().setFleetSize(VehicleRoutingProblem.FleetSize.FINITE).addVehicle(v).build();
        VehicleRoute route = VehicleRoute.Builder.newInstance(v).setJobActivityFactory(vrp.getJobActivityFactory()).addService(aBreak).build();
        TourActivity tourActivity = route.getActivities().get(0);
        Assert.assertTrue(tourActivity instanceof BreakActivity);
        RuinBreaks ruinBreaks = new RuinBreaks();
        List<Job> unassigned = new ArrayList<Job>();
        ruinBreaks.ruinEnds(Arrays.asList(route), unassigned);
        Assert.assertEquals(1, unassigned.size());
        Assert.assertEquals(aBreak, unassigned.get(0));
    }
}

/*
 *  Licensed to GraphHopper GmbH under one or more contributor
 *  license agreements. See the NOTICE file distributed with this work for 
 *  additional information regarding copyright ownership.
 * 
 *  GraphHopper GmbH licenses this file to you under the Apache License, 
 *  Version 2.0 (the "License"); you may not use this file except in 
 *  compliance with the License. You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.graphhopper.routing.util;

import com.graphhopper.reader.ReaderRelation;
import com.graphhopper.reader.ReaderWay;
import com.graphhopper.util.Helper;
import com.graphhopper.util.PMap;

import java.util.*;

/**
 * Defines bit layout for cars. (speed, access, ferries, ...)
 * <p>
 *
 * @author Peter Karich
 * @author Nop
 */
public class CarAvoidTollFlagEncoder extends CarFlagEncoder {
    protected final Map<String, Integer> trackTypeSpeedMap = new HashMap<String, Integer>();
    protected final Set<String> badSurfaceSpeedMap = new HashSet<String>();

    // This value determines the maximal possible on roads with bad surfaces
    protected int badSurfaceSpeed;

    // This value determines the speed for roads with access=destination
    protected int destinationSpeed;
    /**
     * A map which associates string to speed. Get some impression:
     * http://www.itoworld.com/map/124#fullscreen
     * http://wiki.openstreetmap.org/wiki/OSM_tags_for_routing/Maxspeed
     */
    protected final Map<String, Integer> defaultSpeedMap = new HashMap<String, Integer>();

    public CarAvoidTollFlagEncoder() {
        this(5, 5, 0);
    }

    public CarAvoidTollFlagEncoder(PMap properties) {
        this((int) properties.getLong("speed_bits", 5),
                properties.getDouble("speed_factor", 5),
                properties.getBool("turn_costs", false) ? 1 : 0);
        this.properties = properties;
        this.setBlockFords(properties.getBool("block_fords", true));
        this.setBlockByDefault(properties.getBool("block_barriers", true));
    }

    public CarAvoidTollFlagEncoder(String propertiesStr) {
        this(new PMap(propertiesStr));
    }

    public CarAvoidTollFlagEncoder(int speedBits, double speedFactor, int maxTurnCosts) {
        super(speedBits, speedFactor, maxTurnCosts);
    }

    @Override
    public long acceptWay(ReaderWay way) {
    	// avoid toll in Japan
    	// See Japan tagging https://wiki.openstreetmap.org/wiki/Japan_tagging
    	if (way.hasTag("highway", "motorway") && !way.hasTag("toll", "no")) { 	
    		return 0;
   	 	}
    	return super.acceptWay(way);
    }

    @Override
    public String toString() {
        return "car_avoid_toll";
    }
}

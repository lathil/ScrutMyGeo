package com.ptoceti.mongo.geoname;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION : Ptoceti
 * PROJECT : com.ptoceti.mongo.geoname
 * FILENAME : GeoNamePoint.java
 * 
 * This file is part of the Ptoceti project. More information about
 * this project can be found here: http://www.ptoceti.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2013 Ptoceti
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */

import java.util.ArrayList;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

public class GeoNamePoint extends BasicDBObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7256726871647404593L;
	
	public static final String TYPE = "type";
	public static final String POINT = "Point";
	
	public static final String COORDINATES = "coordinates";
	
	public GeoNamePoint() {
		put(TYPE, POINT);
	}
	
	public void setCoordinates(Double lattitude, Double longitude) {
		
		ArrayList<Double> coord = new ArrayList<Double>();
		coord.add(longitude);
		coord.add(lattitude);
		
		put(COORDINATES, coord);
	}
	
	public Double[] getCoordinates() {
		
		BasicDBList coords = (BasicDBList)get(COORDINATES);
		Double result[] = new Double[coords.size()];
		return coords.toArray(result);
	}

}

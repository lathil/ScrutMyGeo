package com.ptoceti.mongo.geoname;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION : Ptoceti
 * PROJECT : com.ptoceti.mongo.geoname
 * FILENAME : GeoNameCollection.java
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

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;

public class GeoNameCollection {

	public static final String GEONAMECOLLECTION = "geonames";
	
	private DBCollection geonames;
	
	public GeoNameCollection(DB db) {
		
		if( db.collectionExists(GEONAMECOLLECTION)) {
			geonames = db.getCollection(GEONAMECOLLECTION);
		} else {
			geonames = db.createCollection(GEONAMECOLLECTION, new BasicDBObject("capped", false));
			geonames.setObjectClass(GeoNameLoc.class);
		}
		
		setUpIndexes();
	}
	
	private void setUpIndexes() {
		
		geonames.ensureIndex(new BasicDBObject(GeoNameLoc.NAME, 1));
		geonames.ensureIndex(new BasicDBObject(GeoNameLoc.ASCIINAME, 1));
		geonames.ensureIndex(new BasicDBObject(GeoNameLoc.COUNTRYCODE, 1));
		geonames.ensureIndex(new BasicDBObject(GeoNameLoc.POPULATION, 1));
		geonames.ensureIndex(new BasicDBObject(GeoNameLoc.ELEVATION, 1));
		geonames.ensureIndex(new BasicDBObject(GeoNameLoc.LOCATION, "2dsphere"));
	}
	
	public boolean insertLocation(GeoNameLoc location){
	
		WriteResult result = geonames.insert(location, WriteConcern.ACKNOWLEDGED);
		return result.getLastError(WriteConcern.ACKNOWLEDGED).ok();
	}
}

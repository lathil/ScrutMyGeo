package com.ptoceti.mongo.geoname;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION : Ptoceti
 * PROJECT : com.ptoceti.mongo.geoname
 * FILENAME : GeoNameImporter.java
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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Scanner;

/**
 * 
 * 
 * 
 * 
 * The main 'geoname' table has the following fields :
---------------------------------------------------
geonameid         : integer id of record in geonames database
name              : name of geographical point (utf8) varchar(200)
asciiname         : name of geographical point in plain ascii characters, varchar(200)
alternatenames    : alternatenames, comma separated varchar(5000)
latitude          : latitude in decimal degrees (wgs84)
longitude         : longitude in decimal degrees (wgs84)
feature class     : see http://www.geonames.org/export/codes.html, char(1)
feature code      : see http://www.geonames.org/export/codes.html, varchar(10)
country code      : ISO-3166 2-letter country code, 2 characters
cc2               : alternate country codes, comma separated, ISO-3166 2-letter country code, 60 characters
admin1 code       : fipscode (subject to change to iso code), see exceptions below, see file admin1Codes.txt for display names of this code; varchar(20)
admin2 code       : code for the second administrative division, a county in the US, see file admin2Codes.txt; varchar(80) 
admin3 code       : code for third level administrative division, varchar(20)
admin4 code       : code for fourth level administrative division, varchar(20)
population        : bigint (8 byte int) 
elevation         : in meters, integer
dem               : digital elevation model, srtm3 or gtopo30, average elevation of 3''x3'' (ca 90mx90m) or 30''x30'' (ca 900mx900m) area in meters, integer. srtm processed by cgiar/ciat.
timezone          : the timezone id (see file timeZone.txt) varchar(40)
modification date : date of last modification in yyyy-MM-dd format

 * @author lor
 *
 */
public class GeoNameImporter {

	public void scanFile(String filePath, GeoNameCollection collection) throws FileNotFoundException {

		File fileToScan = new File(filePath);
		FileInputStream fis = new FileInputStream(fileToScan);
		scanStream( fis, collection);
	}
	
	public void scanStream(InputStream geoStream, GeoNameCollection collection) {
		Scanner streamScanner = new Scanner(new BufferedInputStream(geoStream)).useDelimiter("\n");
		while( streamScanner.hasNext()) {
			String nextLoc = streamScanner.next();
			GeoNameLoc location = parseGeonameLoc(nextLoc);
			if( location != null) {
				collection.insertLocation(location);
			} else {
				// if error while parsing exit from import file.
				break;
			}
		}
	}
	
	/**
	 * Parse a geoname location from a export file. Fields are tab delimited.
	 * 
	 * @param geoLoc
	 */
	public GeoNameLoc parseGeonameLoc(String geoLoc) {
		
		String[] values = geoLoc.split("\\t", -1); 
		GeoNameLoc result = null;
		
		try {
			GeoNameLoc loc = new GeoNameLoc();
			
			if( !values[0].isEmpty()) loc.setGeonameid( new Integer(values[0]).intValue());
			if( !values[1].isEmpty()) loc.setName( values[1]);
			if( !values[2].isEmpty()) loc.setAsciiname( values[2]);
			/**
			if( !values[3].isEmpty()){ loc.setAlternateNames(alternateNames)( values[0]));
				
			}
			**/
			if( !values[4].isEmpty() && !values[5].isEmpty()) {
				GeoNamePoint point = new GeoNamePoint();
				point.setCoordinates( new Double(values[4]), new Double(values[5]));
				loc.setLocation(point);
			}
			if( !values[6].isEmpty()) loc.setFeatureClass( values[6]);
			if( !values[7].isEmpty()) loc.setFeatureCode( values[7]);
			if( !values[8].isEmpty()) loc.setCountryCode( values[8]);
			
			if( !values[10].isEmpty()) loc.setAdmin1Code( values[10]);
			if( !values[11].isEmpty()) loc.setAdmin2Code( values[11]);
			if( !values[12].isEmpty()) loc.setAdmin3Code( values[12]);
			if( !values[13].isEmpty()) loc.setAdmin4Code( values[13]);
			if( !values[14].isEmpty()) loc.setPopulation( new Long(values[14]).longValue());
			if( !values[15].isEmpty()) loc.setElevation( new Integer(values[15]).intValue());
			
			if( !values[17].isEmpty()) loc.setTimeZone( values[17]);
			if( !values[18].isEmpty()) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				loc.setModificationDate( format.parse ( values[18]));
			}
			
			result = loc;
		} catch (Exception e) {
			
		}
		
		return result;
		
	}
}

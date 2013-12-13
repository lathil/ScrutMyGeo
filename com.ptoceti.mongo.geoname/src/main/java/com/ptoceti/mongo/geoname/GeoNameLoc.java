package com.ptoceti.mongo.geoname;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION : Ptoceti
 * PROJECT : com.ptoceti.mongo.geoname
 * FILENAME : GeoNameLoc.java
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
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

public class GeoNameLoc extends BasicDBObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5672381335384171573L;
	
	
	//integer id of record in geonames database
	public static final String GEONAMEID = "geonameid";
	//name of geographical point (utf8) varchar(200)
	public static final String NAME = "name";
	//name of geographical point in plain ascii characters
	public static final String ASCIINAME = "asciiname";
	// alternatenames,
	public static final String ALTERNATENAMES = "alternateNames";
	
	public static final String LOCATION = "location";
	
	//see http://www.geonames.org/export/codes.html, char(1)
	public static final String FEATURECLASS = "featureClass";
	//see http://www.geonames.org/export/codes.html, varchar(10)
	public static final String FEATURECODE = "featureCode";
	// ISO-3166 2-letter country code, 2 characters
	public static final String COUNTRYCODE = "countryCode";
	//alternate country codes, comma separated, ISO-3166 2-letter country code, 60 characters
	public static final String CC2 = "cc2";
	
	public static final String ADMIN1CODE = "admin1Code";
	//code for the second administrative division, a county in the US, see file admin2Codes.txt
	public static final String ADMIN2CODE = "admin2Code";
	//code for third level administrative division
	public static final String ADMIN3CODE = "admin3Code";
	//code for fourth level administrative division
	public static final String ADMIN4CODE = "admin4Code";
	
	public static final String POPULATION = "population";
	// in meters
	public static final String ELEVATION = "elevation";
	//digital elevation model, srtm3 or gtopo30, average elevation of 3''x3'' (ca 90mx90m) or 30''x30'' (ca 900mx900m) area in meters, integer. srtm processed by cgiar/ciat.
	public static final String DEM = "dem";
	//the timezone id (see file timeZone.txt)
	public static final String TIMEZONE = "timeZone";
	//date of last modification in yyyy-MM-dd format
	public static final String MODIFICATIONDATE = "modificationDate";
	
	
	public int getGeonameid() {
		return getInt(GEONAMEID);
	}
	public void setGeonameid(int geonameid) {
		put(GEONAMEID, geonameid);
	}
	public String getName() {
		return getString(NAME);
	}
	public void setName(String name) {
		put(NAME, name);
	}
	public String getAsciiname() {
		return getString(ASCIINAME);
	}
	public void setAsciiname(String asciiname) {
		put(ASCIINAME, asciiname);
	}
	public List<GeoNameAlternateName> getAlternateNames() {
		BasicDBList list = (BasicDBList) get(ALTERNATENAMES);
		List<GeoNameAlternateName> result = new ArrayList<GeoNameAlternateName>(list.size());
		for( Iterator< Object > it = list.iterator(); it.hasNext(); ){
			result.add((GeoNameAlternateName)it.next());
		}
		return result;
	}
	public void setAlternateNames(List<GeoNameAlternateName> alternateNames) {
		BasicDBList list = new BasicDBList();
		for( GeoNameAlternateName item : alternateNames){
			list.add(item);
		}
		put(CC2, list);
	}
	public GeoNamePoint getLocation() {
		return (GeoNamePoint)get(LOCATION);
	}
	public void setLocation(GeoNamePoint location) {
		put(LOCATION,  location);
	}
	public String getFeatureClass() {
		return getString(FEATURECLASS);
	}
	public void setFeatureClass(String featureClass) {
		put(FEATURECLASS, featureClass);
	}
	public String getFeatureCode() {
		return getString(FEATURECODE);
	}
	public void setFeatureCode(String featureCode) {
		put(FEATURECODE, featureCode);
	}
	public String getCountryCode() {
		return getString(COUNTRYCODE);
	}
	public void setCountryCode(String countryCode) {
		put(COUNTRYCODE, countryCode);
	}
	public List<String> getCc2() {
		BasicDBList list = (BasicDBList) get(CC2);
		List<String> result = new ArrayList<String>(list.size());
		for( Iterator< Object > it = list.iterator(); it.hasNext(); ){
			list.add(it.next());
		};
		
		return result;
	}
	public void setCc2(List<String> cc2) {
		
		BasicDBList list = new BasicDBList();
		for( String item : cc2){
			list.add(item);
		}
		put(CC2, list);
	}
	public String getAdmin1Code() {
		return getString(ADMIN1CODE);
	}
	public void setAdmin1Code(String admin1Code) {
		put(ADMIN1CODE, admin1Code);
	}
	public String getAdmin2Code() {
		return getString(ADMIN2CODE);
	}
	public void setAdmin2Code(String admin2Code) {
		put(ADMIN2CODE, admin2Code);
	}
	public String getAdmin3Code() {
		return getString(ADMIN3CODE);
	}
	public void setAdmin3Code(String admin3Code) {
		put(ADMIN3CODE, admin3Code);
	}
	public String getAdmin4Code() {
		return getString(ADMIN4CODE);
	}
	public void setAdmin4Code(String admin4Code) {
		put(ADMIN4CODE, admin4Code);
	}
	public long getPopulation() {
		return getLong(POPULATION);
	}
	public void setPopulation(long population) {
		put(POPULATION, population);
	}
	public int getElevation() {
		return getInt(ELEVATION);
	}
	public void setElevation(int elevation) {
		put(ELEVATION, elevation);
	}
	public String getDem() {
		return getString(DEM);
	}
	public void setDem(String dem) {
		put(DEM, dem);
	}
	public String getTimeZone() {
		return getString(TIMEZONE);
	}
	public void setTimeZone(String timeZone) {
		put(TIMEZONE, timeZone);
	}
	public Date getModificationDate() {
		return getDate(MODIFICATIONDATE);
	}
	public void setModificationDate(Date modificationDate) {
		put(MODIFICATIONDATE, modificationDate);
	}
	
}

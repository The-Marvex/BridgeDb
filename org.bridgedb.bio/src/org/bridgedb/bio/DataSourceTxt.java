// BridgeDb,
// An abstraction layer for identifier mapping services, both local and online.
// Copyright 2006-2009 BridgeDb developers
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//
package org.bridgedb.bio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Pattern;
import org.bridgedb.DataSource;
import org.bridgedb.DataSourcePatterns;

public class DataSourceTxt 
{

    /** Call this to initialize the DataSourcs from org/bridgedb/bio/datasources.txt
	 * 	You should call this before using any of these constants, 
	 * 	or they may be undefined.
	 */
	public static void init() 
	{
        try{
    		InputStream is = DataSourceTxt.class.getClassLoader().getResourceAsStream("org/bridgedb/bio/datasources.txt");	
            new DataSourceTxt().loadAnInputStream(is);
		}
		catch (IOException ex)
		{
			throw new Error(ex);
		}
	}
      
    public static void loadInputStream(InputStream is) throws IOException{
        new DataSourceTxt().loadAnInputStream(is);
    }

    protected void loadAnInputStream(InputStream is) throws IOException 
	{
		BufferedReader reader = new BufferedReader (
				new InputStreamReader (is));
		String line;
        while ((line = reader.readLine()) != null) {
            String[] fields = line.split ("\\t");
            loadLine(fields);
        }		
	}

	protected void loadLine(String[] fields) throws IOException 
	{
        DataSource.Builder builder = DataSource.register
            (fields[1], // system code 
            fields[0]); // gpml name
        if (fields.length > 2 && fields[2].length() > 0) {
            builder.mainUrl(fields[2]);
        }
        if (fields.length > 3 && fields[3].length() > 0) {
            builder.urlPattern(fields[3]);
        }
        if (fields.length > 4 && fields[4].length() > 0) {
            builder.idExample(fields[4]);
        }
        if (fields.length > 5 && fields[5].length() > 0) {
            builder.type(fields[5]);
        }
        if (fields.length > 6 && fields[6].length() > 0) {
            builder.organism(Organism.fromLatinName(fields[6]));
        }					      
        if (fields.length > 7 && fields[7].length() > 0) {
            builder.primary (fields[7].equals ("1"));
        }					      
        if (fields.length > 8) {
            builder.urnBase(fields[8]);
        }
        if (fields.length > 9) {
            String patternString = fields[9];
            try {
                Pattern pattern = Pattern.compile(patternString);
                DataSourcePatterns.registerPattern(builder.asDataSource(), pattern);
            } catch (Exception ex){
                throw new IllegalArgumentException("Unable to parse pattern " + patternString + " for " + builder.asDataSource(), ex);
            }    
        }
        if (fields.length > 10) builder.alternative(fields[10]);
    }		

}


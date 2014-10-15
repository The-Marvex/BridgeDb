// BridgeDb,
// An abstraction layer for identifier mapping services, both local and online.
//
// Copyright 2006-2009  BridgeDb developers
// Copyright 2012-2013  Christian Y. A. Brenninkmeijer
// Copyright 2012-2013  OpenPhacts
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
package org.bridgedb.uri.api;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 * Holder class for the main Meta Data of MappingSet.
 *
 * Does not include everything in the void header but only what is captured in the SQL.
 * @author Christian
 */
public class MappingsBySet {
    private final String lens;
    private final Set<SetMappings> setMappings;
    
    static final Logger logger = Logger.getLogger(MappingsBySet.class);
    
    /*
     * These are the direct mappings based on namespace substitution
     */
    private final Set<UriMapping> mappings;
    
    public MappingsBySet(String lens){
        this.lens = lens;
        this.setMappings = new HashSet<SetMappings>();
        this.mappings = new HashSet<UriMapping>();
    }
    
    public void addMappings (Set<String> mappingSetIds, String predicate, String justification, String mappingSource, 
            String mappingResource, String sourceUri, Set<String> targetUris){
        String mappingId = sortAndString(mappingSetIds);
        SetMappings setMapping = setMappingById(mappingId);
        if (setMapping == null){
            setMapping = new SetMappings(mappingId, predicate, justification, mappingSource, mappingResource);
            setMappings.add(setMapping);
        }
        for (String targetUri: targetUris){
            setMapping.addMapping(new UriMapping(sourceUri, targetUri));
        }
    }

    public void addSetMapping(SetMappings setMapping){
        setMappings.add(setMapping);
    }
    
    public void addMapping (Set<String> mappingSetIds, String predicate, String justification, String mappingSource, 
            String mappingResource, String sourceUri, String targetUri){
        String mappingSetId = sortAndString(mappingSetIds);
        SetMappings setMapping = setMappingById(mappingSetId);
        if (setMapping == null){
            setMapping = new SetMappings(mappingSetId, predicate, justification, mappingSource, mappingResource);
            getSetMappings().add(setMapping);
        }
        setMapping.addMapping(new UriMapping(sourceUri, targetUri));
    }
    
    public final void addMapping (String sourceUri, String targetUri){
        mappings.add(new UriMapping(sourceUri, targetUri));
    }
    
    public final void addMapping (UriMapping uriMapping){
        mappings.add(uriMapping);
    }
    
    public void addMappings (String sourceUri, Set<String> targetUris){
       for (String targetUri:targetUris){
           addMapping(sourceUri, targetUri);
       }
    }

    public void addMappings (MappingsBySet other){
        for (UriMapping uriMapping: other.getMappings()){
            addMapping(uriMapping);
        }
        for (SetMappings setMapping: other.getSetMappings()){
            addSetMapping(setMapping);
        }
    }
    
    private SetMappings setMappingById(String id) {
        for (SetMappings setMapping: getSetMappings()){
            if (setMapping.getId().equals(id)){
                return setMapping;
            }
        }
        return null;
    }
    
    public Set<String> getTargetUris(){
        HashSet<String> targetUris = new HashSet<String>();
        for (SetMappings setMapping: getSetMappings()){
            targetUris.addAll(setMapping.getTargetUris());           
        }
        for (UriMapping mapping:getMappings()){
            targetUris.add(mapping.getTargetUri());
        }

        return targetUris;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder("Lens: ");
        sb.append(getLens());
        for (SetMappings setMapping: getSetMappings()){
            setMapping.append(sb);           
        }
        sb.append("\n\tUriSpace based mappings");
        for (UriMapping mapping:getMappings()){
            mapping.append(sb);
        }
        return sb.toString();
    }

    /**
     * @return the lens
     */
    public String getLens() {
        return lens;
    }

    /**
     * @return the setMappings
     */
    public Set<SetMappings> getSetMappings() {
        return setMappings;
    }

    /**
     * @return the mappings
     */
    public Set<UriMapping> getMappings() {
        return mappings;
    }
    
    public boolean isEmpty(){
        return mappings.isEmpty() && setMappings.isEmpty();
    }

    private String sortAndString(Set<String> mappingSetIds) {
        if (mappingSetIds == null || mappingSetIds.isEmpty()){
            return "";
        }
        if (mappingSetIds.size() == 1){
            return mappingSetIds.iterator().next();
        }
        ArrayList<String> list = new ArrayList<String>(mappingSetIds);
        java.util.Collections.sort(list);
        StringBuilder sb = new StringBuilder(list.get(0));
        for (int i = 1; i < list.size(); i++){
            sb.append("_").append(list.get(i));
        }
        return sb.toString();
    }
}

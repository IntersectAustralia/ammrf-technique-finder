package org.ammrf.tf

import org.apache.commons.lang.WordUtils;
import org.hibernate.SessionFactory;
import groovy.util.Eval;

/**
 * Service to work with techniques
 * 
 * @author Andrey Chernyshov
 */
class TechniqueService {

    boolean transactional = true

    def optionsByType

    public TechniqueService() {
        optionsByType = [:]
        optionsByType['contacts'] = [
		domain: Contact.class,
		query:'%.techniqueContact = TRUE',
		fields:['name','location'],
		fieldPaths:['location':'location.institution','priority':'location.priority'],
		fieldsInSelect:['name', 'location'],
                fieldsInSort:['priority','name']
		]
        optionsByType['media'] = [
		domain: Media.class,
		query:null
		]
        optionsByType['caseStudies'] = [
		domain: CaseStudy.class,
		query:null,
		fields:['name','url'],
		fieldsInSelect:['name', 'url'],
		fieldsInSort:['name']
		]
        optionsByType['reviews'] = [
		domain: Review.class,
		query:null,
		fields:['referenceNames','title'],
		fieldsInSelect:['referenceNames', 'title'],
		fieldsInSort:['title','referenceNames']
		]
    }

    def getOptionsForAssociates() {
	return optionsByType
    }

    def getOptionsForAssociateType(String type) {
	return optionsByType[type]
    }
    
    /**
     * Checks options for correctness
     * 
     */
    private def checkOptions(Option left, Option right) {
    	if(left.type != OptionType.LEFT) {
    		throw new IllegalArgumentException(OptionType.LEFT.toString() + " option is requiered as first argument")
    	}
    	if(right.type != OptionType.RIGHT) {
    		throw new IllegalArgumentException(OptionType.RIGHT.toString() + " option is requiered as second argument")
    	}
    	if(left.science != right.science) {
    		throw new IllegalArgumentException("Options sceinces should be the same but " + left.science + " != " + right.science)
    	}
    }
    
    /**
     * Returns list of techniques that can be used to investigate provided options combination. 
     * 
     * @param left left column option
     * @param right right column option
     * @param fields names of the fields to return
     * @return list of techniques (map with id, name and summary) or empty list if no matching techniques were found 
     */
    def findTechniquesByOptionCombination(Option left, Option right, List<String> fields) {
    	checkOptions(left, right)
    	def fieldsQuery = ""
    	fields.eachWithIndex() {fieldName, i ->
    		if(i > 0) fieldsQuery += ", "
    		fieldsQuery += "opt.technique.$fieldName as $fieldName"
    	}
    	log.debug "FieldsQuery:$fieldsQuery"
    	
    	return Technique.executeQuery(
    			"select new map($fieldsQuery) \
    			 		from OptionCombination as opt \
    			 		where opt.left=:leftOption and opt.right=:rightOption order by opt.priority",
    			 		[leftOption:left, rightOption:right]
    			);
    }
	
	/**
	* Returns list of all techniques (map with only provided fields populated) ordered by name.
	*
	* @param fields names of the fields to return
	* @return list of techniques (map with id, name, summary, etc.) or empty list if no matching techniques were found
	*/
   def findAllTechniques(List<String> fields) {
	   def fieldsQuery = ""
	   fields.eachWithIndex() {fieldName, i ->
		   if(i > 0) fieldsQuery += ", "
		   fieldsQuery += "t.$fieldName as $fieldName"
	   }
	   log.debug "FieldsQuery:$fieldsQuery"
	   
	   return Technique.executeQuery("select new map($fieldsQuery) from Technique t order by t.name");
   }
    
    /**
     * Returns list of techniques that are linked to the provided object (Review, Contact, Location, CaseStudy) 
     * 
     * @param relationObject linked object
     * @return list of techniques or empty list if no matching techniques were found 
     */
    def findTechniquesByRelation(relationObject) {
    	switch(relationObject) {
    		case Contact:
    			return runQueryForRelation('contacts', relationObject)
    		case Review:
    			return runQueryForRelation('reviews', relationObject)
    		case CaseStudy:
    			return runQueryForRelation('caseStudies', relationObject)
    		case Location:
    			return Technique.executeQuery("select distinct t from Technique as t join t.contacts as c where c.location = :location", [location:relationObject])
			case Option:
				return OptionCombination.executeQuery("select distinct o.technique from OptionCombination as o where o.left = :option or o.right = :option", [option:relationObject])
    		default:
    			throw new IllegalArgumentException("Unsupported relation object type: " + relationObject?.class?.name)
    	}
    }
    
    private def runQueryForRelation(collectionName, relationObject) {
    	return Technique.findAll("from Technique as t where :relationObject member of t.$collectionName", [relationObject:relationObject])
    }
    
    /**
     * Returns list of techniques that are not from the provided list of Ids. 
     * 
     * @param techniqueIds list of technique Ids to exclude
     * @return list of techniques (map with id and name) or empty list if no matching techniques were found 
     */
    def getTechniquesNotInList(List<Long> techniqueIds) {
    	if(techniqueIds != null && techniqueIds.size() > 0) {
	    	return Technique.executeQuery(
	    			"select new map(t.id as id, t.name as name) \
	    			 		from Technique as t \
	    			 		where t.id not in (:techniqueIds) \
	    			 		order by t.name",
	    			 		[techniqueIds:techniqueIds]
	    	);
    	} else {
    		return Technique.executeQuery(
	    			"select new map(t.id as id, t.name as name) \
	    			 		from Technique as t \
	    			 		order by t.name"); 
    	}
    }
    
    /**
     * Linkes techniques to the provided options combination.
     * Creates new links, updates priorities of existing, deletes removed associations. 
     * 
     * @param techniqueIds list of technique Ids to add
     * @param left left column option
     * @param right right column option
     */
    def updateTechniquesAssociationWithOptionCombination(List<Long> techniqueIds, Option left, Option right) {
    	checkOptions(left, right)

    	def currentCombinations = OptionCombination.findAllWhere(left:left, right:right)
    	
    	def idList = [] as Set
    	def optionCombinationStayList = [:] as Map
    	
    	currentCombinations.each {
    		if (techniqueIds == null || !techniqueIds.contains(it.technique.id)) {
				idList += it.technique.id
				def technique = it.technique
				technique.removeFromOptions(it)
				it.delete(flush:true)
    		} else {
    			optionCombinationStayList[it.technique.id] = it
    		}
    	}
    	
    	// add new associations, update existing
    	if(techniqueIds != null && techniqueIds.size() > 0) {
	    	techniqueIds.eachWithIndex() { id, i ->
	    	    def optionCombination = optionCombinationStayList[id]
	    		if (!optionCombination) {
	    			idList += id
	    			def technique = Technique.get(id)
	    			OptionCombination combination= new OptionCombination(left:left, right:right, technique:technique, priority:++i)
	    			technique.addToOptions(combination).save(flush:true)
	    		} else {
	    			optionCombination.priority = ++i
	    			optionCombination.save(flush:true)
	    		}
	    	}
    	}

    	idList.each {
    		Technique.reindex(it)
    	}
    	
    }

    /**
    * Returns the list of Media for a technique and a given section, order by priority.
    * @param technique
    * @param mediaSection
    * @returns list of media [List<Media>]
    */
    def getMediaInTechniqueSection(Technique technique, MediaSection mediaSection) {
        return MediaInSection.executeQuery("select t.media from MediaInSection t where t.technique.id = :id and t.section = :section order by t.priority",
            [id:technique.id, section:mediaSection])
    }

    /**
    * Given a mediaMap -Map<MediaSection,List<Long>>-, i.e. a map from MediaSection into list of Ids, produces a similar map but with the corresponding media for the Id.
    * It needs to keep the same ordering.
    * @param mediaMap [Map<MediaSection,List<Long>>]
    * @returns remapped Ids as mentioned above [Map<MediaSection,List<Media>>]
    */
    def getMediaFromMediaMap(mediaMap) {
        def resp = [:]
        MediaSection.values().each { section ->
            resp[section] = []
            mediaMap[section].each { id ->
                resp[section] += Media.get(id)
            }
        }
        return resp
    }

    /** 
    * Returns the list of Media not appearing in the list. If list is empty, returns all media.
    * @param mediaIds List<Long>
    * @returns list of media [List<Media>]
    */
    def getMediaNotInTechniqueSection(List<Long> ids) {
        return getObjectsNotInTechnique(Media.class, ids, null)
    }

    /**
    * Returns list of potential associates excluding those in ids
    */
    def getAssociatesNotInTechnique(String type, List<Long> ids) {
	def options = optionsByType[type]
        return getObjectsNotInTechnique(options.domain, ids, options.query )
    }

    // Get object from domain not present in id ordered by orderFields. The orderBy is a list of field references.
    // use % to represent the current domain object.
    private def getObjectsNotInTechnique(domain, List<Long> ids, String query) {
        def domainName = domain.getSimpleName()
        if (ids == null || ids.size() == 0) {
            def queryOpt = query == null || query.length() == 0 ? '' : 'where ' + query.replace('%', 'o')
            return domain.executeQuery("select o from ${domainName} o ${queryOpt}").sort()
        } else {
            def queryOpt = query == null || query.length() == 0 ? '' : 'and ' + query.replace('%', 'o')
            return domain.executeQuery("select o from ${domainName} o where o.id not in (:ids) ${queryOpt}", [ids:ids]).sort()
        }
    }

    /**
    * Persists the technique to the database and updates the associated images
    * @param technique
    * @param mediaMap Map<MediaSection,List<Long>>, gives the newly associated Ids for each section in the technique
    * @returns true if updates succedes. Errors due to media that disappeared (due to concurrent updates) are silently ignored.
    */
    def saveTechnique(Technique technique, Map mediaMap) {
        if (!technique.save()) {
            return false;
        }
        if (technique.id) {
            MediaInSection.executeUpdate("delete MediaInSection mis where mis.technique.id = :id",
                [id:technique.id])
        }
        def misItems = []
        mediaMap.each { mediaSection, mediaIds ->
		mediaIds.eachWithIndex() { mid, pos ->
		    def media = Media.get(mid)
		    if (media != null) {
		        misItems += new MediaInSection(technique:technique, media:media, section:mediaSection, priority:pos + 1)
		    }
		}
        }
        misItems.each { it.save() }
        return true
    }

    /**
    * Given a list of techniques (List<Technique>) returns a parallel list of List<Media> where the media is the one associated for the LIST section for
    * the techniques.
    * @param techniques (List<Technique>)
    * @return parallel list to techniques with media, i.e. result.get(i) is associated with techniques.get(i) for MediaSection.LIST; null if such media doesn't exist.
    */
    def attachMedia(techniques) {
	def media = []
	techniques.eachWithIndex() { technique, index ->
            def mediaInSection = MediaInSection.findAll("from MediaInSection mis where mis.technique.id = :techId and mis.section = :section",
                    [techId:technique.id, section:MediaSection.LIST])
            if (mediaInSection != null && mediaInSection.size() > 0) {
                media.add(index, mediaInSection.get(0).media)
            } else {
                media.add(index, null)
            }
        }
	return media
    }

}

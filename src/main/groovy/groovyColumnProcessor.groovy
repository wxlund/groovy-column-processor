package main.groovy

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
/**
 * Created by wlund on 9/12/16.
 */

static String toCamelCase( String text, boolean capitalized = false ) {
    text = text.replaceAll( "(_)([A-Za-z0-9])", { Object[] it -> it[2].toUpperCase() } )
    return capitalized ? capitalize(text) : text
}

// def payload = "{member_id=4, region_code=NCAL, guid=null, uid=null, mrn=78472174, first_name=ALEXA, last_name=PARKER, middle_name=E, display_name=PARKER,ALEXA                  , gender=F, birth_date=1977-08-30 00:00:00.0, death_flag=null, death_date=null, email_address=prompttest102@yahoo.com, zip_code=11195     , invalid_mrn_code=null, mrn_replaced_by=null, last_rev_datetime=2014-09-13 00:00:00.0, last_verify_date=2014-09-13 10:45:35.35, update_datetime=2015-09-03 15:59:24.83, update_flag=null, suffix=null, blind_flag=null, deaf_flag=null, mute_flag=null, kpmcp_emp_flag=null, intrptr_req_code=null, mrn_prefix=11}"
def payload = '{"member_id":"4" }' // , "region_code":"NCAL", "guid":"null", "uid":"null", "mrn":"78472174", "first_name":"ALEXA", "last_name":"PARKER", "middle_name":"E", "display_name":"PARKER,ALEXA", "gender":"F", "birth_date":"1977-08-30 00:00:00.0", "death_flag":"null", "death_date":"null", "email_address":"prompttest102@yahoo.com", "zip_code":"11195     ", "invalid_mrn_code":"null", "mrn_replaced_by":"null", "last_rev_datetime":"2014-09-13 00:00:00.0", "last_verify_date":"2014-09-13 10:45:35.35", "update_datetime":"2015-09-03 15:59:24.83", "update_flag":"null", "suffix":"null", "blind_flag":"null", "deaf_flag":"null", "mute_flag":"null", "kpmcp_emp_flag":"null", "intrptr_req_code":"null", "mrn_prefix":"11"}'

// def s = new JsonBuilder(payload).toString()
def columns = new JsonSlurper().parseText(payload)

// assert columns instanceof Map
// println columns.getClass()

assert columns instanceof Map
assert columns.member_id == '4'
println columns.getClass()

List javaizedFieldNames = (List) new ArrayList();
java.util.Hashtable dict = new java.util.Hashtable();

for(Object m : columns ) {
    String column = m.toString();
    println "column: $column"
    String[] keyAndValue = column.split("=")
    println keyAndValue

    if (keyAndValue[0] == keyAndValue[0].toLowerCase()) {
        def camelCaseColumn = toCamelCase(keyAndValue[0])
        println camelCaseColumn

    } else {

    String edgeColumnNames = keyAndValue[0].split();

    for (int i = 0; i < words.length; i++) {
        if (dict.containsKey(words[i])) {
            // special processing for Acronyms used by TPMG
        } else {
            if (i == 0) {
                fieldName = words[0]
                fieldName = fieldName.toLowerCase()
            } else {
                fieldName = words[i]
                fieldName = toCamelCase(fieldName, false)
                println(fieldName)
                javaizedFieldNames.add();

            }

        }
    }

    return javaizedFieldNames;

}




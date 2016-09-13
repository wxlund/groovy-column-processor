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
def payload = '{"MEMBER_ID":"4", "region_code":"NCAL", "guid":"null", "uid":"null", "mrn":"78472174", "first_name":"ALEXA", "last_name":"PARKER", "middle_name":"E", "display_name":"PARKER,ALEXA", "gender":"F", "birth_date":"1977-08-30 00:00:00.0", "death_flag":"null", "death_date":"null", "email_address":"prompttest102@yahoo.com", "zip_code":"11195     ", "invalid_mrn_code":"null", "mrn_replaced_by":"null", "last_rev_datetime":"2014-09-13 00:00:00.0", "last_verify_date":"2014-09-13 10:45:35.35", "update_datetime":"2015-09-03 15:59:24.83", "update_flag":"null", "suffix":"null", "blind_flag":"null", "deaf_flag":"null", "mute_flag":"null", "kpmcp_emp_flag":"null", "intrptr_req_code":"null", "mrn_prefix":"11"}'

// def s = new JsonBuilder(payload).toString()
def columns = new JsonSlurper().parseText(payload)
println columns

def javaBeanColumnNames = [:]
List javaBeanColumnNameParts = []
java.util.Hashtable dict = new java.util.Hashtable();

for(Object m : columns ) {
    String column = m.toString();
    println "column: $column"
    def columnName = ""
    def javaBeanColumnName

    String[] keyAndValue = column.split("=")
    println keyAndValue

    if (keyAndValue[0] == keyAndValue[0].toLowerCase()) {
        def camelCaseColumn = toCamelCase(keyAndValue[0])
        javaBeanColumnNames.put(camelCaseColumn, keyAndValue[1]);
        println camelCaseColumn

    } else {
        // take care of special case where first word is in the dictionary
        // assumed if we passed the toLowerCase() test above.

        String[] ruleDrivenColumnNames = keyAndValue[0].split('_');
        println "ruleDrivenColumnNames: $ruleDrivenColumnNames"
        def columnNamePart = ""
        for (int i = 0; i < ruleDrivenColumnNames.length; i++) {
            if (dict.containsKey(ruleDrivenColumnNames[i])) {
                // special processing for Acronyms used by TPMG
                javaBeanColumnNameParts.add(ruleDrivenColumnNames[i]);
            } else {
                //  the first word may have been all caps but not in the dictionary. Just convert to lowercase.
                if (i == 0) {
                    columnNamePart = ruleDrivenColumnNames[0].toLowerCase()
                    println "columnNamePart @ 0: $columnNamePart"
                    javaBeanColumnNameParts.add(columnNamePart.toLowerCase());

                } else {
                    columnNamePart = ruleDrivenColumnNames[i]
                    javaBeanColumnName = columnNamePart.toLowerCase().capitalize()
                    println("$javaBeanColumnName: $javaBeanColumnName")
                    javaBeanColumnNameParts.add(javaBeanColumnName);
                }


            }
        }
        println javaBeanColumnNameParts.join()
        javaBeanColumnNames.put(javaBeanColumnNameParts.join(), keyAndValue[1]);

    }

}

println javaBeanColumnNames
return javaBeanColumnNames;




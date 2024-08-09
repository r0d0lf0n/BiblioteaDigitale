/**
 * 
 */
/**
 * 
 */
module Biblio {
	requires java.desktop;
	requires com.opencsv;
	requires java.sql;
	requires ormlite.jdbc;
	requires java.xml;
	requires jdk.javadoc;
	requires jdk.jpackage;
	requires jdatepicker;
	opens models.db to ormlite.jdbc;
}
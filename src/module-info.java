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
	opens models.db to ormlite.jdbc;
}
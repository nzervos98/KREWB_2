package jena.examples.rdf ;

import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.*;
import org.apache.jena.query.*;
import org.apache.jena.util.FileManager;
import org.apache.jena.riot.*;

import java.util.Scanner;
import java.io.*;



public class loadrdf {

    //XWRIS TO ABSOLUTE DIRECTORY TOU ARXEIOU DEN TREXEI TA SPARQL QUERIES
    static final String inputfile  = "C:\\Users\\nikos\\Desktop\\NetBeansProjects\\JenaTest\\src\\rdf\\schematest.rdf";
    
    //PREFIX TOU SCHEMA KAI TWN RDF RDFS XMLSCHEMA
    static final String strURI  = "http://www.uni/";
    static final String strRDF  = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    static final String strRDFS = "http://www.w3.org/2000/01/rdf-schema#";
    static final String strXSD  = "http://www.w3.org/2001/XMLSchema#";
    
                              
    public static void main (String args[]) {
        
        // create an empty model
        Model model = ModelFactory.createDefaultModel();
        
        // eisodos arxeiou schema.rdf
        InputStream inrdf = FileManager.get().open( inputfile );
        if (inrdf == null) {
            throw new IllegalArgumentException(inputfile + " not found!");
        }
        
        // read the RDF file and store to myrdf
        model.read(inrdf, null);
        
        // write it to standard out
        //model.write(System.out);
        
        
        //endless loop gia na trexei to programma
        int truCase = 1;
        while(truCase==1){
            
            System.out.println("\nChoose:\n1)View Departments\n2)Add Resources to RDF\n3)View triplets via URI\n4)Exit");
            Scanner epilogh = new Scanner(System.in);
            String choice = epilogh.nextLine();
        
        
            switch(Integer.parseInt(choice)){

                /*------------------------ ERWTHMA A ------------------------*/
                case 1:
                    //SPARQL query gia na emfanisei ta tmhmata(onomata) gia na diale3ei o xrhsths
                    String queryStrA =      "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                                            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                                            "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" +
                                            "PREFIX base: <http://www.uni/>" +
                                            "SELECT ?TMHMA " +
                                            "WHERE {" +
                                            "       ?x base:dep_name ?TMHMA" +
                                            "      }" +
                                            "ORDER BY ?x";

                    //copy pasta gia ka8e query pou einai na tre3ei kai na ginei print !!!ALLAGH TA VARIABLES
                    Query query = QueryFactory.create(queryStrA);
                    QueryExecution qe = QueryExecutionFactory.create(query, model);

                    //copy pasta gia ta results !!!ALLAGH TA VARIABLES
                    ResultSet results = (ResultSet) qe.execSelect();
                    ResultSetFormatter.out(System.out, (org.apache.jena.query.ResultSet) results, query);

                    qe.close();


                    //Dinoume to department gia to erwthma A
                    System.out.println("Please provide a department of the above: ");
                    Scanner scn = new Scanner(System.in);
                    String inDep = scn.nextLine();


                    //Tsekarei apo tis tripletes poia exoun object iso me to onoma tou department pou 8eloume
                    StmtIterator iter1 = model.listStatements();
                    while(iter1.hasNext()){
                        Statement stmt = iter1.nextStatement();
                        RDFNode obj = stmt.getObject();

                        if (obj.toString().equals(inDep)) {
                            //print tous 4 pinakes pou zhtountai(Professors, Students, Classrooms, Lessons gia to Department epiloghs)

                                //PROFESSORS
                            System.out.println("Professors:");
                            String queryProf =      "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                                                    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                                                    "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" +
                                                    "PREFIX base: <http://www.uni/>" +
                                                    "SELECT ?onomatepwnumo ?hlikia ?thlefwno " +
                                                    "WHERE {" +
                                                    "       ?x base:member_of ?y" +
                                                    ".      ?y base:dep_name ?z" +
                                                    ".      FILTER(?z = \'" + inDep + "\')" +
                                                    ".      ?x rdf:type base:Professor" +
                                                    ".      ?x base:has_name ?onomatepwnumo" +
                                                    ".      ?x base:has_age ?hlikia" +
                                                    ".      ?x base:has_phone ?thlefwno" +
                                                    "      }" +
                                                    "ORDER BY ?x";

                            //copy pasta gia ka8e query pou einai na tre3ei kai na ginei print !!!ALLAGH TA VARIABLES
                            Query query1 = QueryFactory.create(queryProf);
                            QueryExecution qe1 = QueryExecutionFactory.create(query1, model);

                            //copy pasta gia ta results !!!ALLAGH TA VARIABLES
                            ResultSet results1 = (ResultSet) qe1.execSelect();
                            ResultSetFormatter.out(System.out, (org.apache.jena.query.ResultSet) results1, query1);

                            qe1.close();

                                //STUDENTS
                            System.out.println("Students:");
                            String queryStud =       "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                                                    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                                                    "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" +
                                                    "PREFIX base: <http://www.uni/>" +
                                                    "SELECT ?onomatepwnumo ?hlikia ?thlefwno " +
                                                    "WHERE {" +
                                                    "       ?x base:member_of ?y" +
                                                    ".      ?y base:dep_name ?z" +
                                                    ".      FILTER(?z = \'" + inDep + "\')" +
                                                    ".      ?x rdf:type base:Student" +
                                                    ".      ?x base:has_name ?onomatepwnumo" +
                                                    ".      ?x base:has_age ?hlikia" +
                                                    ".      ?x base:has_phone ?thlefwno" +
                                                    "      }" +
                                                    "ORDER BY ?x";

                            //copy pasta gia ka8e query pou einai na tre3ei kai na ginei print !!!ALLAGH TA VARIABLES
                            Query query2 = QueryFactory.create(queryStud);
                            QueryExecution qe2 = QueryExecutionFactory.create(query2, model);

                            //copy pasta gia ta results !!!ALLAGH TA VARIABLES
                            ResultSet results2 = (ResultSet) qe2.execSelect();
                            ResultSetFormatter.out(System.out, (org.apache.jena.query.ResultSet) results2, query2);

                            qe2.close();

                                //CLASSROOMS
                            System.out.println("Classrooms:");
                            String queryRoom =      "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                                                    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                                                    "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" +
                                                    "PREFIX base: <http://www.uni/>" +
                                                    "SELECT ?aithousa ?xwrhtikothta " +
                                                    "WHERE {" +
                                                    "       ?x base:room_department ?y" +
                                                    ".      ?y base:dep_name ?z" +
                                                    ".      FILTER(?z = \'" + inDep + "\')" +
                                                    ".      ?x base:room_name ?aithousa" +
                                                    ".      ?x base:room_capacity ?xwrhtikothta" +
                                                    "      }" +
                                                    "ORDER BY ?x";

                            //copy pasta gia ka8e query pou einai na tre3ei kai na ginei print !!!ALLAGH TA VARIABLES
                            Query query3 = QueryFactory.create(queryRoom);
                            QueryExecution qe3 = QueryExecutionFactory.create(query3, model);

                            //copy pasta gia ta results !!!ALLAGH TA VARIABLES
                            ResultSet results3 = (ResultSet) qe3.execSelect();
                            ResultSetFormatter.out(System.out, (org.apache.jena.query.ResultSet) results3, query3);

                            qe3.close();


                                //LESSONS
                            System.out.println("Lessons:");
                            String queryLesson =    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                                                    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                                                    "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" +
                                                    "PREFIX base: <http://www.uni/>" +
                                                    "SELECT ?onoma ?didaskwn " +
                                                    "WHERE {" +
                                                    "       ?x base:les_name ?onoma" +
                                                    ".      ?x base:taught_by ?z" +
                                                    ".      ?z base:has_name ?didaskwn" +
                                                    ".      ?z base:member_of ?a" +
                                                    ".      ?a base:dep_name ?b" +
                                                    ".      FILTER(?b = \'" + inDep + "\')" +

                                                    "      }" +
                                                    "ORDER BY ?x";

                            //copy pasta gia ka8e query pou einai na tre3ei kai na ginei print !!!ALLAGH TA VARIABLES
                            Query query4 = QueryFactory.create(queryLesson);
                            QueryExecution qe4 = QueryExecutionFactory.create(query4, model);

                            //copy pasta gia ta results !!!ALLAGH TA VARIABLES
                            ResultSet results4 = (ResultSet) qe4.execSelect();
                            ResultSetFormatter.out(System.out, (org.apache.jena.query.ResultSet) results4, query4);

                            qe4.close();

                        }
                    }
                    break;

                /*------------------------ ERWTHMA B ------------------------*/
                case 2:
                    
                    System.out.println("Instance: (Professor/Student/Department/Lesson/Classroom)");
                    Scanner instnc = new Scanner(System.in);
                    String strInstnc = instnc.nextLine();
                    
                    switch(strInstnc){
                        
                        //DHMIOURGIA PROFESSOR
                        case "Professor":
                            System.out.println("Give professor ID:");
                            String profid = instnc.nextLine();
                            Resource prof = model.createResource(strURI + profid, model.createResource(strURI + "Professor"));
                            
                            Property profname = model.createProperty(strURI, "has_name");
                            Property profphone = model.createProperty(strURI, "has_phone");
                            Property profemail = model.createProperty(strURI, "has_email");
                            Property profage = model.createProperty(strURI, "has_age");
                            Property profmember = model.createProperty(strURI, "member_of");
                            Property profteaches = model.createProperty(strURI, "teaches");
                            
                            System.out.println("Give professor name:");
                            String name = instnc.nextLine();
                            prof.addProperty(profname,name);
                            
                            System.out.println("Give professor phone:");
                            String phone = instnc.nextLine();
                            prof.addProperty(profphone,phone);
                            
                            System.out.println("Give professor mail:");
                            String mail = instnc.nextLine();
                            prof.addProperty(profemail, mail);
                            
                            System.out.println("Give professor age:");
                            String age = instnc.nextLine();
                            prof.addProperty(profage, age);
                            
                            System.out.println("Give professor's department:");
                            String dprtmnt = instnc.nextLine();
                            prof.addProperty(profmember, model.createResource(strURI + dprtmnt));
                            
                            System.out.println("Give professor's lesson:");
                            String teach = instnc.nextLine();
                            prof.addProperty(profteaches, model.createResource(strURI + teach));
                            
                            //grafoume me thn parakatw entolh sto input arxeio mas kai meta sthn konsola
                            FileWriter exportfile1;
                            try{
                                exportfile1 =new FileWriter(inputfile);
                                RDFDataMgr.write(exportfile1, model, RDFFormat.RDFXML_PLAIN);
                            }catch(IOException e){
                                System.out.println("Error during file write!");
                            }
                            RDFDataMgr.write(System.out, model, RDFFormat.RDFXML_PLAIN);
                            break;
                        
                        //DHMIOURGIA STUDENT
                        case "Student":
                            System.out.println("Give student ID:");
                            String studid = instnc.nextLine();
                            Resource stud = model.createResource(strURI + studid, model.createResource(strURI + "Student"));
                            
                            
                            Property studname = model.createProperty(strURI, "has_name");
                            Property studphone = model.createProperty(strURI, "has_phone");
                            Property studemail = model.createProperty(strURI, "has_email");
                            Property studage = model.createProperty(strURI, "has_age");
                            Property studmember = model.createProperty(strURI, "member_of");
                            
                            System.out.println("Give student name:");
                            String sname = instnc.nextLine();
                            stud.addProperty(studname,sname);
                            
                            System.out.println("Give student phone:");
                            String sphone = instnc.nextLine();
                            stud.addProperty(studphone,sphone);
                            
                            System.out.println("Give student mail:");
                            String smail = instnc.nextLine();
                            stud.addProperty(studemail, smail);
                            
                            System.out.println("Give student age:");
                            String sage = instnc.nextLine();
                            stud.addProperty(studage, sage);
                            
                            System.out.println("Give student's department:");
                            String sdprtmnt = instnc.nextLine();
                            stud.addProperty(studmember, model.createResource(strURI + sdprtmnt));
                            
                            //grafoume me thn parakatw entolh sto input arxeio mas kai meta sthn konsola
                            FileWriter exportfile2;
                            try{
                                exportfile2 =new FileWriter(inputfile);
                                RDFDataMgr.write(exportfile2, model, RDFFormat.RDFXML_PLAIN);
                            }catch(IOException e){
                                System.out.println("Error during file write!");
                            }
                            RDFDataMgr.write(System.out, model, RDFFormat.RDFXML_PLAIN);
                            break;
                        
                        //DHMIOURGIA DEPARTMENT
                        case "Department":
                            System.out.println("Give department ID:");
                            String depid = instnc.nextLine();
                            Resource dep = model.createResource(strURI + depid, model.createResource(strURI + "Department"));
                            
                            
                            Property depname = model.createProperty(strURI, "dep_name");
                            Property depcity = model.createProperty(strURI, "dep_city");
                            
                            System.out.println("Give department name:");
                            String dname = instnc.nextLine();
                            dep.addProperty(depname,dname);
                            
                            System.out.println("Give department city:");
                            String dcity = instnc.nextLine();
                            dep.addProperty(depcity,dcity);
                            
                            //grafoume me thn parakatw entolh sto input arxeio mas kai meta sthn konsola
                            FileWriter exportfile3;
                            try{
                                exportfile3 =new FileWriter(inputfile);
                                RDFDataMgr.write(exportfile3, model, RDFFormat.RDFXML_PLAIN);
                            }catch(IOException e){
                                System.out.println("Error during file write!");
                            }
                            RDFDataMgr.write(System.out, model, RDFFormat.RDFXML_PLAIN);
                            break;
                            
                        //DHMIOURGIA LESSON
                        case "Lesson":
                            System.out.println("Give lesson ID:");
                            String lesid = instnc.nextLine();
                            Resource les = model.createResource(strURI + lesid);
                            
                            Property lesname = model.createProperty(strURI, "les_name");
                            Property lestaught = model.createProperty(strURI, "taught_by");
                            
                            System.out.println("Give lesson name:");
                            String lname = instnc.nextLine();
                            les.addProperty(lesname,lname);
                            
                            System.out.println("Give lesson's professor:");
                            String ltaught = instnc.nextLine();
                            les.addProperty(lestaught, model.createResource(strURI + ltaught));
                            
                            //grafoume me thn parakatw entolh sto input arxeio mas kai meta sthn konsola
                            FileWriter exportfile4;
                            try{
                                exportfile4 =new FileWriter(inputfile);
                                RDFDataMgr.write(exportfile4, model, RDFFormat.RDFXML_PLAIN);
                            }catch(IOException e){
                                System.out.println("Error during file write!");
                            }
                            RDFDataMgr.write(System.out, model, RDFFormat.RDFXML_PLAIN);
                            break;
                            
                        //DHMIOURGIA CLASSROOM
                        case "Classroom":
                            System.out.println("Give classroom ID:");
                            String classid = instnc.nextLine();
                            Resource clss = model.createResource(strURI + classid, model.createResource(strURI + "Classroom"));
                            
                            
                            Property classname = model.createProperty(strURI, "room_name");
                            Property classcap = model.createProperty(strURI, "room_capacity");
                            Property classdep = model.createProperty(strURI, "room_department");
                            
                            System.out.println("Give classroom name:");
                            String cname = instnc.nextLine();
                            clss.addProperty(classname,cname);
                            
                            System.out.println("Give classroom capacity:");
                            String ccap = instnc.nextLine();
                            clss.addProperty(classcap,ccap);
                            
                            System.out.println("Give classroom's department:");
                            String cdep = instnc.nextLine();
                            clss.addProperty(classdep, model.createResource(strURI + cdep));
                            
                            //grafoume me thn parakatw entolh sto input arxeio mas kai meta sthn konsola
                            FileWriter exportfile5;
                            try{
                                exportfile5 =new FileWriter(inputfile);
                                RDFDataMgr.write(exportfile5, model, RDFFormat.RDFXML_PLAIN);
                            }catch(IOException e){
                                System.out.println("Error during file write!");
                            }
                            RDFDataMgr.write(System.out, model, RDFFormat.RDFXML_PLAIN);
                            break;
                            
                        //AKYRO APO TA PARAPANW INPUT
                        default:
                            System.out.println("Invalid instance!");
                    }
                    break;

                /*------------------------ ERWTHMA C ------------------------*/
                case 3:

                    //scannaroume thn eisodo kai kollame to prefix uri mas
                    System.out.println("\nGive a resource URI:");
                    Scanner rsrc = new Scanner(System.in);
                    String inRsrc = strURI + rsrc.nextLine();

                    //Gia na paroume oles tis tripletes kai sugkrinoume me to resource input tou xrhsth.
                    StmtIterator iter2 = model.listStatements();
                    while(iter2.hasNext()){
                        Statement stmt = iter2.nextStatement();
                        Resource subj1 = stmt.getSubject();
                        Property pred1 = stmt.getPredicate();
                        RDFNode obj1 = stmt.getObject();

                        if(subj1.toString().equals(inRsrc)){
                            System.out.print(subj1.toString());
                            System.out.print(" " + pred1.toString() + " ");
                            if (obj1 instanceof Resource){
                                System.out.print(obj1.toString());
                            }else{
                                //Literal object
                                System.out.print(" \"" + obj1.toString() + "\"");
                            }
                            System.out.println(" .");
                        }
                    }
                    break;

                
                case 4:
                    truCase = 0;
                    break;
                    
                
                default:
                    System.out.println("Invalid input!");
            }
        }
    }
}
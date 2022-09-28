package hr.fer.oprpp1.hw04.db;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class StudentDB {
	public static void main(String[] args) {
		StudentDatabase db=null;
		try {
			List<String> list= Files.readAllLines(Path.of("C:\\Eclipse Radne Povrsine\\hw04-0036524183\\hw04-0036524183\\database.txt"));
			db= new StudentDatabase(list);	
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Write the query for database:");
		System.out.print(">");
		
		Scanner scanner= new Scanner(System.in);
		
			
		String query= scanner.nextLine();
		boolean first=true;
		
		while(!query.equals("exit")) {
			try {
				first=true;
				QueryParser parser= new QueryParser(query);
				int recordsSelected=0;
				
				List<StudentRecord> list=db.filter(new QueryFilter(parser.getQuery()));
				
				RecordParameters parameters= new RecordParameters(list);
				
				
				for(StudentRecord r: db.filter(new QueryFilter(parser.getQuery()))) {
					recordsSelected++;
					if(first) {
						first=false;
						linija(parameters.getJmbagLength(), parameters.getLastNameLength(), parameters.getFirstNameLength());
					}
					
					System.out.print("| "+ r.getJmbag());
					for(int i=0; i<(parameters.getJmbagLength()-r.getJmbag().length())+1;i++)
						System.out.print(" ");
					
					System.out.print("| "+ r.getLastName());
					for(int i=0; i<(parameters.getLastNameLength()-r.getLastName().length())+1;i++) {
						System.out.print(" ");
					}
					System.out.print("| "+ r.getFirstName());
					for(int i=0; i<(parameters.getFirstNameLength()-r.getFirstName().length())+1;i++)
						System.out.print(" ");
					
					System.out.println("| "+ r.getFinalGrade()+" |");
					
					linija(parameters.getJmbagLength(), parameters.getLastNameLength(), parameters.getFirstNameLength());
				}
				System.out.println("Records selected:"+ recordsSelected);
			}catch(NullPointerException e) {
				System.out.println("Records selected:0");
			}catch(IllegalArgumentException e) {
				System.out.println("Query format is wrong. Try again!");
			}
			
			System.out.print(">");
			query=scanner.nextLine();
		}
		System.out.println("Goodbye!");
	}	
	
	public static void linija(int jL, int lL, int fL) {
		System.out.print("+");
		for(int i=0;i<jL+2;i++)
			System.out.print("=");
		System.out.print("+");
		for(int i=0;i<lL+2;i++)
			System.out.print("=");
		System.out.print("+");
		for(int i=0;i<fL+2;i++)
			System.out.print("=");
		System.out.println("+===+");
	}
}


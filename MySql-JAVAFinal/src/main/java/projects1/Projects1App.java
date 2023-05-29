package projects1;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import projects1.service.Projects1Service;
import java.util.Scanner;
import projects1.entity.Project;
import projects1.exception.DbException;
import projects1.dao.DbConnection;

public class Projects1App {
	  private Scanner scanner = new Scanner(System.in);
	  private Projects1Service projects1Service = new Projects1Service();
	  private Project curProject;	
	//Used Scanner to obtain user input. 
	//input source System.in, 
	//Used Scanner to read from the console.
	//User types in selections and the Scanner 
	//reads the input and gives it to the application.

	//Added a private instance variable scanner. type java.util.Scanner. 
    //initialized to a new Scanner object. System.in to the constructor. 
	//scanner accepts user input from the Java console.

        //@formatter:off
		
	private List<String> operations = List.of(
				"1) Add a project",
				"2) List projects",
				"3) Select a project",
				"4) Update project details", //.....added UPDATE of CRUD to list of operations
				"5) Delete project" //..............added delete a project to list
			);
		
		// @formatter:on
	
//entry point for java application
	public static void main(String[] args) {
	    DbConnection.getConnection(); //test line

		new Projects1App().processUserSelections(); 
	}//......................... main ........................................................................
	

			private void processUserSelections() {
                  boolean done = false;           //added local variable
			
     while(!done) {
    	  //while loop until done is true  added try/catch to catch exception
      
	           //Concatenating the Exception object to a string literal 
	           //Java implicitly calls the toString() method
      
    	  try{
		      int selection = getUserSelection(); // int variable named selection to return value from getUserSelection()  
		      
		      switch(selection) {//switch statement code that will process the user's selection. Since the user enters an Integer value (the menu selection number) use a switch statement to process the selection. 
		      
		      case -1:
		            done = exitMenu();
		            break;
		          case 1:
		            createProject();
		            break;
		          case 2:
		            listProjects();
		            break;  
		          case 3:
		            selectProject();
		            break; 
		          case 4:
		            updateProjectDetails();
		            break;
		          case 5:
		            deleteProject();
		            break;

		          default:
		            System.out.println("\n" + selection + " is not a valid selection. Try again.");
		        } 
		      } 
		      catch (Exception e) {
		        System.out.println("\nError: " + e + "Try again.");
		      } 
		    } 
		  }	//...........................................processUserSelections end



	private void updateProjectDetails() { //.....................if statement to see if project is null
	  if(Objects.isNull(curProject)) {
		      System.out.println("\nPlease select a project.");
		      return;
	  }
	//update project details.....................................................................................
	  
	  String projectName = getStringInput("Enter the project name [" + curProject.getProjectName() + "]");
	    BigDecimal estimatedHours = getDecimalInput("Enter the estimated hours [" + curProject.getEstimatedHours() + "]");
	    BigDecimal actualHours = getDecimalInput("Enter the actual hours + [" + curProject.getActualHours() + "]");
	    Integer difficulty = getIntInput("Enter the project difficulty (1-5) [" + curProject.getDifficulty() + "]");
	    String notes = getStringInput("Enter the project notes [" + curProject.getNotes() + "]");
	    Project project = new Project();
	    project.setProjectId(curProject.getProjectId());
	    project.setProjectName(Objects.isNull(projectName) ? curProject.getProjectName() : projectName);
	    project.setEstimatedHours(Objects.isNull(estimatedHours) ? curProject.getEstimatedHours() : estimatedHours);
	    project.setActualHours(Objects.isNull(actualHours) ? curProject.getActualHours() : actualHours);
	    project.setDifficulty(Objects.isNull(difficulty) ? curProject.getDifficulty() : difficulty);
	    project.setNotes(Objects.isNull(notes) ? curProject.getNotes() : notes);
	   projects1Service.modifyProjectDetails(project);//.....................call projectService.modifyProjectDetails() pass in Project object
	    curProject = projects1Service.fetchProjectById(curProject.getProjectId());}
	//.......................................................................................................................
	  
	
	
	private void deleteProject() { //...................deleteAProject Method....week 11
	    listProjects();
	    Integer projectId = getIntInput("Enter ID of the project to delete");
	    projects1Service.deleteProject(projectId);
	    System.out.println("Project " + projectId + " has been deleted!");
	    if(Objects.nonNull(curProject) && curProject.getProjectId().equals(projectId)) {
	        curProject = null;
	      }
	  } 
	
	
	
     //......................................................................................
    private void selectProject() {
			listProjects();
			Integer projectId = getIntInput("Enter a project ID to select a project");
			curProject = null;
		//.................throws an exception if invalid project Id 
		    curProject = projects1Service.fetchProjectById(projectId);
		}
		
     
		//.........................................................................................
		private void listProjects() { //no parameters 
		    
			List<Project> projects = projects1Service.fetchAllProjects();
		       
			System.out.println("\nProjects:");
		   
			projects.forEach(project -> System.out.println("\t" + project.getProjectId()
	      	    + ": " + project.getProjectName()));
		  }
//.................................................................................
		  private void createProject() {
		     String projectName = getStringInput("Enter the project name");
	         BigDecimal estimatedHours = getDecimalInput("Enter the estimated hours");
		     BigDecimal actualHours = getDecimalInput("Enter the actual hours");
		     Integer difficulty = getIntInput("Enter the project difficulty (1-5)");
		     if(Objects.nonNull(difficulty)) {
		     validateDifficulty(difficulty); }
		     String notes = getStringInput("Enter the project notes");
		    Project project = new Project();
		    project.setProjectName(projectName);
		    project.setEstimatedHours(estimatedHours);
		    project.setActualHours(actualHours);
		    project.setDifficulty(difficulty);
		    project.setNotes(notes);
		    Project dbProject = projects1Service.addProject(project);
		    System.out.println("You have successfully created project: " + dbProject);
		  
		  } // ...................................createProject Method end............................

		  private void validateDifficulty(Integer difficulty) {
		    if (difficulty < 1 || difficulty > 5) {
		  
		    	throw new DbException(difficulty + " is not between 1 and 5. Please re-enter difficulty level. ");
		    }
		  } //........................validateDifficulty Method end...................................

		  private BigDecimal getDecimalInput(String prompt) {
		    String input = getStringInput(prompt);
		  if (Objects.isNull(input)) {
		    }
		    try {
		  return new BigDecimal(input).setScale(2);
		   } catch (NumberFormatException e) {
		      throw new DbException(input + " is not a valid decimal number.");
		    }
		  } //............................................................getDecimalInput Method end...............

		  private int getUserSelection() {
		    printOperations();
		    Integer input = getIntInput("\nEnter menu selection, or enter to exit");
		    return Objects.isNull(input) ? -1 : input;
		  } //........................ ...............getUserSelection Method end......................

		       private Integer getIntInput(String prompt) {
		          String input = getStringInput(prompt);
		
		        if (Objects.isNull(input)) {
		      return null;
		    }
		    try {
	      	      return Integer.valueOf(input);
		   
		    } catch (NumberFormatException e) {
		      throw new DbException(input + " is not valid number.");
		    }
		  } // .....................................................................................

		 
		       
		       private String getStringInput(String prompt) {
	   	    System.out.print(prompt + ": ");
		
		    String input = scanner.nextLine();
	       	    return input.isBlank() ? null : input.trim();
		  } //end getStringInput Method........................................
		 
		  
		  
		  private void printOperations() {
		    System.out.println("\nThese are the available selections. Enter key to quit");
		
		         operations.forEach(line -> System.out.println("\t" + line));    
		    if(Objects.isNull(curProject)) {
		   
		    	System.out.println("\nYou are Not working with a project.");
		    }
		    else {
		      System.out.println("\nYou are working with a project: " + curProject);
		    }
		
		  } //..................................printOperations Method end............
		  private boolean exitMenu() {
		 
			  System.out.println("\nExit menu.");
		
			  return true;
		  } // Method end
 
		} // .................Class end.............................................
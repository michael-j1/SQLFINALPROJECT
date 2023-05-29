package projects1.service;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import projects1.dao.DbConnection;
import projects1.dao.ProjectDao;
import projects1.entity.Project;
import projects1.exception.DbException;


public class Projects1Service {
	
	private static final String SCHEMA_FILE = "project-schema.sql";

	  private ProjectDao projectDao = new ProjectDao();

	  public void createAndPopulateTables() {
	    loadFromFile(SCHEMA_FILE);
	  }
private void loadFromFile(String schemaFile) {
		
	}
		 
	//................................................................................

	  public List<Project> fetchAllProjects() {
	    List<Project> projects = projectDao.fetchAllProjects();
	    projects.sort(
	        (Project p1, Project p2) -> {
	            return 
	            p1.getProjectId() - p2.getProjectId();
	        }
	      );
	    
	    return projects;
	  }
	//................................................................................

	  public Project fetchProjectById(Integer projectId) {
	    return projectDao.fetchProjectById(projectId).orElseThrow(() -> new NoSuchElementException(
	        "Project with project ID=" + projectId + " does not exist."));
	  }
	//................................................................................

	  public void modifyProjectDetails(Project project) {
	    if(!projectDao.modifyProjectDetails(project)) {
	      throw new DbException("Project with ID=" + project.getProjectId() + " does not exist.");
	    }
	  } 
	//................................................................................

	
	  public static void main(String[] args) {
	    new Projects1Service().createAndPopulateTables();
	  }
		//................................................................................

	  public Project addProject(Project project) {
	    return projectDao.insertProject(project);
	  }
		//................................................................................

	  public void deleteProject(Integer projectId) {
	    if(!projectDao.deleteProject(projectId)) {
	      throw new DbException("Project with ID=" + projectId + " does not exist.");
	    }
	  } 

	} //...class.............................................................................
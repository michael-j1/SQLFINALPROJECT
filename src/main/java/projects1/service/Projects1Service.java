package projects1.service;
import java.util.List;
import java.util.NoSuchElementException;
import projects1.dao.ProjectDao;
import projects1.entity.Project;
public class Projects1Service {
 private  ProjectDao projectDao = new ProjectDao();
 //method calls the DAO class to insert a project row

 
 public Project addProject(Project project) {
	 return projectDao.insertProject(project);
 }

 
 public List<Project> fetchAllProjects() {
	 return projectDao.fetchAllProjects(); //method calls project DAO to retrieve all project rows wo details

}

public Project fetchProjectById(Integer projectId) {
	return projectDao.fetchProjectById(projectId).orElseThrow(() -> new NoSuchElementException(
	"Project with project ID=" + projectId + " does not exist."));
}

}

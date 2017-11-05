package net.blesso.puzzle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Demonstrates two ways to create an order of dependencies, say for building a group of projects.
 * From McDowell, question 4.7.
 * @author francis
 */
public class DependencyBuilder {
	
	public static final class Project {
		private boolean discovered = false;
		private boolean processed = false;
		private final String name;
		private final Set<Project> before = new HashSet<>();
		private final Set<Project> after = new HashSet<>();
		
		private Project(String name) {
			this.name = name;
		}
		
	}

	private static Map<String, Project> createProjectMap(List<String> projectNames, String[][] deps) {
		final Map<String, Project> projectMap = new HashMap<>();
		
		for (String proj : projectNames) {
			projectMap.put(proj, new Project(proj));
		}
		for (String[] projectDeps : deps) {
			final Project before = projectMap.get(projectDeps[0]);
			final Project after = projectMap.get(projectDeps[1]);
			if (before == null) {
				throw new RuntimeException("no before project for " + projectDeps);
			}
			if (after == null) {
				throw new RuntimeException("no after project for " + projectDeps);
			}
			before.after.add(after);
			after.before.add(before);
		}
		return projectMap;
	}
	
	/**
	 * @param projectNames the projects to build
	 * @param deps array of project pairs. the one at [0] must be build before the one at [1]
	 * @return List of projects in their build order
	 */
	public static List<String> getBuildOrderDfs(List<String> projectNames, String[][] deps) {
		final List<String> buildOrder = new ArrayList<>();
		final Map<String, Project> projectMap = createProjectMap(projectNames, deps);
		
		for (Project project : projectMap.values()) {
			processNode(project, buildOrder);
		}
		
		return buildOrder;
	}
	
	private static void processNode(Project project, List<String> buildOrder) {

		if (project.processed) {
			return;
		}
		if (project.discovered) {
			throw new RuntimeException("cycle found ending at project " + project.name);
		}
		project.discovered = true;
		for (Project after : project.after) {
			processNode(after, buildOrder);
		}
		buildOrder.add(0, project.name);
		project.processed = true;
		
	}

	public static List<String> getBuildOrder(List<String> projs, String[][] deps) {
		final List<String> buildOrder = new ArrayList<>();
		final Map<String, Set<String>> depMap = new LinkedHashMap<>();
		
		//This upfront work is O(P + D) where P is number of projects and D is number of dependencies
		for (String proj : projs) {
			depMap.put(proj, new HashSet<>());
		}
		for (String[] projectDeps : deps) {
			if (depMap.containsKey(projectDeps[1])){
				depMap.get(projectDeps[1]).add(projectDeps[0]);
			}
		}
		
		List<String> nextToAdd = getNodesWithNoRemainingDependencies(depMap);
		while(nextToAdd.size() > 0) {
			buildOrder.addAll(nextToAdd);
			removeDependcies(depMap, nextToAdd);
			nextToAdd = getNodesWithNoRemainingDependencies(depMap);
		}
		
		return buildOrder;
	}

	private static List<String> getNodesWithNoRemainingDependencies(Map<String, Set<String>> depMap) {
		final List<String> projectList = new ArrayList<>();
		
		for(Map.Entry<String, Set<String>> entry : depMap.entrySet()) {
			if(entry.getValue().size() == 0) {
				projectList.add(entry.getKey());
			}
		}
		
		return projectList;
	}

	private static void removeDependcies(Map<String, Set<String>> depMap, List<String> nextToAdd) {
		for (String project : nextToAdd) {
			depMap.remove(project);
		}
		for (Set<String> deps : depMap.values()){
			deps.removeAll(nextToAdd);
		}
	}
	
	
		
}

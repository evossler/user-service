package com.vossler.userservice.dto;

public class ProjectMembership {
	private String id;
	private String projectId;
	private String userId;

	public ProjectMembership() {
		super();
	}

	public ProjectMembership(String id, String projectId, String userId) {
		super();
		this.id = id;
		this.projectId = projectId;
		this.userId = userId;
	}

	public String getId() {
		return id;
	}

	public ProjectMembership setId(String id) {
		this.id = id;
		return this;
	}

	public String getProjectId() {
		return projectId;
	}

	public ProjectMembership setProjectId(String projectId) {
		this.projectId = projectId;
		return this;
	}

	public String getUserId() {
		return userId;
	}

	public ProjectMembership setUserId(String userId) {
		this.userId = userId;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((projectId == null) ? 0 : projectId.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProjectMembership other = (ProjectMembership) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (projectId == null) {
			if (other.projectId != null)
				return false;
		} else if (!projectId.equals(other.projectId))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
}

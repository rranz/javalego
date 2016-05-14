package com.javalego.store.items.impl.listeners;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.javalego.store.items.impl.Project;

public class ProjectListener {

	@PrePersist
	@PreUpdate
	public void prePersist(Project project) {
		project.setText_providers(project.getProviders() != null && project.getProviders().size() > 0 ? project.getProviders().toString() : null);
	}
}

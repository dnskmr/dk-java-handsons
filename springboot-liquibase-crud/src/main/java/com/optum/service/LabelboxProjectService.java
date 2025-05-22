package com.optum.service;

import com.optum.consumer.FastAPIClient;
import com.optum.dto.*;
import com.optum.repository.LabelboxProjectRepository;
import com.optum.repository.UAISProjectRepository;
import com.optum.repository.UserProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class LabelboxProjectService {

    @Autowired
    private LabelboxProjectRepository repository;
    @Autowired
    private UAISProjectRepository uaisProjectRepository;
    @Autowired
    private UserProjectRepository userProjectRepository;
    @Autowired
    private FastAPIClient fastApiClient;

    public LabelboxProject create(LabelboxProject labelboxProject) {
        return repository.save(labelboxProject);
    }

    public List<LabelboxProject> getAll() {
        return repository.findAll();
    }

    public Optional<LabelboxProject> getById(String id) {
        return repository.findById(id);
    }

    public LabelboxProject update(String id, LabelboxProject updated) {
        return repository.findById(id).map(existing -> {
            existing.setUserProject(updated.getUserProject());
            existing.setLbProjectId(updated.getLbProjectId());
            existing.setModifiedAt(updated.getModifiedAt());
            return repository.save(existing);
        }).orElseThrow(() -> new RuntimeException("LabelboxProject not found with id: " + id));
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    public void handleCreateEvent(HcpEventPayload payload) {

        var def = payload.getResourceDefinition();

        // 1. Create UserProject
        UserProject userProject = new UserProject();
        userProject.setUserProjectId(UUID.randomUUID().toString());
        userProject.setName(def.getProjectName());
        userProject.setDescription("Auto-created from HCP webhook");
        userProject.setCreatedAt(Instant.now());
        userProject.setModifiedAt(Instant.now());
        userProjectRepository.save(userProject);


        // 2. Create Workspace

        Workspace workspace = new Workspace();
        workspace.setWorkspaceId(UUID.randomUUID().toString());
        workspace.setWorkspaceName(def.getProjectName() + "_Workspace");
        workspace.setStatus("Active");
        workspace.setDescription("Auto-created workspace for HCP events");
        workspace.setCreatedAt(Instant.now());

        // 3. UAI Project Creation
        UAISProject uaisProject = new UAISProject();
        uaisProject.setUaisProjectId(def.getUaisProjectId());
        uaisProject.setProjectName(def.getProjectName());
        uaisProject.setDescription("Auto-created from webhook");
        uaisProject.setAide(def.getCreatedBy().getId());
        uaisProject.setWorkspace(workspace);
        uaisProject.setMetadata(Map.of("source", "HCP"));
        uaisProject.setCreatedAt(Instant.now());
        uaisProject.setModifiedAt(Instant.now());
        uaisProjectRepository.save(uaisProject);

        // 4. Call Fast API to create LB Project
        String lbProjectId = fastApiClient.createLabelboxProject(def);

        // 5. Create LabelboxProject mapping
        LabelboxProject lbProject = new LabelboxProject();
        lbProject.setLabelboxProjectId(UUID.randomUUID().toString());
        lbProject.setLbProjectId(lbProjectId);
        lbProject.setUserProject(userProject);
        lbProject.setCreatedAt(Instant.now());
        lbProject.setModifiedAt(Instant.now());
        repository.save(lbProject);
    }

    public void handleUpdateEvent(HcpEventPayload payload) {
        var def = payload.getResourceDefinition();

        UAISProject uaisProject = uaisProjectRepository.findById(def.getUaisProjectId())
                .orElseThrow(() -> new RuntimeException("UAIS project not found"));

        uaisProject.setProjectName(def.getProjectName());
        uaisProject.setModifiedAt(Instant.now());
        uaisProjectRepository.save(uaisProject);
    }

}

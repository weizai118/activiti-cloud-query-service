/*
 * Copyright 2018 Alfresco, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.activiti.cloud.services.query.events.handlers;

import java.util.Date;

import org.activiti.cloud.services.query.app.repository.ProcessInstanceRepository;
import org.activiti.cloud.services.query.model.ProcessInstance;
import org.activiti.runtime.api.event.CloudProcessCreatedEvent;
import org.activiti.runtime.api.event.CloudRuntimeEvent;
import org.activiti.runtime.api.event.ProcessRuntimeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProcessCreatedEventHandler implements QueryEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessCreatedEventHandler.class);
    private ProcessInstanceRepository processInstanceRepository;

    @Autowired
    public ProcessCreatedEventHandler(ProcessInstanceRepository processInstanceRepository) {
        this.processInstanceRepository = processInstanceRepository;
    }

    @Override
    public void handle(CloudRuntimeEvent<?, ?> event) {
        CloudProcessCreatedEvent createdEvent = (CloudProcessCreatedEvent) event;
        LOGGER.debug("Handling created process Instance " + createdEvent.getEntity().getId());

        ProcessInstance createdProcessInstance = new ProcessInstance();
        createdProcessInstance.setServiceName(createdEvent.getServiceName());
        createdProcessInstance.setServiceFullName(createdEvent.getServiceFullName());
        createdProcessInstance.setServiceVersion(createdEvent.getServiceVersion());
        createdProcessInstance.setAppName(createdEvent.getAppName());
        createdProcessInstance.setAppVersion(createdEvent.getAppVersion());

        createdProcessInstance.setProcessDefinitionId(createdEvent.getEntity().getProcessDefinitionId());
        createdProcessInstance.setId(createdEvent.getEntity().getId());
        createdProcessInstance.setStatus("CREATED");
        createdProcessInstance.setLastModified(new Date(createdEvent.getTimestamp()));

        createdProcessInstance.setProcessDefinitionKey(createdEvent.getEntity().getProcessDefinitionKey());
        createdProcessInstance.setInitiator(createdEvent.getEntity().getInitiator());
        createdProcessInstance.setBusinessKey(createdEvent.getEntity().getBusinessKey());
        createdProcessInstance.setDescription(createdEvent.getEntity().getDescription());
        createdProcessInstance.setStartDate(createdEvent.getEntity().getStartDate());

        processInstanceRepository.save(createdProcessInstance);
    }

    @Override
    public String getHandledEvent() {
        return ProcessRuntimeEvent.ProcessEvents.PROCESS_CREATED.name();
    }
}

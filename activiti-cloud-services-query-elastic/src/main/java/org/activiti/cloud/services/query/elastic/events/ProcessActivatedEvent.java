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

package org.activiti.cloud.services.query.elastic.events;

import org.activiti.cloud.services.query.model.ProcessInstance;

public class ProcessActivatedEvent extends AbstractProcessEngineEvent {

    private ProcessInstance processInstance;

    public ProcessActivatedEvent() {
    }

    public ProcessActivatedEvent(Long timestamp,
                                 String eventType,
                                 String executionId,
                                 String processDefinitionId,
                                 String processInstanceId,
                                 String applicationName,
                                 ProcessInstance processInstance) {
        super(timestamp,
              eventType,
              executionId,
              processDefinitionId,
              processInstanceId,
              applicationName);
        this.processInstance = processInstance;
    }

    public ProcessInstance getProcessInstance() {
        return processInstance;
    }
}
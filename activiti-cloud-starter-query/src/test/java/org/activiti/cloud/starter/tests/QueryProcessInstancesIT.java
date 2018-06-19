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

package org.activiti.cloud.starter.tests;

import java.util.Collection;

import org.activiti.cloud.services.query.app.repository.ProcessInstanceRepository;
import org.activiti.cloud.services.query.model.ProcessInstance;
import org.activiti.cloud.starters.test.EventsAggregator;
import org.activiti.cloud.starters.test.MyProducer;
import org.activiti.cloud.starters.test.builder.ProcessInstanceEventContainedBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.awaitility.Awaitility.await;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class QueryProcessInstancesIT {

    private static final String PROC_URL = "/v1/process-instances";
    private static final ParameterizedTypeReference<PagedResources<ProcessInstance>> PAGED_PROCESS_INSTANCE_RESPONSE_TYPE = new ParameterizedTypeReference<PagedResources<ProcessInstance>>() {
    };

    @Autowired
    private KeycloakTokenProducer keycloakTokenProducer;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ProcessInstanceRepository processInstanceRepository;

    @Autowired
    private MyProducer producer;

    private EventsAggregator eventsAggregator;

    private ProcessInstanceEventContainedBuilder processInstanceBuilder;

    @Before
    public void setUp() {
        eventsAggregator = new EventsAggregator(producer);
        processInstanceBuilder = new ProcessInstanceEventContainedBuilder(eventsAggregator);
    }

    @After
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void tearDown() {
        processInstanceRepository.deleteAll();
    }

    @Test
    public void shouldGetAvailableProcInstancesAndFilteredProcessInstances() {
        //given
        org.activiti.runtime.api.model.ProcessInstance completedProcess = processInstanceBuilder.aCompletedProcessInstance("first");
        org.activiti.runtime.api.model.ProcessInstance runningProcess = processInstanceBuilder.aRunningProcessInstance("second");

        eventsAggregator.sendAll();

        await().untilAsserted(() -> {

            //when
            ResponseEntity<PagedResources<ProcessInstance>> responseEntity = executeRequestGetProcInstances();

            //then
            assertThat(responseEntity).isNotNull();
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

            Collection<ProcessInstance> processInstances = responseEntity.getBody().getContent();
            assertThat(processInstances)
                    .extracting(ProcessInstance::getId,
                                ProcessInstance::getStatus)
                    .contains(tuple(completedProcess.getId(),
                                    "COMPLETED"),
                              tuple(runningProcess.getId(),
                                    "RUNNING"));
        });

        await().untilAsserted(() -> {

            //and filter by status
            //when
            ResponseEntity<PagedResources<ProcessInstance>> responseEntityFiltered = testRestTemplate.exchange(PROC_URL + "?status={status}",
                                                                                                               HttpMethod.GET,
                                                                                                               keycloakTokenProducer.entityWithAuthorizationHeader(),
                                                                                                               PAGED_PROCESS_INSTANCE_RESPONSE_TYPE,
                                                                                                               "COMPLETED");

            //then
            assertThat(responseEntityFiltered).isNotNull();
            assertThat(responseEntityFiltered.getStatusCode()).isEqualTo(HttpStatus.OK);

            Collection<ProcessInstance> filteredProcessInstances = responseEntityFiltered.getBody().getContent();
            assertThat(filteredProcessInstances)
                    .extracting(ProcessInstance::getId,
                                ProcessInstance::getStatus)
                    .containsExactly(tuple(completedProcess.getId(),
                                           "COMPLETED"));
        });
    }

    private ResponseEntity<PagedResources<ProcessInstance>> executeRequestGetProcInstances() {

        return testRestTemplate.exchange(PROC_URL,
                                         HttpMethod.GET,
                                         keycloakTokenProducer.entityWithAuthorizationHeader(),
                                         PAGED_PROCESS_INSTANCE_RESPONSE_TYPE);
    }
}

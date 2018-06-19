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

import org.activiti.cloud.services.query.graphql.autoconfigure.EnableActivitiGraphQLQueryService;
import org.activiti.cloud.services.query.notifications.config.EnableActivitiNotificationsGateway;
import org.activiti.cloud.services.query.qraphql.ws.config.EnableActivitiGraphQLNotifications;
import org.activiti.cloud.starter.query.configuration.EnableActivitiQuery;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import static org.mockito.Mockito.mock;

@SpringBootApplication
@EnableActivitiQuery
@EnableActivitiGraphQLNotifications
@EnableActivitiNotificationsGateway
@EnableActivitiGraphQLQueryService
@EnableSpringDataWebSupport
@ComponentScan({"org.activiti.cloud.services.query.app",
        "org.activiti.cloud.services.query.events",
        "org.activiti.cloud.services.query.rest",
        "org.activiti.cloud.services.security",
        "org.activiti.cloud.starters",
        "org.activiti.cloud.starter",
        "org.activiti.cloud.services.identity",
        "org.activiti.cloud.alfresco"})
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class,
                              args);
    }

    //TODO remove this when no dependency to engine has been removed
    @Bean
    public TaskService taskService(){
        return mock(TaskService.class);
    }

    @Bean
    public RuntimeService runtimeService() {
        return mock(RuntimeService.class);
    }

    @Bean
    public RepositoryService repositoryService(){
        return mock(RepositoryService.class);
    }

}
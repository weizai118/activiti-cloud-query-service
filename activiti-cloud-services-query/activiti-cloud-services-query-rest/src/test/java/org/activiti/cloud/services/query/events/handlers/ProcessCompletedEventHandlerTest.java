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

public class ProcessCompletedEventHandlerTest {

//    @InjectMocks
//    private ProcessCompletedEventHandler handler;
//
//    @Mock
//    private ProcessInstanceRepository processInstanceRepository;
//
//    @Rule
//    public ExpectedException expectedException = ExpectedException.none();
//
//    @Before
//    public void setUp() throws Exception {
//        initMocks(this);
//    }
//
//    @Test
//    public void handleShouldUpdateCurrentProcessInstanceStateToCompleted() throws Exception {
//        //given
//        ProcessCompletedEvent event = new ProcessCompletedEvent(System.currentTimeMillis(),
//                                                                                "ProcessCompletedEvent",
//                                                                                "10",
//                                                                                "100",
//                                                                                "200",
//                "runtime-bundle-a",
//                "runtime-bundle-a",
//                "runtime-bundle",
//                "1",
//                null,
//                null,
//                                                                                new ProcessInstance());
//
//        ProcessInstance currentProcessInstance = mock(ProcessInstance.class);
//        given(processInstanceRepository.findById("200")).willReturn(Optional.of(currentProcessInstance));
//
//        //when
//        handler.handle(event);
//
//        //then
//        verify(processInstanceRepository).save(currentProcessInstance);
//        verify(currentProcessInstance).setStatus("COMPLETED");
//        verify(currentProcessInstance).setLastModified(any(Date.class));
//    }
//
//    @Test
//    public void handleShouldThrowExceptionWhenRelatedProcessInstanceIsNotFound() throws Exception {
//        //given
//        ProcessCompletedEvent event = new ProcessCompletedEvent(System.currentTimeMillis(),
//                                                                                "ProcessCompletedEvent",
//                                                                                "10",
//                                                                                "100",
//                                                                                "200",
//                "runtime-bundle-a",
//                "runtime-bundle-a",
//                "runtime-bundle",
//                "1",
//                null,
//                null,
//                                                                                new ProcessInstance());
//
//        given(processInstanceRepository.findById("200")).willReturn(Optional.empty());
//
//        //then
//        expectedException.expect(ActivitiException.class);
//        expectedException.expectMessage("Unable to find process instance with the given id: ");
//
//        //when
//        handler.handle(event);
//
//    }
//
//    @Test
//    public void getHandledEventClassShouldReturnProcessCompletedEvent() throws Exception {
//        //when
//        Class<? extends ProcessEngineEvent> handledEventClass = handler.getHandledEventClass();
//
//        //then
//        assertThat(handledEventClass).isEqualTo(ProcessCompletedEvent.class);
//    }
}
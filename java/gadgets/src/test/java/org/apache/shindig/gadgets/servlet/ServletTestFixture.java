/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.shindig.gadgets.servlet;

import org.apache.shindig.common.EasyMockTestCase;
import org.apache.shindig.common.servlet.HttpServletResponseRecorder;
import org.apache.shindig.gadgets.LockedDomainService;
import org.apache.shindig.gadgets.http.RequestPipeline;
import org.apache.shindig.gadgets.rewrite.CaptureRewriter;
import org.apache.shindig.gadgets.rewrite.DefaultRequestRewriterRegistry;
import org.apache.shindig.gadgets.rewrite.RequestRewriter;
import org.apache.shindig.gadgets.rewrite.RequestRewriterRegistry;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Contains everything needed for making servlet requests, plus a bunch of stuff that shouldn't be
 * here.
 *
 * TODO: Get rid of 'stuff that shouldn't be here'.
 */
public abstract class ServletTestFixture extends EasyMockTestCase {
  public final RequestPipeline pipeline = mock(RequestPipeline.class);
  public final CaptureRewriter rewriter = new CaptureRewriter();
  public final RequestRewriterRegistry rewriterRegistry
      = new DefaultRequestRewriterRegistry(Arrays.<RequestRewriter>asList(rewriter), null);
  public final HttpServletRequest request = mock(HttpServletRequest.class);
  public final HttpServletResponse response = mock(HttpServletResponse.class);
  public final HttpServletResponseRecorder recorder = new HttpServletResponseRecorder(response);
  public final LockedDomainService lockedDomainService = mock(LockedDomainService.class);
}

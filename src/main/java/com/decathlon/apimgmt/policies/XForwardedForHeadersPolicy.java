/**
 * Copyright (C) 2015 The Gravitee team (http://gravitee.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.decathlon.apimgmt.policies;

import io.gravitee.common.http.HttpHeaders;
import io.gravitee.common.http.HttpStatusCode;
import io.gravitee.gateway.api.Request;
import io.gravitee.gateway.api.Response;
import io.gravitee.policy.api.PolicyChain;
import io.gravitee.policy.api.PolicyResult;
import io.gravitee.policy.api.annotations.OnRequest;
import io.gravitee.policy.api.annotations.OnResponse;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unused")
public class XForwardedForHeadersPolicy {

    /**
     * The associated configuration to this XForwardedForHeaders Policy
     */
    private XForwardedForHeadersPolicyConfiguration configuration;

    /**
     * Create a new XForwardedForHeaders Policy instance based on its associated configuration
     *
     * @param configuration the associated configuration to the new XForwardedForHeaders Policy instance
     */
    public XForwardedForHeadersPolicy(XForwardedForHeadersPolicyConfiguration configuration) {
        this.configuration = configuration;
    }

    @OnRequest
    public void onRequest(Request request, Response response, PolicyChain policyChain) {
        // Add a dummy header
        request.headers().set("X-Forwarded-For", request.headers().getFirst(HttpHeaders.HOST));

        // Finally continue chaining
        policyChain.doNext(request, response);
    }

    @OnResponse
    public void onResponse(Request request, Response response, PolicyChain policyChain) {

        LoggerFactory.getLogger(XForwardedForHeadersPolicy.class).error("I should set a header");

        // Add a dummy header
        response.headers().set("X-Forwarded-Host", "un truc intéressant");

        // Finally continue chaining
        policyChain.doNext(request, response);
    }

}

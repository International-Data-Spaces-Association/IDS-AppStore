/*
 * Copyright 2020-2022 Fraunhofer Institute for Software and Systems Engineering
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.dataspaceconnector.service.message.handler.processor.base;

import java.util.Optional;

import io.dataspaceconnector.service.message.handler.dto.Request;
import io.dataspaceconnector.service.message.handler.dto.Response;
import io.dataspaceconnector.service.message.handler.dto.RouteMsg;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Superclass for Camel processors that execute the final logic to generate a response to an
 * incoming message, e.g. generating a description or providing data.
 *
 * @param <I> the expected input type (body of the Camel {@link Exchange}).
 */
public abstract class IdsProcessor<I extends RouteMsg<?, ?>> implements Processor {

    /**
     * Override of the {@link Processor}'s process method. Calls the implementing class's
     * processInternal method and sets the result as the {@link Exchange}'s body.
     *
     * @param exchange the input.
     * @throws Exception if an error occurs.
     */
    @Override
    @SuppressWarnings("unchecked")
    public void process(final Exchange exchange) throws Exception {
        var request = exchange.getIn().getBody(Request.class);
        var claims = (Optional<Jws<Claims>>) request.getClaims();

        if (claims.isPresent()) {
            exchange.getIn().setBody(processInternal((I) request, claims.get()));
        } else {
            exchange.getIn().setBody(processInternal((I) request, null));
        }
    }

    /**
     * Contains the logic to generate a response to an incoming message.
     *
     * @param msg the incoming message.
     * @param claims the JWT claims of the issuer.
     * @return the generated response.
     * @throws Exception if an error occurs.
     */
    protected abstract Response processInternal(I msg, Jws<Claims> claims) throws Exception;
}

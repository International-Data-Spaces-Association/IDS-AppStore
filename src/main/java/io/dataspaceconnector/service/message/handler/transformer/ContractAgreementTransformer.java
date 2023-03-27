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
package io.dataspaceconnector.service.message.handler.transformer;

import de.fraunhofer.iais.eis.ContractAgreement;
import de.fraunhofer.iais.eis.ContractAgreementMessageImpl;
import ids.messaging.handler.message.MessagePayload;
import io.dataspaceconnector.common.ids.DeserializationService;
import io.dataspaceconnector.common.ids.message.MessageUtils;
import io.dataspaceconnector.service.message.handler.dto.Request;
import io.dataspaceconnector.service.message.handler.dto.RouteMsg;
import io.dataspaceconnector.service.message.handler.transformer.base.IdsTransformer;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Transforms the payload of a ContractAgreementMessage from a {@link MessagePayload} to a
 * {@link ContractAgreement}.
 */
@Component("AgreementDeserializer")
@RequiredArgsConstructor
class ContractAgreementTransformer extends IdsTransformer<
        Request<ContractAgreementMessageImpl, MessagePayload, Optional<Jws<Claims>>>,
        RouteMsg<ContractAgreementMessageImpl, ContractAgreement>> {

    /**
     * Service for ids deserialization.
     */
    private final @NonNull
    DeserializationService deserializationService;

    /**
     * Deserializes the payload of a ContractAgreementMessage to a ContractAgreement.
     *
     * @param msg the incoming message.
     * @return a RouteMsg object with the initial header and the ContractAgreement as payload.
     * @throws Exception if the payload cannot be deserialized.
     */
    @Override
    protected RouteMsg<ContractAgreementMessageImpl, ContractAgreement> processInternal(
            final Request<ContractAgreementMessageImpl, MessagePayload, Optional<Jws<Claims>>> msg)
            throws Exception {
        final var payload = MessageUtils.getPayloadAsString(msg.getBody());
        final var agreement = deserializationService.getContractAgreement(payload);
        return new Request<>(msg.getHeader(), agreement, msg.getClaims());
    }

}

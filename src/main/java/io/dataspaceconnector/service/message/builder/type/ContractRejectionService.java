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
package io.dataspaceconnector.service.message.builder.type;

import de.fraunhofer.iais.eis.ContractRejectionMessageBuilder;
import de.fraunhofer.iais.eis.Message;
import de.fraunhofer.iais.eis.RejectionReason;
import de.fraunhofer.iais.eis.util.ConstraintViolationException;
import de.fraunhofer.iais.eis.util.TypedLiteral;
import de.fraunhofer.iais.eis.util.Util;
import ids.messaging.util.IdsMessageUtils;
import io.dataspaceconnector.common.exception.ErrorMessage;
import io.dataspaceconnector.common.util.Utils;
import io.dataspaceconnector.model.message.ContractRejectionMessageDesc;
import io.dataspaceconnector.service.message.builder.type.base.AbstractMessageService;
import org.springframework.stereotype.Service;

/**
 * Message service for ids contract request messages.
 */
@Service
public final class ContractRejectionService
        extends AbstractMessageService<ContractRejectionMessageDesc> {

    /**
     * @throws IllegalArgumentException     if desc is null.
     * @throws ConstraintViolationException if security tokes is null or another error appears
     *                                      when building the message.
     */
    @Override
    public Message buildMessage(final ContractRejectionMessageDesc desc)
            throws ConstraintViolationException {
        Utils.requireNonNull(desc, ErrorMessage.DESC_NULL);

        final var connectorId = getConnectorService().getConnectorId();
        final var modelVersion = getConnectorService().getOutboundModelVersion();
        final var token = getConnectorService().getCurrentDat();

        Utils.requireNonNull(token, ErrorMessage.DAT_NULL);

        final var recipient = desc.getRecipient();
        final var correlationMessage = desc.getCorrelationMessage();
        final var rejectionReason = new TypedLiteral("Contract not accepted.", "en");

        return new ContractRejectionMessageBuilder()
                ._securityToken_(token)
                ._correlationMessage_(correlationMessage)
                ._issued_(IdsMessageUtils.getGregorianNow())
                ._issuerConnector_(connectorId)
                ._modelVersion_(modelVersion)
                ._senderAgent_(connectorId)
                ._recipientConnector_(Util.asList(recipient))
                ._rejectionReason_(RejectionReason.MALFORMED_MESSAGE)
                ._contractRejectionReason_(rejectionReason)
                .build();
    }

    @Override
    protected Class<?> getResponseMessageType() {
        return null;
    }
}

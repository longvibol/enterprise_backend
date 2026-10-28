package com.pisethjavaschool.platform.propertyowner.client;

import java.util.UUID;
import com.pisethjavaschool.platform.propertyowner.client.dto.CreatePropertyOwnerCommand;
import reactor.core.publisher.Mono;

public interface PropertyOwnerProvisioningClient {
    Mono<UUID> createOwner(CreatePropertyOwnerCommand command);
}

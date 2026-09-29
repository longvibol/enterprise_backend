package com.pisethjavaschool.property.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import com.pisethjavaschool.property.dto.CreatePropertyRequest;
import com.pisethjavaschool.property.enums.BusinessType;
import com.pisethjavaschool.property.enums.PropertyStatus;

class PropertyFactoryImplTest {
	@Test
    void shouldCreateRestaurantDraft() {
        var ownerId = UUID.randomUUID();
        var request = new CreatePropertyRequest("Piseth Restaurant", BusinessType.RESTAURANT, null, null, null, null, null, "12", "1201", null, null, "Phnom Penh", null, null, null, null);
        var property = new PropertyFactoryImpl().createDraft(ownerId, request);
        assertEquals(ownerId, property.getOwnerId());
        assertEquals(BusinessType.RESTAURANT, property.getBusinessType());
        assertEquals(PropertyStatus.DRAFT, property.getStatus());
    }
}

package com.pisethjavaschool.property.service;

import java.util.UUID;

import com.pisethjavaschool.property.dto.CreatePropertyRequest;
import com.pisethjavaschool.property.entity.Property;

public interface PropertyFactory {

    Property createDraft(UUID ownerId, CreatePropertyRequest request);
}
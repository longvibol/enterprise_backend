package com.pisethjavaschool.property.service;

import com.pisethjavaschool.property.dto.UpdatePropertyRequest;
import com.pisethjavaschool.property.entity.Property;

public interface PropertyUpdater {

    Property apply(Property property, UpdatePropertyRequest request);
}
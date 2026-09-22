package com.pisethjavaschool.propertyowner.service;

import com.pisethjavaschool.propertyowner.dto.UpdatePropertyOwnerRequest;
import com.pisethjavaschool.propertyowner.entity.PropertyOwner;

public interface PropertyOwnerUpdater {
	PropertyOwner update(PropertyOwner owner, UpdatePropertyOwnerRequest request);
}

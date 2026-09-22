package com.pisethjavaschool.propertyowner.mapper;

import org.mapstruct.Mapper;

import com.pisethjavaschool.propertyowner.dto.OwnerDocumentResponse;
import com.pisethjavaschool.propertyowner.dto.OwnerVerificationHistoryResponse;
import com.pisethjavaschool.propertyowner.dto.PropertyOwnerResponse;
import com.pisethjavaschool.propertyowner.dto.PropertyOwnerSummaryResponse;
import com.pisethjavaschool.propertyowner.entity.OwnerDocument;
import com.pisethjavaschool.propertyowner.entity.OwnerVerificationHistory;
import com.pisethjavaschool.propertyowner.entity.PropertyOwner;

@Mapper(componentModel = "spring")
public interface PropertyOwnerMapper {
	PropertyOwnerResponse toResponse(PropertyOwner propertyOwner);

	PropertyOwnerSummaryResponse toSummary(PropertyOwner propertyOwner);

	OwnerDocumentResponse toResponse(OwnerDocument document);

	OwnerVerificationHistoryResponse toResponse(OwnerVerificationHistory history);
}

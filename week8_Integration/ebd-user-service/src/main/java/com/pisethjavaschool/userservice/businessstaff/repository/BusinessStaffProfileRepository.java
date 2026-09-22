package com.pisethjavaschool.userservice.businessstaff.repository;

import java.util.UUID;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import com.pisethjavaschool.userservice.businessstaff.entity.BusinessStaffProfile;
import com.pisethjavaschool.userservice.user.enums.UserType;

public interface BusinessStaffProfileRepository extends ReactiveCrudRepository<BusinessStaffProfile, UUID> {
}

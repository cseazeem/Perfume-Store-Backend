package com.perfume.store.repository;

import com.perfume.store.model.OtpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.Optional;

public interface OtpRepository extends JpaRepository<OtpEntity, String> {

    /**
     * Find OTP details by email.
     *
     * @param email the email of the user
     * @return an Optional containing the OtpEntity if found
     */
    Optional<OtpEntity> findByEmail(String email);

    /**
     * Delete all OTPs that have expired.
     *
     * @param time the current time to check against expiry times
     */
    void deleteByExpiryTimeBefore(LocalDateTime time);
}


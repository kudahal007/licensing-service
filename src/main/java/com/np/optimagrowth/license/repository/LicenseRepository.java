package com.np.optimagrowth.license.repository;

import com.np.optimagrowth.license.model.License;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LicenseRepository extends JpaRepository<License,String> {
     List<License> findByOrganizationId(String organizationId);
     License findByOrganizationIdAndLicenseId(String organizationId, String licenseId);
}

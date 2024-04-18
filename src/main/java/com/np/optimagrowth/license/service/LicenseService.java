package com.np.optimagrowth.license.service;

import com.np.optimagrowth.license.config.ServiceConfig;
import com.np.optimagrowth.license.model.License;
import com.np.optimagrowth.license.repository.LicenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LicenseService {
    private final MessageSource messages;
    private final LicenseRepository licenseRepository;
    private final ServiceConfig serviceConfig;

    public License getLicense(String licenseId, String organizationId) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        if (null == license) {
            throw new IllegalArgumentException(String.format(messages.getMessage("license.error.message", null, null),
                    licenseId, organizationId));
        }
        return license.withComment(serviceConfig.getProperty());
    }

    public String createLicense(String organizationId, License license, Locale locale) {
        String responseMessage = null;
        if (license != null) {
            license.setLicenseId(UUID.randomUUID().toString());
            license.setOrganizationId(organizationId);
            license.withComment(serviceConfig.getProperty());
            licenseRepository.save(license);
            responseMessage = String.format(
                    messages.getMessage("license.create.message", null, locale)
                    , license);
        }
        return responseMessage;
    }

    public String updateLicense(License license) {
        String responseMessage = null;
        if (license != null) {
            license.withComment(serviceConfig.getProperty());
            licenseRepository.save(license);
            responseMessage = String.format(
                    messages.getMessage("license.update.message", null, null)
                    , license);
        }
        return responseMessage;
    }

    public String deleteLicense(String licenseId) {
        String responseMessage = null;
        licenseRepository.deleteById(licenseId);
        responseMessage = String.format("Deleting license with id %s ", licenseId);
        return responseMessage;
    }

}

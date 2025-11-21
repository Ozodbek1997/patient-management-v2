/**
 * @author: Bek
 * Date: 01/06/2025
 * Time: 12:16
 * Project Name: patient-management
 */

package com.pm.patientservice.exception;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class EmailAreadyExistsException extends RuntimeException {
    public EmailAreadyExistsException(String message) {
        super(message);
    }
}

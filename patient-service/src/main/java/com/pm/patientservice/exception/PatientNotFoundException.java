/**
 * @author: Bek
 * Date: 01/06/2025
 * Time: 16:34
 * Project Name: patient-management
 */

package com.pm.patientservice.exception;

import java.util.UUID;

public class PatientNotFoundException extends RuntimeException {
    public PatientNotFoundException(String message) {
    super(message);
    }
}

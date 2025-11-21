/**
 * @author: Bek
 * Date: 31/05/2025
 * Time: 17:34
 * Project Name: patient-management
 */

package com.pm.patientservice.repos;

import com.pm.patientservice.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {

    boolean existsPatientByEmail(String email);

    boolean existsByEmailAndIdNot(String email, UUID id);

}

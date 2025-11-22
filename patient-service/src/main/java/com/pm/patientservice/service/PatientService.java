/**
 * @author: Bek
 * Date: 31/05/2025
 * Time: 17:37
 * Project Name: patient-management
 */

package com.pm.patientservice.service;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.exception.EmailAreadyExistsException;
import com.pm.patientservice.exception.PatientNotFoundException;
import com.pm.patientservice.grpc.BillingServiceGrpcClient;
import com.pm.patientservice.kafka.KafkaProducer;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repos.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientService {


    private final PatientRepository patientRepository;
    private final BillingServiceGrpcClient billingServiceGrpcClient;
    private final KafkaProducer kafkaProducer;

    public List<PatientResponseDTO> getPatients() {
        List<Patient> patients = patientRepository.findAll();

        return patients.stream().map(PatientMapper::toDTO).toList();
    }

    public PatientResponseDTO createPatient(PatientRequestDTO requestDTO) {

        if (patientRepository.existsPatientByEmail(requestDTO.getEmail())) {
            throw new EmailAreadyExistsException(" A patient with this email already exist: " + requestDTO.getEmail());
        }

        Patient entity = PatientMapper.toModel(requestDTO);
        Patient newPatient = patientRepository.save(entity);


        billingServiceGrpcClient.createBillingAccount(newPatient.getId().toString(), newPatient.getName(), newPatient.getEmail());

        kafkaProducer.sendEvent(newPatient);

        return PatientMapper.toDTO(newPatient);
    }

    public PatientResponseDTO updatePatient(UUID uuid, PatientRequestDTO dto) {
        Patient patient = patientRepository
                .findById(uuid).orElseThrow(() -> new PatientNotFoundException("Patient not found with ID: " + uuid));

        if (patientRepository.existsByEmailAndIdNot(dto.getEmail(), uuid)) {
            throw new EmailAreadyExistsException(" A patient with this email already exist: " + dto.getEmail());
        }
        patient.setName(dto.getName());
        patient.setEmail(dto.getEmail());
        patient.setAddress(dto.getAddress());
        patient.setDateOfBirth(LocalDate.parse(dto.getDateOfBirth()));

        Patient updatedPatient = patientRepository.save(patient);

        return PatientMapper.toDTO(updatedPatient);
    }

    public void deletePatientId(UUID id) {
        patientRepository.deleteById(id);
    }

}

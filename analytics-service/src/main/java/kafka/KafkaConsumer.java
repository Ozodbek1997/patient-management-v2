/**
 * @author: Bek
 * Date: 23/11/2025
 * Time: 11:38
 * Project Name: patient-management
 */

package kafka;


import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

@Service
public class KafkaConsumer {


    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "patient", groupId = "analytics-service")
    public void consumeEvent(byte[] event) {
        log.info("Hello everybody");
        try {
            PatientEvent patientEvent = PatientEvent.parseFrom(event);

            //perform any business logic related to analytics here

            log.info("Received patient event: {}", patientEvent.toString());
        } catch (InvalidProtocolBufferException e) {
            log.error(e.getMessage(), e);
        }
    }

}

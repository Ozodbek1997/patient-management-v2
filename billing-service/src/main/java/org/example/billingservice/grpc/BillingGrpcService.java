/**
 * @author: Bek
 * Date: 17/11/2025
 * Time: 20:00
 * Project Name: patient-management
 */

package org.example.billingservice.grpc;

import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@GrpcService
public class BillingGrpcService extends BillingServiceGrpc.BillingServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(BillingGrpcService.class);

    @Override
    public void createBillingAccount(billing.BillingRequest request, StreamObserver<billing.BillingResponse> responseObserver) {

        log.info("createBillingAccount request received : {}", request.toString());

        //Business logic - e.g save to database,perform calculates etc

        BillingResponse billingResponse = BillingResponse.newBuilder()
                .setAccountId("1234")
                .setStatus("ACTIVE")
                .build();

        responseObserver.onNext(billingResponse);
        responseObserver.onCompleted();
    }
}

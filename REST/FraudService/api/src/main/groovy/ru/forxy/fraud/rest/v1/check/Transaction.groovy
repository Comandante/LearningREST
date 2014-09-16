package ru.forxy.fraud.rest.v1.check

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import ru.forxy.fraud.rest.v1.check.payment.Payment
import ru.forxy.fraud.rest.v1.check.person.Account
import ru.forxy.fraud.rest.v1.check.product.Product

@Document(collection = "transaction")
@ToString
@EqualsAndHashCode(callSuper = true)
class Transaction extends Entity {
    @Id
    String transactionID;
    String ipAddress;
    String machineGUID;
    Account account;
    List<Payment> payments;
    List<? extends Product> products;
}

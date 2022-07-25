package com.bilyoner.assignment.balanceapi.persistence.entity;

import com.bilyoner.assignment.balanceapi.model.enums.TransactionTypeEnum;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UserBalanceHistoryEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private BigDecimal oldBalance;

    @Column(nullable = false)
    private BigDecimal newBalance;

    @Column(columnDefinition = "TIMESTAMP", nullable = false)
    @LastModifiedDate
    private LocalDateTime updateDate;

    @Column(nullable = false)
    private String transactionId;

    @Column(nullable = false)
    private TransactionTypeEnum transactionType;

}

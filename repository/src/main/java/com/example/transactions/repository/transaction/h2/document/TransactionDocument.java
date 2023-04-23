package com.example.transactions.repository.transaction.h2.document;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "TRANSACTION")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TransactionDocument implements Serializable {

  @Id
  @Column(unique = true, nullable = false)
  private String reference;

  private Long date;

  private Double amount;

  private Double fee;

  private String description;

  private String iban;

}

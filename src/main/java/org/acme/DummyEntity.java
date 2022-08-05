package org.acme;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class DummyEntity {
  
  @Id
  Long id;
}

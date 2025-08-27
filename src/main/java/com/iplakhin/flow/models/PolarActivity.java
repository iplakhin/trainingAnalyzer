package com.iplakhin.flow.models;


import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "polar_activities")
public class PolarActivity {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column
   private long id;
   @Column(name = "list_item_id", nullable = false)
   private long listItemId;
   @Column(nullable = true)
   private Instant timestamp;

   public PolarActivity() {}

   public PolarActivity(long listItemId, Instant timestamp) {
      this.listItemId = listItemId;
      this.timestamp = timestamp;
   }

    public long getListItemId() {
        return listItemId;
    }

    public void setListItemId(long listItemId) {
        this.listItemId = listItemId;
    }




}


package com.bookit.demo;

public enum SUBJECT {
    PENSIONSAVINGS ("Pensionsparande"),
    CAPITALSAVINGS ("Kapitalplacering"),
    MORTAGES ("Bolån"),
    INSURANCE ("Försäkring"),
    CHILDSAVINGS ("Barnsparande");

  private final String displayValue;
   private SUBJECT (String displayValue) {
      this.displayValue = displayValue;
   }
   public String getDisplayValue() {
       return displayValue;
   }
}


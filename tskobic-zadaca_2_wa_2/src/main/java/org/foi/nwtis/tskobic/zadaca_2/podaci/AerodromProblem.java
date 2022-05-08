package org.foi.nwtis.tskobic.zadaca_2.podaci;

import lombok.Getter;
import lombok.Setter;
import lombok.NonNull;

import java.util.Date;

import lombok.AllArgsConstructor;

/**
 * Klasa koja predstavlja problem aerodroma
 *
 * @param icao icao aerodroma
 * @param opis opis problema
 * @param vrijeme vrijeme zapisa
 */
@AllArgsConstructor
public class AerodromProblem {
    
    @Getter
    @Setter 
    @NonNull 
    private String icao;
    
    @Getter
    @Setter 
    @NonNull
    private String opis;
    
    @Getter
    @Setter 
    @NonNull
    private Date vrijeme;
}

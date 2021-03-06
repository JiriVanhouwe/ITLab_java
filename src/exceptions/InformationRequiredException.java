package exceptions;

import domain.RequiredElement;
import java.util.Collections;
import java.util.Set;

public class InformationRequiredException extends Exception{

    private static final String MESSAGE = "Sessie is niet aangemaakt fout in de gegevens";

    private Set<RequiredElement> informationRequired;
    
    public InformationRequiredException(Set<RequiredElement> itemsRequired){
        super(MESSAGE);
        informationRequired = itemsRequired;
    }
    
    public Set<RequiredElement> getInformationRequired(){
        return Collections.unmodifiableSet(informationRequired); 
    }
}
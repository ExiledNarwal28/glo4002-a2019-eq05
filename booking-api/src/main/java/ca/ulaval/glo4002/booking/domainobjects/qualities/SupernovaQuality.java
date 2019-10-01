package ca.ulaval.glo4002.booking.domainobjects.qualities;

import ca.ulaval.glo4002.booking.constants.QualityConstants;

public class SupernovaQuality extends Quality {

    public SupernovaQuality() {
        this.id = QualityConstants.SUPERNOVA_ID;
        this.name = QualityConstants.SUPERNOVA_NAME;
        this.oxygenTanksNeededPerDay = QualityConstants.SUPERNOVA_OXYGENTANK_NEEDED_PER_DAY;
    }
}

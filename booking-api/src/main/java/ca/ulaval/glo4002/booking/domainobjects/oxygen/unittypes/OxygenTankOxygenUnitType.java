package ca.ulaval.glo4002.booking.domainobjects.oxygen.unittypes;

import ca.ulaval.glo4002.booking.constants.OxygenConstants;

public class OxygenTankOxygenUnitType extends OxygenUnitType {

    public OxygenTankOxygenUnitType() {
        this.id = OxygenConstants.UnitTypes.OXYGEN_TANKS_ID;
        this.name = OxygenConstants.UnitTypes.OXYGEN_TANKS_NAME;
    }
}
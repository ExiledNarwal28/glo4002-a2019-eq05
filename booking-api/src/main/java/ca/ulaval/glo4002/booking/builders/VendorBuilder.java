package ca.ulaval.glo4002.booking.builders;

import ca.ulaval.glo4002.booking.constants.ExceptionConstants;
import ca.ulaval.glo4002.booking.constants.VendorConstants;
import ca.ulaval.glo4002.booking.domainobjects.vendors.TeamVendor;
import ca.ulaval.glo4002.booking.domainobjects.vendors.Vendor;
import ca.ulaval.glo4002.booking.exceptions.VendorNotFoundException;

public class VendorBuilder implements Builder<Vendor> {

    public Vendor buildById(Long id) {
        if(id.equals(VendorConstants.TEAM_VENDOR_ID)){
            return buildTeamVendor();
        } else {
            throw new VendorNotFoundException(id.toString());
        }
    }

    public Vendor buildByCode(String vendorCode) {
        if(vendorCode.equals(VendorConstants.TEAM_VENDOR_CODE)) {
            return buildTeamVendor();
        } else {
            throw new VendorNotFoundException(vendorCode);
        }
    }

    @Override
    public Vendor buildByName(String name) {
        throw new IllegalStateException(ExceptionConstants.UNUSED_METHOD_DESCRIPTION);
    }

    private Vendor buildTeamVendor() {
        return new TeamVendor();
    }
}
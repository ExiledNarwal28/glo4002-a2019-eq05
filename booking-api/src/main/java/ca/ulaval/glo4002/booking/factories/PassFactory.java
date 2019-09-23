package ca.ulaval.glo4002.booking.factories;

import ca.ulaval.glo4002.booking.constants.PassConstants;
import ca.ulaval.glo4002.booking.entities.passes.categories.NebulaPassCategory;
import ca.ulaval.glo4002.booking.entities.passes.categories.PassCategory;
import ca.ulaval.glo4002.booking.entities.passes.categories.SupergiantPassCategory;
import ca.ulaval.glo4002.booking.entities.passes.categories.SupernovaPassCategory;
import ca.ulaval.glo4002.booking.entities.passes.options.PackagePassOption;
import ca.ulaval.glo4002.booking.entities.passes.options.PassOption;
import ca.ulaval.glo4002.booking.entities.passes.options.SinglePassPassOption;

import java.util.HashMap;

public class PassFactory {
    // TODO : How do we pass shuttle and oxygen factories?
    private ShuttleFactory shuttleFactory = new ShuttleFactory();
    private OxygenFactory oxygenFactory = new OxygenFactory();

    public PassCategory getPassCategoryById(Long categoryId) {
        // TODO : I wanted to do a switch-case, but apparently static final isn't const?
        if (categoryId.equals(PassConstants.Categories.SUPERNOVA_ID)) {
            return buildPassCategorySupernova();
        } else if (categoryId.equals(PassConstants.Categories.SUPERGIANT_ID)) {
            return buildPassCategorySupergiant();
        } else if (categoryId.equals(PassConstants.Categories.NEBULA_ID)) {
            return buildPassCategoryNebula();
        }

        return null; // TODO : Throw exception?
    }

    public PassOption getPassOptionById(Long optionId) {
        // TODO : I wanted to do a switch-case, but apparently static final isn't const?
        if (optionId.equals(PassConstants.Options.PACKAGE_ID)) {
            return buildPassOptionPackage();
        } else if (optionId.equals(PassConstants.Options.SINGLE_PASS_ID)) {
            return buildPassOptionSinglePass();
        }

        return null; // TODO : Throw exception?
    }

    private PassCategory buildPassCategorySupernova() {
        HashMap<PassOption, Double> pricePerOption = new HashMap<>();
        pricePerOption.put(getPassOptionById(PassConstants.Options.PACKAGE_ID), PassConstants.Categories.SUPERNOVA_PACKAGE_PRICE);
        pricePerOption.put(getPassOptionById(PassConstants.Options.SINGLE_PASS_ID), PassConstants.Categories.SUPERNOVA_SINGLE_PASS_PRICE);

        return new SupernovaPassCategory(
                pricePerOption,
                shuttleFactory.getShuttleCategoryById(PassConstants.Categories.SUPERNOVA_SHUTTLE_CATEGORY_ID),
                oxygenFactory.getOxygenCategoryById(PassConstants.Categories.SUPERNOVA_OXYGEN_CATEGORY_ID)
        );
    }

    private PassCategory buildPassCategorySupergiant() {
        HashMap<PassOption, Double> pricePerOption = new HashMap<>();
        pricePerOption.put(getPassOptionById(PassConstants.Options.PACKAGE_ID), PassConstants.Categories.SUPERGIANT_PACKAGE_PRICE);
        pricePerOption.put(getPassOptionById(PassConstants.Options.SINGLE_PASS_ID), PassConstants.Categories.SUPERGIANT_SINGLE_PASS_PRICE);

        return new SupergiantPassCategory(
                pricePerOption,
                shuttleFactory.getShuttleCategoryById(PassConstants.Categories.SUPERGIANT_SHUTTLE_CATEGORY_ID),
                oxygenFactory.getOxygenCategoryById(PassConstants.Categories.SUPERGIANT_OXYGEN_CATEGORY_ID)
        );
    }

    private PassCategory buildPassCategoryNebula() {
        HashMap<PassOption, Double> pricePerOption = new HashMap<>();
        pricePerOption.put(getPassOptionById(PassConstants.Options.PACKAGE_ID), PassConstants.Categories.NEBULA_PACKAGE_PRICE);
        pricePerOption.put(getPassOptionById(PassConstants.Options.SINGLE_PASS_ID), PassConstants.Categories.NEBULA_SINGLE_PASS_PRICE);

        return new NebulaPassCategory(
                pricePerOption,
                shuttleFactory.getShuttleCategoryById(PassConstants.Categories.NEBULA_SHUTTLE_CATEGORY_ID),
                oxygenFactory.getOxygenCategoryById(PassConstants.Categories.NEBULA_OXYGEN_CATEGORY_ID)
        );
    }

    private PassOption buildPassOptionPackage() {
        return new PackagePassOption();
    }

    private PassOption buildPassOptionSinglePass() {
        return new SinglePassPassOption();
    }
}

package ca.ulaval.glo4002.booking.builders.passes;

import ca.ulaval.glo4002.booking.constants.ExceptionConstants;
import ca.ulaval.glo4002.booking.constants.PassConstants;
import ca.ulaval.glo4002.booking.domainobjects.passes.categories.PassCategory;
import ca.ulaval.glo4002.booking.domainobjects.passes.options.PassOption;
import ca.ulaval.glo4002.booking.exceptions.passes.PassCategoryNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PassCategoryBuilderTest {

    private PassCategoryBuilder subject;
    private PassOptionBuilder optionBuilder;
    private static final Long AN_INVALID_ID = -1L;
    private static final String AN_INVALID_NAME = "An invalid code";

    @BeforeEach
    public void setUp() {
        subject = new PassCategoryBuilder();
        optionBuilder = new PassOptionBuilder();
    }

    @Test
    public void buildById_shouldThrowPassCategoryNotFoundException_whenCategoryDoesNotExist() {
        PassCategoryNotFoundException thrown = assertThrows(
                PassCategoryNotFoundException.class,
                () -> subject.buildById(AN_INVALID_ID)
        );

        assertEquals(ExceptionConstants.Pass.CATEGORY_NOT_FOUND_MESSAGE, thrown.getMessage());
    }

    @Test
    public void buildById_shouldReturnCorrectPassCategory_whenCategoryIsSupernova() {
        PassCategory passCategory = subject.buildById(PassConstants.Categories.SUPERNOVA_ID);
        Map<PassOption, Double> pricePerOption = new HashMap<>();
        pricePerOption.put(optionBuilder.buildById(PassConstants.Options.PACKAGE_ID), PassConstants.Categories.SUPERNOVA_PACKAGE_PRICE);
        pricePerOption.put(optionBuilder.buildById(PassConstants.Options.SINGLE_ID), PassConstants.Categories.SUPERNOVA_SINGLE_PASS_PRICE);

        validatePassCategory(
                passCategory,
                PassConstants.Categories.SUPERNOVA_ID,
                PassConstants.Categories.SUPERNOVA_NAME,
                pricePerOption
        );
    }

    @Test
    public void buildById_shouldReturnCorrectPassCategory_whenCategoryIsSupergiant() {
        PassCategory passCategory = subject.buildById(PassConstants.Categories.SUPERGIANT_ID);
        Map<PassOption, Double> pricePerOption = new HashMap<>();
        pricePerOption.put(optionBuilder.buildById(PassConstants.Options.PACKAGE_ID), PassConstants.Categories.SUPERGIANT_PACKAGE_PRICE);
        pricePerOption.put(optionBuilder.buildById(PassConstants.Options.SINGLE_ID), PassConstants.Categories.SUPERGIANT_SINGLE_PASS_PRICE);

        validatePassCategory(
                passCategory,
                PassConstants.Categories.SUPERGIANT_ID,
                PassConstants.Categories.SUPERGIANT_NAME,
                pricePerOption
        );
    }

    @Test
    public void buildById_shouldReturnCorrectPassCategory_whenCategoryIsNebula() {
        PassCategory passCategory = subject.buildById(PassConstants.Categories.NEBULA_ID);
        Map<PassOption, Double> pricePerOption = new HashMap<>();
        pricePerOption.put(optionBuilder.buildById(PassConstants.Options.PACKAGE_ID), PassConstants.Categories.NEBULA_PACKAGE_PRICE);
        pricePerOption.put(optionBuilder.buildById(PassConstants.Options.SINGLE_ID), PassConstants.Categories.NEBULA_SINGLE_PASS_PRICE);

        validatePassCategory(
                passCategory,
                PassConstants.Categories.NEBULA_ID,
                PassConstants.Categories.NEBULA_NAME,
                pricePerOption
        );
    }

    @Test
    public void buildByName_shouldThrowPassCategoryNotFoundException_whenCategoryDoesNotExist() {
        PassCategoryNotFoundException thrown = assertThrows(
                PassCategoryNotFoundException.class,
                () -> subject.buildByName(AN_INVALID_NAME)
        );

        assertEquals(ExceptionConstants.Pass.CATEGORY_NOT_FOUND_MESSAGE, thrown.getMessage());
    }

    @Test
    public void buildByName_shouldReturnCorrectPassCategory_whenCategoryIsSupernova() {
        PassCategory passCategory = subject.buildByName(PassConstants.Categories.SUPERNOVA_NAME);
        Map<PassOption, Double> pricePerOption = new HashMap<>();
        pricePerOption.put(optionBuilder.buildById(PassConstants.Options.PACKAGE_ID), PassConstants.Categories.SUPERNOVA_PACKAGE_PRICE);
        pricePerOption.put(optionBuilder.buildById(PassConstants.Options.SINGLE_ID), PassConstants.Categories.SUPERNOVA_SINGLE_PASS_PRICE);

        validatePassCategory(
                passCategory,
                PassConstants.Categories.SUPERNOVA_ID,
                PassConstants.Categories.SUPERNOVA_NAME,
                pricePerOption
        );
    }

    @Test
    public void buildByName_shouldReturnCorrectPassCategory_whenCategoryIsSupergiant() {
        PassCategory passCategory = subject.buildByName(PassConstants.Categories.SUPERGIANT_NAME);
        Map<PassOption, Double> pricePerOption = new HashMap<>();
        pricePerOption.put(optionBuilder.buildById(PassConstants.Options.PACKAGE_ID), PassConstants.Categories.SUPERGIANT_PACKAGE_PRICE);
        pricePerOption.put(optionBuilder.buildById(PassConstants.Options.SINGLE_ID), PassConstants.Categories.SUPERGIANT_SINGLE_PASS_PRICE);

        validatePassCategory(
                passCategory,
                PassConstants.Categories.SUPERGIANT_ID,
                PassConstants.Categories.SUPERGIANT_NAME,
                pricePerOption
        );
    }

    @Test
    public void buildByName_shouldReturnCorrectPassCategory_whenCategoryIsNebula() {
        PassCategory passCategory = subject.buildByName(PassConstants.Categories.NEBULA_NAME);
        Map<PassOption, Double> pricePerOption = new HashMap<>();
        pricePerOption.put(optionBuilder.buildById(PassConstants.Options.PACKAGE_ID), PassConstants.Categories.NEBULA_PACKAGE_PRICE);
        pricePerOption.put(optionBuilder.buildById(PassConstants.Options.SINGLE_ID), PassConstants.Categories.NEBULA_SINGLE_PASS_PRICE);

        validatePassCategory(
                passCategory,
                PassConstants.Categories.NEBULA_ID,
                PassConstants.Categories.NEBULA_NAME,
                pricePerOption
        );
    }

    private void validatePassCategory(PassCategory passCategory, Long id, String name, Map<PassOption, Double> pricePerOption) {
        assertNotNull(passCategory);
        assertEquals(passCategory.getId(), id);
        assertEquals(passCategory.getName(), name);
        // TODO : Find a correct way to assert what is in pricePerOption
        // assertTrue(Maps.difference(passCategory.getPricePerOption(), pricePerOption).areEqual());
    }
}

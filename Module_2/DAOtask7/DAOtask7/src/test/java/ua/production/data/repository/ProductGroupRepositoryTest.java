package ua.production.data.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.production.data.dao.impl.ParameterDao;
import ua.production.data.dao.impl.ParameterGroupDao;
import ua.production.data.dao.impl.ProductDao;
import ua.production.data.dao.impl.ProductGroupDao;
import ua.production.data.entity.Parameter;
import ua.production.data.entity.ParameterGroup;
import ua.production.data.entity.Product;
import ua.production.data.entity.ProductGroup;
import ua.production.exception.DaoException;
import ua.production.util.FillDB;
import ua.production.util.SqlParser;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductGroupRepositoryTest {
    private Logger logger = LoggerFactory.getLogger(ProductGroupRepositoryTest.class);

    ProductDao productDao;
    ParameterDao parameterDao;
    ProductGroupDao productGroupDao;
    ParameterGroupDao parameterGroupDao;

    ProductGroupRepository productGroupRepository;

    @BeforeEach
    void setUp() {
        String scriptFilePath = "./src/main/resources/tables.sql";
        SqlParser.executeScriptUsingStatement(scriptFilePath);

        productDao = new ProductDao();
        parameterDao = new ParameterDao();
        productGroupDao = new ProductGroupDao();
        parameterGroupDao = new ParameterGroupDao();

        FillDB fillDB = new FillDB(productDao, parameterDao, productGroupDao, parameterGroupDao);
        fillDB.fillTables();

        productGroupRepository = new ProductGroupRepository(productGroupDao, parameterGroupDao,
                productDao, parameterDao);
    }

    @Test
    void getParametersForProductGroup() {
        List<ParameterGroup> expected = new ArrayList<>();
        expected.add(new ParameterGroup(1, "dimensions", 1));
        expected.add(new ParameterGroup(2, "display", 1));

        List<ParameterGroup> actual = new ArrayList<>();
        try {
            ProductGroup productGroup = productGroupDao.get(1).get();
            actual.addAll(productGroupRepository
                    .getParametersForProductGroup(productGroup));
        } catch (DaoException e) {
            logger.error("ProductGroupRepositoryTest", e);
        }

        assertIterableEquals(expected, actual);
    }

    @Test
    void getProductionThatNotContainsParameter() {
        List<Product> expected = new ArrayList<>();
        expected.add(new Product(2, "Mi A2", "Second cellphone", LocalDate.of(2018, 7, 1), 1));
        expected.add(new Product(3, "Mi A2 lite", "Second cellphone lite version", LocalDate.of(2018, 7, 18), 1));
        expected.add(new Product(4, "Apple iPad Air", "First tablet", LocalDate.of(2019, 3, 20), 2));
        expected.add(new Product(5, "Samsung Galaxy Tab S5e", "Second tablet", LocalDate.of(2019, 4, 11), 2));
        expected.add(new Product(6, "Xiaomi Mi Pad 4", "Third tablet", LocalDate.of(2018, 9, 16), 2));
        expected.add(new Product(7, "Mi TV UHD 4S", "Xiaomi smart tv", LocalDate.of(2019, 10, 15), 3));
        expected.add(new Product(8, "LG OLED55C9", "LG smart tv", LocalDate.of(2019, 7, 21), 3));
        expected.add(new Product(9, "LG 43UM7100", "Second LG smart tv", LocalDate.of(2019, 7, 31), 3));

        List<Product> actual = new ArrayList<>();
        try {
            Parameter parameterNotToShow = parameterDao.get(1).get();
            actual = productGroupRepository.getProductionThatNotContainsParameter(parameterNotToShow);
        } catch (DaoException e) {
            logger.error("ProductGroupRepositoryTest", e);
        }

        assertIterableEquals(expected, actual);
    }

    @Test
    void getProductionFromProductionGroup() {
        List<Product> expected = new ArrayList<>();
        expected.add(new Product(1, "Mi A1", "First cellphone", LocalDate.of(2017, 11, 7), 1));
        expected.add(new Product(2, "Mi A2", "Second cellphone", LocalDate.of(2018, 7, 1), 1));
        expected.add(new Product(3, "Mi A2 lite", "Second cellphone lite version", LocalDate.of(2018, 7, 18), 1));

        List<Product> actual = new ArrayList<>();
        try {
            ProductGroup requiredGroup = productGroupDao.get(1).get();
            actual = productGroupRepository
                    .getProductionFromProductionGroup(requiredGroup);
        } catch (DaoException e) {
            logger.error("ProductGroupRepositoryTest", e);
        }
        assertIterableEquals(expected, actual);
    }

    @Test
    void getProduction() {
        List<Product> expected = new ArrayList<>();
        expected.add(new Product(1, "Mi A1", "First cellphone", LocalDate.of(2017, 11, 7), 1));
        expected.add(new Product(2, "Mi A2", "Second cellphone", LocalDate.of(2018, 7, 1), 1));
        expected.add(new Product(3, "Mi A2 lite", "Second cellphone lite version", LocalDate.of(2018, 7, 18), 1));
        expected.add(new Product(4, "Apple iPad Air", "First tablet", LocalDate.of(2019, 3, 20), 2));
        expected.add(new Product(5, "Samsung Galaxy Tab S5e", "Second tablet", LocalDate.of(2019, 4, 11), 2));
        expected.add(new Product(6, "Xiaomi Mi Pad 4", "Third tablet", LocalDate.of(2018, 9, 16), 2));
        expected.add(new Product(7, "Mi TV UHD 4S", "Xiaomi smart tv", LocalDate.of(2019, 10, 15), 3));
        expected.add(new Product(8, "LG OLED55C9", "LG smart tv", LocalDate.of(2019, 7, 21), 3));
        expected.add(new Product(9, "LG 43UM7100", "Second LG smart tv", LocalDate.of(2019, 7, 31), 3));

        List<Product> actual = productGroupRepository.getProduction();

        assertIterableEquals(expected, actual);
    }

    @Test
    void deleteProductionFromDBThatContainsParameters() {
        List<Product> expected = new ArrayList<>();
        expected.add(new Product(4, "Apple iPad Air", "First tablet", LocalDate.of(2019, 3, 20), 2));
        expected.add(new Product(5, "Samsung Galaxy Tab S5e", "Second tablet", LocalDate.of(2019, 4, 11), 2));
        expected.add(new Product(6, "Xiaomi Mi Pad 4", "Third tablet", LocalDate.of(2018, 9, 16), 2));
        expected.add(new Product(7, "Mi TV UHD 4S", "Xiaomi smart tv", LocalDate.of(2019, 10, 15), 3));
        expected.add(new Product(8, "LG OLED55C9", "LG smart tv", LocalDate.of(2019, 7, 21), 3));
        expected.add(new Product(9, "LG 43UM7100", "Second LG smart tv", LocalDate.of(2019, 7, 31), 3));

        List<Parameter> parametersForDelete = new ArrayList<>();
        boolean actual = false;
        try {
            for (int i = 1; i <= 15; i++) {
                if (parameterDao.get(i).isPresent()) {
                    parametersForDelete.add(parameterDao.get(i).get());
                }
            }
            actual = productGroupRepository.deleteProductionFromDBThatContainsParameters(parametersForDelete);
        } catch (DaoException e) {
            logger.error("ProductGroupRepositoryTest", e);
        }
        assertTrue(actual);
    }

    @Test
    void moveParametersGroup() {
        boolean actual = false;
        try {
            ProductGroup productGroupToReplace = productGroupDao.get(1).get();
            ProductGroup productGroupFromReplace = productGroupDao.get(2).get();
            actual = productGroupRepository.moveParametersGroup(productGroupToReplace, productGroupFromReplace);
            System.out.println(actual);
        } catch (DaoException e) {
            logger.error("ProductGroupRepositoryTest", e);
        }
        assertTrue(actual);
    }
}
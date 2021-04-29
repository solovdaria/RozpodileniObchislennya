package ua.production.util;

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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FillDB {
    private Logger logger = LoggerFactory.getLogger(FillDB.class);

    private ProductDao productDao;
    private ParameterDao parameterDao;
    private ProductGroupDao productGroupDao;
    private ParameterGroupDao parameterGroupDao;

    public FillDB(ProductDao productDao, ParameterDao parameterDao,
                  ProductGroupDao productGroupDao, ParameterGroupDao parameterGroupDao) {
        this.productDao = productDao;
        this.parameterDao = parameterDao;
        this.productGroupDao = productGroupDao;
        this.parameterGroupDao = parameterGroupDao;
    }

    public void fillTables() {
        saveProductGroups();
        saveProducts();
        saveParameterGroups();
        saveParameters();
    }

    public void saveProductGroups() {
        try {
            productGroupDao.save(new ProductGroup("cellphone"));
            productGroupDao.save(new ProductGroup("tablet"));
            productGroupDao.save(new ProductGroup("tv set"));
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public void saveProducts() {
        // cellphones
        Product cellphone1 = new Product("Mi A1", "First cellphone",
                LocalDate.of(2017, 11, 7), 1);
        Product cellphone2 = new Product("Mi A2", "Second cellphone",
                LocalDate.of(2018, 7, 1), 1);
        Product cellphone3 = new Product("Mi A2 lite", "Second cellphone lite version",
                LocalDate.of(2018, 7, 18), 1);

        // tablets
        Product tablet1 = new Product("Apple iPad Air", "First tablet",
                LocalDate.of(2019, 3, 20), 2);
        Product tablet2 = new Product("Samsung Galaxy Tab S5e", "Second tablet",
                LocalDate.of(2019, 4, 11), 2);
        Product tablet3 = new Product("Xiaomi Mi Pad 4", "Third tablet",
                LocalDate.of(2018, 9, 16), 2);

        // tv sets
        Product tv1 = new Product("Mi TV UHD 4S", "Xiaomi smart tv",
                LocalDate.of(2019, 10, 15), 3);
        Product tv2 = new Product("LG OLED55C9", "LG smart tv",
                LocalDate.of(2019, 7, 21), 3);
        Product tv3 = new Product("LG 43UM7100", "Second LG smart tv",
                LocalDate.of(2019, 7, 31), 3);

        List<Product> products = List.of(cellphone1, cellphone2, cellphone3,
                tablet1, tablet2, tablet3,
                tv1, tv2, tv3);

        for (Product product : products) {
            try {
                productDao.save(product);
            } catch (DaoException e) {
                logger.error("Error when save product ", e);
            }
        }

    }

    public void saveParameterGroups() {
        ParameterGroup dimensionsFor1 = new ParameterGroup("dimensions", 1);
        ParameterGroup displayFor1 = new ParameterGroup("display", 1);

        ParameterGroup dimensionsFor2 = new ParameterGroup("dimensions", 2);
        ParameterGroup displayFor2 = new ParameterGroup("display", 2);

        ParameterGroup dimensionsFor3 = new ParameterGroup("dimensions", 3);
        ParameterGroup displayFor3 = new ParameterGroup("display", 3);

        List<ParameterGroup> parameterGroups = List.of(dimensionsFor1, displayFor1,
                dimensionsFor2, displayFor2,
                dimensionsFor3, displayFor3);

        for (ParameterGroup parameterGroup : parameterGroups) {
            try {
                parameterGroupDao.save(parameterGroup);
            } catch (DaoException e) {
                logger.error("Error when save parameterGroup ", e);
            }
        }

    }

    public void saveParameters() {
        List<Parameter> cellphones = getCellphonesParameters();
        List<Parameter> tablets = getTabletsParameters();
        List<Parameter> tvSets = getTvSetsParameters();

        List<Parameter> parameters = new ArrayList<>();
        parameters.addAll(cellphones);
        parameters.addAll(tablets);
        parameters.addAll(tvSets);

        for (Parameter parameter : parameters) {
            try {
                parameterDao.save(parameter);
            } catch (DaoException e) {
                logger.error("Error when save parameter ", e);
            }
        }
    }

    private List<Parameter> getCellphonesParameters() {
        // cellphone1 parameters
        Parameter cellphone1Length = new Parameter
                ("length", "155.4", "mm", 1, 1);
        Parameter cellphone1Width = new Parameter
                ("width", "75.8", "mm", 1, 1);
        Parameter cellphone1thickness = new Parameter
                ("thickness", "7.3", "mm", 1, 1);
        //display
        Parameter cellphone1Diagonal = new Parameter
                ("diagonal", "5.5", "inch", 1, 2);
        Parameter cellphone1Resolution = new Parameter
                ("resolution", "1920x1080", null, 1, 2);
        Parameter cellphone1Matrix = new Parameter
                ("matrix", "IPS", null, 1, 2);


        // cellphone2 parameters
        Parameter cellphone2Length = new Parameter
                ("length", "158.7", "mm", 2, 1);
        Parameter cellphone2Width = new Parameter
                ("width", "75.4", "mm", 2, 1);
        Parameter cellphone2thickness = new Parameter
                ("thickness", "7.3", "mm", 2, 1);
        //display
        Parameter cellphone2Diagonal = new Parameter
                ("diagonal", "5.99", "inch", 2, 2);
        Parameter cellphone2Resolution = new Parameter
                ("resolution", "2160x1080", null, 2, 2);
        Parameter cellphone2Matrix = new Parameter
                ("matrix", "IPS", null, 2, 2);


        // cellphone3 parameters
        //dimensions
        Parameter cellphone3Length = new Parameter
                ("length", "149.3", "mm", 3, 1);
        Parameter cellphone3Width = new Parameter
                ("width", "71.74", "mm", 3, 1);
        Parameter cellphone3thickness = new Parameter
                ("thickness", "8.8", "mm", 3, 1);
        //display
        Parameter cellphone3Diagonal = new Parameter
                ("diagonal", "5.84", "inch", 3, 2);
        Parameter cellphone3Resolution = new Parameter
                ("resolution", "2280x1080", null, 3, 2);
        Parameter cellphone3Matrix = new Parameter
                ("matrix", "IPS", null, 3, 2);

        return List.of(cellphone1Length, cellphone1Width, cellphone1thickness,
                cellphone1Diagonal, cellphone1Resolution, cellphone1Matrix,
                cellphone2Length, cellphone2Width, cellphone2thickness,
                cellphone2Diagonal, cellphone2Resolution, cellphone2Matrix,
                cellphone3Length, cellphone3Width, cellphone3thickness,
                cellphone3Diagonal, cellphone3Resolution, cellphone3Matrix);
    }

    private List<Parameter> getTabletsParameters() {
        // tablet1 parameters
        Parameter tablet1Length = new Parameter
                ("length", "250.6", "mm", 4, 3);
        Parameter tablet1Width = new Parameter
                ("width", "174.1", "mm", 4, 3);
        Parameter tablet1thickness = new Parameter
                ("thickness", "6.1", "mm", 4, 3);
        //display
        Parameter tablet1Diagonal = new Parameter
                ("diagonal", "10.5", "inch", 4, 4);
        Parameter tablet1Resolution = new Parameter
                ("resolution", "2224x1668", null, 4, 4);
        Parameter tablet1Matrix = new Parameter
                ("matrix", "IPS", null, 4, 4);


        // tablet2 parameters
        Parameter tablet2Length = new Parameter
                ("length", "245.0", "mm", 5, 3);
        Parameter tablet2Width = new Parameter
                ("width", "160.2", "mm", 5, 3);
        Parameter tablet2thickness = new Parameter
                ("thickness", "5.5", "mm", 5, 3);
        //display
        Parameter tablet2Diagonal = new Parameter
                ("diagonal", "10.5", "inch", 5, 4);
        Parameter tablet2Resolution = new Parameter
                ("resolution", "2560x1600", null, 5, 4);
        Parameter tablet2Matrix = new Parameter
                ("matrix", "Super AMOLED", null, 5, 4);


        // tablet3 parameters
        //dimensions
        Parameter tablet3Length = new Parameter
                ("length", "200.2", "mm", 6, 3);
        Parameter tablet3Width = new Parameter
                ("width", "120.3", "mm", 6, 3);
        Parameter tablet3thickness = new Parameter
                ("thickness", "7.9", "mm", 6, 3);
        //display
        Parameter tablet3Diagonal = new Parameter
                ("diagonal", "8", "inch", 6, 4);
        Parameter tablet3Resolution = new Parameter
                ("resolution", "1920x1200", null, 6, 4);
        Parameter tablet3Matrix = new Parameter
                ("matrix", "IPS", null, 6, 4);

        return List.of(tablet1Length, tablet1Width, tablet1thickness,
                tablet1Diagonal, tablet1Resolution, tablet1Matrix,
                tablet2Length, tablet2Width, tablet2thickness,
                tablet2Diagonal, tablet2Resolution, tablet2Matrix,
                tablet3Length, tablet3Width, tablet3thickness,
                tablet3Diagonal, tablet3Resolution, tablet3Matrix);
    }

    private List<Parameter> getTvSetsParameters() {
        // tv1 parameters
        Parameter tv1Length = new Parameter
                ("length", "975", "mm", 7, 5);
        Parameter tv1Width = new Parameter
                ("width", "627", "mm", 7, 5);
        Parameter tv1thickness = new Parameter
                ("thickness", "214", "mm", 7, 5);
        //display
        Parameter tv1Diagonal = new Parameter
                ("diagonal", "43", "inch", 7, 6);
        Parameter tv1Resolution = new Parameter
                ("resolution", "3840x2160", null, 7, 6);
        Parameter tv1Matrix = new Parameter
                ("matrix", "IPS", null, 7, 6);


        // tv2 parameters
        Parameter tv2Length = new Parameter
                ("length", "1228", "mm", 8, 5);
        Parameter tv2Width = new Parameter
                ("width", "810", "mm", 8, 5);
        Parameter tv2thickness = new Parameter
                ("thickness", "207", "mm", 8, 5);
        //display
        Parameter tv2Diagonal = new Parameter
                ("diagonal", "55", "inch", 8, 6);
        Parameter tv2Resolution = new Parameter
                ("resolution", "3840x2160", null, 8, 6);
        Parameter tv2Matrix = new Parameter
                ("matrix", "OLED", null, 8, 6);


        // tv3 parameters
        //dimensions
        Parameter tv3Length = new Parameter
                ("length", "975", "mm", 9, 5);
        Parameter tv3Width = new Parameter
                ("width", "615", "mm", 9, 5);
        Parameter tv3thickness = new Parameter
                ("thickness", "188", "mm", 9, 5);
        //display
        Parameter tv3Diagonal = new Parameter
                ("diagonal", "43", "inch", 9, 6);
        Parameter tv3Resolution = new Parameter
                ("resolution", "3840x2160", null, 9, 6);
        Parameter tv3Matrix = new Parameter
                ("matrix", "LED IPS", null, 9, 6);

        return List.of(tv1Length, tv1Width, tv1thickness,
                tv1Diagonal,     tv1Resolution,  tv1Matrix,
                tv2Length,       tv2Width,       tv2thickness,
                tv2Diagonal,     tv2Resolution,  tv2Matrix,
                tv3Length,       tv3Width,       tv3thickness,
                tv3Diagonal,     tv3Resolution,  tv3Matrix);
    }
}

package ua.production.data.repository;

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

import java.util.ArrayList;
import java.util.List;

public class ProductGroupRepository {
    private Logger logger = LoggerFactory.getLogger(ProductGroupRepository.class);

    private ProductGroupDao productGroupDao;
    private ParameterGroupDao parameterGroupDao;
    private ProductDao productDao;
    private ParameterDao parameterDao;

    public ProductGroupRepository(ProductGroupDao productGroupDao, ParameterGroupDao parameterGroupDao,
                                  ProductDao productDao, ParameterDao parameterDao) {
        this.productGroupDao = productGroupDao;
        this.parameterGroupDao = parameterGroupDao;
        this.productDao = productDao;
        this.parameterDao = parameterDao;
    }

    //1 - Вывести перечень параметров для заданной группы продукции
    public List<ParameterGroup> getParametersForProductGroup(ProductGroup productGroup) {
        List<ParameterGroup> parameterGroups = new ArrayList<>();
        try {
            for (ProductGroup group : productGroupDao.getAll()) {
                for (ParameterGroup parameterGroup : parameterGroupDao.getAll()) {
                    if (group.getId() == productGroup.getId() &&
                            parameterGroup.getProductGroupId() == group.getId()) {
                        parameterGroups.add(parameterGroup);
                    }
                }
            }

        } catch (DaoException e) {
            logger.error("getParametersForProductGroup exception ", e);
        }
        return parameterGroups;
    }

    //2 - Вывести перечень продукции, не содержащий заданного параметра
    public List<Product> getProductionThatNotContainsParameter(Parameter parameter) {
        List<Product> result = new ArrayList<>();
        List<Product> products = getProduction();

        for (Product product : products) {
            List<Parameter> parameters = product.getParameters();
            for (Parameter param : parameters) {
                if (param.getProductId() != parameter.getProductId() &&
                        !result.contains(product)) {
                    result.add(product);
                }
            }
        }

        return result;
    }

    //3 - Вывести информацию о продукции для заданной группы продукции
    public List<Product> getProductionFromProductionGroup(ProductGroup productGroup) {
        List<Product> result = new ArrayList<>();
        List<Product> products = getProduction();
        for (Product product : products) {
            if (product.getProductGroupId() == productGroup.getId() &&
            !result.contains(product)) {
                result.add(product);
            }
        }
        return result;
    }

    //4 - Вывести информацию о продукции и всех ее параметрах со значениями
    public List<Product> getProduction() {
        List<Product> products = new ArrayList<>();
        try {
            List<Product> allProducts = new ArrayList<>(productDao.getAll());
            List<Parameter> allParameters = new ArrayList<>(parameterDao.getAll());

            for (Product product : allProducts) {
                for (Parameter parameter : allParameters) {
                    if (parameter.getProductId() == product.getId()) {
                        product.addParameter(parameter);
                    }
                }
            }

            return allProducts;
        } catch (DaoException e) {
            logger.error("get production ", e);
        }
        return products;
    }

    //5 - Удалить из базы продукцию, содержащую заданные параметры
    public boolean deleteProductionFromDBThatContainsParameters(List<Parameter> parameters) {
        List<Boolean> result = new ArrayList<>();
        for (Parameter parameter : parameters) {
            try {
                int productId = parameter.getProductId();
                if (productDao.get(productId).isPresent()) {
                    result.add(productDao.delete(productDao.get(productId).get()));
                }
            } catch (DaoException e) {
                logger.error("delete production with this parameter ", e);
            }
        }
        return !result.contains(false);
    }

    //6 - Переместить группу параметров из одной группы товаров в другую
    public boolean moveParametersGroup(ProductGroup fromProductGroup, ProductGroup toProductGroup) {
        try {
            toProductGroup.setParameterGroups(fromProductGroup.getParameterGroups());
            productGroupDao.update(toProductGroup);
            return true;
        } catch (DaoException e) {
            logger.error("moveParametersGroup ", e);
        }
        return false;
    }
}

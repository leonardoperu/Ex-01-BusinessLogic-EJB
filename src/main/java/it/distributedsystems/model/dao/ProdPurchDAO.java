package it.distributedsystems.model.dao;

import java.util.List;

public interface ProdPurchDAO {

    public int insertProdPurchDAO(ProdPurchDAO ProdPurchDAO);

    public int removeProdPurchDAOByName(String name);

    public int removeProdPurchDAOById(int id);

    public ProdPurchDAO findProdPurchDAOByName(String name);

    public ProdPurchDAO findProdPurchDAOById(int id);

    public List<ProdPurchDAO> getAllProdPurchDAOs();
}

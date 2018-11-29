package a1819.m2ihm.sortirametz.bdd.dao;

import a1819.m2ihm.sortirametz.models.Place;
import a1819.m2ihm.sortirametz.models.Recyclerable;

import java.util.List;

public interface PlaceDAO extends DAO<Place> {
    List<Recyclerable> findAllGroupByCategory();
}

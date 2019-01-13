package a1819.m2ihm.sortirametz.helpers

import a1819.m2ihm.sortirametz.bdd.factory.AbstractDAOFactory

enum class ValueHelper {
    INSTANCE;

    val tag = "VisiteAMetz"
    val factoryType: AbstractDAOFactory.FactoryType = AbstractDAOFactory.FactoryType.SQLite

}
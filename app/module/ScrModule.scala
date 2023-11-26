package module

import models.dao.repositories.{InMemoryProductRepositoryImpl, PhoneRecordRepository, PhoneRecordRepositoryImpl, ProductRepository}
import models.{LoginService, LoginServiceImpl, ProductService, ProductServiceImpl}

class ScrModule extends AppModule {
  override def configure(): Unit = {
    bindSingleton[LoginService, LoginServiceImpl]
    bindSingleton[PhoneRecordRepository, PhoneRecordRepositoryImpl]
    bindSingleton[ProductRepository, InMemoryProductRepositoryImpl]
    bindSingleton[ProductService, ProductServiceImpl]
  }
}

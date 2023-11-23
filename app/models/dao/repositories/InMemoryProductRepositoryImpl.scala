package models.dao.repositories

import models.Product
import models.dto.{ProductCreateDTO, ProductDTO}

import scala.collection.mutable.{Map => MutableMap}

class InMemoryProductRepositoryImpl extends ProductRepository {
  private val products: MutableMap[String, Product] = MutableMap.empty

  override def save(productCreateDTO: ProductCreateDTO): Product = {
    val id = java.util.UUID.randomUUID.toString
    val product = Product(id, productCreateDTO.title, productCreateDTO.description)
    products += (id -> product)
    product
  }

  override def get(id: Option[String] = None, title: Option[String] = None): List[Product] = {
    (id, title) match {
      case (Some(idV), Some(titleV)) => products.find(p => (p._1 == idV) && (p._2.title == titleV)).map(_._2).toList
      case (Some(v), None) => products.find(p => p._1 == v).map(_._2).toList
      case (None, Some(v)) => products.find(p => p._2.title == v).map(_._2).toList
      case (None, None) => products.values.toList
    }
  }

  override def delete(id: String): Product = {
    val product = products.find(_._1 == id)
    if (product.isEmpty) {
      throw new ClassNotFoundException
    }
    products -= id
    product.map(_._2).head
  }

  override def update(productDTO: ProductDTO): Product = {
    if (!products.exists(_._1 == productDTO.id)) {
      throw new ClassNotFoundException
    }
    val product = Product(productDTO.id, productDTO.title, productDTO.description)
    products -= productDTO.id
    products += (productDTO.id -> product)
    product
  }
}

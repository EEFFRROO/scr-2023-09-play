package models.dao.repositories

import models.Product
import models.dto.{ProductCreateDTO, ProductDTO}

trait ProductRepository {
  def save(productCreateDTO: ProductCreateDTO): Product
  def get(id: Option[String] = None, title: Option[String] = None): List[Product]
  def delete(id: String): Product
  def update(productDTO: ProductDTO): Product
}

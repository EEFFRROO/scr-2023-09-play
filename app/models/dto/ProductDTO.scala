package models.dto

import models.Product
import play.api.libs.json.{Format, Json}

case class ProductDTO(id: String, title: String, description: String)

object ProductDTO {
  def fromProduct(product: Product): ProductDTO = {
    ProductDTO(
      product.id,
      product.title,
      product.description
    )
  }
}

case class ProductCreateDTO(title: String, description: String)

object Mappings {
  implicit val productDto: Format[ProductDTO] = Json.format[ProductDTO]
  implicit val productCreateDto: Format[ProductCreateDTO] = Json.format[ProductCreateDTO]
}

package models

import models.dao.repositories.ProductRepository
import models.dto.{ProductCreateDTO, ProductDTO}

import javax.inject.Inject

trait ProductService {
  def create(productCreateDTO: ProductCreateDTO): ProductDTO
  def list(id: Option[String] = None, title: Option[String] = None): List[ProductDTO]
  def delete(productId: String): ProductDTO
  def update(productDTO: ProductDTO): ProductDTO
}
class ProductServiceImpl @Inject()(productRepository: ProductRepository) extends ProductService {
  override def create(productCreateDTO: ProductCreateDTO): ProductDTO = {
    ProductDTO.fromProduct(productRepository.save(productCreateDTO))
  }

  override def list(id: Option[String] = None, title: Option[String] = None): List[ProductDTO] = {
    productRepository.get(id, title).map(ProductDTO.fromProduct)
  }

  override def delete(productId: String): ProductDTO = {
    ProductDTO.fromProduct(productRepository.delete(productId))
  }

  override def update(productDTO: ProductDTO): ProductDTO = {
    ProductDTO.fromProduct(productRepository.update(productDTO))
  }
}

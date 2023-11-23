package models.dao.repositories
import models.dto.{ProductCreateDTO, ProductDTO}
import models.Product
import models.dao.schema.ProductSchema
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.Table
import org.squeryl.PrimitiveTypeMode.{update => updateEntity}

class ProductRepositoryImpl extends ProductRepository {
  val products: Table[Product] = ProductSchema.products

  override def save(productCreateDTO: ProductCreateDTO): Product = transaction {
    val id = java.util.UUID.randomUUID.toString
    val product = Product(id, productCreateDTO.title, productCreateDTO.description)
    products.insert(product)
    product
  }

  override def get(id: Option[String], title: Option[String]): List[Product] = transaction {
    (id, title) match {
      case (Some(idV), Some(titleV)) => from(products)(p => where(p.id === idV and p.title === titleV) select p).toList
      case (Some(v), None) => from(products)(p => where(p.id === v) select p).toList
      case (None, Some(v)) => from(products)(p => where(p.title === v) select p).toList
      case (None, None) => from(products)(p => select (p)).toList
    }
  }

  override def delete(id: String): Product = transaction {
    val product = from(products)(p => where(p.id === id) select p).headOption
    if (product.isEmpty) {
      throw new ClassNotFoundException
    }
    products.deleteWhere(_.id === id)
    product.head
  }

  override def update(productDTO: ProductDTO): Product = transaction {
    if (from(products)(p => where(p.id === productDTO.id) select p).headOption.isEmpty) {
      throw new ClassNotFoundException
    }

    updateEntity(products)(p =>
      where(p.id === productDTO.id)
        set(p.title := productDTO.title,
        p.description := productDTO.description)
    )

    from(products)(p => where(p.id === productDTO.id) select p).head
  }
}

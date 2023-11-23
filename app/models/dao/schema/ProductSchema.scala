package models.dao.schema

import models.Product
import org.squeryl.{Schema, Table}

object ProductSchema extends Schema {
  val products: Table[Product] = table[Product]
}

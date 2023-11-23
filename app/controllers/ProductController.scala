package controllers

import com.google.inject.Inject
import models.ProductService
import models.dto.Mappings._
import models.dto.{ProductCreateDTO, ProductDTO}
import play.api.http.Writeable
import play.api.libs.json.Writes
import play.api.mvc.{Action, AnyContent, Controller}

import scala.util.{Failure, Success, Try}

class ProductController @Inject()(productService: ProductService) extends Controller{

  implicit def writeable[T](implicit writes: Writes[T]): Writeable[T] =
    new Writeable[T](Writeable.writeableOf_JsValue.transform compose writes.writes, Some(JSON))

  def list(id: Option[String], title: Option[String]): Action[AnyContent] = Action { rc =>
    Try(productService.list(id, title)) match {
      case Success(value) => Ok(value)
      case Failure(exception) => InternalServerError(exception.getMessage)
    }
  }

  def create(): Action[ProductCreateDTO] = Action(parse.json[ProductCreateDTO]) { rc =>
    val productCreateDTO: ProductCreateDTO = rc.body
    Try(productService.create(productCreateDTO)) match {
      case Success(value) => Ok(value)
      case Failure(exception) => InternalServerError(exception.getMessage)
    }
  }
  def delete(productId: String): Action[AnyContent] = Action { rc =>
      Try(productService.delete(productId)) match {
        case Failure(exception: ClassNotFoundException) => NotFound(exception.getMessage)
        case Success(value) => Ok(value)
        case Failure(exception) => InternalServerError(exception.getMessage)
      }
  }
  def update(): Action[ProductDTO] =  Action(parse.json[ProductDTO]) { rc =>
    val productDTO: ProductDTO = rc.body
    Try(productService.update(productDTO)) match {
      case Failure(exception: ClassNotFoundException) => NotFound(exception.getMessage)
      case Success(value) => Ok(value)
      case Failure(exception) => InternalServerError(exception.getMessage)
    }
  }
}

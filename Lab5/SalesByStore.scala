import scala.io._
import scala.collection._
import scala.collection.immutable.TreeMap

object SalesByStore {

  def main(args: Array[String]): Unit = {
    val basePath = "../Lab1/"

    val storeState = mutable.HashMap[Int, String]()
    for (line <- Source.fromFile(basePath + "stores.txt").getLines()) {
      val parts = line.split(", ")
      storeState += (parts(0).toInt -> parts(5))
    }

    val productPrice = mutable.HashMap[Int, Double]()
    for (line <- Source.fromFile(basePath + "products.txt").getLines()) {
      val parts = line.split(", ")
      productPrice += (parts(0).toInt -> parts(2).toDouble)
    }

    val saleStore = mutable.HashMap[Int, Int]()
    for (line <- Source.fromFile(basePath + "sales.txt").getLines()) {
      val parts = line.split(", ")
      saleStore += (parts(0).toInt -> parts(3).toInt)
    }

    val storeTotal = mutable.HashMap[Int, Double]().withDefaultValue(0.0)
    for (line <- Source.fromFile(basePath + "lineItems.txt").getLines()) {
      val parts     = line.split(", ")
      val saleId    = parts(1).toInt
      val productId = parts(2).toInt
      val qty       = parts(3).toInt
      val storeId   = saleStore(saleId)
      storeTotal(storeId) = storeTotal(storeId) + qty * productPrice(productId)
    }

    var byState = TreeMap.empty[String, TreeMap[Int, Double]]
    for ((storeId, total) <- storeTotal) {
      val state = storeState(storeId)
      val inner = byState.getOrElse(state, TreeMap.empty[Int, Double])
      byState   = byState + (state -> (inner + (storeId -> total)))
    }

    for ((state, stores) <- byState; (storeId, total) <- stores) {
      println(f"$state, $storeId, $total%.2f")
    }
  }
}

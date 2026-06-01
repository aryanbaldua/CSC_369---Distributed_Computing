import org.apache.spark.{SparkConf, SparkContext}
import org.apache.log4j.{Level, Logger}

object SalesByStore {

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)

    val conf = new SparkConf().setAppName("SalesByStore").setMaster("local[4]")
    val sc = new SparkContext(conf)

    val basePath = "../Lab1/"

    val stores = sc.textFile(basePath + "stores.txt").map { line =>
      val parts = line.split(", ")
      (parts(0).toInt, parts(5))
    }

    val products = sc.textFile(basePath + "products.txt").map { line =>
      val parts = line.split(", ")
      (parts(0).toInt, parts(2).toDouble)
    }

    val sales = sc.textFile(basePath + "sales.txt").map { line =>
      val parts = line.split(", ")
      (parts(0).toInt, parts(3).toInt)
    }

    val lineItems = sc.textFile(basePath + "lineItems.txt").map { line =>
      val parts = line.split(", ")
      (parts(1).toInt, (parts(2).toInt, parts(3).toInt))
    }

    val storeTotals = lineItems
      .join(sales)
      .map { case (saleId, ((productId, qty), storeId)) => (productId, (storeId, qty)) }
      .join(products)
      .map { case (productId, ((storeId, qty), price)) => (storeId, qty * price) }
      .reduceByKey(_ + _)

    val result = storeTotals.join(stores)
      .map { case (storeId, (total, state)) => (state, storeId, total) }
      .sortBy(r => (r._1, r._2))

    result.collect().foreach { case (state, storeId, total) =>
      println(f"$state, $storeId, $total%.0f")
    }

    sc.stop()
  }
}
